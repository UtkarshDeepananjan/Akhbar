package com.uds.akhbar.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SourceResponse {

	@SerializedName("sources")
	private List<SourcesItem> sources;

	@SerializedName("status")
	private String status;

	public List<SourcesItem> getSources(){
		return sources;
	}

	public String getStatus(){
		return status;
	}
}