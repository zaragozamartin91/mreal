package com.mz.mreal.service;

import com.mz.mreal.model.Meme;
import com.mz.mreal.model.MemeRepository;
import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.model.RealityKeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final RealityKeeperRepository realityKeeperRepository;
    private final MemeRepository memeRepository;

    @Autowired public PostService(RealityKeeperRepository realityKeeperRepository, MemeRepository memeRepository) {
        this.realityKeeperRepository = realityKeeperRepository;
        this.memeRepository = memeRepository;
    }

    @Transactional
    public Meme postMeme(String username, String title, String destFileName, String description) {
        RealityKeeper realityKeeper = realityKeeperRepository.findByUsername(username);
        Meme meme = new Meme(title, realityKeeper, destFileName, description);
        return memeRepository.save(meme);
    }

    /**
     * Obtiene los memes disponibles. Utiliza paginacion mediante query params: Ejemplo: ?page=1.
     *
     * @param pageable Parametro de paginacion inyectado.
     * @return Los memes disponibles.
     */
    @Transactional(readOnly = true)
    public List<Meme> getMemes(Pageable pageable) {
        return memeRepository.findAll(pageable).stream().collect(Collectors.toList());
    }

    @Transactional
    public boolean upvoteMeme(Long id, String username) {
        Meme meme = memeRepository.hasUpvote(id, username);
        if (meme == null) {
            RealityKeeper user = realityKeeperRepository.findByUsername(username);
            user.getUpvotedMemes().add(memeRepository.findById(id).get());
            realityKeeperRepository.save(user);
            return true;
        }
        return false;
    }
}
