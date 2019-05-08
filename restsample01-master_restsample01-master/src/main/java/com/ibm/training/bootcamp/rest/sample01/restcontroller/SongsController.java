package com.ibm.training.bootcamp.rest.sample01.restcontroller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.domain.Song;
import com.ibm.training.bootcamp.rest.sample01.service.SongService;
import com.ibm.training.bootcamp.rest.sample01.service.SongServiceImpl;

@Path("/songs")
public class SongsController {

		private SongService songService;

		public SongsController() {
			this.songService = new SongServiceImpl();
		}

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<Song> getSongs (
				@QueryParam("title") String title,
				@QueryParam("artist") String artist,
				@QueryParam("label") String label,
				@QueryParam("date") String date,
				@QueryParam("genre") String genre) {

			try {
				List<Song> songs;
				
				if (StringUtils.isAllBlank(title, artist, label, date, genre)) {
					songs = songService.findAll();
				} else {
					songs = songService.findByName(title, artist, label, date, genre);
				}
							
				return songs;
				
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}

		}

		@GET
		@Path("{songID}")
		@Produces(MediaType.APPLICATION_JSON)
		public Song getSong(@PathParam("songID") String songID) {

			try {
				Long longsongID = Long.parseLong(songID);
				Song song = songService.find(longsongID);
				return song;
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}
		}

		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		public Response addSong(Song song) {

			try {
				songService.add(song);
				String result = "Song saved : " + song.getTitle() + " " + song.getArtist()
				 + " " + song.getLabel() + " " + song.getDate() + " " + song.getGenre();
				return Response.status(201).entity(result).build();
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}

		}

		@PUT
		@Consumes(MediaType.APPLICATION_JSON)
		public Response updateSong(Song song) {

			try {
				songService.upsert(song);
				String result = "Song updated : " + song.getTitle() + " " + song.getArtist() + " " + song.getLabel() + " " + song.getDate() + " " + song.getGenre();
				return Response.status(200).entity(result).build();
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}

		}

		@DELETE
		@Path("{songID}")
		public Response deleteSong(@PathParam("songID") String id) {

			try {
				Long longsongID = Long.parseLong(id);
				songService.delete(longsongID);
				String result = "Song deleted";
				return Response.status(200).entity(result).build();
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}
		}

}
