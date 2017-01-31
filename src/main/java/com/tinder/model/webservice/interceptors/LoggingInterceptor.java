package com.tinder.model.webservice.interceptors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private final static Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        LOGGER.info("-- REQUEST --");
        LOGGER.info("URI -> " + request.getURI());
        LOGGER.info("Method -> " + request.getMethod().toString());

        HttpHeaders headers = request.getHeaders();

        // Need to change the user agent, the default is "Java/1.8.0_111" and
        // Tinder would deny the request
        headers.add("User-Agent", "Tinder Android Version 6.7.2");

        Iterator<String> headerIterator = headers.keySet().iterator();
        LOGGER.info("Headers: ");
        while (headerIterator.hasNext()) {
            String key = headerIterator.next();
            LOGGER.info(key + " : " + headers.get(key));
        }

        LOGGER.info("Body -> " + new String(body, StandardCharsets.UTF_8));

        // LOGGER.info("-- RESPONSE --");
        //
        // LOGGER.info("Headers: ");
        ClientHttpResponse response = execution.execute(request, body);
        // HttpHeaders responseHeaders = response.getHeaders();
        // Iterator<String> responseHeaderIterator =
        // responseHeaders.keySet().iterator();
        // while (responseHeaderIterator.hasNext()) {
        // String key = responseHeaderIterator.next();
        // LOGGER.info(key + " : " + headers.get(key));
        // }

        // try (Scanner scanner = new Scanner(response.getBody(),
        // StandardCharsets.UTF_8.name())) {
        // LOGGER.info("Response body -> " +
        // scanner.useDelimiter("\\A").next());
        // }

        return response;
    }
}