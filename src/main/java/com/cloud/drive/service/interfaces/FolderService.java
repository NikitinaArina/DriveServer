package com.cloud.drive.service.interfaces;

import com.cloud.drive.entity.Folder;

import java.util.List;

public interface FolderService {

    Folder createFolder(Folder folder);

    List<Folder> getFiles(Long userId, Long parentId);

    void delete(Long id);

    Folder updateFolder(Folder folder);
}
