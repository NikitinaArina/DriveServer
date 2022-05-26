package com.cloud.drive.controller;

import com.cloud.drive.entity.Folder;
import com.cloud.drive.service.interfaces.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping(value = "/createFolder")
    public Folder createFolder(@RequestBody Folder folder) {
        return folderService.createFolder(folder);
    }

    @GetMapping("/getFiles")
    public List<Folder> getFiles(@RequestParam Long userId, @RequestParam Long parentId) {
        return folderService.getFiles(userId, parentId);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        folderService.delete(id);
    }

    @PatchMapping("/update")
    public Folder update(@RequestBody Folder folder) {
        return folderService.updateFolder(folder);
    }
}
