package com.mz.mreal.controller.api.post;

import com.mz.mreal.model.Meme;
import com.mz.mreal.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PostController {
    private final File imagesDir;
    private final PostService postService;

    @Autowired
    public PostController(@Qualifier("imagesDir") File imagesDir, PostService postService) {
        this.imagesDir = imagesDir;
        this.postService = postService;
    }

    @RequestMapping(path = "/meme", method = RequestMethod.POST)
    public ResponseEntity<PostResponse> postMeme(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam("username") String username,
            @RequestParam("title") String title,
            @RequestParam("description") String description) {

        try {
            String normalizedFileName = Optional.ofNullable(imageFile.getOriginalFilename()).orElse("IMAGE").replaceAll(Pattern.quote(" "), "");
            String destFileName = String.format("%d_%s", Calendar.getInstance().getTimeInMillis(), normalizedFileName);
            copyImg(imageFile, destFileName);

            Meme savedMeme = postService.postMeme(username, title, destFileName, description);

            PostResponse postResponse = new PostResponse(String.format("Post %d : %s subido", savedMeme.getId(), destFileName));
            return new ResponseEntity<>(postResponse, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new PostResponse("Error al mover el archivo"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    void copyImg(MultipartFile imageFile, String destFileName) throws IOException {
        File dest = new File(imagesDir, destFileName);
        Files.copy(imageFile.getInputStream(), dest.toPath());
    }

    /**
     * Obtiene los memes disponibles. Utiliza paginacion mediante query params: Ejemplo: ?page=1.
     *
     * @param pageable Parametro de paginacion inyectado.
     * @return Los memes disponibles.
     */
    @GetMapping(path = "/memes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public @ResponseBody
    List<MemeJson> getMemes(@PageableDefault(value = 20, page = 0, direction = Sort.Direction.DESC, sort = { "date" }) Pageable pageable) {
        return postService.getMemes(pageable)
                .stream()
                .map(MemeJson::new)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/meme/{id}/{username}")
    public @ResponseBody
    ResponseEntity<PostResponse> upvoteMeme(@PathVariable Long id, @PathVariable String username) {
        //        memeRepository.upvote(id);
        try {
            postService.upvoteMeme(id, username);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new PostResponse("Error al votar usuario"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new PostResponse("ok"), HttpStatus.OK);
    }
}
