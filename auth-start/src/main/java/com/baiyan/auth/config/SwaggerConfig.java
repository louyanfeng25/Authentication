package com.baiyan.auth.config;

import com.baiyan.auth.api.constant.AuthConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * swagger配置文件
 * @author baiyan
 * @date 2020/11/13
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket bulletinWarningRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("auth")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.baiyan.auth"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .globalOperationParameters(setHeaderToken());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("鉴权中心API")
                .description("鉴权中心")
                .version("0.0.1")
                .build();
    }

    private List<Parameter> setHeaderToken() {
        List<Parameter> params = new ArrayList<>();
        String defaultValue = "Bearer test";
        ParameterBuilder builder = new ParameterBuilder();
        builder.name(AuthConstant.HEADER_TOKEN_KEY)
                .description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue(defaultValue)
                .required(false)
                .build();
        params.add(builder.build());
        return params;
    }
}
