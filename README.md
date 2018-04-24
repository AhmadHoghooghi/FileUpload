* [Project1](https://github.com/AhmadHoghooghi/FileUpload/tree/master/file-upload-jsp-servlet): file upload webapplication with JSP/Servlet
  * MediaType: multipart/form-data
* [Project2](https://github.com/AhmadHoghooghi/FileUpload/tree/master/file-upload-webservice/upload-webservice): file upload webservice

  * MediaType: application/octet-stream
    
* [Project3](https://github.com/AhmadHoghooghi/FileUpload/tree/master/file-upload-java-client/upload-client): client for file upload to webservcie [**Java**] project
* [Project4](https://github.com/AhmadHoghooghi/FileUpload/tree/master/file-upload-webservice-python-client): client for file upload to webservice [**Python**] file



 Task List:
- [x] Test big file upload
- [x] Add Test Using jetty embeded server run by 'maven install'
- [x] Add Logging
- [ ] Test concurrent file receive/write on server
- [x] Comment code
- [x] Add Uploaded file address to response
- [x] Externalize upload path to properties file
- [ ] stop getting path from properties file on each request