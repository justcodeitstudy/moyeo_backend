package com.justcodeit.moyeo.study.interfaces.resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.justcodeit.moyeo.study.application.user.UserService;
import com.justcodeit.moyeo.study.application.user.dto.EditUserReqDto;
import com.justcodeit.moyeo.study.application.user.dto.GetUserResDto;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import com.justcodeit.moyeo.study.persistence.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "local")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    protected UserToken userToken;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

//    @DisplayName("프로필 조회")
//    @Test
//    void accessProfileTest() throws Exception {
//        //given
//        given(userService.accessProfile("userId"))
//        .willReturn(new GetUserResDto(
//                "email",
//                "nickname",
//                "introduction",
//                "portfolio",
//                "skills"
//        ));
//
//        mockMvc.perform(get("/users/userId")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(content().string("{\"code\":200,\"data\":{\"email\":\"email\",\"nickname\":\"nickname\",\"introduction\":\"introduction\",\"portfolio\":\"portfolio\",\"skills\":\"skills\"}}"))
//                .andExpect(status().isOk());
//    }

//    @DisplayName("프로필 수정")
//    @Test
//    @WithMockUser(username = "test", password = "test", roles = "USER")
//
//    void editProfileTest() throws Exception {
//        doNothing().when(userService).editProfile(any(String.class), any(EditUserReqDto.class));
//        Map<String, String> input = new HashMap<>();
//        input.put("nickname", "nicknameedit");
//        input.put("introduction", "introductionedit");
//        input.put("portfolio", "portfolioedit");
//        input.put("skills", "skillsedit");
//
//        mockMvc.perform(patch("/users/me")
//                .with(new UsernamePasswordAuthenticationToken(new UserToken("userId", "email", "display", "USER"), null))
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isOk());
//    }
}