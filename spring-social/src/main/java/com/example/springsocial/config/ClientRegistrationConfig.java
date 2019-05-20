package com.example.springsocial.config;

import com.example.springsocial.security.oauth2.CustomOAuth2Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ClientRegistrationConfig {

    @Autowired
    private ClientProperties clientProperties;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clientProperties.getRegistration().entrySet().stream()
                .map(c -> getRegistration(c.getKey(), c.getValue()))
                .filter(registration -> registration != null)
                .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(String client, ClientProperties.Config config) {
        String clientId = config.getClientId();
        String clientSecret = config.getClientSecret();
        String redirectUriTemplate = config.getRedirectUriTemplate();
        List<String> scopes = config.getScope();

        return CustomOAuth2Provider.valueOf(client).getBuilder(client)
                .clientId(clientId).clientSecret(clientSecret)
                .redirectUriTemplate(redirectUriTemplate)
                .scope(scopes).build();
    }

}
