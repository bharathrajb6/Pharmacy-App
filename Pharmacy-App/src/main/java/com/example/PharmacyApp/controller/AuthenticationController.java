package com.example.PharmacyApp.controller;import com.example.PharmacyApp.model.AuthenticationResponse;import com.example.PharmacyApp.model.User;import com.example.PharmacyApp.service.AuthenticationService;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.PostMapping;import org.springframework.web.bind.annotation.RequestBody;import org.springframework.web.bind.annotation.RestController;@RestControllerpublic class AuthenticationController {    @Autowired    private AuthenticationService authenticationService;    @PostMapping("/register")    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request) throws Exception {        return ResponseEntity.ok(authenticationService.register(request));    }    @PostMapping("/login")    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody User request) throws Exception {        return ResponseEntity.ok(authenticationService.authenticate(request));    }}