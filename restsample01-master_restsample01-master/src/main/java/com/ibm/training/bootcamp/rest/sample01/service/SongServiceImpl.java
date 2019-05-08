package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.dao.SongDao;
import com.ibm.training.bootcamp.rest.sample01.dao.SongJdbcDaoImpl;
import com.ibm.training.bootcamp.rest.sample01.domain.Song;

public class SongServiceImpl implements SongService {

	SongDao songDao;

	public SongServiceImpl() {
		this.songDao = SongJdbcDaoImpl.getInstance();
		//this.userDao = UserHashMapDaoImpl.getInstance();
	}
	
	@Override
	public List<Song> findAll() {
		return songDao.findAll();
	}

	@Override
	public Song find(Long songID) {
		return songDao.find(songID);
	}

	@Override
	public List<Song> findByName(String title, String artist, String label, String date, String genre) {
		return songDao.findByName(title, artist, label, date, genre);
	}

	@Override
	public void add(Song song) {
		if (validate(song)) {
			songDao.add(song);
		} else {
			throw new IllegalArgumentException("Fields title, artist, label, date and genre cannot be blank.");
		}
	}

	@Override
	public void upsert(Song song) {
		if (validate(song)) {
			if(song.getSongID() != null && song.getSongID() >= 0) {
				songDao.update(song);
			} else {
				songDao.add(song);
			}
		} else {
			throw new IllegalArgumentException("Fields title, artist, label, date and genre cannot be blank.");
		}
	}

	@Override
	public void delete(Long songID) {
		songDao.delete(songID);
	}
	
	private boolean validate(Song song) {
		return !StringUtils.isAnyBlank(song.getTitle(), song.getArtist(), song.getLabel(), song.getDate(), song.getGenre());
	}
}
