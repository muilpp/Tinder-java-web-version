package com.tinder.model.webservice.data;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    private List<Photos> photoList;
    private String name, id, birth_date, bio;

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

	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}

}