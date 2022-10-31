package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.skill.SkillService;

import com.justcodeit.moyeo.study.interfaces.dto.FailureRes;
import com.justcodeit.moyeo.study.interfaces.dto.skill.SkillCreateRequestDto;
import com.justcodeit.moyeo.study.model.skill.SkillDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "기술스택", description = "모집글에서 사용하는 기술스택에 대한 API.")
@RequestMapping("/skill")
@RestController
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @Operation(hidden = true)
    @PostMapping
    public ResponseEntity<SkillDto> createSkill(@ModelAttribute("skillCreateDto") @Valid SkillCreateRequestDto skillCreateRequestDto) throws IOException {
        if(!skillCreateRequestDto.isSvg()) {
            throw new IOException();
        }
        return ResponseEntity.ok(skillService.saveSkill(skillCreateRequestDto));
    }
    @Operation(summary = "기술스택 이름으로 조회", description = "기술 스택 영문 이름으로 기술스택 단건 조회")
    @Parameter(name = "name", description = "기술스택의 이름", in = ParameterIn.PATH )
    @ApiResponse(responseCode = "404", description = "기술스택을 찾을 수 없음.",
            content = @Content(schema = @Schema(implementation = FailureRes.class))
    )
    @GetMapping("/{name}")
    public ResponseEntity<SkillDto> findSkillByName(@Parameter(hidden = true) @PathVariable(name = "name") String name) {
        return ResponseEntity.ok(skillService.findSkillByName(name));
    }
    @Operation(summary = "기술스택 전체 조회", description = "기술스택의 목록 전체 반환하는 API")
    @GetMapping
    public ResponseEntity<List<SkillDto>> findAllSkill() {
        return ResponseEntity.ok(skillService.findSkillAll());
    }

    @Operation(hidden = true)
    @PreAuthorize("hasRole('Role_ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<Boolean> uploadZip() {
        return ResponseEntity.ok(skillService.skillcreateAllFromS3());
    }
}
