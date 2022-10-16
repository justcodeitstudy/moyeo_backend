package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.scrap.ScrapService;
import com.justcodeit.moyeo.study.interfaces.dto.scrap.ScrapQueryDto;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import com.justcodeit.moyeo.study.persistence.repository.scrap.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final ScrapRepository scrapRepository;

  @GetMapping
  public ResponseEntity<List<ScrapQueryDto>> getScrapList(@AuthenticationPrincipal UserToken userToken) {
    return ResponseEntity.ok(scrapRepository.findScrapListByUserId(userToken.getUserId()));
  }

  @PostMapping
  public ResponseEntity<Long> createScrap(@AuthenticationPrincipal UserToken userToken, @RequestParam String postId) {
    return ResponseEntity.ok(scrapService.makeScrap(userToken.getUserId(), Long.valueOf(postId)));
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteScrap(@RequestParam String scrapId) {
    scrapService.deleteScrap(Long.valueOf(scrapId));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}