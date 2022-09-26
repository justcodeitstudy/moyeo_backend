package com.justcodeit.moyeo.study.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import com.justcodeit.moyeo.study.persistence.QUser;

import java.util.List;

/**
 * querydsl 테스트용 레파지토리입니다.
 * 실제로 사용하지 않을 가능성이 높습니다.
 */
@Repository
public class CustomPostRepository extends QuerydslRepositorySupport {
  private final JPAQueryFactory queryFactory;

  public CustomPostRepository(JPAQueryFactory queryFactory) {
    super(User.class);
    this.queryFactory = queryFactory;
  }

  /**
   * User와 관련이 있는 Entity 클래스를 만들고
   * 릴레이션, 조인에 관한 exam 진행해 달라.
   * exam - (안 써본 사람들을 위해서 예시를 작성해달라)
   * 내일 오후까지
   */

  /**
   * 기술(Framework, Library)에 종속되지 않는 코드
   * Mock, 환경, Build할 때 테스트 돌리는 경우
   * 내가 작성한 클래스들에 대해서 테스트 하는 것이 단위 테스트
   *
   */
  public List<User> findByName(String name) {
    QUser user = QUser.user;
    return queryFactory.selectFrom(user).where(user.username.eq(name)).fetch();
  }
}