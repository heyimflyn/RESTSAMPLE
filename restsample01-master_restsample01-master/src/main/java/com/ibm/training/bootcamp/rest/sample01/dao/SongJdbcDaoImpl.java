package com.ibm.training.bootcamp.rest.sample01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.jdbc.JDBCDataSource;

import com.ibm.training.bootcamp.rest.sample01.domain.Song;

public class SongJdbcDaoImpl implements SongDao {
	
	private static SongJdbcDaoImpl INSTANCE;
	
	private JDBCDataSource dataSource;

	static public SongJdbcDaoImpl getInstance() {

		SongJdbcDaoImpl instance;
		if (INSTANCE != null) {
			instance = INSTANCE;
		} else {
			instance = new SongJdbcDaoImpl();
			INSTANCE = instance;
		}

		return instance;
	}

	private SongJdbcDaoImpl() {
		init();
	}
	
	private void init() {
		dataSource = new JDBCDataSource();
		dataSource.setDatabase("jdbc:hsqldb:mem:SONG");
		dataSource.setUser("username");
		dataSource.setPassword("password");
		
		createSongTable();
		insertInitSongs();

	}
	
	private void createSongTable() {
		String createSql = "CREATE TABLE SONGS " + "(songID INTEGER IDENTITY PRIMARY KEY, " + " title VARCHAR(255), " 
				+ " artist VARCHAR(255), " + " label VARCHAR(255), " + " date VARCHAR(255), " + " genre VARCHAR(255))";

		try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

			stmt.executeUpdate(createSql);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void insertInitSongs() {


		add(new Song("Bad Guy", "Billie Eilish", "Interscope", "April 2019", "Indie Pop"));
		add(new Song("Ilomilo",  "Billie Eilish", "Interscope", "April 2019", "Indie Pop"));
		add(new Song("Bury a Friend", "Billie Eilish", "Interscope", "April 2019", "Indie Pop"));
		add(new Song("High Hopes", "Panic! at the Disco", "Fueled by Ramen", "September 2018", "Alternative Rock"));
		add(new Song("Hey Look Ma, I Made It", "Panic! at the Disco", "Fueled by Ramen", "September 2018", "Alternative Rock"));
		
	}
	@Override
	public List<Song> findAll() {

		return findByName(null, null, null, null, null);
	}

	@Override
	public Song find(Long songID) {

		Song song = null;

		if (songID != null) {
			String sql = "SELECT songID, title, artist, label, date, genre FROM SONGS where songID = ?";
			try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

				ps.setInt(1, songID.intValue());
				ResultSet results = ps.executeQuery();

				if (results.next()) {
					song = new Song(Long.valueOf(results.getInt("songID")), results.getString("title"),
							results.getString("artist"), results.getString("label"), results.getString("date"), results.getString("genre"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return song;
	}
	@Override
	public List<Song> findByName(String title, String artist, String label, String date, String genre) {
		List<Song> songs = new ArrayList<>();

		String sql = "SELECT songID, title, artist, label, date, genre FROM SONGS WHERE title LIKE ? AND artist LIKE ? AND label LIKE ? AND date LIKE ? AND genre LIKE ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, createSearchValue(title));
			ps.setString(2, createSearchValue(artist));
			ps.setString(3, createSearchValue(label));
			ps.setString(4, createSearchValue(date));
			ps.setString(5, createSearchValue(genre));
			
			ResultSet results = ps.executeQuery();

			while (results.next()) {
				Song song = new Song(Long.valueOf(results.getInt("songID")), results.getString("title"),
						results.getString("artist"), results.getString("label"), results.getString("date"), results.getString("genre"));
						songs.add(song);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return songs;
	}

	private String createSearchValue(String string) {
		
		String value;
		
		if (StringUtils.isBlank(string)) {
			value = "%";
		} else {
			value = string;
		}
		
		return value;
	}
	
	@Override
	public void add(Song song) {
		
		String insertSql = "INSERT INTO SONGS (title, artist, label, date, genre) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {

			ps.setString(1, song.getTitle());
			ps.setString(2, song.getArtist());
			ps.setString(3, song.getLabel());
			ps.setString(4, song.getDate());
			ps.setString(5, song.getGenre());
	
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Song song) {
		String updateSql = "UPDATE song SET title = ?, artist = ?, label = ?, date = ?, genre = ? WHERE songID = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setString(1, song.getTitle());
			ps.setString(2, song.getArtist());
			ps.setString(3, song.getLabel());
			ps.setString(4, song.getDate());
			ps.setString(5, song.getGenre());
			ps.setLong(6, song.getSongID());
			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Long songID) {
		String updateSql = "DELETE FROM songs WHERE songID = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql)) {

			ps.setLong(1, songID);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
