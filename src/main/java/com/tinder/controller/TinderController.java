package com.tinder.controller;

import static com.tinder.model.Constants.FACEBOOK_TOKEN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tinder.model.TinderAPI;
import com.tinder.model.webservice.data.TinderUser;

@RestController
public class TinderController {

    @Autowired
    private TinderAPI tinderAPI;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/authorization")
    public TinderUser login() {
        return tinderAPI.authorize(FACEBOOK_TOKEN);
    }

    @GetMapping("/recs")
    public void getRecommendations() {
        tinderAPI.getRecommendations("");
    }

    @GetMapping("/test")
    public TinderUser getTest() {
        TinderUser tUser = new TinderUser();
        tUser.setToken("ACB");

        return tUser;
    }
}