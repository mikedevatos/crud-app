package com.example.demo.security;


import com.example.demo.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsServiceImpl;
    private AuthorizationJwtFilter authorizationJwtFilter;

    @Autowired
    public SecurityConfiguration(UserDetailsServiceImpl userDetailsServiceImpl,
                                 AuthorizationJwtFilter authorizationJwtFilter) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authorizationJwtFilter = authorizationJwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .addFilter(new AuthenticationJwtFilter(authenticationManager()))
                .addFilterBefore(authorizationJwtFilter, AuthenticationJwtFilter.class)

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()

//                .antMatchers(HttpMethod.GET,"/api/userinfo").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                .antMatchers(HttpMethod.GET,"/api/customer/?").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/staff/register").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/forgot").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/customer/?").hasAnyRole("ADMIN,MANAGER")
                .antMatchers(HttpMethod.PUT,"/api/customer").hasAnyRole("ADMIN","MANAGER")
                .antMatchers(HttpMethod.POST,"/api/customer").hasAnyRole("ADMIN","MANAGER")
                .antMatchers().authenticated().anyRequest()
                .access("hasIpAddress('127.0.0.0/8') or hasIpAddress('::1')")
                .and()
                .httpBasic().disable().formLogin().disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .cors();

    }



    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsServiceImpl);

        return daoAuthenticationProvider;
    }

    //Not encoding password only for developent    also change data.sql !!!
            @Bean
            public PasswordEncoder getPasswordEncoder(){
                return NoOpPasswordEncoder.getInstance();
        }


    //  encoding password   also change data.sql
//    @Bean
//    PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }




}







