package com.uds.akhbar.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Articles implements Parcelable {

    private String id;
    @SerializedName("publishedAt")
    private String publishedAt;

    @SerializedName("author")
    private String author;

    @SerializedName("urlToImage")
    private String urlToImage;

    @SerializedName("description")
    private String description;

    @SerializedName("source")
    private Source source;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("content")
    private String content;


    protected Articles(Parcel in) {
        id = in.readString();
        publishedAt = in.readString();
        author = in.readString();
        urlToImage = in.readString();
        description = in.readString();
        source = in.readParcelable(Source.class.getClassLoader());
        title = in.readString();
        url = in.readString();
        content = in.readString();
    }

    public static final Creator<Articles> CREATOR = new Creator<Articles>() {
        @Override
        public Articles createFromParcel(Parcel in) {
            return new Articles(in);
        }

        @Override
        public Articles[] newArray(int size) {
            return new Articles[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(publishedAt);
        dest.writeString(author);
        dest.writeString(urlToImage);
        dest.writeString(description);
        dest.writeParcelable(source, flags);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(content);
    }

    public Articles(String id, String publishedAt, String author, String urlToImage, String description, Source source, String title, String url, String content) {
        this.id = id;
        this.publishedAt = publishedAt;
        this.author = author;
        this.urlToImage = urlToImage;
        this.description = description;
        this.source = source;
        this.title = title;
        this.url = url;
        this.content = content;
    }

    public Articles() {
    }

    public String getId() {
        return id;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public Source getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public void setId(String id) {
        this.id = id;
    }
}