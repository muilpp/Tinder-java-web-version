package model.webservice_data_model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    private long time;
    private String latitude;
    private String longitude;

    @JsonProperty("at")
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    
    @JsonProperty("lat")
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    @JsonProperty("lon")
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
