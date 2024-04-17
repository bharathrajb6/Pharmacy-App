package com.example.PharmacyApp.service;import com.example.PharmacyApp.model.AuthenticationResponse;import com.example.PharmacyApp.model.Persistance.Token;import com.example.PharmacyApp.model.Persistance.User;import com.example.PharmacyApp.repository.TokenRepository;import com.example.PharmacyApp.repository.UserRepository;import com.example.PharmacyApp.utils.CommonUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.userdetails.UsernameNotFoundException;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.stereotype.Service;import java.util.List;@Servicepublic class AuthenticationService {    @Autowired    private UserRepository userRepository;    @Autowired    private JwtService jwtService;    @Autowired    private AuthenticationManager authenticationManager;    @Autowired    private PasswordEncoder passwordEncoder;    @Autowired    private TokenRepository tokenRepository;    public AuthenticationResponse register(User request) throws Exception {        User user = new User();        user.setUserID(CommonUtils.generateRandomNumber(3));        user.setFirstName(request.getFirstName());        user.setLastName(request.getLastName());        user.setUsername(request.getUsername());        user.setPassword(passwordEncoder.encode(request.getPassword()));        user.setRole(request.getRole());        user.setEmail(request.getEmail());        user.setContact(request.getContact());        user = userRepository.save(user);        String jwt_token = jwtService.generateToken(user);        saveUserToken(jwt_token, user);        return new AuthenticationResponse(jwt_token);    }    public AuthenticationResponse authenticate(User request) {        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username not found."));        String jwt_token = jwtService.generateToken(user);        revokeAllTokensByUser(user);        saveUserToken(jwt_token, user);        return new AuthenticationResponse(jwt_token);    }    private void revokeAllTokensByUser(User user) {        List<Token> validTokensListByUser = tokenRepository.findAllTokenByUSer(user.getUserID());        if (!validTokensListByUser.isEmpty()) {            validTokensListByUser.forEach(t -> {                t.setIs_logged_out(true);            });        }        tokenRepository.saveAll(validTokensListByUser);    }    private void saveUserToken(String jwt_token, User user) {        Token token = new Token();        token.setToken(jwt_token);        token.setUser(user);        token.setIs_logged_out(false);        tokenRepository.save(token);    }}