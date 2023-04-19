package com.metrics.linebot.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class PropertiesReader {

	static public Properties getProperties() {
		return getProperties(null);
	}
	
    static public Properties getProperties(String configFile) {
    	Properties properties = new Properties();
		if(configFile==null) {
			configFile = "/application.properties";
		}
		
		try {
			properties.load(new InputStreamReader(PropertiesReader.class.getResourceAsStream(configFile), Charset.forName("UTF-8")));
			 
		} catch (FileNotFoundException ex) {
		    ex.printStackTrace();
		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		return properties;
    }
}
