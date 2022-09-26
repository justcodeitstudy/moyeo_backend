package com.justcodeit.moyeo.study.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserJPA 테스트용 레파지토리입니다.
 * 실제로 사용하지 않을 가능성이 높습니다.
 */
public interface UserJPARepository extends JpaRepository<User,Long> {

}