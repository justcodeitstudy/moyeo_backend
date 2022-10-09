package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.user.UserService;
import com.justcodeit.moyeo.study.application.user.dto.EditUserReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.BaseResponse;
import com.justcodeit.moyeo.study.interfaces.dto.SuccessRes;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "다른 사람 프로필 조회", description = "다른 회원의 프로필 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse> getProfile(@PathVariable String userId) {
        var getUserResDto = userService.accessProfile(userId);
        return ResponseEntity.ok(new SuccessRes<>(getUserResDto));
    }

    @Operation(summary = "내 프로필 조회", description = "자신의 프로필 조회")
    @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
    @Secured("ROLE_USER")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse> getProfile(@AuthenticationPrincipal UserToken userToken) {
        var getUserResDto = userService.accessProfile(userToken.getUserId());
        return ResponseEntity.ok(new SuccessRes<>(getUserResDto));
    }

    @Operation(summary = "프로필 수정 ", description = "요청시 사용된 jwt가 유효한지 확인 및 프로필 수정")
    @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
    @Secured("ROLE_USER")
    @PatchMapping("/me")
    public ResponseEntity<BaseResponse> editProfile(@AuthenticationPrincipal UserToken userToken, @RequestBody @Valid EditUserReqDto editUserReqDto) {
        userService.editProfile(userToken.getUserId(), editUserReqDto);
        return ResponseEntity.ok(new SuccessRes<>());
    }

//    @Operation(summary = "프로필 이미지 수정", description = "요청시 사용된 jwt가 유효한지 확인 및 프로필 이미지 수정")
//    @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
//    @Secured("ROLE_USER")
//    @PatchMapping("/me/picture")
//    public ResponseEntity<BaseResponse> editPicture(@AuthenticationPrincipal UserToken userToken, MultipartFile file){
//        userService.editPicture(userToken.getUserId(), file);
//        return ResponseEntity.ok(new SuccessRes<>());
//    }
}