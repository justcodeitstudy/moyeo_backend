package com.justcodeit.moyeo.study.interfaces.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justcodeit.moyeo.study.application.JwtProvider;
import com.justcodeit.moyeo.study.application.user.UserService;
import com.justcodeit.moyeo.study.application.user.dto.EditUserReqDto;
import com.justcodeit.moyeo.study.application.user.dto.GetUserResDto;
import com.justcodeit.moyeo.study.common.RandomIdUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({ "local", "aws" })
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest
//@ContextConfiguration(classes = {ApplicationEntry.class, ApplicationConfig.class, SecurityConfig.class, S3Config.class})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    ObjectMapper objectMapper;

//    @MockBean
//    OAuthUserService oAuthUserService;
//    @MockBean
//    OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
//    @MockBean
//    SkillService skillService;

    @MockBean
    UserService userService;
    // 컴포넌트는 @WebMvcTest가 자동으로 생성해주지 않으므로 목빈으로 등록함. 테스트 도중에는 비어있는 객체

    String userId;
    String username;
    String role;
    String email;
    String picture;
    String nickname;
    String introduction;
    String jwtString;

    @BeforeEach
    void setUp() {
        userId = new RandomIdUtil().userId();
        username = "randomusername";
        role = "ROLE_USER";
        email = "void@nowhere.com";// 현재 더미코드로 하드코딩
        picture = "picture";
        nickname = "nickname";
        introduction = "introduction";
        jwtString = jwtProvider.generate(Map.of(
                "userId", userId,
                "email", email,
                "displayName", username,
                "role", role));
    }

    @Test
    // @WithMockUser(username = "mockUser", roles = {"USER"}) // security를 손쉽게 통과할수 있게 해주는 어노테이션 이지만
    //  이번 테스트에서는 실제 jwt 가 객체화 된 데이터가 필요하므로 코멘트처리
    void me() throws Exception {
        given(userService.accessProfile(userId)).willReturn(new GetUserResDto(
          email,picture, nickname, introduction, new ArrayList<Long>()
        ));

        MvcResult res = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/me")
                        .header("X-MOYEO-AUTH-TOKEN", jwtString)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                // @WithMockUser 설정이 있으니 header의 jwt 가 별의미 없다고 생각할수 있으나,
                //실제로 UserController.me(UserToken token) 메소드의 userToken이 null로 전달되기 때문에 jwtstring을 넘겨줘야함
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo(email)))
                .andExpect(jsonPath("$.picture", equalTo(picture)))
                .andExpect(jsonPath("$.nickname", equalTo(nickname)))
                .andExpect(jsonPath("$.introduction", equalTo(introduction)))
                .andExpect(jsonPath("$.skillIds", equalTo(new ArrayList<Long>())))
                .andReturn();
    }

    @Test
    void getProfile() throws Exception {
        given(userService.accessProfile(userId)).willReturn(new GetUserResDto(
                email,picture, nickname, introduction, new ArrayList<Long>()
        ));

        MvcResult res = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/" + userId)
                        .header("X-MOYEO-AUTH-TOKEN", jwtString)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo(email)))
                .andExpect(jsonPath("$.picture", equalTo(picture)))
                .andExpect(jsonPath("$.nickname", equalTo(nickname)))
                .andExpect(jsonPath("$.introduction", equalTo(introduction)))
                .andExpect(jsonPath("$.skillIds", equalTo(new ArrayList<Long>())))
                .andReturn();
    }

    @Test
    void edit() throws Exception {
        doNothing().when(userService).editProfile(any(String.class),any(EditUserReqDto.class));
        String requestBody = objectMapper.writeValueAsString(Map.of("nickname","nicknameedit", "introduction","intro","skillIds", List.of(1L,2L)));
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/users/me")
                        .content(requestBody)
                        .header("X-MOYEO-AUTH-TOKEN", jwtString)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
    }
}