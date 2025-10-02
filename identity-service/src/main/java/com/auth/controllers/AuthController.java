package com.auth.controllers;


import com.auth.domains.User;
import com.auth.dtos.AuthRequest;
import com.auth.dtos.AuthResponse;
import com.auth.dtos.SignupRequest;
import com.auth.repos.UserRepository;
import com.auth.security.UserDetails;
import com.auth.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@RefreshScope

public class AuthController {


    @Value("${refresh.test}")
    String test;


    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        System.out.println("************* refresh test **************" + test);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        User user = User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role("ROLE_USER")
                .fullNameEn(req.getFullNameEn())
                .fullNameAr(req.getFullNameAr())
                .email(req.getEmail())
                .telephone(req.getTelephone())
                .address(req.getAddress())
                .build();

        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created: " + saved.getUserId());
    }

    @GetMapping("/validate")
    public boolean validate(@RequestParam("token") String token) {

        return jwtUtil.validateTokenAPI(token);

    }
}