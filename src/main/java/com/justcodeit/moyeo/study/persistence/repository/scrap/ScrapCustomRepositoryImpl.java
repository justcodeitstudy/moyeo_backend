package com.justcodeit.moyeo.study.persistence.repository.scrap;

import static com.justcodeit.moyeo.study.persistence.QPost.post;
import static com.justcodeit.moyeo.study.persistence.QScrap.scrap;

import com.justcodeit.moyeo.study.interfaces.dto.scrap.QScrapQueryDto;
import com.justcodeit.moyeo.study.interfaces.dto.scrap.ScrapQueryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScrapCustomRepositoryImpl implements ScrapCustomRepository {

    private final JPAQueryFactory queryFactory;

    // TODO : post 필드가 추가됨에 따라 수정 예정
    @Override
    public List<ScrapQueryDto> findScrapListByUserId(String userId) {
        return queryFactory
            .select(new QScrapQueryDto(
                scrap.id,
                post.id,
                post.title
            ))
            .from(scrap)
            .join(post).on(scrap.postId.eq(post.id))
            .where(scrap.userId.eq(userId))
            .orderBy(scrap.createdAt.desc())
            .fetch();
    }
}