package com.example.springsocial.model;

import javax.persistence.*;

@Entity
public class UserAccessInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_access_seq")
    @SequenceGenerator(name = "user_access_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Enumerated(value = EnumType.STRING)
    private AccessType accessType;

    @OneToOne(optional = false, mappedBy = "userAccessInfo")
    private RefreshToken refreshToken;

    private Boolean isRefreshActive;

    public UserAccessInfo() {
    }

    public UserAccessInfo(Long id, User user, AccessType accessType, RefreshToken refreshToken, Boolean isRefreshActive) {
        this.id = id;
        this.user = user;
        this.accessType = accessType;
        this.refreshToken = refreshToken;
        this.isRefreshActive = isRefreshActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public Boolean getRefreshActive() {
        return isRefreshActive;
    }

    public void setRefreshActive(Boolean refreshActive) {
        isRefreshActive = refreshActive;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }
}
