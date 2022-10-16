package com.justcodeit.moyeo.study.application.scrap;

import com.justcodeit.moyeo.study.common.RandomIdUtil;
import com.justcodeit.moyeo.study.model.type.Role;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.Scrap;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.repository.PostRepository;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import com.justcodeit.moyeo.study.persistence.repository.scrap.ScrapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ScrapServiceTest {

  @Autowired
  ScrapService scrapService;

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
  void makeScrap() throws Exception {
    //given
    for (int i = 1; i <= 5; i++) {
      Post post = new Post("This is test" + i, "test" + i);
      postRepository.save(post);
      Thread.sleep(10);
    }

    //when
    scrapService.makeScrap(user.getUserId(), 3L);
    Scrap scrap = scrapRepository.getReferenceById(1L);

    //then
    assertThat(scrap).isNotNull();
    assertThat(scrap.getPostId()).isEqualTo(3L);
  }

  @Test
  void deleteScrap() throws Exception {
    //given
    for (int i = 1; i <= 5; i++) {
      Post post = new Post("This is test" + i, "test" + i);
      postRepository.save(post);
      Thread.sleep(10);
    }

    //when
    scrapService.makeScrap(user.getUserId(), 1L);
    scrapService.makeScrap(user.getUserId(), 2L);
    scrapService.makeScrap(user.getUserId(), 3L);

    scrapService.deleteScrap(2L);
    List<Scrap> result = scrapRepository.findAll();

    //then
    assertThat(result.size()).isEqualTo(2);
    assertThat(result.stream().map(Scrap::getPostId)).contains(1L, 3L);
  }
}