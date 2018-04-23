<%-- 
    Document   : result
    Created on : Apr 18, 2018, 10:02:40 PM
    Author     : Ahmad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css">
    </head>
    <body>
        <h1>Result:</h1>
        <p>${message}</p>
        
        <table border="1">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Value</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Upload Start:</td>
                    <td>${uploadStart}</td>
                </tr>
                <tr>
                    <td>Upload Finish:</td>
                    <td>${uploadFinish}</td>
                </tr>
                <tr>
                    <td>Upload Duration:</td>
                    <td>${uploadDuration}</td>
                </tr>
            </tbody>
        </table>
                <br/>
        <form action="index.jsp">
            <input type="submit" value="Upload an other file">
        </form>
    </body>
</html>
