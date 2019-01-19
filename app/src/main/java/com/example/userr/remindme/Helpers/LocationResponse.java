package com.example.userr.remindme.Helpers;

import com.google.gson.annotations.SerializedName;

public class LocationResponse {

	@SerializedName("date")
	private String date;

	public LocationResponse(String date, String address, String time) {
		this.date = date;
		this.address = address;
		this.time = time;
	}

	@SerializedName("address")
	private String address;

	@SerializedName("log")
	private String log;

	@SerializedName("details")
	private String details;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("lat")
	private String lat;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setLog(String log){
		this.log = log;
	}

	public String getLog(){
		return log;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public String getDetails(){
		return details;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	@Override
 	public String toString(){
		return 
			"LocationResponse{" +
			"date = '" + date + '\'' + 
			",address = '" + address + '\'' + 
			",log = '" + log + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			",time = '" + time + '\'' + 
			",lat = '" + lat + '\'' + 
			"}";
		}
}