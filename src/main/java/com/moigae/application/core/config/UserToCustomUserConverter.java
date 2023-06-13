package com.moigae.application.core.config;

import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToCustomUserConverter implements Converter<User, CustomUser> {

    @Override
    public CustomUser convert(User user) {

        CustomUser customUser = new CustomUser();
        customUser.setUsername(user.getEmail());
        customUser.setPassword(user.getPassword());
        customUser.setId(user.getId());
        return customUser;
    }
}
