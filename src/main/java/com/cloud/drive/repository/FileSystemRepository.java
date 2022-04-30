package com.cloud.drive.repository;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileSystemRepository {
    private final static String RESOURCE_DIR = "Cloud/";
    private final static String USER = "User_";
    private final static Path commonPath = Paths.get(RESOURCE_DIR);
    private final static Path userPath = Paths.get(RESOURCE_DIR + USER);

    @PostConstruct
    public void init() throws IOException {
        if (!Files.exists(commonPath)) {
            Files.createDirectory(commonPath);
        }
    }

    public String save(Long id, byte[] content, String fileName) throws IOException {
        String userFolder = createDirectoryToUser(id);

        Path newFile = Paths.get(userFolder + fileName);
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile.toAbsolutePath()
                .toString();
    }

    private String createDirectoryToUser(Long id) throws IOException {
        Path path = Path.of(userPath + id.toString() + "/");

        if (!Files.exists(path)) {
            Path directory = Files.createDirectory(Paths.get(RESOURCE_DIR));
            return directory.toAbsolutePath().toString();
        } else return Path.of(userPath + id.toString() + "/").toAbsolutePath().toString();
    }
}
