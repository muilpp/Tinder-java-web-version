package com.tinder.model.webservice.interceptors;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class AcceptHeaderInterceptor implements ClientHttpRequestInterceptor{

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		System.out.println(MediaType.APPLICATION_JSON.toString());
        HttpHeaders headers = request.getHeaders();
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Content-Type", "application/json");
        return execution.execute(request, body);
	}

}
