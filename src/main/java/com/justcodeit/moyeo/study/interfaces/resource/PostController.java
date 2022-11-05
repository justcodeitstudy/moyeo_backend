package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.post.PostService;
import com.justcodeit.moyeo.study.interfaces.dto.FailureRes;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostCreateReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostResDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostSearchCondition;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostSimpleResponseDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.RecruitmentStatusReqDto;
import com.justcodeit.moyeo.study.model.inquiry.PostQueryDto;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "모집글", description = "모집글 CRUD API.")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "모집글 작성", description = "로그인한 유저의 모집글 작성")
    @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                content = @Content(schema = @Schema(implementation = PostResDto.class))),
            @ApiResponse(responseCode = "401", description = "401 Unauthorized",
                content = @Content(schema = @Schema(implementation = FailureRes.class)))
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public PostResDto createPost(@RequestBody @Valid PostCreateReqDto postCreateRequestDto,@Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken) {
        Long postId = postService.createPost(postCreateRequestDto, userToken.getUserId());
        return postService.findPost(postId);
    }

    @Operation(summary = "모집글 상세 정보", description = "모집글 단건 조회")
    @Parameter(name = "id",description = "모집글 번호", in = ParameterIn.PATH, required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = @Content(schema = @Schema(implementation = PostResDto.class))),
            @ApiResponse(responseCode = "404", description = "404 Not Found",
                    content = @Content(schema = @Schema(implementation = FailureRes.class)))
    })
    @GetMapping("/{id}")
    public PostResDto findPostById(@PathVariable(name = "id") Long id) {
        return postService.findPost(id);
    }

    @Operation(summary = "모집글 전체 목록", description = "모집글 List 조회")
    @Parameters(value = {
            @Parameter(name = "page", description = "현재 페이지. 0이상 정수",
                    in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(type = "integer", defaultValue = "0")), required = true),
            @Parameter(name = "size", description = "한 page 크기. 기본값 20. 0이상 정수.",
                    in = ParameterIn.QUERY,
                    content = @Content(schema = @Schema(type = "integer", defaultValue = "20")), required = false),
            @Parameter(name = "sort", description = "정렬 기준. 정렬기준이 여러개라면 sort=정렬기준,(ASC|DESC)&...으로 사용",
                    content = @Content(array = @ArraySchema(schema = @Schema(type = "string"))), required = false),
            @Parameter(name = "X-MOYEO-AUTH-TOKEN", description = "JWT 토큰",
                    in = ParameterIn.HEADER, required = false)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success")
    })
    @GetMapping
    public Page<PostQueryDto> findPostAll(@Parameter(hidden = true) @PageableDefault(size = 20) Pageable pageable,
                                          @Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken,
                                          @ParameterObject @ModelAttribute PostSearchCondition searchCondition) {
        String userId = "";
        if(userToken != null) {
            userId = userToken.getUserId();
        }
        return postService.findPostAll(pageable, userId, searchCondition);
    }

    @Operation(summary = "모집글 모집 상태 변경", description = "모집글의 모집 상태를 변경한다. ex) 모집중 -> 모집완료")
    @Parameters(value = {
        @Parameter(name = "id", description = "모집글 번호", in = ParameterIn.PATH, required = true),
        @Parameter(name = "X-MOYEO-AUTH-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER, required = true)
    })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success"),
        @ApiResponse(responseCode = "401", description = "401 Unauthorized",
            content = @Content(schema = @Schema(implementation = FailureRes.class))
        )
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void postRecruitStatusChange(@PathVariable(name = "id") Long postId,
                                        @RequestBody RecruitmentStatusReqDto recruitmentStatusReqDto,
                                        @Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken) {
        postService.postRecruitStatusChange(postId, userToken.getUserId(), recruitmentStatusReqDto);
    }
    @Operation(summary = "모집글 수정", description = "모집글의 내용을 변경한다.")
    @Parameters(value = {
            @Parameter(name = "id", description = "모집글 번호", in = ParameterIn.PATH, required = true),
            @Parameter(name = "X-MOYEO-AUTH-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER, required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "401", description = "401 Unauthorized",
                    content = @Content(schema = @Schema(implementation = FailureRes.class))
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void postModify(@PathVariable(name = "id") Long postId,
                            @Parameter(hidden = true) @RequestBody @Valid PostCreateReqDto postCreateRequestDto,
                            @Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken) {
        postService.postModify(postId, userToken.getUserId(), postCreateRequestDto);
    }

    @Operation(summary = "모집글 삭제", description = "자신이 작성한 모집글 삭제")
    @Parameters(value = {
            @Parameter(name = "id", description = "모집글 번호", in = ParameterIn.PATH, required = true),
            @Parameter(name = "X-MOYEO-AUTH-TOKEN", description = "JWT 토큰", in = ParameterIn.HEADER, required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "401", description = "401 Unauthorized",
                    content = @Content(schema = @Schema(implementation = FailureRes.class))
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void postDelete(@PathVariable(name = "id") Long postId,
                           @Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken) {
        postService.postDelete(postId, userToken.getUserId());
    }

    @Operation(summary = "자신이 작성한 모집글 리스트 조회", description = "로그인한 유저 자신이 작성한 모집글 리스트 조회")
    @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
    @GetMapping("/me")
    public ResponseEntity<List<PostSimpleResponseDto>> findPostListByUser(@Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken) {
        return ResponseEntity.ok(postService.findPostListByUser(userToken.getUserId()));
    }
}