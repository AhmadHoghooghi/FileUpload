package com.rhotiz.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

public class FileUploadClient {

	public static void main(String[] args) {

		
		File file = new File("C:\\Uploads\\Source\\4M.dat");
		try {
			uploadLargeFile(file);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		
	}
	
	private static void uploadLargeFile(File file) throws FileNotFoundException {
		Client client = ClientBuilder.newClient();
		client.property(ClientProperties.REQUEST_ENTITY_PROCESSING, "CHUNKED");
		client.property(ClientProperties.CHUNKED_ENCODING_SIZE, 4096);
		String SERVICE_URI = "http://localhost:8080/upload-webservice/bigfileupload";
		WebTarget target = client.target(SERVICE_URI); 
		InputStream fileInStream = new FileInputStream(file);
		String contentDisposition = "attachment;";// filename=\"" + file.getName() + "\"";
		System.out.println("sending: " + file.length() + " bytes...");
		Response response = target
		            .request(MediaType.APPLICATION_OCTET_STREAM_TYPE)
		            .header("Content-Disposition", contentDisposition)
		            .header("Content-Length", (int) file.length())
		            .header("File-Name", file.getName())
		            .post(Entity.entity(fileInStream, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		System.out.println("Response status: " + response.getStatus());
	}
}
