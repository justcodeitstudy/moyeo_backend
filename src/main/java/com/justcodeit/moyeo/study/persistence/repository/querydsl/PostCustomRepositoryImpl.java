package com.justcodeit.moyeo.study.persistence.repository.querydsl;

import com.justcodeit.moyeo.study.model.inquiry.PostQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.PostSkillQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.QPostQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.QPostSkillQueryDto;
import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import com.justcodeit.moyeo.study.persistence.Post;
import static com.justcodeit.moyeo.study.persistence.QPost.post;
import static com.justcodeit.moyeo.study.persistence.QPostSkill.postSkill;
import static com.justcodeit.moyeo.study.persistence.QRecruitment.recruitment;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.justcodeit.moyeo.study.persistence.QPost.post;
import static com.justcodeit.moyeo.study.persistence.QPostSkill.*;
import static com.justcodeit.moyeo.study.persistence.QScrap.*;
import static com.justcodeit.moyeo.study.persistence.QSkill.*;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Post findByIdCustom(Long id) {
        return Optional.of(jpaQueryFactory.selectFrom(post)
                .leftJoin(recruitment)
                .on(recruitment.post.id.eq(post.id))
                .leftJoin(postSkill)
                .on(postSkill.post.id.eq(post.id))
                .where(post.id.eq(id))
                .fetchJoin()
                .fetchOne()
        ).orElseThrow(PostCannotFoundException::new);
    }

    @Override
    public List<Post> findAllBySearchCondition(Pageable pageable, PostSearchCondition searchCondition) {
        return jpaQueryFactory.selectFrom(post)
                .leftJoin(recruitment)
                    .on(recruitment.post.id.eq(post.id))
                .leftJoin(postSkill)
                    .on(postSkill.post.id.eq(post.id))
                .where(gtPostId(pageable.getOffset())
                        .and(createSearchCondition(searchCondition)))
                .limit(pageable.getPageSize())
                .orderBy(postSort(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    public List<PostQueryDto> findPostListByUserId(String userId) {
        List<PostQueryDto> postDtoList = jpaQueryFactory
                .select(new QPostQueryDto(
                        post.id,
                        post.title,
                        post.createDate,
                        post.viewCount,
                        null
                ))
                .from(post)
                .where(post.userId.eq(userId))
                .orderBy(post.createDate.desc())
                .fetch();

        List<Long> postIds = extractPostIds(postDtoList);

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

        combineIntoOne(postDtoList, postSkillDtoList);
        return postDtoList;
    }

    private BooleanExpression gtPostId(Long postId) {
        if(postId == null) {
            return null;
        }
        return post.id.gt(postId);
    }
    private BooleanExpression createSearchCondition(PostSearchCondition postSearchReqDto) {
        BooleanExpression expression = post.postStatus.eq(PostStatus.NORMAL);

        if(StringUtils.hasText(postSearchReqDto.getTitle())) {
            String title = postSearchReqDto.getTitle();
            expression = expression.and(post.title.like(title + "%"));
        }
        if(postSearchReqDto.getRecruitStatus() != null) {
            RecruitStatus recruitStatus = postSearchReqDto.getRecruitStatus();
            expression = expression.and(post.recruitStatus.eq(recruitStatus));
        }
        if(postSearchReqDto.getSkillList() != null && postSearchReqDto.getSkillList().size() != 0) {
            List<Long> skillList = postSearchReqDto.getSkillList();
            expression = expression.andAnyOf(postSkill.skill.id.in(skillList));
        }
        return expression;
    }

    private List<OrderSpecifier> postSort(Sort sort) {
        List<OrderSpecifier> ORDERS = new ArrayList<>();
        if (sort.isEmpty()) {
            return ORDERS;
        }
        for (Sort.Order order : sort) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            if(order.getProperty().equals("id")) {
                ORDERS.add(new OrderSpecifier(direction, post.id));
            }
        }
        return ORDERS;
    }

    private List<Long> extractPostIds(List<PostQueryDto> postDtoList) {
        return postDtoList.stream()
                .map(PostQueryDto::getPostId)
                .collect(Collectors.toList());
    }

    private void combineIntoOne(List<PostQueryDto> postDtoList, List<PostSkillQueryDto> postSkillDtoList) {
        Map<Long, List<PostSkillQueryDto>> postSkillDtoListMap = postSkillDtoList.stream()
                .collect(Collectors.groupingBy(PostSkillQueryDto::getPostId));

        postSkillDtoListMap.forEach((postId, dtos) -> {
            if (dtos.size() > 3) {
                List<PostSkillQueryDto> subDtos = new ArrayList<>(dtos.subList(0, 3));
                postSkillDtoListMap.put(postId, subDtos);
            }
        });

        postDtoList.forEach(postQueryDto -> postQueryDto.setPostSkills(postSkillDtoListMap.get(postQueryDto.getPostId())));
    }
}