package com.moigae.application.core.config;

import com.moigae.application.component.meeting.domain.enumeraion.MeetingCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MeetingConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
    }

    class StringToEnumConverter implements Converter<String, MeetingCategory> {
        @Override
        public MeetingCategory convert(String source) {
            try {
                return MeetingCategory.valueOf(source.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }
}