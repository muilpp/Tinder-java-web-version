package com.tinder.model;

import static com.tinder.model.webservice.Endpoints.getAllMatchesEP;
import static com.tinder.model.webservice.Endpoints.getAuthorizationEP;
import static com.tinder.model.webservice.Endpoints.getMatchInfoEP;
import static com.tinder.model.webservice.Endpoints.getRecsEP;
import static com.tinder.model.webservice.Endpoints.getSendLikeEP;
import static com.tinder.model.webservice.Endpoints.getSendPassEP;
import static com.tinder.model.webservice.Endpoints.getSendSuperlikeEP;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tinder.model.webservice.data.FacebookUser;
import com.tinder.model.webservice.data.MatchDTO;
import com.tinder.model.webservice.data.MatchesDTO;
import com.tinder.model.webservice.data.Message;
import com.tinder.model.webservice.data.RecommendationsDTO;
import com.tinder.model.webservice.data.TinderUser;
import com.tinder.model.webservice.errorhandler.ErrorHandler;
import com.tinder.model.webservice.interceptors.BearerHeaderInterceptor;
import com.tinder.model.webservice.interceptors.LoggingInterceptor;

@SuppressWarnings("serial")
@Service
public class TinderAPIImpl implements TinderAPI {
    private static final Logger LOGGER = Logger.getLogger(TinderAPIImpl.class.getName());

    @Autowired
    private RestTemplate restTemplate;
    
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ErrorHandler());
        restTemplate.getInterceptors().add(new LoggingInterceptor());
        return restTemplate;
    }

    @Override
    public TinderUser authorize(String facebookToken) {
        FacebookUser facebookUser = new FacebookUser(facebookToken);

        ResponseEntity<TinderUser> response = restTemplate.postForEntity(getAuthorizationEP(), facebookUser,
                TinderUser.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            //remove old token header
            restTemplate.getInterceptors().removeIf(s -> s.getClass().equals(BearerHeaderInterceptor.class));
            restTemplate.getInterceptors().add(new BearerHeaderInterceptor(response.getBody().getToken()));
            
            return response.getBody();
        } else {
            LOGGER.log(Level.SEVERE, "Failed authorization : HTTP response, [{0}] -> ", response.getStatusCodeValue());
            LOGGER.severe(response.getStatusCode().getReasonPhrase());
        }

        return new TinderUser();
    }

    @Override
    public RecommendationsDTO getRecommendations(String userToken) {
        ResponseEntity<RecommendationsDTO> response = restTemplate.postForEntity(getRecsEP(), "",
                RecommendationsDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            LOGGER.log(Level.SEVERE, "Failed recommendations : HTTP response, [{0}] -> ", response.getStatusCodeValue());
            LOGGER.severe(response.getStatusCode().getReasonPhrase());
        }

        return new RecommendationsDTO();
    }

    @Override
    public boolean sendLike(String userID, String userToken) {
        ResponseEntity<String> response = restTemplate.getForEntity(getSendLikeEP(userID), String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info(response.getBody());
            return response.getBody().contains("true");
        } else {
            LOGGER.log(Level.SEVERE, "Failed like : HTTP response, [{0}] -> ", response.getStatusCodeValue());
            LOGGER.severe(response.getStatusCode().getReasonPhrase());
        }

        return false;
    }

    @Override
    public void sendPass(String userID, String userToken) {
        ResponseEntity<String> response = restTemplate.getForEntity(getSendPassEP(userID), String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info(response.getBody());
        } else {
            LOGGER.log(Level.SEVERE, "Failed pass : HTTP response, [{0}] -> ", response.getStatusCodeValue());
            LOGGER.severe(response.getStatusCode().getReasonPhrase());
        }
    }

    @Override
    public int sendSuperlike(String userID, String userToken) {
        ResponseEntity<String> response = restTemplate.postForEntity(getSendSuperlikeEP(userID), "", String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info(response.getBody());
            if (response.getBody().contains("limit_exceeded"))
                return -1;
            else if (response.getBody().contains("true"))
                return 1;
        } else {
            LOGGER.log(Level.SEVERE, "Failed super like : HTTP response, [{0}] -> ", response.getStatusCodeValue());
            LOGGER.severe(response.getStatusCode().getReasonPhrase());
        }

        return 0;
    }

    @Override
    public MatchesDTO getAllMatches(String userToken) {
        ResponseEntity<MatchesDTO> response = restTemplate.postForEntity(getAllMatchesEP(), "{\"matches\":\"\"}",
                MatchesDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            LOGGER.log(Level.SEVERE, "Failed all mathces : HTTP response, [{0}] -> ", response.getStatusCodeValue());
            LOGGER.severe(response.getStatusCode().getReasonPhrase());
        }

        return null;
    }

    @Override
    public MatchDTO getMatchInfo(String userToken, String matchID) {
        ResponseEntity<MatchDTO> response = restTemplate.getForEntity(getMatchInfoEP(matchID), MatchDTO.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            LOGGER.log(Level.SEVERE, "Failed match info : HTTP response, [{0}] -> ", response.getStatusCodeValue());
            LOGGER.severe(response.getStatusCode().getReasonPhrase());
        }

        return null;
    }

    @Override
    public boolean sendMessage(String userToken, String messageText, String matchID) {
        Message message = new Message();
        message.setMessage(messageText);
        ResponseEntity<String> response = restTemplate.postForEntity(getMatchInfoEP(matchID), message, String.class);

        return response.getStatusCode() == HttpStatus.OK;
    }
}