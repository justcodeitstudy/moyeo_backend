package com.justcodeit.moyeo.study.interfaces.resource;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.justcodeit.moyeo.study.ApplicationEntry;
import com.justcodeit.moyeo.study.application.JwtProvider;
import com.justcodeit.moyeo.study.application.oauth.OAuth2AuthenticationSuccessHandler;
import com.justcodeit.moyeo.study.application.oauth.OAuthUserService;
import com.justcodeit.moyeo.study.config.ApplicationConfig;
import com.justcodeit.moyeo.study.config.SecurityConfig;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ActiveProfiles("local")
@WebMvcTest
@ContextConfiguration(classes = {ApplicationEntry.class, ApplicationConfig.class, SecurityConfig.class})
// @WebMvcTest 가 컨트롤러 레이어의 컴포넌트만 생성해주므로 필요한 컴포넌트를 생성해 주는 작업이 필요함
// 무슨말인지 모르겠다면 제외해보고 로그를 확인
// 사실 이쯤되면 @SpringBootTest 를 사용하는거랑 별 차이 없다는게 무슨말인지 이해 될듯
class UserControllerTest {

  //{
  //    "code": 200,
  //    "data": {
  //        "username": null,
  //        "email": "void@nowhere.com",
  //        "role": "ROLE_USER",
  //        "skills": [
  //            "code_python"
  //        ]
  //    }
  //} //현재 리턴되는 json string

  @Autowired
  MockMvc mockMvc;

  @Autowired
  JwtProvider jwtProvider;

  @MockBean
  OAuthUserService oAuthUserService;
  @MockBean
  OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  // 위 두개의 컴포넌트는 @WebMvcTest가 자동으로 생성해주지 않으므로 목빈으로 등록함. 테스트 도중에는 비어있는 객체

  @Test
//  @WithMockUser(username = "mockUser", roles = {"USER"}) // security를 손쉽게 통과할수 있게 해주는 어노테이션 이지만
//  이번 테스트에서는 실제 jwt 가 객체화 된 데이터가 필요하므로 코멘트처리
  void me() throws Exception {
    var username = "randomusername";
    var role = "ROLE_USER";
    var email = "void@nowhere.com";// 현재 더미코드로 하드코딩
    var jwtString = jwtProvider.generate(Map.of("username", username, "role", role));

    MvcResult res = mockMvc.perform(MockMvcRequestBuilders
            .get("/user/me")
            .header("x-moyeo-auth-token", jwtString)
            .contentType(MediaType.APPLICATION_JSON)
        ) // @WithMockUser 설정이 있으니 header의 jwt 가 별의미 없다고 생각할수 있으나,
        //실제로 UserController.me(UserToken token) 메소드의 userToken이 null로 전달되기 때문에 jwtstring을 넘겨줘야함
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.email", equalTo(email)))
        .andExpect(jsonPath("$.data.username", equalTo(username)))
        .andExpect(jsonPath("$.data.role", equalTo(role)))
        .andReturn();

    var resString = res.getResponse().getContentAsString();
    System.out.println(resString);
  }

}