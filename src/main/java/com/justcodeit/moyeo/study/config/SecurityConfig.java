package com.justcodeit.moyeo.study.config;

import com.justcodeit.moyeo.study.application.oauth.OAuth2AuthenticationSuccessHandler;
import com.justcodeit.moyeo.study.application.oauth.OAuthUserService;
import com.justcodeit.moyeo.study.config.entrypoint.AuthenticationEntryPoint;
import com.justcodeit.moyeo.study.config.filter.TokenAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

  private final OAuthUserService oAuthUserService;
  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  private final TokenAuthFilter tokenAuthFilter;

  private final AuthenticationEntryPoint authenticationEntryPoint;
  @Bean
  public WebSecurityCustomizer customizer() {
    return web -> web.ignoring()
            .antMatchers("/v3/**") // swagger-ui 관련,
            .antMatchers("/v1/**") // swagger-ui 관련,
            .antMatchers("/swagger-ui/**")
            .antMatchers("/");
  }
  @Bean
  protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http
        .addFilterAfter(tokenAuthFilter,
            UsernamePasswordAuthenticationFilter.class) // request 발생시 auth 필터
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 정보 x

    http.authorizeRequests()
        .antMatchers("/scraps/**").hasRole("USER");

    http.headers().defaultsDisabled().contentTypeOptions();
    http.headers().frameOptions().disable().xssProtection().block(true);

    http.formLogin().disable()
        .logout().disable()
        .cors().and().csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint) // 인증문제 발생시 처리
        .and()
        .oauth2Login()
        .userInfoEndpoint()
            .userService(oAuthUserService) //oauth 인증후 처리
        .and()
        .successHandler(oAuth2AuthenticationSuccessHandler);
//        .failureHandler();// 그럴일은 없어보이지만, 필요한 정보가 선택적일때 누락되어 보내질수 있으므로 작성할때는 이쪽에.
    return http.build();
  }
}
