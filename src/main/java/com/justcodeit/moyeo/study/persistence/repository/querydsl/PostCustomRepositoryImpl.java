package com.justcodeit.moyeo.study.persistence.repository.querydsl;

import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.QPost;
import com.justcodeit.moyeo.study.persistence.QPostSkill;
import com.justcodeit.moyeo.study.persistence.QRecruitment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                .leftJoin(postSkill)
                .where(ltPostId(pageable.getOffset()))
                .fetchJoin()
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression ltPostId(Long postId) {
        if(postId == null) {
            return null;
        }
        return QPost.post.id.lt(postId);
    }
    private BooleanExpression defaultShow() {
        return null;
    }
}
