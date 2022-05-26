package com.cloud.drive.repository;

import com.cloud.drive.entity.Folder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends CrudRepository<Folder, Long> {

    List<Folder> findAllByUser_IdAndFolder_Id(Long userId, Long parentId);

    @Query(value = "SELECT * FROM Folder where user_id = :userId AND parent_id IS NULL", nativeQuery = true)
    List<Folder> findAllByUserIdAndParentId(Long userId);

    void deleteById(Long id);
}
