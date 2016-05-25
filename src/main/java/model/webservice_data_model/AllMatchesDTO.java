package model.webservice_data_model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllMatchesDTO {
    private List<Match> matchList;
    private List<String> blocks;

    @JsonProperty("matches")
    public List<Match> getMatchList() {
        return matchList;
    }
    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
    }

    @JsonProperty("blocks")
    public List<String> getBlocks() {
        return blocks;
    }
    public void setBlocks(List<String> blocks) {
        this.blocks = blocks;
    }
}