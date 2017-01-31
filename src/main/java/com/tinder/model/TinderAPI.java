package com.tinder.model;

import com.tinder.model.webservice.data.MatchDTO;
import com.tinder.model.webservice.data.MatchesDTO;
import com.tinder.model.webservice.data.RecommendationsDTO;
import com.tinder.model.webservice.data.TinderUser;

public interface TinderAPI {
    /**
     * Method used to get the Tinder token of a user
     * 
     * @param facebookToken
     * @return a TinderUser object with a token inside if found
     */
    public TinderUser authorize(String facebookToken);

    /**
     * Method used to get new recommendations
     * 
     * @param userToken
     */
    public RecommendationsDTO getRecommendations(String userToken);

    /**
     * Method used to send a superlike to a user
     * 
     * @param userID
     * @return 1 if it's a match, 0 if it's not a match and -1 if superlikes are
     *         exceeded
     */
    public int sendSuperlike(String userID, String userToken);

    /**
     * Method used to send a like to a user
     * 
     * @param userID
     * @return true if it's a match, false otherwise
     */
    public boolean sendLike(String userID, String userToken);

    /**
     * Method used to send a pass to a user
     * 
     * @param userID
     * @return
     */
    public void sendPass(String userID, String userToken);

    /**
     * Method used to send a message to the match
     * 
     * @param message
     * @param matchID
     * @return true if the message was sent, false otherwise
     */
    public boolean sendMessage(String userToken, String message, String matchID);

    /**
     * Method used to fetch all the user matches
     * 
     * @param userToken
     * @return all the matches, null if no matches are present
     */
    public MatchesDTO getAllMatches(String userToken);

    /**
     * Method used to get all the information of a match
     * 
     * @param matchID
     * @return the match if found, null otherwise
     */
    public MatchDTO getMatchInfo(String userToken, String matchID);
}