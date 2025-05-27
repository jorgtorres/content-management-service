package com.unionestate.cm.web.configuration;

import com.unionestate.cm.web.common.RequestInterceptor;
import com.unionestate.commons.model.UestCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${springdoc.swagger-ui.path}")
    private String apiDocUrl;

    @Autowired
    RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).excludePathPatterns(apiDocUrl, "/v3/api-docs/**", "/swagger-ui/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UestCollectionConverter());
    }

    private class UestCollectionConverter implements Converter<String, UestCollection> {

        @Override
        public UestCollection convert(String source) {
            return UestCollection.forValue(source);
        }

    }
}
