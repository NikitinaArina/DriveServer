package com.cloud.drive.controller;

import com.cloud.drive.entity.Image;
import com.cloud.drive.service.interfaces.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("file")
public class FileController {

    private FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public Image uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long id) throws IOException {
        return fileStorageService.save(file, id);
    }

    @PostMapping("/savePhoto")
    public Long uploadPhoto(@RequestParam("photo") MultipartFile file) throws IOException {
        return fileStorageService.save(file);
    }

    @GetMapping("/getImage")
    public byte[] getImage(@RequestParam Long userId) {
        return fileStorageService.getImage(userId);
    }
}
