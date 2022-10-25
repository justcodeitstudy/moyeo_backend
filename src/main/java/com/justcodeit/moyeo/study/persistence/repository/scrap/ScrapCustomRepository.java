package com.justcodeit.moyeo.study.persistence.repository.scrap;

import com.justcodeit.moyeo.study.model.inquiry.ScrapQueryDto;

import java.util.List;

public interface ScrapCustomRepository {

  List<ScrapQueryDto> findScrapListByUserId(String userId);
}