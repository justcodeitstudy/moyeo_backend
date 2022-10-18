package com.justcodeit.moyeo.study.application.scrap;

import com.justcodeit.moyeo.study.application.post.exception.PostCannotFoundException;
import com.justcodeit.moyeo.study.application.scrap.exception.PostAlreadyDeletedException;
import com.justcodeit.moyeo.study.application.scrap.exception.ScrapCannotFoundException;
import com.justcodeit.moyeo.study.model.scrap.ScrapQueryDto;
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

    if (post.getPostStatus() == PostStatus.DELETE) {
      throw new PostAlreadyDeletedException();
    }
    Scrap scrap = new Scrap(userId, post.getId());

    return scrapRepository.save(scrap).getId();
  }

  @Transactional
  public void deleteScrap(Long scrapId) {
    Scrap scrap = findScrap(scrapId);
    Post post = postRepository.findById(scrap.getPostId())
            .orElseThrow(PostCannotFoundException::new);

    if (post.getPostStatus() == PostStatus.DELETE) {
      throw new PostAlreadyDeletedException();
    }
    scrapRepository.delete(scrap);
  }

  @Transactional(readOnly = true)
  public List<ScrapQueryDto> findScrapListByUser(String userId) {
    return scrapRepository.findScrapListByUserId(userId);
  }

  @Transactional(readOnly = true)
  private Scrap findScrap(Long scrapId) {
    return scrapRepository.findById(scrapId)
            .orElseThrow(() -> new ScrapCannotFoundException(String.format("해당 스크랩을 찾을 수 없습니다 : %s", scrapId)));
  }
}