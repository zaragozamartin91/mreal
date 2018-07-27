package com.mz.mreal.controller.api.post;

import com.mz.mreal.model.Meme;
import com.mz.mreal.model.MemeRepository;
import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.model.RealityKeeperRepository;
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
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final File imagesDir;
    private final MemeRepository memeRepository;
    private final RealityKeeperRepository realityKeeperRepository;

    @Autowired
    public PostController(@Qualifier("imagesDir") File imagesDir, MemeRepository memeRepository, RealityKeeperRepository realityKeeperRepository) {
        this.imagesDir = imagesDir;
        this.memeRepository = memeRepository;
        this.realityKeeperRepository = realityKeeperRepository;
    }

    @RequestMapping(path = "/meme/{username}/{title}", method = RequestMethod.POST)
    public ResponseEntity<PostResponse> postMeme(@RequestParam("image") MultipartFile imageFile, @PathVariable String username, @PathVariable String title) {
        System.out.println(new File("./").getAbsolutePath());
        try {

            Optional<RealityKeeper> realityKeeper = Optional.ofNullable(realityKeeperRepository.findByUsername(username));
            if (realityKeeper.isPresent()) {
                String normalizedFileName = Optional.ofNullable(imageFile.getOriginalFilename()).orElse("IMAGE").replaceAll(Pattern.quote(" "), "");
                String destFileName = String.format("%d_%s", Calendar.getInstance().getTimeInMillis(), normalizedFileName);
                File dest = new File(imagesDir, destFileName);
                Files.copy(imageFile.getInputStream(), dest.toPath());

                Meme meme = new Meme(title, realityKeeper.get(), destFileName);
                Meme savedMeme = memeRepository.save(meme);

                PostResponse postResponse = new PostResponse(String.format("Post %d : %s subido", savedMeme.getId(), destFileName));
                return new ResponseEntity<>(postResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new PostResponse("El usuario " + username + " no existe"), HttpStatus.NOT_FOUND);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new PostResponse("Error al mover el archivo"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
