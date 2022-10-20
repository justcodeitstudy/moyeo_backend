package com.justcodeit.moyeo.study.persistence.repository.scrap;

import com.justcodeit.moyeo.study.model.inquiry.QScrapQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.ScrapQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.PostSkillQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.QPostSkillQueryDto;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.justcodeit.moyeo.study.persistence.QPost.*;
import static com.justcodeit.moyeo.study.persistence.QPostSkill.*;
import static com.justcodeit.moyeo.study.persistence.QScrap.*;
import static com.justcodeit.moyeo.study.persistence.QSkill.*;

@Repository
@RequiredArgsConstructor
public class ScrapCustomRepositoryImpl implements ScrapCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<ScrapQueryDto> findScrapListByUserId(String userId) {
    // 스크랩 리스트 (= 포스트 카드 리스트) 조회
    List<ScrapQueryDto> scrapDtoList = queryFactory
            .select(new QScrapQueryDto(
                    scrap.id,
                    post.id,
                    post.title,
                    post.createDate,
                    post.viewCount
            ))
            .from(scrap)
            .join(post).on(scrap.postId.eq(post.id))
            .where(scrap.userId.eq(userId))
            .orderBy(scrap.createdAt.desc())
            .fetch();

    List<Long> postIds = scrapDtoList.stream()
            .map(ScrapQueryDto::getPostId)
            .collect(Collectors.toList());

    // 각 포스트마다 스킬 리스트 조회
    List<PostSkillQueryDto> postSkillDtoList = queryFactory
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

    scrapDtoList.forEach(scrapQueryDto -> scrapQueryDto.setPostSkills(postSkillDtoListMap.get(scrapQueryDto.getPostId())));

    return scrapDtoList;
  }
}