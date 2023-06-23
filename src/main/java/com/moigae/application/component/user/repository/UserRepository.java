package com.moigae.application.component.user.repository;

import com.moigae.application.component.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    User findByUserName(String username);

    User findByUserNameAndPhone(String name, String phone);

    User findByEmailAndUserNameAndPhone(String email, String name, String phone);
}