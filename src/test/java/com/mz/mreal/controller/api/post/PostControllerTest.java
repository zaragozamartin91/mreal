package com.mz.mreal.controller.api.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mz.mreal.controller.api.signup.SignupController;
import com.mz.mreal.controller.api.signup.SignupRequest;
import com.mz.mreal.model.Meme;
import com.mz.mreal.model.MemeRepository;
import com.mz.mreal.model.RealityKeeper;
import com.mz.mreal.model.RealityKeeperRepository;
import com.mz.mreal.util.json.JsonMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class PostControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private PostController postController;

    @Autowired
    private SignupController signupController;
    private SignupRequest mockSignupRequest = new SignupRequest("bar", "barou");

    @Autowired
    private MemeRepository memeRepository;
    @Autowired
    private RealityKeeperRepository realityKeeperRepository;

    @Autowired
    private JsonMapper jsonMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        signupController.createUser(mockSignupRequest);
    }

    @After
    public void cleanup() {
        memeRepository.deleteAll();
        realityKeeperRepository.deleteAll();
    }

    @Test
    public void postMeme() throws Exception {
        PostController spy = spy(postController);
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("image", "filename.txt", "text/plain", "some xml".getBytes());
        doNothing().when(spy).copyImg(any(), any());

        mockMvc.perform(multipart("/api/meme")
                .file(mockMultipartFile)
                .param("username", mockSignupRequest.getUsername())
                .param("title", "fooTitle")
                .param("description", "fooDescription"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getMemes() throws Exception {
        RealityKeeper owner = realityKeeperRepository.findByUsername(mockSignupRequest.getUsername());
        List<Meme> memes = new ArrayList<>();
        int memeCount = 5;
        for (int i = 0; i < memeCount; i++) {
            Meme savedMeme = memeRepository.save(new Meme("title_" + i, owner, "img_" + i, "desc_" + i));
            memes.add(savedMeme);
        }

        List<MemeJson> memeJsons = memes.stream().map(MemeJson::new).collect(Collectors.toList());
        String jsonMemes = jsonMapper.map(memeJsons);

        mockMvc.perform(get("/api/memes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void fooTest() throws JsonProcessingException {
        jsonMapper = new JsonMapper();
        List<SignupRequest> signupRequests = Arrays.asList(new SignupRequest("foo", "bar"), new SignupRequest("asd", "qwe"));
        String value = jsonMapper.map(signupRequests);
        System.out.println(value);
    }

}