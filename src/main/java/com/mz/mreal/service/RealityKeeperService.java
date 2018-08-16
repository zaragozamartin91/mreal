package com.mz.mreal.service;

import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.model.RealityKeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RealityKeeperService {
    private final PasswordEncoder passwordEncoder;
    private final RealityKeeperRepository repository;

    @Autowired
    public RealityKeeperService(PasswordEncoder passwordEncoder, RealityKeeperRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Transactional
    public RealityKeeper createUser(String username, String plainPassword) {
        String encodedPassword = passwordEncoder.encode(plainPassword);
        RealityKeeper realityKeeper = new RealityKeeper(username, encodedPassword);
        return repository.save(realityKeeper);
    }

    @Transactional
    public void deleteUser(RealityKeeper realityKeeper) {
        repository.delete(realityKeeper);
    }
}
