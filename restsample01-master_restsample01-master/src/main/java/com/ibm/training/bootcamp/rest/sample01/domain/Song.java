package com.ibm.training.bootcamp.rest.sample01.domain;

public class Song {
	
	Long SongID;
	private String Title;
	private String Artist;
	private String Label;
	private String Date;
	private String Genre;
	
	public Song() {
		
	}
	
	public Song(String title, String artist, String label, String date, String genre) {
		this(null, title, artist, label, date, genre);	
	}
	
	public Song(Long songID, String title, String artist, String label, String date, String genre) {
		this.SongID = songID;
		this.Title = title;
		this.Artist = artist;
		this.Label = label;
		this.Date = date;
		this.Genre = genre;
	}
	
	public Long getSongID() {
		return SongID;
	}
	public void setSongID(Long songID) {
		SongID = songID;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getArtist() {
		return Artist;
	}
	public void setArtist(String artist) {
		Artist = artist;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getGenre() {
		return Genre;
	}
	public void setGenre(String genre) {
		Genre = genre;
	}
	
	
	
	
	
	


}
