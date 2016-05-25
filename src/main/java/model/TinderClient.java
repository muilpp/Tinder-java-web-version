package model;

import static model.EndPoints.AUTHORIZATION;
import static model.EndPoints.GET_MATCHES;
import static model.EndPoints.GET_MATCH_INFO;
import static model.EndPoints.GET_USER_LIKES;
import static model.EndPoints.RECOMMENDATIONS;
import static model.EndPoints.SEND_LIKE;
import static model.EndPoints.SEND_MESSAGE;
import static model.EndPoints.SEND_PASS;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static model.Constants.TINDER_TOKEN;

import model.webservice_data_model.AllMatchesDTO;
import model.webservice_data_model.LoginDTO;
import model.webservice_data_model.MatchDTO;
import model.webservice_data_model.RecommendationsDTO;
import model.webservice_data_model.Results;
import model.webservice_data_model.UserProfile;

public class TinderClient {
    private static Logger LOGGER = Logger.getLogger(TinderClient.class.getName());

    //Write the Facebook token found inspecting with Charles
    private final String FACEBOOK_TOKEN = "";

    private int numLoginTries = 0;

    public void login(ServletContext context) {
        String authToken = "";
        Client client = ClientBuilder.newClient();

        //Token is out of date, we get a new one
        WebTarget webTarget = client.target(AUTHORIZATION);

        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .post(Entity.json("{\"facebook_token\":\"" + FACEBOOK_TOKEN + "\"}"));

        if (response.getStatus() != 200) {
            LOGGER.log(Level.SEVERE, "Error while getting token -> " + response.getStatus());
        } else {
            LoginDTO loginDTO = response.readEntity(LoginDTO.class);
            if (loginDTO != null) {
                authToken = loginDTO.getToken();
                context.setAttribute(TINDER_TOKEN, authToken);
            }
        }
    }

    public List<Results> getRecommendations(ServletContext context) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(RECOMMENDATIONS);

        Response response = webTarget.request(MediaType.APPLICATION_JSON).header("X-Auth-Token", context.getAttribute(TINDER_TOKEN)).post(null);

        if (response.getStatus() == 401 && numLoginTries < 2) {
            LOGGER.log(Level.INFO, "401 received, do login");
            login(context);
            numLoginTries++;
            getRecommendations(context);
        } else if (response.getStatus() != 200) {
            LOGGER.log(Level.INFO, "Failed : HTTP error code : " + response.getStatus());
        } else {
            RecommendationsDTO results = response.readEntity(RecommendationsDTO.class);

            if (results.getResults() == null || results.getResults().size() == 0) {
                LOGGER.log(Level.INFO, "No more recommendations found");
                return Collections.emptyList();
            }

            return results.getResults();
        }

        return Collections.emptyList();
    }

    public Results getUserLikes(String userID, ServletContext context) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(GET_USER_LIKES + userID);

        Response response = webTarget.request(MediaType.APPLICATION_JSON).header("X-Auth-Token", context.getAttribute(TINDER_TOKEN)).get();

        if (response.getStatus() != 200) {
            LOGGER.log(Level.INFO, "Failed : HTTP error code : " + response.getStatus());
        } else {
            UserProfile results = response.readEntity(UserProfile.class);

            if (results.getResults() == null)
                LOGGER.log(Level.INFO, "UserID not found -> " + userID);

            return results.getResults();
        }

        return null;
    }

    public AllMatchesDTO getAllMatches(ServletContext context) {
        AllMatchesDTO results;
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(GET_MATCHES);

        Response response = webTarget.request(MediaType.APPLICATION_JSON).header("X-Auth-Token", context.getAttribute(TINDER_TOKEN))
                .post(Entity.json("{\"matches\":\"\"}"));

        if (response.getStatus() != 200) {
            LOGGER.log(Level.INFO, "Failed : HTTP error code : " + response.getStatus());
        } else {
            results = response.readEntity(AllMatchesDTO.class);

            return results;
        }

        return null;
    }

    public MatchDTO getMatch(String matchID, ServletContext context) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(GET_MATCH_INFO + matchID);

        Response response = webTarget.request(MediaType.APPLICATION_JSON).header("X-Auth-Token", context.getAttribute(TINDER_TOKEN)).get();

        if (response.getStatus() != 200) {
            LOGGER.log(Level.INFO, "Failed : HTTP error code : " + response.getStatus());
        } else {
            MatchDTO result = response.readEntity(MatchDTO.class);

            return result;
        }

        return null;
    }

    public int sendLike(String userID, ServletContext context) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(SEND_LIKE + userID);

        Response response = webTarget.request(MediaType.APPLICATION_JSON).header("X-Auth-Token", context.getAttribute(TINDER_TOKEN)).post(null);

        if (response.getStatus() != 200)
            LOGGER.log(Level.INFO, "Status -> " + response.getStatus());

        LOGGER.log(Level.INFO, "Response -> " + response);

        return response.getStatus();
    }

    public int sendPass(String userID, ServletContext context) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(SEND_PASS + userID);

        Response response = webTarget.request(MediaType.APPLICATION_JSON).header("X-Auth-Token", context.getAttribute(TINDER_TOKEN)).post(null);

        if (response.getStatus() != 200)
            LOGGER.log(Level.INFO, "Status -> " + response.getStatus());

        LOGGER.log(Level.INFO, "Response -> " + response);

        return response.getStatus();
    }

    public int sendMessage(String message, String matchID, ServletContext context) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(SEND_MESSAGE + matchID);

        Response response = webTarget.request(MediaType.APPLICATION_JSON).header("X-Auth-Token", context.getAttribute(TINDER_TOKEN))
                .post(Entity.json("{\"message\":\"" + message + "\"}"));

        if (response.getStatus() != 200)
            LOGGER.log(Level.INFO, "Status -> " + response.getStatus());

        LOGGER.log(Level.INFO, "Response -> " + response);

        return response.getStatus();
    }
}