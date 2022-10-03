package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.JwtProvider;
import com.justcodeit.moyeo.study.interfaces.dto.SuccessRes;
import com.justcodeit.moyeo.study.interfaces.dto.user.UserMeResponseDto;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

  private final JwtProvider jwtProvider;

  @Operation(summary = "로그인 확인 ", description = "요청시 사용된 jwt가 유효한지 확인 및 본인의 데이터 전달 ")
  @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
  @Secured("ROLE_USER")
  @GetMapping("user/me")
  public ResponseEntity<SuccessRes<UserMeResponseDto>> me(
      @Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken) {
    var me = new UserMeResponseDto(userToken.getUsername(), "void@nowhere.com", userToken.getRole(),
        Collections.singletonList("code_python"));
//    var me = userService.getUser(userToken.getEmail());
    // 요구에 따라 직접 user 테이블을 다녀올지 결정

    return ResponseEntity.ok(new SuccessRes<>(me));
  }

}
