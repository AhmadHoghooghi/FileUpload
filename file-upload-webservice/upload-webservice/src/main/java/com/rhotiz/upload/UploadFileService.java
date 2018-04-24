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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("")
public class UploadFileService {
	static Logger LOGGER = LoggerFactory.getLogger(UploadFileService.class);
	
	@POST
	@Path("/bigfileupload")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response uploadBigFile(InputStream inputStream,@HeaderParam("File-Name") String fileName) {
		LOGGER.info("request received");
		// TODO how to get file name when consuming APPLICATION_OCTET_STREAM (from input stream)
		if (fileName == null) {
			LOGGER.info("File-Name Header is null");
			fileName = String.valueOf(new Random().nextInt(1000));
		}
		String uploadedFileLocation = "C:\\Uploads\\"+fileName;
		// save it
		File objFile = new File(uploadedFileLocation);
		if (objFile.exists()) {
			objFile.delete();
			LOGGER.info("File Exists, It will be overwrite");
		}

		saveToFile(inputStream, uploadedFileLocation);
		LOGGER.info("File is saved with name: {}",fileName);
		return Response.ok().entity("Successful big file upload").build();
	}

	private void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[4096];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);// buffered write
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			LOGGER.error("Exception happende while writing file to Disk \r {}",Arrays.toString(e.getStackTrace()));
		}
	}
}
