package com.cloud.drive.service.impl;

import com.cloud.drive.entity.Image;
import com.cloud.drive.entity.Photo;
import com.cloud.drive.entity.User;
import com.cloud.drive.repository.ImageStorageRepository;
import com.cloud.drive.repository.PhotoRepository;
import com.cloud.drive.repository.UserRepository;
import com.cloud.drive.service.interfaces.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final ImageStorageRepository imageStorageRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    @Autowired
    public FileStorageServiceImpl(ImageStorageRepository imageStorageRepository, PhotoRepository photoRepository, UserRepository userRepository) {
        this.imageStorageRepository = imageStorageRepository;
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Image save(MultipartFile file, Long id) throws IOException {
        User user = userRepository.findById(id).isEmpty() ? null : userRepository.findById(id).get();
        Image image = imageStorageRepository.findImageByUserId(user.getId());
        if (image != null) {
            image.setImage(file.getBytes());
            return imageStorageRepository.save(image);
        }
        return imageStorageRepository.save(new Image(file.getBytes(), user));
    }

    @Override
    public byte[] getImage(Long userId) {
        return imageStorageRepository.findImageByUserId(userId).getImage();
    }

    @Override
    public Long save(MultipartFile photo) throws IOException {
        Photo response = photoRepository.save(new Photo(photo.getBytes()));
        return response.getId();
    }
}
