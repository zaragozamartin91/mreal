package com.mz.mreal.controller.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @RequestMapping(path = "/meme/{username}/{title}", method = RequestMethod.POST)
    public ResponseEntity<?> postMeme(@RequestParam("image") MultipartFile imageFile, @PathVariable String username, @PathVariable String title) {
        System.out.println(new File("./").getAbsolutePath());
        return new ResponseEntity(HttpStatus.OK);
    }
}
