package com.cloud.drive.service.interfaces;

import com.cloud.drive.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    Image save(MultipartFile file, Long id) throws IOException;

    byte[] getImage(Long userId);

    Long save(MultipartFile photo) throws IOException;
}
