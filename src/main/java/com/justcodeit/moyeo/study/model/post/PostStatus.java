package com.justcodeit.moyeo.study.model.post;

import lombok.Getter;

/**
 * 모집 글의 게시 상태
 */
@Getter
public enum PostStatus {
    DELETE,
    NORMAL,
    ABNORMAL;
}
