package com.tinder.model.webservice;

public final class Endpoints {
    public final static String BASE_URL = "https://api.gotinder.com";
    public final static String RECS_EP = "/user/recs";
    public final static String SUPERLIKE_EP = "/super/";
    public final static String LIKE_EP = "/like/";
    public final static String PASS_EP = "/pass/";
    public final static String ALL_MATCHES_EP = "/updates";
    public final static String MATCH_INFO_EP = "/user/matches/";
    public final static String AUTH_EP = "/auth";

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