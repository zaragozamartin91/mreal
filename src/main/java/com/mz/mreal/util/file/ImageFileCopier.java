package com.mz.mreal.util.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Service
public class ImageFileCopier {
    private final File imagesDir;

    @Autowired public ImageFileCopier(File imagesDir) {this.imagesDir = imagesDir;}

    public void copy(InputStream inputStream, String destFileName) throws IOException {
        File dest = new File(imagesDir, destFileName);
        Files.copy(inputStream, dest.toPath());
    }
}
