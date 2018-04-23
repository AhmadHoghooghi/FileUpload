<%-- 
    Document   : index.jsp
    Created on : Apr 19, 2018, 7:46:42 PM
    Author     : Ahmad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>File Upload</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
     <h1>Please Upload Your File</h1>
        <form action="upload" method="post" enctype="multipart/form-data">
            <input type="file" name="file" >
            <input type="submit" value="Submit">
        </form>
</html>
