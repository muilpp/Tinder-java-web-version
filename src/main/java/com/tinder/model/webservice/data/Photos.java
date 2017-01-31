package com.tinder.model.webservice.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photos {
    private String url;
    private List<ProcessedFiles> processedFiles;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ProcessedFiles> getProcessedFiles() {
        return processedFiles;
    }

    public void setProcessedFiles(List<ProcessedFiles> processedFiles) {
        this.processedFiles = processedFiles;
    }
}
