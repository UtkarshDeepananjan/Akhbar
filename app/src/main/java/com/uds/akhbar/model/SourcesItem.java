package com.uds.akhbar.model;

import com.google.gson.annotations.SerializedName;

public class SourcesItem{

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
}