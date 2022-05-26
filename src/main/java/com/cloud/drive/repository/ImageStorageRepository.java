package com.cloud.drive.repository;

import com.cloud.drive.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageStorageRepository extends CrudRepository<Image, Long> {

    Image findImageByUserId(Long userId);
}
