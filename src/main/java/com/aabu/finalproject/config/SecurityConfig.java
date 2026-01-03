package com.aabu.finalproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    SecurityFilterChain filterChain (HttpSecurity http  ) throws Exception{
        http.csrf(csrf->csrf.disable())


//                // make session just after log in
//                .sessionManagement(sm->sm.sessionCreationPolicy(
//                        SessionCreationPolicy.IF_REQUIRED
//                ))
//
//
//                .authorizeHttpRequests(auth->
//                        auth.requestMatchers("/api/sellers/createShop").authenticated()
//                        .anyRequest().permitAll());

                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (registration + login)
                        .requestMatchers("/users", "/users/login").permitAll()

                        // Public product search (browse as guest)
                        .requestMatchers("/products/search").permitAll()

                        // Everything else requires authentication; roles are enforced via @PreAuthorize
                        .anyRequest().authenticated());

        return http.build();
    }


    @Bean
     public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean // from UserDetailsService / PasswordEncoder  /DaoAuthenticationProvider
    public AuthenticationManager authenticationManager (AuthenticationConfiguration cfg)throws Exception{
        return cfg.getAuthenticationManager();
    }
}
