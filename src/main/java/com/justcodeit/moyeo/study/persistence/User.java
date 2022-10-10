package com.justcodeit.moyeo.study.persistence;

import com.justcodeit.moyeo.study.model.type.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity

@Table(name = "users", // user가 예약어
    indexes = {
        @Index(columnList = "email"),
        @Index(columnList = "domesticId, providerType", unique = true)
    }
)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String userId;
  private String email;
  private String picture;
  @Enumerated(EnumType.STRING)
  private Role role;

  private String displayName; // oauth provider 가 전달하는 이름(혹은 해당 서비스에서의 닉네임), moyeo 내의 닉네임이랑은 다름
  private String providerType;
  private String domesticId; // provider가 가지고 있는 유저 구분값

  private String nickname;
  private String introduction;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserSkill> userSkills = new ArrayList<>();

  protected User() {
  }

  public User(String userId, String email, String picture, Role role, String displayName, String providerType,
      String domesticId, String nickname) {
    this.userId = userId;
    this.email = email;
    this.picture = picture;
    this.role = role;
    this.displayName = displayName;
    this.providerType = providerType;
    this.domesticId = domesticId;
    this.nickname = nickname;
  }

  public User update(String displayName, String picture) {
    this.displayName = displayName;
    this.picture = picture;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getRoleKey() {
    return this.role.getKey();
  }

  public String getProviderType() {
    return providerType;
  }

  public String getDomesticId() {
    return domesticId;
  }

  public String getPicture() { return picture; }

  public String getNickname() { return nickname; }

  public String getIntroduction() { return introduction; }

  public String getUserId() { return userId; }

  public List<Long> getSkillIds() {
    return userSkills.stream()
            .map(userSkill -> userSkill.getSkillId())
            .collect(Collectors.toList());
  }

  public void editProfile(String nickname, String introduction, List<Long> skillIds) {
    this.nickname = nickname;
    this.introduction = introduction;
    this.userSkills.clear();
    skillIds.forEach(skillId -> addUserSkill(new UserSkill(this, skillId)));
  }

  private void addUserSkill(UserSkill userSkill) {
    this.userSkills.add(userSkill);
    userSkill.setUser(this);
  }
}
