package com.example.springsocial.payload;

public class LogoutRequest {

    private long userAccessInfoId;

    public LogoutRequest() {
    }

    public LogoutRequest(long userAccessInfoId) {
        this.userAccessInfoId = userAccessInfoId;
    }

    public long getUserAccessInfoId() {
        return userAccessInfoId;
    }

    public void setUserAccessInfoId(long userAccessInfoId) {
        this.userAccessInfoId = userAccessInfoId;
    }
}
