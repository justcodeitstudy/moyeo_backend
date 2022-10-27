package com.justcodeit.moyeo.study.persistence.repository.scrap;

import com.justcodeit.moyeo.study.model.inquiry.PostSkillQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.QPostSkillQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.QScrapQueryDto;
import com.justcodeit.moyeo.study.model.inquiry.ScrapQueryDto;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.justcodeit.moyeo.study.persistence.QPost.post;
import static com.justcodeit.moyeo.study.persistence.QPostSkill.postSkill;
import static com.justcodeit.moyeo.study.persistence.QScrap.scrap;
import static com.justcodeit.moyeo.study.persistence.QSkill.skill;

@Repository
@RequiredArgsConstructor
public class ScrapCustomRepositoryImpl implements ScrapCustomRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<ScrapQueryDto> findScrapListByUserId(String userId) {
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

    List<Long> postIds = extractPostIds(scrapDtoList);

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

    combineIntoOne(scrapDtoList, postSkillDtoList);
    return scrapDtoList;
  }

  private List<Long> extractPostIds(List<ScrapQueryDto> scrapDtoList) {
    return scrapDtoList.stream()
            .map(ScrapQueryDto::getPostId)
            .collect(Collectors.toList());
  }

  private void combineIntoOne(List<ScrapQueryDto> scrapDtoList, List<PostSkillQueryDto> postSkillDtoList) {
    Map<Long, List<PostSkillQueryDto>> postSkillDtoListMap = postSkillDtoList.stream()
            .collect(Collectors.groupingBy(PostSkillQueryDto::getPostId));

    postSkillDtoListMap.forEach((postId, dtos) -> {
        if (dtos.size() > 3) {
            List<PostSkillQueryDto> subDtos = new ArrayList<>(dtos.subList(0, 3));
            postSkillDtoListMap.put(postId, subDtos);
        }
    });

    scrapDtoList.forEach(scrapQueryDto -> scrapQueryDto.setPostSkills(postSkillDtoListMap.get(scrapQueryDto.getPostId())));
  }
}