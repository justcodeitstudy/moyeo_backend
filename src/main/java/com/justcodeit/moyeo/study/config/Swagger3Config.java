package com.justcodeit.moyeo.study.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Swagger3Config {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.swagger-ui.version}") String docVersion) {
        Info apiInfo = new Info()
                        .title("Moyeo !!")
                .version(docVersion)
                .description("모여는 프로젝트/스터디를 모집하는 글을 작성하는 플랫폼입니다!");

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo);
    }
}
