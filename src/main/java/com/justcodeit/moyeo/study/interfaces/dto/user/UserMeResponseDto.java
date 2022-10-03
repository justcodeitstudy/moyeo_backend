package com.justcodeit.moyeo.study.interfaces.dto.user;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMeResponseDto {
  private String username;
  private String email;
  private String role;
  private List<String> skills;

}
