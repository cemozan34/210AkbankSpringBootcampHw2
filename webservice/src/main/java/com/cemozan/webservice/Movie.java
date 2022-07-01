package com.cemozan.webservice;


public class Movie {
	
	private String title;
	private String year;
	private String imdbId;
	private String type;
	private String poster;
	
	
	
	public Movie() {
		super();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getImdbId() {
		return imdbId;
	}
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	@Override
	public String toString() {
		return "Movie [title=" + title + ", year=" + year + ", imdbId=" + imdbId + ", type=" + type + ", poster="
				+ poster + "]";
	}
	
  	
	
	
	
	
	
	

}
