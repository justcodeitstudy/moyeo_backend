package com.justcodeit.moyeo.study.model.post;

import com.justcodeit.moyeo.study.persistence.SkillCategory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recuitment")
public class Recruitment { // 모집 분야

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private SkillCategory skillCategory;
    private Integer recruitPeopleNum;
    @Column(name = "post_id")
    private Long postId;
}