package com.cloud.drive.service.impl;

import com.cloud.drive.entity.Folder;
import com.cloud.drive.entity.Photo;
import com.cloud.drive.repository.FileSystemRepository;
import com.cloud.drive.repository.FolderRepository;
import com.cloud.drive.repository.PhotoRepository;
import com.cloud.drive.service.interfaces.FolderService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final PhotoRepository photoRepository;
    private final FileSystemRepository fileSystemRepository;
    private final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    @Autowired
    public FolderServiceImpl(FolderRepository folderRepository, PhotoRepository photoRepository, FileSystemRepository fileSystemRepository) {
        this.folderRepository = folderRepository;
        this.photoRepository = photoRepository;
        this.fileSystemRepository = fileSystemRepository;
    }

    @Override
    public Folder createFolder(Folder folder) {
        Long id = folder.getUser().getId();

        fileSystemRepository.createDirectoryToUser(id);

        if (folder.getPhoto() != null) {
            Optional<Photo> photoById = photoRepository.findById(folder.getPhoto().getId());
            Photo photo;

            if (photoById.isPresent()) {
                photo = photoById.get();

                folder.setPhoto(photo);
            }
        }

        if (folder.getFolder() != null) {
            return getDataOnLevelHierarchy(folder.getFolder().getId(), id, folder);
        }
        return getDataOnLevelHierarchy(0L, id, folder);
    }

    @NonNull
    private Folder getDataOnLevelHierarchy(Long parentId, Long id, Folder folder) {
        long count = getFiles(id, parentId).stream().map(Folder::getFolderName).filter(x -> x.equals(folder.getFolderName())).count();

        if (count == 0) {
            folder.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
            return folderRepository.save(folder);
        }
        throw new EntityExistsException("Folder with name: " + folder.getFolderName() + " already exist");
    }

    @Override
    public List<Folder> getFiles(Long userId, Long parentId) {
        if (parentId == 0L) {
            return folderRepository.findAllByUserIdAndParentId(userId);
        }
        return folderRepository.findAllByUser_IdAndFolder_Id(userId, parentId);
    }

    @Override
    public void delete(Long id) {
        folderRepository.deleteById(id);
    }

    @Override
    public Folder updateFolder(Folder folder) {
        return folderRepository.save(folder);
    }
}
