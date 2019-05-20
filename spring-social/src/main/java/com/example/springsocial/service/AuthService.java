package com.example.springsocial.service;

import com.example.springsocial.exception.TokenRefreshException;
import com.example.springsocial.model.*;
import com.example.springsocial.payload.LoginRequest;
import com.example.springsocial.payload.RefreshRequest;
import com.example.springsocial.payload.SignUpRequest;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserAccessInfoService userAccessInfoService;

    @Autowired
    private RefreshTokenService refreshTokenService;


//    public Optional<User> registerUser() {
//        return ;
//    }

//    public Boolean usernameAlreadyExists(String username) {
//        return userService.existsByUsername(username);
//    }

    public User signup(SignUpRequest signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);
        return result;
    }

    public Optional<Authentication> authenticateUser(LoginRequest loginRequest) {
        return Optional.ofNullable(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword())));
    }

    public String generateToken(CustomUserDetails customUserDetails) {
        return tokenProvider.generateToken(customUserDetails);
    }

    private String generateTokenFromUserId(Long userId) {
        return tokenProvider.generateTokenFromUserId(userId);
    }

    public Optional<RefreshToken> createAndPersistRefreshTokenForAccessInfo(Authentication authentication) {
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        User currentUser = userRepository.findByEmail(userDetail.getUsername()).orElse(null);

        userAccessInfoService.findByUserId(currentUser.getId())
                .map(UserAccessInfo::getRefreshToken)
                .map(RefreshToken::getId)
                .ifPresent(refreshTokenService::deleteById);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken();

        UserAccessInfo userAccessInfo = new UserAccessInfo();
        userAccessInfo.setUser(currentUser);
        userAccessInfo.setRefreshToken(refreshToken);
        refreshToken.setUserAccessInfo(userAccessInfo);

        refreshToken = refreshTokenService.save(refreshToken);
        return Optional.ofNullable(refreshToken);
    }

    public Optional<String> refreshJwtToken(RefreshRequest refreshRequest) {
        String requestRefreshToken = refreshRequest.getRefreshToken();

        return Optional.of(refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshToken -> {
                    refreshTokenService.verifyExpiration(refreshToken);
                    userAccessInfoService.verifyRefreshAvailability(refreshToken);
                    refreshTokenService.increaseCount(refreshToken);
                    return refreshToken;
                })
                .map(RefreshToken::getUserAccessInfo)
                .map(UserAccessInfo::getUser)
                .map(User::getId).map(this::generateTokenFromUserId))
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Missing refresh token in database.Please login again"));
    }

}
