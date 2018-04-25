package com.rhotiz.upload;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UploadFileServiceTest {
	private Client client;
	private static File file;
	private final String SERVICE_URI = "http://localhost:9090/upload-webservice/bigfileupload";
	private File savePath;

	@BeforeClass
	public static void setFile() throws IOException {
		if (file == null) {
			file = CreateFileUtil.createFileOfSize_InMB(8);
		}
		
		
	}

	@Before
	public void createClient() throws FileNotFoundException, IOException {
		client = ClientBuilder.newClient();
		client.property(ClientProperties.REQUEST_ENTITY_PROCESSING, "CHUNKED");
		client.property(ClientProperties.CHUNKED_ENCODING_SIZE, 4096);
		
		savePath = retrieveSavePath();
		deleteContentOfUploadDirectory(savePath);
	}

	@Test
	public void testHappyPath() throws IOException {
		WebTarget target = client.target(SERVICE_URI);
		InputStream fileInStream = new FileInputStream(file);
		String contentDisposition = "attachment;";
		Response response = target.request(MediaType.APPLICATION_OCTET_STREAM_TYPE)
				.header("Content-Disposition", contentDisposition)
				.header("File-Name", file.getName())
				.post(Entity.entity(fileInStream, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		assertEquals(Response.Status.Family.SUCCESSFUL, response.getStatusInfo().getFamily());
		
	}

	@Test
	public void testUploadWithoutNameHeader() throws IOException {
		WebTarget target = client.target(SERVICE_URI);
		InputStream fileInStream = new FileInputStream(file);
		String contentDisposition = "attachment;";
		Response response = target.request(MediaType.APPLICATION_OCTET_STREAM_TYPE)
				.header("Content-Disposition", contentDisposition)
				.post(Entity.entity(fileInStream, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		assertEquals(Response.Status.Family.SUCCESSFUL, response.getStatusInfo().getFamily());
	}

	@Test
	public void testUploadToSavePathThatDoesNotExist() throws FileNotFoundException, IOException {
		deleteDir(savePath);
		WebTarget target = client.target(SERVICE_URI);
		InputStream fileInStream = new FileInputStream(file);
		String contentDisposition = "attachment;";
		Response response = target.request(MediaType.APPLICATION_OCTET_STREAM_TYPE)
				.header("Content-Disposition", contentDisposition)
				.header("File-Name", file.getName())
				.post(Entity.entity(fileInStream, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		assertEquals(Response.Status.Family.SUCCESSFUL, response.getStatusInfo().getFamily());
	}
	
	@Test
	public void uploadAndOverwriteAFile() throws FileNotFoundException, IOException {
		WebTarget target = client.target(SERVICE_URI);
		InputStream fileInStream = new FileInputStream(file);
		String contentDisposition = "attachment;";
		Response response = target.request(MediaType.APPLICATION_OCTET_STREAM_TYPE)
				.header("Content-Disposition", contentDisposition)
				.header("File-Name", file.getName())
				.post(Entity.entity(fileInStream, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		assertEquals(Response.Status.Family.SUCCESSFUL, response.getStatusInfo().getFamily());
		InputStream fileInStream2 = new FileInputStream(file);
		Response response2 = target.request(MediaType.APPLICATION_OCTET_STREAM_TYPE)
				.header("Content-Disposition", contentDisposition)
				.header("File-Name", file.getName())
				.post(Entity.entity(fileInStream2, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		assertEquals(Response.Status.Family.SUCCESSFUL, response2.getStatusInfo().getFamily());
	}
	

	@After
	public void closeClient() {
		client.close();
	}
	
	@AfterClass
	public static void deleteCreatedFile() {
		file.delete();
	}
	
	
	void deleteDir(File file) {
	    
	    file.delete();
	}
	private File retrieveSavePath() throws FileNotFoundException, IOException {
		Properties savePathProperties = new Properties();
		ClassLoader classLoader = getClass().getClassLoader();
		File propertiesFileObj = new File(classLoader.getResource("savePath.properties").getFile());
		savePathProperties.load(new FileInputStream(propertiesFileObj));
		String savePath = savePathProperties.getProperty("directory");
		File dir = new File(savePath);
		return dir;
	}
	private void deleteContentOfUploadDirectory(File dir){
		File[] contents = dir.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            deleteDir(f);
	        }
	    }
	}

}
