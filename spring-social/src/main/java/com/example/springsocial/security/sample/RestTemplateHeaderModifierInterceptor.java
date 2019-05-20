package com.example.springsocial.security.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
//        String responsebody = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
//        InputStream is = response.getBody();
//        byte[] out = StreamUtils.copyToByteArray(is);
//        String responsebody = new String(out, StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return response;
    }
}
