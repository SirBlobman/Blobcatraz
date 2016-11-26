package com.SirBlobman.blobcatraz.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.bukkit.configuration.file.YamlConfiguration;

import com.SirBlobman.blobcatraz.config.ConfigMySQL;

public class MySQL
{
	private Connection connection;
	private YamlConfiguration sql = ConfigMySQL.load();
	private String host = sql.getString("hostname");
	private String database = sql.getString("database");
	private String username = sql.getString("username");
	private String password = sql.getString("password");
	private int port = sql.getInt("port");
	
	public MySQL() {}
	
	public void openConnection()
	{
		try
		{
			if(connection != null && !connection.isClosed()) return;
			
			synchronized(this)
			{
				if(connection != null && !connection.isClosed()) return;
				
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection
				(
					"jdbc:mysql://" + host + ":" + port + "/" + database, 
					username, 
					password
				);
			}
		}
		catch (Exception ex) {ex.printStackTrace();}
	}
	
	public void set(String uuid, String value)
	{
		try
		{
			openConnection();
			Statement st = connection.createStatement();
			st.executeUpdate("INSERT INTO PlayerData (UUID, BALANCE) VALUES ('" + uuid + "', " + value + ");");
		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}