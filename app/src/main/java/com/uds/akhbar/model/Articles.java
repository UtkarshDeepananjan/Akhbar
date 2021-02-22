package com.uds.akhbar.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Articles implements Parcelable {

	@SerializedName("publishedAt")
	private final String publishedAt;

	@SerializedName("author")
	private final String author;

	@SerializedName("urlToImage")
	private final String urlToImage;

	@SerializedName("description")
	private final String description;

	@SerializedName("source")
	private Source source;

	@SerializedName("title")
	private final String title;

	@SerializedName("url")
	private final String url;

	@SerializedName("content")
	private final String content;

	protected Articles(Parcel in) {
		publishedAt = in.readString();
		author = in.readString();
		urlToImage = in.readString();
		description = in.readString();
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

	public String getPublishedAt(){
		return publishedAt;
	}

	public String getAuthor(){
		return author;
	}

	public String getUrlToImage(){
		return urlToImage;
	}

	public String getDescription(){
		return description;
	}

	public Source getSource(){
		return source;
	}

	public String getTitle(){
		return title;
	}

	public String getUrl(){
		return url;
	}

	public String getContent(){
		return content;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(publishedAt);
		dest.writeString(author);
		dest.writeString(urlToImage);
		dest.writeString(description);
		dest.writeString(title);
		dest.writeString(url);
		dest.writeString(content);
	}
}