package com.cloud.drive.service.impl;

import com.cloud.drive.entity.Image;
import com.cloud.drive.entity.User;
import com.cloud.drive.repository.FileStorageRepository;
import com.cloud.drive.service.interfaces.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private FileStorageRepository fileStorageRepository;

    @Autowired
    public FileStorageServiceImpl(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }

    @Override
    public Image save(MultipartFile file, Long id) throws IOException {
        return fileStorageRepository.save(new Image(file.getBytes(), new User(id)));
    }

    @Override
    public byte[] getImage(Long userId) {
        return fileStorageRepository.findImageByUserId(userId).getImage();
    }
}
