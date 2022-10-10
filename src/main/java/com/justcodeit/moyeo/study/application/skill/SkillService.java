package com.justcodeit.moyeo.study.application.skill;

import com.justcodeit.moyeo.study.application.aws.s3.S3Service;
import com.justcodeit.moyeo.study.interfaces.dto.skill.SkillCreateDto;
import com.justcodeit.moyeo.study.interfaces.mapper.SkillMapper;
import com.justcodeit.moyeo.study.model.skill.SkillCategoryConverter;
import com.justcodeit.moyeo.study.model.skill.SkillDto;
import com.justcodeit.moyeo.study.model.type.SkillCategory;
import com.justcodeit.moyeo.study.persistence.Skill;
import com.justcodeit.moyeo.study.persistence.repository.SkillRepository;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    private final S3Service s3Service;

    public SkillDto saveSkill(SkillCreateDto skillCreateDto) throws IOException {
        String url = s3Service.fileUpload(skillCreateDto.getFile(), skillCreateDto.getFolderName());
        SkillDto skillDto = SkillMapper.SKILL_INSTANCE.skillDtoToEntity(skillCreateDto);
        skillDto.setImageUrl(url);

        Skill skill = SkillMapper.SKILL_INSTANCE.skillDtoToEntity(skillDto);
        skillRepository.save(skill);

        return SkillMapper.SKILL_INSTANCE.entityToSkillDto(skill);
    }
    public List<SkillDto> findSkillAll() {
        List<Skill> skillList = skillRepository.findAll();
        return SkillMapper.SKILL_INSTANCE.entityListToSkillList(skillList);
    }
    public SkillDto findSkillByName(String name) {
        Skill skill = skillRepository.findByName(name);
        return SkillMapper.SKILL_INSTANCE.entityToSkillDto(skill);
    }

    public Boolean skillcreateAllFromS3() {
        try {
            List<String> objectFileName = s3Service.getObjectFileName();

            for (String fileLocation : objectFileName) {
                String[] fileFolderAndName = fileLocation.split("/");
                String folder = fileFolderAndName[0];
                String name = fileFolderAndName[1].replaceAll(".svg","");

                SkillCategory skillCategory = SkillCategoryConverter.getForFolderName(folder);
                String url = "https://moyeo-skillstack.s3.ap-northeast-2.amazonaws.com/" + skillCategory.getEngWord();

                Skill skill = new Skill(skillCategory, folder , name, url);
                skillRepository.save(skill);
            }
        }catch (Exception e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
