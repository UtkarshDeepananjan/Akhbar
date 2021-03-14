package com.uds.akhbar.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SourcesItem implements Parcelable {

	@SerializedName("country")
	private String country;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("language")
	private String language;

	@SerializedName("id")
	private String id;

	@SerializedName("category")
	private String category;

	@SerializedName("url")
	private String url;

	protected SourcesItem(Parcel in) {
		country = in.readString();
		name = in.readString();
		description = in.readString();
		language = in.readString();
		id = in.readString();
		category = in.readString();
		url = in.readString();
	}

	public static final Creator<SourcesItem> CREATOR = new Creator<SourcesItem>() {
		@Override
		public SourcesItem createFromParcel(Parcel in) {
			return new SourcesItem(in);
		}

		@Override
		public SourcesItem[] newArray(int size) {
			return new SourcesItem[size];
		}
	};

	public String getCountry(){
		return country;
	}

	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	public String getLanguage(){
		return language;
	}

	public String getId(){
		return id;
	}

	public String getCategory(){
		return category;
	}

	public String getUrl(){
		return url;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(country);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(language);
		dest.writeString(id);
		dest.writeString(category);
		dest.writeString(url);
	}
}