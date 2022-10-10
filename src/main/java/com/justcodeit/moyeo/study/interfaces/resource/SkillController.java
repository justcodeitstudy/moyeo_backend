package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.skill.SkillService;
import com.justcodeit.moyeo.study.interfaces.dto.skill.SkillCreateRequestDto;
import com.justcodeit.moyeo.study.model.skill.SkillDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequestMapping("/skill")
@RestController
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillDto> createSkill(@ModelAttribute("skillCreateDto") @Valid SkillCreateRequestDto skillCreateRequestDto) throws IOException {
        if(!skillCreateRequestDto.isSvg()) {
            throw new IOException();
        }
        return ResponseEntity.ok(skillService.saveSkill(skillCreateRequestDto));
    }
    @GetMapping
    public ResponseEntity<SkillDto> findSkillByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(skillService.findSkillByName(name));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SkillDto>> findAllSkill() {
        return ResponseEntity.ok(skillService.findSkillAll());
    }

    @PostMapping("/upload")
    public ResponseEntity<Boolean> uploadZip() {
        return ResponseEntity.ok(skillService.skillcreateAllFromS3());
    }
}
