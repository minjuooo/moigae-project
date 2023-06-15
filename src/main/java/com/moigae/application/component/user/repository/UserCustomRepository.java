package com.moigae.application.component.user.repository;

import com.moigae.application.component.user.domain.User;

public interface UserCustomRepository {
    //uuid 대응
    User findCustomUserById(String userId);
}