package com.example.ce216project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileIOController {
    public static final String DOCUMENTS_DIR = System.getProperty("user.home") + File.separator + "Documents";
    public static final String ARTIFACTS_DIR = DOCUMENTS_DIR + File.separator + "artifacts";
    public static final String CONTENT_DIR = ARTIFACTS_DIR + File.separator + "content";
    public static final String IMAGE_DIR = ARTIFACTS_DIR + File.separator + "image";

    //TO BE DEVELOPED
    public static void ImportArtifact(String sourceFilePath){
        try {
            File sourceFile = new File(sourceFilePath);
            if (!sourceFile.exists() || !sourceFile.isFile()) {
                System.out.println("Error: File does not exist or is not a valid file.");
                return;
            }
            createDirectories();
            Path destinationPath = Path.of(CONTENT_DIR, sourceFile.getName());
            Files.copy(sourceFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File successfully imported to: " + destinationPath);
        } catch (IOException e) {
            System.out.println("Error importing file: " + e.getMessage());
        }
    }

    //TO BE IMPLEMENTED
    public static void ExportArtifact(){
    }

    public static void createDirectories() {
        new File(CONTENT_DIR).mkdirs();
        new File(IMAGE_DIR).mkdirs();
    }
}
