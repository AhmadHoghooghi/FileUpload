package com.rhotiz.upload;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("")
public class UploadApplication extends Application {
	Set<Object> singletons = new HashSet<>();

	public UploadApplication() {
		singletons.add(new UploadFileService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
