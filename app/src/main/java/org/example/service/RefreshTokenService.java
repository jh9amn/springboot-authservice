package org.example.service;

import org.example.entities.RefreshToken;
import org.example.entities.UserInfo;
import org.example.repository.RefreshTokenRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
@Autowired  RefreshTokenRepository refreshTokenRepository;
@Autowired  UserRepository userRepository;
    public RefreshToken createRefreshToken(String userName) {
        UserInfo userInfoExtracted = userRepository.findByUserName(userName);
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        // token expire then
        if(token.getExpiryDate().compareTo(Instant.now())< 0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login.");
        }
        // if token not expire then return token
        return token;
    }

    public Optional<RefreshToken> finfByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
