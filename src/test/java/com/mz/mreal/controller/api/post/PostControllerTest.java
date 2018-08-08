package com.mz.mreal.controller.api.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mz.mreal.controller.api.signup.SignupController;
import com.mz.mreal.controller.api.signup.SignupRequest;
import com.mz.mreal.model.Meme;
import com.mz.mreal.model.MemeRepository;
import com.mz.mreal.model.RealityKeeperRepository;
import com.mz.mreal.service.PostService;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

    @Autowired
    private PostService postService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        signupController.createUser(mockSignupRequest);
    }

    @After
    public void cleanup() {
        realityKeeperRepository.deleteAll();
        memeRepository.deleteAll();
    }

    @Test
    public void postMeme() throws Exception {
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("image", "filename.txt", "text/plain", "some xml".getBytes());

        mockMvc.perform(multipart("/api/meme")
                .file(mockMultipartFile)
                .param("username", mockSignupRequest.getUsername())
                .param("title", "fooTitle")
                .param("description", "fooDescription"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Meme> allMemes = memeRepository.findAll();
        assertTrue(allMemes.size() > 0);

    }

    @Test
    public void upvoteMeme() throws Exception {
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("image", "filename.txt", "text/plain", "some xml".getBytes());

        String username = mockSignupRequest.getUsername();

        mockMvc.perform(multipart("/api/meme")
                .file(mockMultipartFile)
                .param("username", username)
                .param("title", "fooTitle")
                .param("description", "fooDescription"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        List<Meme> allMemes = memeRepository.findAll();
        Meme meme = allMemes.get(0);

        Long memeId = meme.getId();
        String url = String.format("/api/meme/%d/%s", memeId, username);
        mockMvc.perform(post(url))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Meme upvotedMeme = memeRepository.hasUpvote(memeId, username);
        assertNotNull(upvotedMeme);

        int upvoteCount = memeRepository.countUpvotes(upvotedMeme.getId());
        assertTrue(upvoteCount > 0);
    }

    @Test
    public void getMemes() throws Exception {
        List<Meme> memes = new ArrayList<>();
        String username = mockSignupRequest.getUsername();
        int memeCount = 5;
        for (int i = 0; i < memeCount; i++) {
            Meme savedMeme = postService.postMeme(username, "title_" + i, "img_" + i, "desc_" + i);
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