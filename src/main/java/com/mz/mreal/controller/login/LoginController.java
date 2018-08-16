package com.mz.mreal.controller.login;

import com.mz.mreal.util.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/login")
@PropertySource("classpath:jwt.properties")
public class LoginController {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    public LoginController(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public @ResponseBody
    LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String username = loginRequest.getUsername();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);

        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setMaxAge(expiration.intValue());
        Cookie usernameCookie = new Cookie("username", username);
        usernameCookie.setMaxAge(expiration.intValue());

        response.addCookie(tokenCookie);
        response.addCookie(usernameCookie);
        return new LoginResponse(token, username);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public void handleUsernameNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no encontrado");
    }
}
