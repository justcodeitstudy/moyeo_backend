package com.justcodeit.moyeo.study.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userskill",
    indexes = {
        @Index(columnList = "user_id")
    }
)
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
}
