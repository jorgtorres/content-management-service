package com.unionestate.cm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.TimeZone;

@SpringBootApplication(
        exclude = {
                RedisAutoConfiguration.class,
                RedisRepositoriesAutoConfiguration.class,
                JpaRepositoriesAutoConfiguration.class,
                DataSourceAutoConfiguration.class
        }
)
@SecurityScheme(
        name = "User Service Bearer Token",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(
                title = "${openapi.info.title}",
                version = "${openapi.info.version}",
                license = @License(name = "Apache 2.0", url = "http://springdoc.org")),
        servers = {
                @Server(url = "${spring.servlet.context.path}", description = "Default Server URL")
        },
        security = {
                @SecurityRequirement(name = "CM Service Bearer Token")
        }
)
@ComponentScan({ "com.unionestate" })
public class CmApplication {

    @Value("${spring.servlet.context.path}")
    String contextPath;

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(CmApplication.class, args);
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setContextPath(contextPath);
    }

    @Bean
    @Qualifier("rdsResponseMapper")
    public ObjectMapper getRdsResponseMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)

                .modules(new ParameterNamesModule(), new Jdk8Module(), new JavaTimeModule())
                .indentOutput(true)
                .build();
    }
}
