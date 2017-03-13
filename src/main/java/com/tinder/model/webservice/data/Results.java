package com.tinder.model.webservice.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {
    private String id;
    private String birthDate;
    private String name;
    private String bio;
    private String distance;
    private String connectionCount;
    private String lastConnection;
    private List<Photos> photos;
    private Teaser teaser;
    private Instagram instagram;
    private List<Badges> badgesList;

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("birth_date")
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photos> photos) {
        this.photos = photos;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @JsonProperty("distance_mi")
    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Teaser getTeaser() {
        return teaser;
    }

    public void setTeaser(Teaser teaser) {
        this.teaser = teaser;
    }

    @JsonProperty("connection_count")
    public String getConnectionCount() {
        return connectionCount;
    }
    
    public void setConnectionCount(String connectionCount) {
        this.connectionCount = connectionCount;
    }

    @JsonProperty("ping_time")
    public String getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(String lastConnection) {
        this.lastConnection = lastConnection;
    }

    public Instagram getInstagram() {
        return instagram;
    }

    public void setInstagram(Instagram instagram) {
        this.instagram = instagram;
    }

    @JsonProperty("badges")
    public List<Badges> getBadgesList() {
        return badgesList;
    }

    public void setBadgesList(List<Badges> badgesList) {
        this.badgesList = badgesList;
    }
}