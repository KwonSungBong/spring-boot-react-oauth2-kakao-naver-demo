package com.example.springsocial.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.security.oauth2.client")
public class ClientProperties {

    private final Map<String, Config> registration = new HashMap<>();

    public static class Config {
        private String clientId;
        private String clientSecret;
        private String redirectUriTemplate;
        private List<String> scope = new ArrayList<>();

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getRedirectUriTemplate() {
            return redirectUriTemplate;
        }

        public void setRedirectUriTemplate(String redirectUriTemplate) {
            this.redirectUriTemplate = redirectUriTemplate;
        }

        public List<String> getScope() {
            return scope;
        }

        public void setScope(List<String> scope) {
            this.scope = scope;
        }
    }

    public Map<String, Config> getRegistration() {
        return registration;
    }
}
