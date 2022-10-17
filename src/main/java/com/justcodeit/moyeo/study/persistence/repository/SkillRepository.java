package com.justcodeit.moyeo.study.persistence.repository;

import com.justcodeit.moyeo.study.persistence.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    Skill findByName(String name);

    @Query("SELECT COUNT(s) FROM Skill s WHERE s.id IN (:ids)")
    Long getCountByIds(@Param("ids") List<Long> ids);
}
