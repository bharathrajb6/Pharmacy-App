package com.example.PharmacyApp.config;

import com.example.PharmacyApp.model.Persistance.Token;
import com.example.PharmacyApp.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        String jwt_token = authHeader.substring(7);
        Token token = tokenRepository.findByToken(jwt_token).orElse(null);
        if (token != null) {
            token.setIs_logged_out(true);
            tokenRepository.save(token);
        }
    }
}
