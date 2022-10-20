package com.justcodeit.moyeo.study.persistence.repository.querydsl;

import com.justcodeit.moyeo.study.model.inquiry.PostQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.PostSkillQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.QPostQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.QPostSkillQueryDto;
import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.QPost;
import com.justcodeit.moyeo.study.persistence.QPostSkill;
import com.justcodeit.moyeo.study.persistence.QRecruitment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.justcodeit.moyeo.study.persistence.QPost.post;
import static com.justcodeit.moyeo.study.persistence.QPostSkill.*;
import static com.justcodeit.moyeo.study.persistence.QSkill.*;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Post findById(Long id) {
        QPost post = QPost.post;
        QRecruitment recruitment = QRecruitment.recruitment;
        QPostSkill postSkill = QPostSkill.postSkill;

        return jpaQueryFactory.selectFrom(post)
                .leftJoin(recruitment)
                    .on(recruitment.post.id.eq(post.id))
                .leftJoin(postSkill)
                    .on(postSkill.post.id.eq(post.id))
                .where(post.id.eq(id))
                .fetchJoin()
                .fetchOne();
    }

    @Override
    public List<Post> findAll(Pageable pageable) {
        QPost post = QPost.post;
        QRecruitment recruitment = QRecruitment.recruitment;
        QPostSkill postSkill = QPostSkill.postSkill;

        return jpaQueryFactory.selectFrom(post)
                .leftJoin(recruitment)
                    .on(recruitment.post.id.eq(post.id))
                .leftJoin(postSkill)
                    .on(postSkill.post.id.eq(post.id))
                .where(gtPostId(pageable.getOffset()))
                .fetchJoin()
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<PostQueryDto> findPostListByUserId(String userId) {
        List<PostQueryDto> postDtoList = jpaQueryFactory
                .select(new QPostQueryDto(
                        post.id,
                        post.title,
                        post.createDate,
                        post.viewCount
                ))
                .from(post)
                .where(post.userId.eq(userId))
                .orderBy(post.createDate.desc())
                .fetch();

        List<Long> postIds = postDtoList.stream()
                .map(PostQueryDto::getPostId)
                .collect(Collectors.toList());

        List<PostSkillQueryDto> postSkillDtoList = jpaQueryFactory
                .select(new QPostSkillQueryDto(
                        postSkill.id,
                        post.id,
                        skill.id,
                        skill.name
                ))
                .from(postSkill)
                .join(postSkill.post, post)
                .join(postSkill.skill, skill)
                .where(
                        post.id.in(postIds),
                        skill.orderNum.in(
                                JPAExpressions
                                        .select(skill.orderNum)
                                        .from(skill)
                                        .orderBy(skill.orderNum.asc())
                        )
                )
                .fetch();

        Map<Long, List<PostSkillQueryDto>> postSkillDtoListMap = postSkillDtoList.stream()
                .collect(Collectors.groupingBy(PostSkillQueryDto::getPostId));

        postSkillDtoListMap.forEach((postId, dtos) -> {
            if (dtos.size() > 3) {
                List<PostSkillQueryDto> subDtos = new ArrayList<>(dtos.subList(0, 3));
                postSkillDtoListMap.put(postId, subDtos);
            }
        });

        postDtoList.forEach(postQueryDto -> postQueryDto.setPostSkills(postSkillDtoListMap.get(postQueryDto.getPostId())));

        return postDtoList;
    }

    private BooleanExpression gtPostId(Long postId) {
        if(postId == null) {
            return null;
        }
        return QPost.post.id.gt(postId);
    }
    private BooleanExpression defaultShow() {
        return null;
    }
}
