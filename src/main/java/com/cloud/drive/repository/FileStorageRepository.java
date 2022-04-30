package com.cloud.drive.repository;

import com.cloud.drive.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends CrudRepository<Image, Long> {

    Image findImageByUserId(Long userId);
}
