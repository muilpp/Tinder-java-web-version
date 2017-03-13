package com.tinder.model.webservice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
    private String tokenValue;
    private String resetToken;

    @JsonProperty("token")
    public String getToken() {
        return tokenValue;
    }

    public void setToken(String token) {
        this.tokenValue = token;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
