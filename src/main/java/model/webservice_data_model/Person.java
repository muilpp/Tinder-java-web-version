package model.webservice_data_model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    private List<Photos> photoList;
    private String name;
    private Position position;
    private String id;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("photos")
    public List<Photos> getPhotoList() {
        return photoList;
    }
    public void setPhotoList(List<Photos> photoList) {
        this.photoList = photoList;
    }
    
    @JsonProperty("pos")
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}