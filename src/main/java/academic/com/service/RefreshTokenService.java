package academic.com.service;


import academic.com.dto.request.RefreshTokenRequest;
import academic.com.model.auth.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken();

    void validationToken(String refreshToken);

    void refreshTokenDelete(RefreshTokenRequest request);
}
