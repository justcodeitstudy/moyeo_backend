package com.justcodeit.moyeo.study.application.scrap;

import com.justcodeit.moyeo.study.model.inquiry.ScrapQueryDto;
import com.justcodeit.moyeo.study.model.post.PostStatus;
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
  Scrap scrap;

  @BeforeEach
  void beforeEach() {
    user = createUser("userId", "test@gmail.com", Role.USER, null, null, "tester");
    post = createPost("this is test", user.getUserId());
    scrap = createScrap(user.getUserId(), post.getId());
  }

  @Test
  void makeScrap() throws Exception {
    //given
    when(postRepository.findById(any())).thenReturn(Optional.of(post));
    when(scrapRepository.save(any())).thenReturn(scrap);
    when(scrapRepository.findById(any())).thenReturn(Optional.of(scrap));
    when(scrapRepository.existsByUserIdAndPostId(user.getUserId(), post.getId())).thenReturn(false);

    //when
    Long scrapId = scrapService.makeScrap("userId", post.getId());
    Scrap result = scrapRepository.findById(scrapId).get();

    //then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(scrap.getId());
    assertThat(result.getUserId()).isEqualTo("userId");
    assertThat(result.getPostId()).isEqualTo(post.getId());
  }

  @Test
  void deleteScrap() throws Exception {
    //given
    when(postRepository.findById(any())).thenReturn(Optional.of(post));
    when(scrapRepository.findByUserIdAndPostId(user.getUserId(), post.getId())).thenReturn(Optional.of(scrap));

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
            .postStatus(PostStatus.NORMAL)
            .build();
  }

  private Scrap createScrap(String userId, Long postId) {
    return new Scrap(userId, postId);
  }
}