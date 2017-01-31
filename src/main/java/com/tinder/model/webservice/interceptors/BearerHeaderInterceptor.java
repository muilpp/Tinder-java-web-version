package com.tinder.model.webservice.interceptors;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class BearerHeaderInterceptor implements ClientHttpRequestInterceptor {
    private String userToken;

    public BearerHeaderInterceptor(String userToken) {
        this.userToken = userToken;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        request.getHeaders().add("X-Auth-Token", userToken);

        return execution.execute(request, body);
    }
}