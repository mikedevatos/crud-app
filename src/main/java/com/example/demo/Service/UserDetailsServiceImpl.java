package com.example.demo.Service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.Optional;

@Service
@Validated
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepo;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s){
        Optional<User> user = this.userRepo.findByUsername(s);

        if (user.isPresent()){
            return (UserDetails) user.get();
        }
        else{
            throw new UsernameNotFoundException(String.format("Username not found"));
        }

    }

   
}
