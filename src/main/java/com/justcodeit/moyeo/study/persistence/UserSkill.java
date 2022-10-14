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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public UserSkill(User user, Long skillId) {
        this.user = user;
        this.skillId = skillId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
