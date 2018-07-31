package com.mz.mreal.controller.api.post;

import com.mz.mreal.model.Meme;
import com.mz.mreal.model.MemeRepository;
import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.model.RealityKeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
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

    @RequestMapping(path = "/meme", method = RequestMethod.POST)
    public ResponseEntity<PostResponse> postMeme(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam("username") String username,
            @RequestParam("title") String title,
            @RequestParam("description") String description) {

        try {

            Optional<RealityKeeper> realityKeeper = Optional.ofNullable(realityKeeperRepository.findByUsername(username));
            if (realityKeeper.isPresent()) {
                String normalizedFileName = Optional.ofNullable(imageFile.getOriginalFilename()).orElse("IMAGE").replaceAll(Pattern.quote(" "), "");
                String destFileName = String.format("%d_%s", Calendar.getInstance().getTimeInMillis(), normalizedFileName);

                copyImg(imageFile, destFileName);

                Meme meme = new Meme(title, realityKeeper.get(), destFileName, description);
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
    public @ResponseBody
    List<MemeJson> getMemes(@PageableDefault(value = 20, page = 0, direction = Sort.Direction.DESC, sort = {"date"}) Pageable pageable) {
        return memeRepository.findAll(pageable).stream().map(MemeJson::new).collect(Collectors.toList());
    }
}
