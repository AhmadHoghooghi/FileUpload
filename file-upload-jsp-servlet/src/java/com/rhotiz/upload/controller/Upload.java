/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rhotiz.upload.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Ahmad
 */
@WebServlet(name = "Upload", urlPatterns = {"/upload"})
@MultipartConfig(
        maxFileSize = -1L,//unlimited
        maxRequestSize = -1L,//unLimited
        //considering maximum available ram : 2G,
        //fileSizeThreshold is limited to 256MB
        //TODO: find a way to run application with minimum RAM usage
        fileSizeThreshold = 256 * 1024 * 1024  //256M
        //TODO: make location OS independent
        //wrong on uncreated location will force request.getParameter to return null
        //,location = "C:/Uploads"//set on web.xml
)
public class Upload extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get uploading file in Part object
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        //Retrieve uploading file name
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        //Retrieve proper save location form web.xml file (as context param)
        File saveLocation = new File(getServletContext().getInitParameter("upload.location"));
        //create file
        File file = new File(saveLocation, fileName);
        LocalDateTime uploadStart = LocalDateTime.now();
        //transfer data from part object to file
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        LocalDateTime uploadFinish = LocalDateTime.now();
        Duration uploadDuration = Duration.between(uploadFinish, uploadStart);
        //save time sheet data to request object
        request.setAttribute("uploadStart", uploadStart);
        request.setAttribute("uploadFinish", uploadFinish);
        request.setAttribute("uploadDuration", uploadDuration);
        
        getServletContext().getRequestDispatcher("/result").forward(request, response);
        
    }
}
