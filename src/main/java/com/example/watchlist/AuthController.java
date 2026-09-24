package com.example.watchlist;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);

    }
    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginDto loginDto) {

        return userService.login(loginDto);
    }

}
