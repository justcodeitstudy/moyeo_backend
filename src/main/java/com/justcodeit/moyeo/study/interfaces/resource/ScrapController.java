package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.scrap.ScrapService;
import com.justcodeit.moyeo.study.model.inquiry.ScrapQueryDto;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "스크랩", description = "스크랩 CRUD API")
@RestController
@RequestMapping("/scraps")
@RequiredArgsConstructor
public class ScrapController {

  private final ScrapService scrapService;

  // TODO: 무한 스크롤 방식이 아닌 리스트 형식으로 전달해도 되는지
  @Operation(summary = "북마크 리스트 조회", description = "로그인한 유저의 북마크 리스트 조회")
  @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
  @GetMapping
  public ResponseEntity<List<ScrapQueryDto>> getScrapList(@AuthenticationPrincipal UserToken userToken) {
    return ResponseEntity.ok(scrapService.findScrapListByUser(userToken.getUserId()));
  }

  @Operation(summary = "북마크 생성", description = "하나의 모집글에 대한 북마크 생성")
  @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
  @PostMapping
  public ResponseEntity<Void> createScrap(@Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken, @RequestParam String postId) {
    scrapService.makeScrap(userToken.getUserId(), Long.valueOf(postId));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "스크랩 리스트 조회", description = "로그인한 유저의 스크랩 리스트 조회")
  @Parameter(name = "X-MOYEO-AUTH-TOKEN", in = ParameterIn.HEADER, required = true)
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deleteScrap(@Parameter(hidden = true) @AuthenticationPrincipal UserToken userToken, @PathVariable String postId) {
    scrapService.deleteScrap(userToken.getUserId(), Long.valueOf(postId));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}