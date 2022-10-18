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
  void findScrapListByUserId() throws Exception {
    //given
    Skill skill1 = new Skill(SkillCategory.FRONT_END, null, "front", null);
    Skill skill2 = new Skill(SkillCategory.BACK_END, null, "back", null);
    Skill skill3 = new Skill(SkillCategory.DESIGN, null, "design", null);
    skillRepository.save(skill1);
    skillRepository.save(skill2);
    skillRepository.save(skill3);

    for (int i = 1; i <= 5; i++) {
      Post post = createPost("This is test " + i, user.getUserId());
      postRepository.save(post);

      PostSkill postSkill1 = new PostSkill(post, skill1);
      PostSkill postSkill2 = new PostSkill(post, skill2);
      PostSkill postSkill3 = new PostSkill(post, skill3);

      List<PostSkill> postSkills = new ArrayList<>() {{
        add(postSkill1);
        add(postSkill2);
        add(postSkill3);
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
    assertThat(result.get(0).getPostSkills()).extracting("name").containsExactly("front", "back", "design");
  }

  private Post createPost(String title, String userId) {
    return Post.builder()
            .title(title)
            .userId(userId)
            .build();
  }
}