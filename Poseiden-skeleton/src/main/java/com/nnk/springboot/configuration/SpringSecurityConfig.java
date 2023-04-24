package com.nnk.springboot.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * This class handles authentication and authorization for Poseidon App
 *
 * @author Emmanuelle Bonnemay
 * created on 23/04/2023
 */

@Configuration

public class SpringSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * This method configures the password encoder. Here BCrypt is used to stock data in a safe way.
     * @return BCryptPasswordEncoder
     *
     */
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     *
     * This method configures HttpSecurity given in parameter.
     * Poseidon requires to authorize "user" pages and actions (display list, update, add, delete) to admin only.
     * It also requires an authentication to have access to any financial entity and handles the login form to authenticate.
     */
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> {
                try {
                    authorize
                    .requestMatchers("/user/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .and()
                    .formLogin()
                    .and()
                    .oauth2Login()
                    .and()
                    .logout()
                    .logoutUrl("/app-logout");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        );
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)throws Exception{
        return authConfig.getAuthenticationManager();
    }

    /**
     * This method provides authentication for any user wanting to connect.
     * @return daoAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }


}
