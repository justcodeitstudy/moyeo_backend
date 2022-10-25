package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.scrap.ScrapService;
import com.justcodeit.moyeo.study.model.inquiry.ScrapQueryDto;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
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

@RestController
@RequestMapping("/scraps")
@RequiredArgsConstructor
public class ScrapController {

  private final ScrapService scrapService;

  @GetMapping
  public ResponseEntity<List<ScrapQueryDto>> getScrapList(@AuthenticationPrincipal UserToken userToken) {
    return ResponseEntity.ok(scrapService.findScrapListByUser(userToken.getUserId()));
  }

  @PostMapping
  public ResponseEntity<Void> createScrap(@AuthenticationPrincipal UserToken userToken, @RequestParam String postId) {
    scrapService.makeScrap(userToken.getUserId(), Long.valueOf(postId));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deleteScrap(@AuthenticationPrincipal UserToken userToken, @PathVariable String postId) {
    scrapService.deleteScrap(userToken.getUserId(), Long.valueOf(postId));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}