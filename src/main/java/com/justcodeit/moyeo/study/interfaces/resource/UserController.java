package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.user.UserService;
import com.justcodeit.moyeo.study.application.user.dto.EditUserReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.BaseResponse;
import com.justcodeit.moyeo.study.interfaces.dto.FailureRes;
import com.justcodeit.moyeo.study.interfaces.dto.SuccessRes;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "프로필 조회", description = "자신의 또는 다른 회원의 프로필 조 ")
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse> getProfile(@PathVariable String userId) {
        try {
            var getUserResDto = userService.accessProfile(userId);
            return ResponseEntity.ok(new SuccessRes<>(getUserResDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new FailureRes<>(500, e.getMessage()));
        }
    }

    @Operation(summary = "프로필 수정 ", description = "요청시 사용된 jwt가 유효한지 확인 및 프로필 수정")
    @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
    @Secured("ROLE_USER")
    @PatchMapping("/me")
    public ResponseEntity<BaseResponse> editProfile(@AuthenticationPrincipal UserToken userToken, @RequestBody EditUserReqDto editUserReqDto) {
        try {
            // TODO userId나 unique값을 넘길 필요 있어보임 체크
            var getUserResDto = userService.editProfile(userToken.getUsername(), editUserReqDto);
            return ResponseEntity.ok(new SuccessRes<>(getUserResDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new FailureRes<>(500, e.getMessage()));
        }
    }
}
