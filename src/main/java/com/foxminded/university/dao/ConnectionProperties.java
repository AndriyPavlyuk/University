package com.foxminded.university.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionProperties {
	public static String DRIVER;
	public static String URL;
	public static String USER_NAME;
	public static String PASSWORD;
	static{
		Properties properties = new Properties();
		try {
			InputStream dbPropertiesFile = ConnectionProperties.class.getClassLoader().getResourceAsStream("db.properties");
			properties.load(dbPropertiesFile);
			DRIVER = properties.getProperty("db.Driver");
			URL = properties.getProperty("db.Url");
			USER_NAME = properties.getProperty("db.UserName");
			PASSWORD = properties.getProperty("db.Password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
