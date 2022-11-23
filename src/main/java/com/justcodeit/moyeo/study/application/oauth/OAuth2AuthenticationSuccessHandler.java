package com.justcodeit.moyeo.study.application.oauth;

import com.justcodeit.moyeo.study.common.CookieUtils;
import com.justcodeit.moyeo.study.model.jwt.AuthTokenConverter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Value("${moyeo.auth.header.scheme}")
  private String TOKEN_SCHEME;

  @Value("${moyeo.auth.redirect.whenLoginSuccess.uri}")
  private String REDIRECT_URI;

  private final AuthTokenConverter authTokenConverter;
  private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    String redirectUrl = REDIRECT_URI; // for test url, 완료시 리다이렉트 주소
    setCookie(request, response, authentication); // 쿠키로 리턴할때
//    sendRedirect 에 헤더로 설정하면 백엔드에서 하면 프론트 header에서 잡을 수가 없음.
//    setResponseHeader(request, response, authentication); // 헤더로 리턴할때

    clearAuthenticationAttributes(request, response);
    getRedirectStrategy().sendRedirect(request, response, redirectUrl);
  }

  private void setCookie(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    String token = authTokenConverter.toTokenString(authentication);

    CookieUtils.addCookie(response, TOKEN_SCHEME, token, 60 * 60);
  }

  private void setResponseHeader(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    String token = authTokenConverter.toTokenString(authentication);

    response.setHeader(TOKEN_SCHEME, token);
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request,
      HttpServletResponse response) {
    super.clearAuthenticationAttributes(request);
    httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request,
        response);
  }

}

