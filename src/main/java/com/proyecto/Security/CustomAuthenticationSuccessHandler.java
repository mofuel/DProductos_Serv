package com.proyecto.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String userEmail = "";

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            userEmail = userDetails.getUsername();
        }

        if ("admin@gmail.com".equalsIgnoreCase(userEmail)) {
            response.sendRedirect("/dashboard");
        } else {
            response.sendRedirect("/index");
        }
    }
}
