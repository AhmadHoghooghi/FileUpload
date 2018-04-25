package com.rhotiz.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("")
public class UploadApplication extends Application {
	Set<Object> singletons = new HashSet<>();
	Map<String,Object> properties = new HashMap<>();

	public UploadApplication() {
		singletons.add(new UploadFileService());
		properties.put("savePath", retrieveSavePath());
	}
	
	
	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}


	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	private String retrieveSavePath() {
		String path = "";
		// get proper save path form properties file
		Properties savePathProperties = new Properties();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File propertiesFileObj = new File(classLoader.getResource("savePath.properties").getFile());
			savePathProperties.load(new FileInputStream(propertiesFileObj));
			path = savePathProperties.getProperty("directory", ".\\");
		} catch (IOException e) {
			path = ".\\";
		}
		if (!path.endsWith("\\")) {
			path = path+"\\";
		}
		File directory = new File(path);
		if (! directory.exists()){
	        directory.mkdirs();
	    }
		return path;
	}
}
