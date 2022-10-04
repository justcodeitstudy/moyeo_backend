package com.justcodeit.moyeo.study.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users") // user가 예약어
public class  User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String userId;
  private String username;
  @Column(unique = true)
  private String email;
  private String picture;
  @Enumerated(EnumType.STRING)
  private Role role;
  private String nickname;
  private String introduction;
  @Enumerated(EnumType.STRING)
  private JobType job1;
  @Enumerated(EnumType.STRING)
  private JobType job2;
  @Enumerated(EnumType.STRING)
  private JobType job3;
  private String portfolio;
  private String skills;

  private User() {
  }

  public User(String username, String email, String picture, Role role) {
    this.username = username;
    this.email = email;
    this.picture = picture;
    this.role = role;
  }

  public User update(String username, String picture) {
    this.username = username;
    this.picture = picture;
    return this;
  }

  public String getUsername() {
    return this.username;
  }

  public String getRoleKey() {
    return this.role.getKey();
  }

  public String getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public String getNickname() {
    return nickname;
  }

  public String getIntroduction() {
    return introduction;
  }

  public JobType getJob1() {
    return job1;
  }

  public JobType getJob2() {
    return job2;
  }

  public JobType getJob3() {
    return job3;
  }

  public String getPortfolio() {
    return portfolio;
  }

  public String getSkills() {
    return skills;
  }

  public void editProfile(String nickname, String introduction, JobType job1, JobType job2, JobType job3, String portfolio, String skills) {
    this.nickname = nickname;
    this.introduction = introduction;
    this.job1 = job1;
    this.job2 = job2;
    this.job3 = job3;
    this.portfolio = portfolio;
    this.skills = skills;
  }

  @RequiredArgsConstructor
  @Getter
  public enum Role {
    USER("ROLE_USER", "일반사용자"),
    GUEST("GUEST_USER", "게스트");
    private final String key;
    private final String title;
  }

}
