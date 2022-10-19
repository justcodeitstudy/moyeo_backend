package com.justcodeit.moyeo.study.persistence.repository.scrap;

import com.justcodeit.moyeo.study.common.RandomIdUtil;
import com.justcodeit.moyeo.study.model.scrap.PostSkillQueryDto;
import com.justcodeit.moyeo.study.model.scrap.ScrapQueryDto;
import com.justcodeit.moyeo.study.model.type.Role;
import com.justcodeit.moyeo.study.model.type.SkillCategory;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.PostSkill;
import com.justcodeit.moyeo.study.persistence.Scrap;
import com.justcodeit.moyeo.study.persistence.Skill;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.repository.PostRepository;
import com.justcodeit.moyeo.study.persistence.repository.PostSkillRepository;
import com.justcodeit.moyeo.study.persistence.repository.SkillRepository;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ScrapRepositoryTest {

  @Autowired
  ScrapRepository scrapRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  SkillRepository skillRepository;

  @Autowired
  PostRepository postRepository;

  @Autowired
  PostSkillRepository postSkillRepository;

  User user;

  @BeforeEach
  void beforeEach() {
    RandomIdUtil randomIdUtil = new RandomIdUtil();
    String userId = randomIdUtil.userId();

    user = new User(userId, "test@gmail.com", null, Role.USER, "tester", "google", null, null);
    userRepository.save(user);
  }

  @Test
  @Commit
  void findScrapListByUserId() throws Exception {
    //given
    Skill skill1 = createSkill(SkillCategory.FRONT_END, "front", 1);
    Skill skill2 = createSkill(SkillCategory.BACK_END, "back", 2);
    Skill skill3 = createSkill(SkillCategory.DESIGN, "design", null);
    Skill skill4 = createSkill(SkillCategory.DEVOPS, "devops", 3);
    Skill skill5 = createSkill(SkillCategory.CO_WORKING_TOOL, "co-work", null);
    skillRepository.save(skill1);
    skillRepository.save(skill2);
    skillRepository.save(skill3);
    skillRepository.save(skill4);
    skillRepository.save(skill5);

    for (int i = 1; i <= 5; i++) {
      Post post = createPost("This is test " + i, user.getUserId());
      postRepository.save(post);

      PostSkill postSkill1 = new PostSkill(post, skill1);
      PostSkill postSkill2 = new PostSkill(post, skill2);
      PostSkill postSkill3 = new PostSkill(post, skill3);
      PostSkill postSkill4 = new PostSkill(post, skill4);
      PostSkill postSkill5 = new PostSkill(post, skill5);

      List<PostSkill> postSkills = new ArrayList<>() {{
        add(postSkill1);
        add(postSkill2);
        add(postSkill3);
        add(postSkill4);
        add(postSkill5);
      }};

      postSkillRepository.saveAll(postSkills);

      Scrap scrap = new Scrap(user.getUserId(), post.getId());
      scrapRepository.save(scrap);
      Thread.sleep(10);
    }

    //when
    List<ScrapQueryDto> result = scrapRepository.findScrapListByUserId(user.getUserId());

    //then
    assertThat(result).isNotEmpty();
    assertThat(result.size()).isEqualTo(5);
    assertThat(result).extracting("postId").containsExactly(5L, 4L, 3L, 2L, 1L); // 시간 역순
    assertThat(result.get(0).getPostSkills().size()).isEqualTo(3);
  }

  private Post createPost(String title, String userId) {
    return Post.builder()
            .title(title)
            .userId(userId)
            .build();
  }

  private Skill createSkill(SkillCategory category, String name, Integer orderNum) {
    return Skill.builder()
            .category(category)
            .name(name)
            .orderNum(orderNum)
            .build();
  }
}