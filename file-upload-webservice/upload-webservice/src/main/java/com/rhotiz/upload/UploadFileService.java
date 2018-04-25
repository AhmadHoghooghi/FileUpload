package com.rhotiz.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("")
public class UploadFileService {
	private @Context Application application;
	static Logger LOGGER = LoggerFactory.getLogger(UploadFileService.class);

	@POST
	@Path("/bigfileupload")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response uploadBigFile(InputStream inputStream, @HeaderParam("File-Name") String fileName) {
		LOGGER.info("request received");
		//find and create path to save file
		String savePath = (String) application.getProperties().get("savePath");
		File directory = new File(savePath);
		if (! directory.exists()){
	        directory.mkdirs();
	    }
		//check file name presence in Headers
		String checkedFileName = sanityCheck(fileName);
		String fullFileName = savePath + checkedFileName;
		
		saveToFile(inputStream, fullFileName);
		
		return Response.ok().entity("Successful upload, file is saved to " + fullFileName).build();
	}

	private String sanityCheck(String fileName) {
		if (fileName == null) {
			LOGGER.info("File-Name Header is null");
			fileName = String.valueOf(new Random().nextInt(1000) + ".dat");
		}
		return fileName;
	}

	private void saveToFile(InputStream uploadedInputStream, String fullFileName) {
		File fileObj = new File(fullFileName);
		if (fileObj.exists()) {
			fileObj.delete();
			LOGGER.info("File Exists, It will be overwrite");
		}
		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[4096];

			out = new FileOutputStream(new File(fullFileName));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);// buffered write
			}
			out.flush();
			out.close();
			LOGGER.info("File is saved to {}", fullFileName);
		} catch (IOException e) {
			LOGGER.error("Exception happende while writing file to Disk \r {}", Arrays.toString(e.getStackTrace()));
		}
		
	}
}
