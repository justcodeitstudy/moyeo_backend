package com.justcodeit.moyeo.study.application.scrap;

import com.justcodeit.moyeo.study.application.post.exception.PostCannotFoundException;
import com.justcodeit.moyeo.study.application.scrap.exception.PostAlreadyDeletedException;
import com.justcodeit.moyeo.study.application.scrap.exception.PostAlreadyScrappedException;
import com.justcodeit.moyeo.study.application.scrap.exception.ScrapCannotFoundException;
import com.justcodeit.moyeo.study.application.scrap.exception.ScrapNotAuthorizedException;
import com.justcodeit.moyeo.study.interfaces.dto.scrap.ScrapResponseDto;
import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.Scrap;
import com.justcodeit.moyeo.study.persistence.repository.PostRepository;
import com.justcodeit.moyeo.study.persistence.repository.scrap.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapService {

  private final ScrapRepository scrapRepository;
  private final PostRepository postRepository;

  @Transactional
  public Long makeScrap(String userId, Long postId) {
    Post post = postRepository.findById(postId)
            .orElseThrow(PostCannotFoundException::new);

    validatePostByUserId(userId, post);
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
  public List<ScrapResponseDto> findScrapListByUser(String userId) {
    return scrapRepository.findScrapListByUserId(userId).stream()
            .map(dto -> new ScrapResponseDto(dto.getId(), dto.getPostId(), dto.getTitle(), dto.getCreatedAt(), dto.getViewCount(), dto.getPostSkills()))
            .collect(Collectors.toList());
  }

  private void validatePostByUserId(String userId, Post post) {
    if (post.getPostStatus() != PostStatus.NORMAL) {
      throw new PostAlreadyDeletedException("이미 삭제된 모집글입니다");
    }

    if (scrapRepository.existsByUserIdAndPostId(userId, post.getId())) {
      throw new PostAlreadyScrappedException("이미 스크랩이 완료된 모집글입니다");
    }
  }
}