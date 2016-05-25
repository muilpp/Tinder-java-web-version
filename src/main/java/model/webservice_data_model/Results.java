package model.webservice_data_model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Results {
    private String id;
    private String birth_date;
    private String name;
    private String bio;
    private String distance;
    private String connection_count;
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
    public String getBirth_date() {
        return birth_date;
    }
    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
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
    
    public String getConnection_count() {
        return connection_count;
    }
    public void setConnection_count(String connection_count) {
        this.connection_count = connection_count;
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