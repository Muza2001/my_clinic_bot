package academic.com.service.Impl;

import academic.com.dto.request.RefreshTokenRequest;
import academic.com.model.auth.RefreshToken;
import academic.com.repository.RefreshTokenRepository;
import academic.com.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(Instant.now());
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public void validationToken(String refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() ->
                        new IllegalArgumentException("Refresh token invalid"));
    }

    @Override
    public void refreshTokenDelete(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(request.
                getRefreshToken()).orElseThrow(() ->
                new IllegalArgumentException("Token not found"));
        refreshTokenRepository.delete(refreshToken);
    }

}
