package com.ravi.movieflex.controller;

import com.ravi.movieflex.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @Value("${project.poster}")
    private String path;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile multipartFile) throws IOException {

       String fileName = fileService.uploadFile(path,multipartFile);
        return ResponseEntity.ok(fileName + " : uploaded Successfully");
    }

    @GetMapping("/{fileName}")
    public void downloadFile(@PathVariable String fileName, HttpServletResponse httpServletResponse) throws IOException {
        InputStream resourceFile = fileService.getResourceFile(path,fileName);
        httpServletResponse.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile,httpServletResponse.getOutputStream());
    }
}
