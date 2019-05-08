package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.List;

import com.ibm.training.bootcamp.rest.sample01.domain.Song;

public interface SongDao {
	
	public List<Song> findAll();
	
	public Song find(Long songID);
	
	public List<Song> findByName(String title, String artist, String label, String date, String genre);
	
	public void add(Song song);
	
	public void update(Song song);
	
	public void delete(Long songID);

}
