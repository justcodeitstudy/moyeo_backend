package com.justcodeit.moyeo.study.persistence.repository.scrap;

import com.justcodeit.moyeo.study.common.RandomIdUtil;
import com.justcodeit.moyeo.study.interfaces.dto.scrap.ScrapQueryDto;
import com.justcodeit.moyeo.study.model.type.Role;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.Scrap;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.repository.PostRepository;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
  PostRepository postRepository;

  User user;

  @BeforeEach
  void beforeEach() {
    RandomIdUtil randomIdUtil = new RandomIdUtil();
    String userId = randomIdUtil.userId();

    user = new User(userId, "test@gmail.com", null, Role.USER, "tester", "google", null);
    userRepository.save(user);
  }

  @Test
  void findScrapListByUserId() throws Exception {
    //given
    for (int i = 1; i <= 5; i++) {
      Post post = new Post("This is test" + i, "test" + i);
      postRepository.save(post);

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
  }
}