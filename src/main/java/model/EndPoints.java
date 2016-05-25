package model;

public interface EndPoints {
    public final static String AUTHORIZATION = "https://api.gotinder.com/auth";
    public final static String RECOMMENDATIONS = "https://api.gotinder.com/user/recs?locale=de";
    public final static String GET_USER_LIKES = "https://api.gotinder.com/user/";
    public final static String GET_MATCHES = "https://api.gotinder.com/updates";
    public final static String GET_MATCH_INFO = "https://api.gotinder.com/user/matches/";
    public final static String SEND_LIKE = "https://api.gotinder.com/like/";
    public final static String SEND_PASS = "https://api.gotinder.com/pass/";
    public final static String SEND_MESSAGE = "https://api.gotinder.com/user/matches/";
    
}
