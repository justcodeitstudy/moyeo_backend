package com.justcodeit.moyeo.study.persistence;

import com.justcodeit.moyeo.study.model.type.SkillCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "skill")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_category")
    private SkillCategory skillCategory;
    @Column(name = "folder_name")
    private String folderName;
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "order_num")
    private Integer orderNum;

    @Builder
    private Skill(SkillCategory skillCategory, String folderName, String name, String imageUrl, Integer orderNum) {
        this.skillCategory = skillCategory;
        this.folderName = folderName;
        this.name = name;
        this.imageUrl = imageUrl;
        this.orderNum = orderNum;
    }

    public Skill(SkillCategory skillCategory, String folderName, String name, String imageUrl) {
        this.skillCategory = skillCategory;
        this.folderName = folderName;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Skill update(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        return this;
    }
}
