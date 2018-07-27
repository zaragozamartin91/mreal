package com.mz.mreal.controller.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final File imagesDir;

    @Autowired
    public PostController(@Qualifier("imagesDir") File imagesDir) {this.imagesDir = imagesDir;}

    @RequestMapping(path = "/meme/{username}/{title}", method = RequestMethod.POST)
    public ResponseEntity<PostResponse> postMeme(@RequestParam("image") MultipartFile imageFile, @PathVariable String username, @PathVariable String title) {
        System.out.println(new File("./").getAbsolutePath());
        try {
            String destFileName = Calendar.getInstance().getTimeInMillis() + "_" + imageFile.getOriginalFilename();
            File dest = new File(imagesDir, destFileName);
            Files.copy(imageFile.getInputStream(), dest.toPath());
            return new ResponseEntity<>(new PostResponse("Post " + destFileName + " subido"), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new PostResponse("Error al mover el archivo"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
