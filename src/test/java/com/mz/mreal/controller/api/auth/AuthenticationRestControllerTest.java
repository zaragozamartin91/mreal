package com.mz.mreal.controller.api.auth;

import com.mz.mreal.config.security.JwtUserDetailsService;
import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.service.RealityKeeperService;
import com.mz.mreal.util.json.JsonMapper;
import com.mz.mreal.util.jwt.JwtTokenUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@PropertySource("classpath:jwt.properties")
public class AuthenticationRestControllerTest {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private RealityKeeperService realityKeeperService;

    @Autowired
    private JsonMapper jsonMapper;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String username = "foo";
    private String password = "password";
    private RealityKeeper testUser;

    @Before
    public void setup() {
        mockMvc = Optional.ofNullable(mockMvc).orElse(MockMvcBuilders.webAppContextSetup(webApplicationContext).build());
        testUser = realityKeeperService.createUser(username, password);
    }

    @After
    public void cleanup() {
        realityKeeperService.deleteUser(testUser);
    }


    @Test
    public void createAuthenticationTokenOk() throws Exception {
        JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest(username, password);
        String jsonAuthRequest = jsonMapper.map(authenticationRequest);

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAuthRequest))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void createAuthenticationTokenWithNonExistingUser() throws Exception {
        JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest("WRONG_USERNAME", password);
        String jsonAuthRequest = jsonMapper.map(authenticationRequest);

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAuthRequest))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

    @Test
    public void createAuthenticationTokenWithWrongPassword() throws Exception {
        JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest(username, "WRONG_PASSWORD");
        String jsonAuthRequest = jsonMapper.map(authenticationRequest);

        mockMvc.perform(post("/api/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAuthRequest))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

    @Test
    public void refreshAndGetAuthenticationToken() throws Exception {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(testUser.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        String authorizationHeaderValue = "Bearer " + token;

        mockMvc.perform(post("/api/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .header(tokenHeader, authorizationHeaderValue))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}