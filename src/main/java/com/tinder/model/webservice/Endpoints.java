package com.tinder.model.webservice;

public final class Endpoints {
    public static final String BASE_URL = "https://api.gotinder.com";
    public static final String RECS_EP = "/user/recs";
    public static final String SUPERLIKE_EP = "/super/";
    public static final String LIKE_EP = "/like/";
    public static final String PASS_EP = "/pass/";
    public static final String ALL_MATCHES_EP = "/updates";
    public static final String MATCH_INFO_EP = "/user/matches/";
    public static final String AUTH_EP = "/auth";

    private Endpoints() {}
    
    public static String getRecsEP() {
        return BASE_URL + RECS_EP;
    }

    public static String getAllMatchesEP() {
        return BASE_URL + ALL_MATCHES_EP;
    }

    public static String getMatchInfoEP(String matchID) {
        return BASE_URL + MATCH_INFO_EP + matchID;
    }

    public static String getSendSuperlikeEP(String userID) {
        return BASE_URL + LIKE_EP + userID + SUPERLIKE_EP;
    }

    public static String getSendLikeEP(String userID) {
        return BASE_URL + LIKE_EP + userID;
    }

    public static String getSendPassEP(String userID) {
        return BASE_URL + PASS_EP + userID;
    }

    public static String getAuthorizationEP() {
        return BASE_URL + AUTH_EP;
    }
}