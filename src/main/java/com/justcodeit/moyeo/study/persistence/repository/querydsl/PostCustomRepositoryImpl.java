package com.justcodeit.moyeo.study.persistence.repository.querydsl;

import com.justcodeit.moyeo.study.interfaces.dto.post.PostSearchCondition;
import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.QPost;
import com.justcodeit.moyeo.study.persistence.QPostSkill;
import com.justcodeit.moyeo.study.persistence.QRecruitment;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    QPost post = QPost.post;
    QRecruitment recruitment = QRecruitment.recruitment;
    QPostSkill postSkill = QPostSkill.postSkill;

    @Override
    public Post findByIdCustom(Long id) {

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
    public Post findByIdAndUserIdAndPostStatusNormal(Long id, String userId, PostStatus postStatus) {
        return jpaQueryFactory.selectFrom(post)
                .where( post.id.eq(id)
                        .and(post.userId.eq(userId))
                        .and(post.postStatus.eq(postStatus))
                )
                .fetchOne();
    }

    private BooleanExpression gtPostId(Long postId) {
        if(postId == null) {
            return null;
        }
        return QPost.post.id.gt(postId);
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
        if (!sort.isEmpty()) {
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
}