package com.justcodeit.moyeo.study.persistence.repository.querydsl;

import com.justcodeit.moyeo.study.application.post.exception.PostCannotFoundException;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostSearchCondition;
import com.justcodeit.moyeo.study.model.inquiry.PostQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.PostSkillQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.QPostQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.QPostSkillQueryDto;
import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import com.justcodeit.moyeo.study.persistence.Post;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.justcodeit.moyeo.study.persistence.QPost.post;
import static com.justcodeit.moyeo.study.persistence.QPostSkill.postSkill;
import static com.justcodeit.moyeo.study.persistence.QRecruitment.recruitment;
import static com.justcodeit.moyeo.study.persistence.QScrap.scrap;
import static com.justcodeit.moyeo.study.persistence.QSkill.skill;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Post findByIdCustom(Long id) {
        Optional<Post> resultPost = Optional.ofNullable(
                jpaQueryFactory.selectFrom(post)
                    .leftJoin(recruitment)
                    .on(recruitment.post.id.eq(post.id))
                    .leftJoin(postSkill)
                    .on(postSkill.post.id.eq(post.id))
                    .where(post.id.eq(id))
                    .fetchJoin()
                    .fetchOne()
        );
        return resultPost.orElseThrow(() -> new PostCannotFoundException());
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
    public boolean existByIdAndUserIdAndPostStatusNormal(Long postId, String userId, PostStatus postStatus) {
        Integer postCount = jpaQueryFactory.selectOne()
                .from(post)
                .where( post.id.eq(postId)
                        .and(post.userId.eq(userId))
                        .and(post.postStatus.eq(postStatus))
                )
                .fetchOne();
        return postCount != null;
    }

    @Override
    public Page<PostQueryDto> findPostList(String userId, PostSearchCondition searchCondition, Pageable pageable) {
        List<PostQueryDto> postDtoList = jpaQueryFactory
                .select(new QPostQueryDto(
                        post.id,
                        post.title,
                        post.createdAt,
                        post.viewCount,
                        new CaseBuilder()
                                .when(scrap.isNotNull().and(scrap.userId.eq(userId)))
                                .then(true)
                                .otherwise(false)
                ))
                .from(post)
                .leftJoin(scrap).on(post.id.eq(scrap.postId))
                .where(gtPostId(pageable.getOffset()), createSearchCondition(searchCondition))
                .orderBy(postSort(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .limit(pageable.getPageSize())
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
        return new PageImpl<>(postDtoList, pageable, postDtoList.size());
    }

    @Override
    public List<PostQueryDto> findPostListByUserId(String userId) {
        List<PostQueryDto> postDtoList = jpaQueryFactory
                .select(new QPostQueryDto(
                        post.id,
                        post.title,
                        post.createdAt,
                        post.viewCount,
                        null
                ))
                .from(post)
                .where(post.userId.eq(userId), post.postStatus.eq(PostStatus.NORMAL))
                .orderBy(post.createdAt.desc())
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
        if(postSearchReqDto.getStatus() != null) {
            RecruitStatus recruitStatus = postSearchReqDto.getStatus();
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