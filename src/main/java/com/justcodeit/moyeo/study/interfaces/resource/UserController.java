package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.user.UserService;
import com.justcodeit.moyeo.study.interfaces.dto.user.EditProfileReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.user.MyProfileResDto;
import com.justcodeit.moyeo.study.interfaces.dto.user.ProfileResDto;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "타인의 프로필 보기", description = "다른 회원의 프로필 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResDto> getProfile(@PathVariable String userId) {
        var profileInfo = userService.accessProfile(userId);
        var profileResDto = ProfileResDto.fromProfileInfo(profileInfo);
        return ResponseEntity.ok(profileResDto);
    }

    @Operation(summary = "내 프로필 조회", description = "자신(로그인한 회원)의 프로필 조회")
    @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
    @Secured("ROLE_USER")
    @GetMapping("/me")
    public ResponseEntity<MyProfileResDto> getProfile(
        @Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken) {
        var profileInfo = userService.accessProfile(userToken.getUserId());
        var myProfileResDto = MyProfileResDto.fromProfileInfo(profileInfo);
        return ResponseEntity.ok(myProfileResDto);
    }

    @Operation(summary = "내 프로필 수정 ", description = "자신(로그인한 회원)의 프로필 수정")
    @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
    @Secured("ROLE_USER")
    @PatchMapping("/me")
    public ResponseEntity<Void> editProfile(
        @Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken,
        @RequestBody @Valid EditProfileReqDto editProfileReqDto) {
        userService.editProfile(userToken.getUserId(), editProfileReqDto);
        return ResponseEntity.ok().build();
    }
}