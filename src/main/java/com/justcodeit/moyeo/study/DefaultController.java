package com.justcodeit.moyeo.study;

import com.justcodeit.moyeo.study.persistence.CustomPostRepository;
import com.justcodeit.moyeo.study.persistence.CustomUserRepository;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.PostJPARepository;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 테스트용 컨트롤러입니다.
 */
@RequiredArgsConstructor
@RestController
public class DefaultController {

  private final CustomUserRepository userRepository;
  private final UserJPARepository userJPARepository;
  private final PostJPARepository postRepository;
  @GetMapping("hello")
  public String hello(){
    return "hello world";
  }

  @GetMapping("hi")
  public List<User> hello2(){
    return userRepository.findByName("hi2");
  }
  @PostMapping("/member")
  public User createMember(){
    long count = userJPARepository.count();
    String username = "testUser"+count;
    String email = "testUser"+count+"@gmail.com";
    User user = new User(username, email, null, User.Role.USER);
    return userJPARepository.save(user);
  }
  @PostMapping("/post1")
  public Post createPost(){
    long count = postRepository.count();
    String title = "testTitle"+ count;
    String contents = "testTitle"+ count+"'s Contens";
    User user = userJPARepository.findById(count).get();
    Post post = new Post(title, contents, user);

    return postRepository.save(post);
  }
}
