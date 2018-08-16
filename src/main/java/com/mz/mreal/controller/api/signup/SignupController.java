package com.mz.mreal.controller.api.signup;

import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.model.RealityKeeperRepository;
import com.mz.mreal.service.RealityKeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SignupController {
    private final RealityKeeperService realityKeeperService;

    @Autowired
    public SignupController(RealityKeeperService realityKeeperService) {
        this.realityKeeperService = realityKeeperService;
    }

    @PostMapping("/signup")
    public @ResponseBody
    SignupResponse createUser(@RequestBody SignupRequest signupRequest) {
        System.out.println("SignupRequest: " + signupRequest);
        String password = signupRequest.getPassword();
        String username = signupRequest.getUsername();
        RealityKeeper realityKeeper = realityKeeperService.createUser(username, password);
        return new SignupResponse(realityKeeper);
    }
}
