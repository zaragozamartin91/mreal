package com.mz.mreal.controller.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @RequestMapping(path = "/meme/{username}/{title}", method = RequestMethod.POST)
    public ResponseEntity<?> postMeme(@RequestParam("image") MultipartFile imageFile) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
