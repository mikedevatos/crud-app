package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import io.jsonwebtoken.*;
import javax.servlet.ServletException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class AuthorizationJwtFilter extends OncePerRequestFilter {

    private List<String> skipUrls =
                                    new ArrayList<>(Arrays.asList(
                                                                   "/api/login")  );

    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private UserRepository userRepository;



    @Autowired
    public AuthorizationJwtFilter( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        UsernamePasswordAuthenticationToken authe=null;
        try  {
              authe = getAuthenticationReq(request, response);
             }
          catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
              e.printStackTrace();
//              log.trace("Invalid JWT signature trace: {}", e);
              response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT Exception");
              return;
          } catch (ExpiredJwtException e) {
              e.printStackTrace();
//              log.trace("Expired JWT token trace: {}", e);
              response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT Exception");
              return;
          } catch (Exception e) {
              e.printStackTrace();
//              log.trace("JWT Error Exception: {} ",e);
              response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT Exception");
              return;
        }

        if (authe==null)  {
//                log.debug("authentication is invalid :");
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }
        SecurityContextHolder.getContext().setAuthentication(authe);
//        log.debug("authentication is :"+authe);
        filterChain.doFilter(request, response);
        }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
//        log.debug("url for skipping is " + request.getRequestURI() );
        return skipUrls.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI() ) );
    }


    //get bearer_token from request and validate it
    private UsernamePasswordAuthenticationToken getAuthenticationReq(HttpServletRequest request,
                                                                     HttpServletResponse response) {
        //get header
        String token = request.getHeader(JwtTokenSource.header);

        if (StringUtils.isNotEmpty(token) && token.startsWith(JwtTokenSource._prefix)) {
//            try {

                //get username that corresponds to the token
                String bearerToken = token.substring(7);

          Claims claims = Jwts.parser()
                        .setSigningKey( JwtTokenSource.key.getBytes() )
                        .parseClaimsJws(bearerToken)
                        .getBody();
                String username = claims.getSubject();

                //validate bearer_token
                if (StringUtils.isNotEmpty(username)) {

                    Optional<User> u = userRepository.findByUsername(username);

                    if (u.isPresent()) {

//                        log.debug("username is"+ u.get());
                        User user = u.get();
                        return new UsernamePasswordAuthenticationToken(username,
                                                                       user.getPassword(),
                                                                       user.getAuthorities());
                    }
                    return null;
                }
        }
        return null;
    }

}
