package com.aabu.finalproject.utility;

import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Transactional
@Component
public class UserSession {

    public static   String getUserDetailsBySession(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated user found");
        }

        var principle= authentication.getPrincipal();

        if(principle instanceof UserDetails){
            String userPhone= ((UserDetails) principle).getUsername();
            return userPhone;
        }

        // handle "  anonymousUser "
        throw new AuthenticationCredentialsNotFoundException("Invalid principal");

    }

}
