package com.justcodeit.moyeo.study.application.scrap;

import com.justcodeit.moyeo.study.application.scrap.exception.ScrapCannotFoundException;
import com.justcodeit.moyeo.study.persistence.Scrap;
import com.justcodeit.moyeo.study.persistence.repository.scrap.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapService {

  private final ScrapRepository scrapRepository;

  // TODO : post 가 이미 삭제된 상태에 대한 exception 추가 예정
  @Transactional
  public Long makeScrap(String userId, Long postId) {
    Scrap scrap = new Scrap(userId, postId);
    return scrapRepository.save(scrap).getId();
  }

  @Transactional
  public void deleteScrap(Long scrapId) {
    Scrap scrap = findScrap(scrapId);
    scrapRepository.delete(scrap);
  }

  @Transactional(readOnly = true)
  private Scrap findScrap(Long scrapId) {
    return scrapRepository.findById(scrapId)
            .orElseThrow(() -> new ScrapCannotFoundException(String.format("해당 스크랩을 찾을 수 없습니다 : %s", scrapId)));
  }
}