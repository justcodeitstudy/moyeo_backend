package com.justcodeit.moyeo.study.persistence.repository;

import com.justcodeit.moyeo.study.persistence.PostSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostSkillRepository extends JpaRepository<PostSkill, Long> {
    void deleteAllByPostId(Long postId);
}