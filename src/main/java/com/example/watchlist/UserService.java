package com.example.watchlist;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
        authResponseDto.setToken(jwtService.generateToken(user.getUsername(), user.getRole()));
        return authResponseDto;
    }

    public User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username) .orElseThrow(() -> new UserNotFound("User not found"));

    }


}
