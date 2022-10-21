package com.justcodeit.moyeo.study.application.scrap;

import com.justcodeit.moyeo.study.model.inquiry.ScrapQueryDto;
import com.justcodeit.moyeo.study.model.type.Role;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.Scrap;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.repository.PostRepository;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import com.justcodeit.moyeo.study.persistence.repository.scrap.ScrapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScrapServiceTest {

  @InjectMocks
  ScrapService scrapService;

  @Mock
  ScrapRepository scrapRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  PostRepository postRepository;

  User user;
  Post post;

  @BeforeEach
  void beforeEach() {
    user = createUser("testUser", "test@gmail.com", Role.USER, "tester", "google", null);
    post = createPost("test", "this is test");
  }

  @Test
  void makeScrap() throws Exception {
    //given
    Scrap scrap = createScrap(user.getUserId(), post.getId());
    Long fakeScrapId = 100L;
    ReflectionTestUtils.setField(scrap, "id", fakeScrapId);

    when(scrapRepository.save(any())).thenReturn(scrap);
    when(scrapRepository.findById(fakeScrapId)).thenReturn(Optional.of(scrap));

    //when
    Long scrapId = scrapService.makeScrap(user.getUserId(), post.getId());
    Scrap result = scrapRepository.findById(scrapId).get();

    //then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(scrap.getId());
    assertThat(result.getUserId()).isEqualTo("testUser");
    assertThat(result.getPostId()).isEqualTo(post.getId());
  }

  @Test
  void deleteScrap() throws Exception {
    //given
    Scrap scrap = createScrap(user.getUserId(), post.getId());
    Long fakeScrapId = 100L;
    ReflectionTestUtils.setField(scrap, "id", fakeScrapId);

    when(scrapRepository.findById(fakeScrapId)).thenReturn(Optional.of(scrap));

    //when
    scrapService.deleteScrap(user.getUserId(), scrap.getId());
    List<Scrap> result = scrapRepository.findAll();

    //then
    assertThat(result).isEmpty();
  }

  @Test
  void findScrapListByUser() throws Exception {
    //given
    Scrap scrap = createScrap(user.getUserId(), post.getId());
    Long fakeScrapId = 100L;
    ReflectionTestUtils.setField(scrap, "id", fakeScrapId);

    when(scrapRepository.findScrapListByUserId(user.getUserId()))
            .thenReturn(List.of(new ScrapQueryDto(scrap.getId(), post.getId(), "test", LocalDateTime.now(), 0L)));

    //when
    List<ScrapQueryDto> result = scrapService.findScrapListByUser(user.getUserId());

    //then
    assertThat(result).isNotEmpty();
    assertThat(result.size()).isEqualTo(1);
    assertThat(result).extracting("title").contains("test");
  }

  private User createUser(String userId, String email, Role role, String displayName, String providerType, String nickname) {
    return new User(userId, email, null, role, displayName, providerType, null, nickname);
  }

  private Post createPost(String title, String userId) {
    return Post.builder()
            .title(title)
            .userId(userId)
            .build();
  }

  private Scrap createScrap(String userId, Long postId) {
    return new Scrap(userId, postId);
  }
}