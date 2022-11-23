package com.justcodeit.moyeo.study.model.jwt;

import com.justcodeit.moyeo.study.application.oauth.JwtProvider;
import com.justcodeit.moyeo.study.model.session.UserPrincipal;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class AuthTokenConverter {

  private final JwtProvider jwt;

  public UserToken fromToken(String t) {
    var claims = jwt.claims(t);
    var userId = claims.get("userId", String.class);
    var email = claims.get("email", String.class);
    var displayName = claims.get("displayName", String.class);
    var role = claims.get("role", String.class);
    return new UserToken(userId, email, displayName, role);
    // todo 토큰이 변경되면 같이 수정되어야 함
  }

  public String toTokenString(Authentication auth) {
    final var principal = (UserPrincipal) auth.getPrincipal();
    Map<String, Object> claims = new HashMap<>() {{
      put("userId", principal.getUserId());
      put("email", principal.getEmail());
      put("role", principal.getRoleType());
      put("displayName", principal.getDisplayName());
    }};

    return jwt.generate(claims);
  }

}

