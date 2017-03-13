package com.tinder.model.webservice.interceptors;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class AcceptHeaderInterceptor implements ClientHttpRequestInterceptor {
    
    private static final Logger LOGGER = Logger.getLogger(AcceptHeaderInterceptor.class.getName());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        LOGGER.log(Level.INFO, "[{0}]", MediaType.APPLICATION_JSON.toString());
        HttpHeaders headers = request.getHeaders();
        headers.add("Content-Type", "application/json");
        return execution.execute(request, body);
    }

}
