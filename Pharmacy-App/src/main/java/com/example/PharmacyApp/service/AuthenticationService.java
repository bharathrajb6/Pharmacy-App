package com.example.PharmacyApp.service;

import com.example.PharmacyApp.model.Responses.AuthenticationResponse;
import com.example.PharmacyApp.model.Persistance.RefreshToken;
import com.example.PharmacyApp.model.Persistance.Token;
import com.example.PharmacyApp.model.Persistance.User;
import com.example.PharmacyApp.model.Responses.RefreshTokenRequestDTO;
import com.example.PharmacyApp.model.Responses.ResponseMessage;
import com.example.PharmacyApp.repository.TokenRepository;
import com.example.PharmacyApp.repository.UserRepository;
import com.example.PharmacyApp.utils.CommonUtils;
import com.example.PharmacyApp.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private RefreshTokenService refreshTokenService;

    public AuthenticationResponse register(User request) throws Exception {
        User user = getUserObject(request);
        ResponseMessage responseMessage = userUtils.validateUserDetails(user);
        if (responseMessage == null) {
            user = userRepository.save(user);
            String jwt_token = jwtService.generateToken(user);
            saveUserToken(jwt_token, user);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
            return new AuthenticationResponse(jwt_token, refreshToken.getToken());
        } else {
            return new AuthenticationResponse(null, null);
        }
    }

    public AuthenticationResponse authenticate(User request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            authentication = null;
        }
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user != null && authentication != null) {
            String jwt_token = jwtService.generateToken(user);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
            revokeAllTokensByUser(user);
            saveUserToken(jwt_token, user);
            return new AuthenticationResponse(jwt_token, refreshToken.getToken());
        } else {
            return new AuthenticationResponse(null, null);
        }
    }

    private void revokeAllTokensByUser(User user) {
        List<Token> validTokensListByUser = tokenRepository.findAllTokenByUSer(user.getUserID());
        if (!validTokensListByUser.isEmpty()) {
            validTokensListByUser.forEach(t -> {
                t.setIs_logged_out(true);
            });
        }
        tokenRepository.deleteAll(validTokensListByUser);
    }

    private void saveUserToken(String jwt_token, User user) {
        Token token = new Token();
        token.setToken(jwt_token);
        token.setUser(user);
        token.setIs_logged_out(false);
        tokenRepository.save(token);
    }

    private User getUserObject(User request) throws Exception {
        User user = new User();
        user.setUserID(CommonUtils.generateRandomNumber(3));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEmail(request.getEmail());
        user.setContact(request.getContact());
        return user;
    }

    public AuthenticationResponse generateJwtFromRefreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken()).map(refreshTokenService::verifyExpiration).
                map(RefreshToken::getUser).map(user -> {
                    revokeAllTokensByUser(user);
                    String accessToken = jwtService.generateToken(user);
                    saveUserToken(accessToken,user);
                    return new AuthenticationResponse(accessToken, refreshTokenRequestDTO.getToken());
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in DB."));
    }

}
