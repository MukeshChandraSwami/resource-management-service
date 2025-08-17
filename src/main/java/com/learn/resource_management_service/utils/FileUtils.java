package com.learn.resource_management_service.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtils {

    public static String uploadFile(MultipartFile file, String uploadDir) throws IOException {
        // Ensure upload directory exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate unique filename
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Save file locally
        Files.write(filePath, file.getBytes());

        // Build file URL (served later by ResourceHandler)
        return  "/files/" + fileName;
    }
}
