package com.justcodeit.moyeo.study.persistence;

import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import com.justcodeit.moyeo.study.model.post.PostType;
import com.justcodeit.moyeo.study.model.post.ProgressType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @Enumerated(EnumType.STRING)
    private RecruitStatus recruitStatus;
    @Enumerated(EnumType.STRING)
    private ProgressType progressType;
    private String contactInfo;
    private PostStatus postStatus;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Recruitment> recruitmentList = new ArrayList<>(); // 모집 분야
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostSkill> postSkills = new ArrayList<>(); // 모집 분야

    @Builder
    public Post(Long id, String title, String content, PostType postType, RecruitStatus recruitStatus, ProgressType progressType, List<Recruitment> recruitmentList, List<PostSkill> postSkills, String contactInfo, PostStatus postStatus) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.postType = postType;
        this.recruitStatus = recruitStatus;
        this.progressType = progressType;
        this.recruitmentList = recruitmentList;
        this.postSkills = postSkills;
        this.contactInfo = contactInfo;
        this.postStatus = postStatus;
    }
}