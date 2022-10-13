package com.justcodeit.moyeo.study.interfaces.dto.skill;

import com.justcodeit.moyeo.study.model.type.SkillCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class SkillCreateRequestDto {
    private MultipartFile file;
    @NotNull
    private SkillCategory skillCategory;
    @NotBlank
    private String folderName;
    @NotBlank
    private String name;
    private Integer orderNum;
    private String imageUrl;

    public boolean isSvg() {
        return file.getContentType().equals("image/svg+xml");
    }
}
