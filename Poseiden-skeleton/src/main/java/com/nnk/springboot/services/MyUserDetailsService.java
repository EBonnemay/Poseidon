package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.UserPrincipal;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This class implements the UserDetailsService interface. It is used by the Authentication Provider to load details about the user during authentication.
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= userRepository.findUserByUsername(username);

        if (user == null){
            log.info("user not found");
            throw  new UsernameNotFoundException("Username not found"+username);
        }
        log.info("user found");
        return new UserPrincipal(user) ;
    }
}
