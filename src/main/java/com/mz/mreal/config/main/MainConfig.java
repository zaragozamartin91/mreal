package com.mz.mreal.config.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;


@Configuration
public class MainConfig {
    @Bean
    public File imagesDir() {
        Path path = FileSystems.getDefault().getPath("img");
        return path.toFile();
    }

    @PostConstruct
    private void createImagesDir() throws IOException {
        File imagesDir = imagesDir();
        System.out.println("Creando directorio de imagenes " + imagesDir);
        if (imagesDir.exists()) return;

        Files.createDirectory(imagesDir.toPath());
        System.out.println("Directorio de imagenes creado");
    }
}
