package com.mz.mreal.controller;

import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.model.RealityKeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignupController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RealityKeeperRepository repository;

    @PostMapping("/signup")
    public @ResponseBody
    RealityKeeper createUser(@RequestBody SignupRequest signupRequest) {
        String password = signupRequest.getPassword();
        String username = signupRequest.getUsername();
        String encoded = passwordEncoder.encode(password);
        RealityKeeper realityKeeper = new RealityKeeper(username, encoded);
        return repository.save(realityKeeper);
    }
}
