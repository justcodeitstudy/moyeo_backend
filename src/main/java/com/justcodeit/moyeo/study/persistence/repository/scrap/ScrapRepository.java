package com.justcodeit.moyeo.study.persistence.repository.scrap;

import com.justcodeit.moyeo.study.persistence.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapCustomRepository {

  Optional<Scrap> findByUserIdAndPostId(String userId, Long postId);
  boolean existsByUserIdAndPostId(String userId, Long postId);
}