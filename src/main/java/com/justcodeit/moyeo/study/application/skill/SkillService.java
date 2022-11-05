package com.justcodeit.moyeo.study.application.skill;

import com.justcodeit.moyeo.study.application.aws.s3.S3Service;
import com.justcodeit.moyeo.study.application.skill.exception.SkillCannotFoundException;
import com.justcodeit.moyeo.study.interfaces.dto.skill.SkillCreateRequestDto;
import com.justcodeit.moyeo.study.interfaces.mapper.SkillMapper;
import com.justcodeit.moyeo.study.model.skill.SkillCategoryConverter;
import com.justcodeit.moyeo.study.model.skill.SkillDto;
import com.justcodeit.moyeo.study.model.type.SkillCategory;
import com.justcodeit.moyeo.study.persistence.Skill;
import com.justcodeit.moyeo.study.persistence.repository.SkillRepository;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final S3Service s3Service;
    @Value("${moyeo.s3EndPoint}")
    private String S3_ENDPOINT;

    @Transactional(readOnly = true)
    public SkillDto saveSkill(SkillCreateRequestDto skillCreateRequestDto) throws IOException {
        String url = s3Service.fileUpload(skillCreateRequestDto.getFile(), skillCreateRequestDto.getFolderName());
        skillCreateRequestDto.setImageUrl(url);
        Skill skill = SkillMapper.SKILL_INSTANCE.skillCreateDtoToEntity(skillCreateRequestDto);

        skillRepository.save(skill);

        return SkillMapper.SKILL_INSTANCE.entityToSkillDto(skill);
    }

    public List<SkillDto> findSkillAll() {
        List<Skill> skillList = skillRepository.findAll();
        return SkillMapper.SKILL_INSTANCE.entityListToSkillList(skillList);
    }

    public SkillDto findSkillByName(String name) {
        Skill skill = skillRepository.findByName(name).orElseThrow(SkillCannotFoundException::new);
        return SkillMapper.SKILL_INSTANCE.entityToSkillDto(skill);
    }

    public Boolean skillcreateAllFromS3() {
        List<String> objectFileName = s3Service.getObjectFileName();

        for (String fileLocation : objectFileName) {
            if(fileLocation.contains("profile")) continue;
            String[] fileFolderAndName = fileLocation.split("/");
            String folder = fileFolderAndName[0];
            String name = fileFolderAndName[1].replaceAll(".svg", "");

            SkillCategory skillCategory = SkillCategoryConverter.getForFolderName(folder);
            String url = S3_ENDPOINT + fileLocation;

            Skill skill = new Skill(skillCategory, folder, name, url);
            skillRepository.save(skill);
        }
        return Boolean.TRUE;
    }
}
