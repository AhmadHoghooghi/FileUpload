package com.rhotiz.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("")
public class UploadFileService {
	@POST
	@Path("/bigfileupload")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response uploadBigFile(InputStream inputStream,@HeaderParam("File-Name") String fileName) {

		// TODO how to get file name when consuming APPLICATION_OCTET_STREAM (from input stream)
		System.out.println("File-Name = "+ fileName);
		if (fileName == null) {
			fileName = String.valueOf(new Random().nextInt(1000));
		}
		String uploadedFileLocation = "C:\\Uploads\\"+fileName;
		System.out.println(uploadedFileLocation);
		// save it
		File objFile = new File(uploadedFileLocation);
		if (objFile.exists()) {
			objFile.delete();
			System.out.println("file is deleted for new upload");
		}

		saveToFile(inputStream, uploadedFileLocation);

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
				//System.out.println(read + "bytes is writen to filex");
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
