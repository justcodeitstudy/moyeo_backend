package com.justcodeit.moyeo.study.interfaces.dto.user;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMeResponseDto {
  private String email;
  private String nickname;
  private String role;
//  private List<String> skills;
  //필요한 데이터가 있다면
}
