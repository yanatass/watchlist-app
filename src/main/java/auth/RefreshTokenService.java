package auth;

import user.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public RefreshToken createRefreshToken(User user){
        RefreshToken refreshTokens = new RefreshToken();
        refreshTokens.setUser(user);
        refreshTokens.setToken(UUID.randomUUID().toString());
        refreshTokens.setExpiryDate(Instant.now().plusSeconds(7 * 24 * 60 * 60));
        return refreshTokenRepository.save(refreshTokens);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpired("Token expired");
        }
        return token;

    }
    public String refreshAccessToken(String requestRefreshToken){
        RefreshToken refreshTokens = refreshTokenRepository.findByToken(requestRefreshToken) .orElseThrow(() -> new RefreshTokenExpired("Token not found"));
        verifyExpiration(refreshTokens);
        User user = refreshTokens.getUser();
        String newToken = jwtService.generateToken(user.getUsername(), user.getRole());
        return newToken;


    }

}
