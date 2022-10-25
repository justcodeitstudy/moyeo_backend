package com.justcodeit.moyeo.study.interfaces.dto.user;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileInfo {

  private final String email;
  private final String picture;
  private final String nickname;
  private final String introduction;
  private final List<Long> skillIds;
}