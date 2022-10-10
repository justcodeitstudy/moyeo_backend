package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.user.UserService;
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

    @Operation(summary = "다른 회원 프로필 조회", description = "다른 회원의 프로필 조회")
    @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
    @Secured("ROLE_USER")
    @GetMapping("/{userId}")
    public ResponseEntity getProfile(@PathVariable String userId) {
        var getUserResDto = userService.accessProfile(userId);
        return ResponseEntity.ok(getUserResDto);
    }

    @Operation(summary = "내 프로필 조회", description = "자신의 프로필 조회")
    @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
    @Secured("ROLE_USER")
    @GetMapping("/me")
    public ResponseEntity getProfile(@AuthenticationPrincipal UserToken userToken) {
        // TODO userToken 작업한 것 병합 후 "userId" > userToken.getUserId()로 변경
        var getUserResDto = userService.accessProfile("userId");
        return ResponseEntity.ok(getUserResDto);
    }
}
