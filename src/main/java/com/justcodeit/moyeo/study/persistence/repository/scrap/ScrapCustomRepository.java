package com.justcodeit.moyeo.study.persistence.repository.scrap;

import com.justcodeit.moyeo.study.interfaces.dto.scrap.ScrapQueryDto;

import java.util.List;

public interface ScrapCustomRepository {

  List<ScrapQueryDto> findScrapListByUserId(String userId);
}