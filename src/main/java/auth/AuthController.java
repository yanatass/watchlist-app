package auth;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.User;
import user.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(UserService userService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);

    }
    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginDto loginDto) {

        return userService.login(loginDto);
    }

    @PostMapping("/refresh")
    public AuthResponseDto refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto) {
        String newAccessToken = refreshTokenService.refreshAccessToken(refreshTokenDto.getRefreshToken());
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken(newAccessToken);
        return authResponseDto;

    }

}
