package com.example.springsocial.service;

import com.example.springsocial.exception.TokenRefreshException;
import com.example.springsocial.model.RefreshToken;
import com.example.springsocial.model.UserAccessInfo;
import com.example.springsocial.repository.UserAccessInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccessInfoService {

    @Autowired
    private UserAccessInfoRepository userAccessInfoRepository;

    /**
     * Find the user device info by user id
     */
    public Optional<UserAccessInfo> findByUserId(Long userId) {
        return userAccessInfoRepository.findByUserId(userId);
    }

    public Optional<UserAccessInfo> findByRefreshToken(RefreshToken refreshToken) {
        return userAccessInfoRepository.findByRefreshToken(refreshToken);
    }

//    public UserAccessInfo createUserAccessInfo(UserAccessInfo userAccessInfo) {
//        UserAccessInfo createUserAccessInfo = new UserAccessInfo();
////        userAccessInfo.setDeviceId(deviceInfo.getDeviceId());
////        userAccessInfo.setDeviceType(deviceInfo.getDeviceType());
////        userAccessInfo.setNotificationToken(deviceInfo.getNotificationToken());
//        createUserAccessInfo.setRefreshActive(true);
//        return createUserAccessInfo;
//    }

    void verifyRefreshAvailability(RefreshToken refreshToken) {
        UserAccessInfo userAccessInfo = findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenRefreshException(refreshToken.getToken(), "No device found for the matching token. Please login again"));

        if (!userAccessInfo.getRefreshActive()) {
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh blocked for the device. Please login through a different device");
        }
    }
}
