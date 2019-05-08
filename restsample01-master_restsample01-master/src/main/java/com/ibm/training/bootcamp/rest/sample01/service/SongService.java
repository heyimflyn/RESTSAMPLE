package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Song;

public interface SongService {
	
	public List<Song> findAll();
	
	public Song find(Long songID);
	
	public List<Song> findByName(String title, String artist, String label, String date, String genre);
	
	public void add(Song song);
	
	public void upsert(Song song);
	
	public void delete(Long songID);

}
