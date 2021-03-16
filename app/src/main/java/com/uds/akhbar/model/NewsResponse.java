package com.uds.akhbar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private List<Articles> articles;

    @SerializedName("status")
    private String status;


    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    public int getTotalResults() {
        return totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}