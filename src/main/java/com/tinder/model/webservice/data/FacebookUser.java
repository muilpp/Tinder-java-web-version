package com.tinder.model.webservice.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FacebookUser {
	private String token;

	public FacebookUser() {}
	
	public FacebookUser(String token) {
		this.token = token;
	}
	
	@JsonProperty("facebook_token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}