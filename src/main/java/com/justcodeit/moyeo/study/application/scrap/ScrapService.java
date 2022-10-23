package com.justcodeit.moyeo.study.application.scrap;

import com.justcodeit.moyeo.study.application.post.exception.PostCannotFoundException;
import com.justcodeit.moyeo.study.application.scrap.exception.PostAlreadyDeletedException;
import com.justcodeit.moyeo.study.application.scrap.exception.PostAlreadyScrappedException;
import com.justcodeit.moyeo.study.application.scrap.exception.ScrapCannotFoundException;
import com.justcodeit.moyeo.study.application.scrap.exception.ScrapNotAuthorizedException;
import com.justcodeit.moyeo.study.model.inquiry.ScrapQueryDto;
import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.Scrap;
import com.justcodeit.moyeo.study.persistence.repository.PostRepository;
import com.justcodeit.moyeo.study.persistence.repository.scrap.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapService {

  private final ScrapRepository scrapRepository;
  private final PostRepository postRepository;

  @Transactional
  public Long makeScrap(String userId, Long postId) {
    Post post = postRepository.findById(postId)
            .orElseThrow(PostCannotFoundException::new);

    if (post.getPostStatus() != PostStatus.NORMAL) {
      throw new PostAlreadyDeletedException("이미 삭제된 모집글입니다");
    }

    if (scrapRepository.findByUserIdAndPostId(userId, post.getId()).isPresent()) {
      throw new PostAlreadyScrappedException("이미 스크랩이 완료된 모집글입니다");
    }
    Scrap scrap = new Scrap(userId, post.getId());
    return scrapRepository.save(scrap).getId();
  }

  @Transactional
  public void deleteScrap(String userId, Long postId) {
    Post post = postRepository.findById(postId)
            .orElseThrow(PostCannotFoundException::new);
    Scrap scrap = scrapRepository.findByUserIdAndPostId(userId, post.getId())
            .orElseThrow(ScrapCannotFoundException::new);

    if (!scrap.getUserId().equals(userId)) {
      throw new ScrapNotAuthorizedException();
    }
    scrapRepository.delete(scrap);
  }

  @Transactional(readOnly = true)
  public List<ScrapQueryDto> findScrapListByUser(String userId) {
    return scrapRepository.findScrapListByUserId(userId);
  }

  private Scrap findScrap(Long scrapId) {
    return scrapRepository.findById(scrapId)
            .orElseThrow(() -> new ScrapCannotFoundException(String.format("해당 스크랩을 찾을 수 없습니다 : %s", scrapId)));
  }
}