package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.post.PostService;
import com.justcodeit.moyeo.study.application.scrap.ScrapService;
import com.justcodeit.moyeo.study.model.inquiry.PostQueryDto;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import com.justcodeit.moyeo.study.model.inquiry.ScrapQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InquiryController {

  private final PostService postService;
  private final ScrapService scrapService;

  @GetMapping("/posts")
  public ResponseEntity<List<PostQueryDto>> getPostList(@AuthenticationPrincipal UserToken userToken) {
    return ResponseEntity.ok(postService.findPostListByUser(userToken.getUserId()));
  }

  @GetMapping("/scraps")
  public ResponseEntity<List<ScrapQueryDto>> getScrapList(@AuthenticationPrincipal UserToken userToken) {
    return ResponseEntity.ok(scrapService.findScrapListByUser(userToken.getUserId()));
  }
}