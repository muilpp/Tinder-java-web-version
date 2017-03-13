package com.tinder.model.webservice.interceptors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        LOGGER.info("-- REQUEST --");
        LOGGER.log(Level.INFO, "URI -> [{0}]", request.getURI());
        LOGGER.log(Level.INFO, "Method -> [{0}]", request.getMethod().toString());

        HttpHeaders headers = request.getHeaders();

        // Need to change the user agent, the default is "Java/1.8.0_111" and
        // Tinder would deny the request
        headers.add("User-Agent", "Tinder Android Version 6.7.2");

        Iterator<String> headerIterator = headers.keySet().iterator();
        LOGGER.info("Headers: ");
        while (headerIterator.hasNext()) {
            String key = headerIterator.next();
            LOGGER.log(Level.INFO, "[{0}] : [{1}]", new Object[]{key, headers.get(key)});
        }

        LOGGER.log(Level.INFO, "Body -> [{0}]", new String(body, StandardCharsets.UTF_8));

        return execution.execute(request, body);
    }
}