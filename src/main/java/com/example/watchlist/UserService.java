package com.example.watchlist;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public User register(RegisterDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);

    }

    public AuthResponseDto login(LoginDto dto){
        User user = userRepository.findByUsername(dto.getUsername()) .orElseThrow(() -> new InvalidCredentials("Invalid username or password"));

        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new InvalidCredentials("Invalid username or password ");
        }
        AuthResponseDto authResponseDto = new AuthResponseDto();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        authResponseDto.setToken(jwtService.generateToken(user.getUsername(), user.getRole()));
        authResponseDto.setRefreshToken(refreshToken.getToken());
        return authResponseDto;
    }

    public User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username) .orElseThrow(() -> new UserNotFound("User not found"));

    }


}
