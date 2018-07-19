package com.mz.mreal.controller.login;

import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.model.RealityKeeperRepository;
import com.mz.mreal.util.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/login")
public class LoginController {
    private final JwtTokenUtil jwtTokenUtil;
    private final RealityKeeperRepository repository;

    @Autowired
    public LoginController(JwtTokenUtil jwtTokenUtil, RealityKeeperRepository repository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.repository = repository;
    }

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping
    public ModelAndView login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        RealityKeeper user = repository.findByUsername(username);

        if (user == null) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("message", "No existe el usuario " + username);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("index");
            return modelAndView;
        }
    }
}
