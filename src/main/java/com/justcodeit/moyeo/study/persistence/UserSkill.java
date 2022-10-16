package com.justcodeit.moyeo.study.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "userskill")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "user_id")
    private Long userId;

    public UserSkill(Long userId, Long skillId) {
        this.userId = userId;
        this.skillId = skillId;
    }

    public Long getSkillId() {
        return skillId;
    }
}
