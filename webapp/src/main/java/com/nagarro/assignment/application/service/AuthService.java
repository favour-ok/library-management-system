package com.nagarro.assignment.application.service;

import com.nagarro.assignment.application.utils.JwtUtil;
import com.nagarro.assignment.domain.model.Librarian;
import com.nagarro.assignment.domain.model.RefreshToken;
import com.nagarro.assignment.domain.model.repository.RefreshTokenRepository;
import com.nagarro.assignment.dto.ErrorDTO;
import com.nagarro.assignment.dto.request.AuthRequest;
import com.nagarro.assignment.dto.request.RefreshTokenRequest;
import com.nagarro.assignment.dto.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public ResponseEntity<?> login(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));

            Librarian userDetails = (Librarian) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(authentication);

            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
            RefreshToken refreshTokenEntity = new RefreshToken(
                    refreshToken,
                    Date.from(Instant.now().plusMillis(jwtUtil.getRefreshExpirationInMs())),
                    userDetails
            );
            refreshTokenRepository.save(refreshTokenEntity);

            return ResponseEntity.ok(new AuthResponse(jwt, refreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("AuthService#RefreshTokenRepository", "Invalid username or password"));
        }
    }

    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.refreshToken();
            Optional<RefreshToken> refreshTokenFromDb = refreshTokenRepository.findByToken(refreshToken);

            if(refreshTokenFromDb.isEmpty() || refreshTokenFromDb.get().getExpiryDate().before(new Date()) || refreshTokenFromDb.get().isRevoked()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO("AuthService#refreshToken", "Invalid refresh token"));
            }

            RefreshToken validRefreshToken = refreshTokenFromDb.get();
            Librarian userDetails = validRefreshToken.getLibrarian();

            String newJwt = jwtUtil.generateToken(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            validRefreshToken.setToken(newRefreshToken);
            validRefreshToken.setExpiryDate(Date.from(Instant.now().plusMillis(jwtUtil.getRefreshExpirationInMs())));

            refreshTokenRepository.save(validRefreshToken);

            return ResponseEntity.ok(new AuthResponse(newJwt, newRefreshToken));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDTO("AuthService#refreshToken","Invalid refresh token"));
        }
    }

    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        try {
            String authHeader = httpServletRequest.getHeader("Authorization");
            if(authHeader != null && authHeader.startsWith("Bearer ")) {
                String jwt = authHeader.substring(7);
                String username = jwtUtil.getUsernameFromToken(jwt);

                List<RefreshToken> refreshTokens = refreshTokenRepository.findByLibrarian_Username(username);
                for (RefreshToken token : refreshTokens) {
                    token.setRevoked(true);
                }
                refreshTokenRepository.saveAll(refreshTokens);
            }

            return ResponseEntity.ok("Logged out successfully !!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("AuthService#logout", "Logout failed !!"));
        }
    }
}
