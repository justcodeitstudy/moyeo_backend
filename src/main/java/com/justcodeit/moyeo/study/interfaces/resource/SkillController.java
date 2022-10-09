package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.skill.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/skill")
@RestController
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<Boolean> uploadZip() {
        return ResponseEntity.ok(skillService.skillSaveAllFromS3());
    }
}