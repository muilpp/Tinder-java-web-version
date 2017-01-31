package com.tinder.model.webservice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Teaser {
    private String string;
    private String type;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}