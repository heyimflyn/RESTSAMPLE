package com.ibm.training.bootcamp.rest.sample01.dao;

import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ibm.training.bootcamp.rest.sample01.domain.Developer;

public class DeveloperJdbc implements DeveloperDao {
	
	private static DeveloperJdbc INSTANCE;

	static public DeveloperJdbc getInstance() {

		DeveloperJdbc instance;
		if (INSTANCE != null) {
			instance = INSTANCE;
		} else {
			instance = new DeveloperJdbc();
			INSTANCE = instance;
		}

		return instance;
	}
	
	private DeveloperJdbc() {
		getConnection();
	}
	
	public Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver"); //load driver in the program
			DriverManager.setLoginTimeout(10); //optional
			                                         //constant                //port//database //username //password
			connection = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/dscs","root","root");
		    System.out.println("CONNECTION: " + connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	@Override
	public boolean addDeveloper(Developer adddev) {
		 boolean adev = false;
		
		String sql = "INSERT INTO DEVELOPERS (DEV_ID, FIRSTNAME, MIDDLENAME, LASTNAME, BDAY, POSITION)"
				+ "values (?,?,?,?,?,?)";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql))
		    { 
			ps.setLong(1, adddev.getDevID());
			ps.setString(2, adddev.getFirstname());
			ps.setString(3, adddev.getMiddlename());
			ps.setString(4, adddev.getLastname());
			ps.setDate(5, adddev.getBirthdate());
			ps.setString(6, adddev.getPosition());
			ps.executeUpdate();
			
			//adev=true;
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}return adev;
	}


	@Override
	public boolean delDeveloper(Long devID) {
		
		boolean deldev = false;
		
		String sql = "DELETE FROM DEVELOPERS WHERE DEV_ID = ?";

		try (Connection con = getConnection();
				 PreparedStatement ps = con.prepareStatement(sql);)
			{ 
			ps.setLong(1, devID);
			ps.executeUpdate();

			deldev = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}return deldev;
	}
		
	public boolean getDeveloper(Developer getdev) {
		
		boolean gdev = false;
		
		String sql = "SELECT * FROM DEVELOPERS";
		try  ( Connection con = getConnection();
			   PreparedStatement ps = con.prepareStatement(sql))
		     {
			   ResultSet rs = ps.executeQuery();
              while (rs.next()) {
				//getdev = new Developer();
				getdev.setDevID(rs.getInt("devID"));
				getdev.setFirstname(rs.getString("firstname"));
				getdev.setMiddlename(rs.getString("middlename"));
				getdev.setLastname(rs.getString("lastName"));
				getdev.setBirthdate(rs.getDate("birthdate"));
				getdev.setPosition(rs.getString("position"));
				
				gdev= true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
       }return gdev;
	}

	@Override
	public List<Developer> findAll() {
		List<Developer> devs = new ArrayList<>();

		String sql = "SELECT DEV_ID, FIRSTNAME, MIDDLENAME, LASTNAME, BIRTHDATE, POSITION FROM DEVELOPERS "
				+ "WHERE FIRSTNAME LIKE ? AND MIDDLENAME LIKE ? AND LASTNAME LIKE ? AND BIRTHDATE LIKE ? AND POSITION LIKE ?";

		try (Connection con = getConnection();
			  PreparedStatement ps = con.prepareStatement(sql);)
		    {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Developer dev = new Developer(Long.valueOf(rs.getInt("devID")), 
						                 rs.getString("firstname"),
						                 rs.getString("middlename"),
						                 rs.getString("lastname"),
						                 rs.getDate("birthdate"),
				                         rs.getString("position"));
				                         devs.add(dev);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return devs;
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
	public Developer find(Long devID) {
		
		Developer dev = null;
		
		if (devID != null) { //gosh bat error //0=null dapat
			String sql = "SELECT DEV_ID, FIRSTNAME, LASTNAME FROM DEVELOPERS WHERE DEV_ID = ?";
			try (Connection con = getConnection();
			     PreparedStatement ps = con.prepareStatement(sql);)
			   
				{
				ps.setInt(1, devID.intValue()); //eto din bat error gosh //ps.setInt(1, devID.intValue());
				   ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					dev = new Developer(Long.valueOf(rs.getInt("devID")), 
							            rs.getString("firstname"),
							            rs.getString("lastname"));

				}

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return dev;
	}

	@Override
	public List<Developer> findByName(String firstname, String lastname) {
		List<Developer> devs = new ArrayList<>();

		String sql = "SELECT DEV_ID, FIRSTNAME, LASTNAME FROM DEVELOPERS WHERE FIRSTNAME LIKE ? AND LASTNAME LIKE ?";

		try (Connection con = getConnection();
			  PreparedStatement ps = con.prepareStatement(sql);)
		    {
			ps.setString(1, createSearchValue(firstname));
			ps.setString(2, createSearchValue(lastname));
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Developer dev = new Developer(Long.valueOf(rs.getInt("devID")), 
						                 rs.getString("firstname"),
						                 rs.getString("lastname"));
				                         devs.add(dev);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return devs;
	}

}
