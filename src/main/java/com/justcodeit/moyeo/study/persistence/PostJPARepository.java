package com.justcodeit.moyeo.study.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserJPA 테스트용 레파지토리입니다.
 * 실제로 사용하지 않을 가능성이 높습니다.
 */
public interface PostJPARepository extends JpaRepository<Post,Long> {

}