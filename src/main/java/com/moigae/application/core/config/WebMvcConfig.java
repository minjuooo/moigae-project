package com.moigae.application.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserToCustomUserConverter userToCustomUserConverter;

    public WebMvcConfig(UserToCustomUserConverter userToCustomUserConverter) {
        this.userToCustomUserConverter = userToCustomUserConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(userToCustomUserConverter);
    }
}

