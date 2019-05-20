package com.example.springsocial.repository;

import com.example.springsocial.model.RefreshToken;
import com.example.springsocial.model.UserAccessInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccessInfoRepository extends JpaRepository<UserAccessInfo, Long> {

    @Override
    Optional<UserAccessInfo> findById(Long id);

    Optional<UserAccessInfo> findByRefreshToken(RefreshToken refreshToken);

    Optional<UserAccessInfo> findByUserId(Long userId);
}
