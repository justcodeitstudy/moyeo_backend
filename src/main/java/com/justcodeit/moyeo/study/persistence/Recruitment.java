package com.justcodeit.moyeo.study.persistence;

import com.justcodeit.moyeo.study.model.post.RecruitType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "recruitment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment { // 모집 분야

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "recruit_type")
    private RecruitType recruitType;
    @Column(name = "recruit_people_num")
    private int recruitPeopleNum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Recruitment(Long id, RecruitType recruitType, int recruitPeopleNum) {
        this.id = id;
        this.recruitType = recruitType;
        this.recruitPeopleNum = recruitPeopleNum;
    }
    public void setPost(Post post) {
        this.post = post;
    }
}
