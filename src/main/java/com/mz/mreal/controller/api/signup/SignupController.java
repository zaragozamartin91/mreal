package com.mz.mreal.controller.api.signup;

import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.model.RealityKeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SignupController {
    private final PasswordEncoder passwordEncoder;
    private final RealityKeeperRepository repository;

    @Autowired
    public SignupController(PasswordEncoder passwordEncoder, RealityKeeperRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

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
