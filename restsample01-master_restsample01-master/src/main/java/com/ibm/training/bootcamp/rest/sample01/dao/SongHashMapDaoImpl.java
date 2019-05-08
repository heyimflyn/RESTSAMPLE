package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.domain.Song;

public class SongHashMapDaoImpl implements SongDao {

	static private SongHashMapDaoImpl INSTANCE;
	static private final Map<Long, Song> SONG_STORE;
	static private long songID = 0;
	
	static {
		SONG_STORE = new HashMap<>();
		Song s1 = new Song(songID++, "Bad Guy", "Billie Eilish", "Interscope", "April 2019", "Indie Pop");
		Song s2 = new Song(songID++, "Ilomilo",  "Billie Eilish", "Interscope", "April 2019", "Indie Pop");
		Song s3 = new Song(songID++, "Bury a Friend", "Billie Eilish", "Interscope", "April 2019", "Indie Pop");
		Song s4 = new Song(songID++, "High Hopes", "Panic! at the Disco", "Fueled by Ramen", "September 2018", "Alternative Rock");
		Song s5 = new Song(songID++, "Hey Look Ma, I Made It", "Panic! at the Disco", "Fueled by Ramen", "September 2018", "Alternative Rock");
		SONG_STORE.put(s1.getSongID(), s1);
		SONG_STORE.put(s2.getSongID(), s2);
		SONG_STORE.put(s2.getSongID(), s3);
		SONG_STORE.put(s4.getSongID(), s4);
		SONG_STORE.put(s5.getSongID(), s5);
	}
		
		private SongHashMapDaoImpl() {
			
		}
		
		static public SongHashMapDaoImpl getInstance( ) {
			
			SongHashMapDaoImpl instance;
			if (INSTANCE != null) {
				instance = INSTANCE;
			} else {
				instance = new SongHashMapDaoImpl();
				INSTANCE = instance;
			}
			
			return instance;
		}
		
		@Override
		public List<Song> findAll() {
			return new ArrayList<Song>(SONG_STORE.values());
		}

		@Override
		public Song find(Long songID) {
			return SONG_STORE.get(songID);
		}

		@Override
		public List<Song> findByName(String title, String artist, String label, String date, String genre) {
			List<Song> songs = SONG_STORE.values().stream()
					.filter(song -> StringUtils.isBlank(title) || song.getTitle().equalsIgnoreCase(title))
					.filter(song -> StringUtils.isBlank(artist) || song.getArtist().equalsIgnoreCase(artist))
					.filter(song -> StringUtils.isBlank(label) || song.getLabel().equalsIgnoreCase(label))
					.filter(song -> StringUtils.isBlank(date) || song.getDate().equalsIgnoreCase(date))
					.filter(song -> StringUtils.isBlank(genre) || song.getGenre().equalsIgnoreCase(genre))
					.collect(Collectors.toList());
			
			return songs;
		}

		@Override
		public void add(Song song) {
			if (song != null && song.getSongID() == null) {
				song.setSongID(songID++);
				SONG_STORE.put(song.getSongID(), song);
			}
		}

		@Override
		public void update(Song song) {
			if (song != null && song.getSongID() != null) {
				SONG_STORE.put(song.getSongID(), song);
			}
		}

		@Override
		public void delete(Long songID) {
			if (songID != null) {
				SONG_STORE.remove(songID);
		}
	}
}
