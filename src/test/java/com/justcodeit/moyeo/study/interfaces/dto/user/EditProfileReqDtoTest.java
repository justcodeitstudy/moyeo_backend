package com.justcodeit.moyeo.study.interfaces.dto.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EditProfileReqDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;
    private EditProfileReqDto dto;

    @BeforeAll
    public static void setup() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close() {
        factory.close();
    }

    @Test
    void skillId_empty_invalid() {
        dto = new EditProfileReqDto("nickname", null, List.of());
        var validate = validator.validate(dto);
        assertThat(validate).isNotEmpty();
    }

    @Test
    void skillId_notempty_valid() {
        dto = new EditProfileReqDto("nickname", null, List.of(1L));
        var validate = validator.validate(dto);
        assertThat(validate).isEmpty();
    }

    @Test
    void introduction_length_over3000_invalid() {
        dto = new EditProfileReqDto("nickname", "i".repeat(3001), List.of(1L));
        var validate = validator.validate(dto);
        assertThat(validate).isNotEmpty();
    }

    @Test
    void introduction_length_under3000_valid() {
        dto = new EditProfileReqDto("nickname", "i".repeat(3000), List.of(1L));
        var validate = validator.validate(dto);
        assertThat(validate).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"가", "가!", "a!", "1@", "12345678911", "안녕_테스트"})
    void nickname_pattern_invalid(String nickname) {
        dto = new EditProfileReqDto(nickname, "introduction", List.of(1L));
        var validate = validator.validate(dto);
        assertThat(validate).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   "})
    void nickname_blank_invalid(String nickname) {
        dto = new EditProfileReqDto(nickname, "introduction", List.of(1L));
        var validate = validator.validate(dto);
        assertThat(validate).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"가rk", "가ddddddddd", "가1abd", "안녕 테스트이다"})
    void nickname_valid(String nickname) {
        dto = new EditProfileReqDto(nickname, "introduction", List.of(1L));
        var validate = validator.validate(dto);
        assertThat(validate).isEmpty();
    }
}