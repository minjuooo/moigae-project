package com.moigae.application.component.user.application;

import com.moigae.application.component.user.api.request.UserLoginForm;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.domain.enumeration.UserRole;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.dto.UserDto;
import com.moigae.application.component.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto login(UserLoginForm userLoginForm) {
        UserDto userDto = (UserDto) loadUserByUsername(userLoginForm.getEmail());
        log.info(userDto.getEmail());
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 email 입니다.");
        }
        boolean admin = false;
        if (user.getUserRole() != null && user.getUserRole().equals(UserRole.ADMIN)) {
            admin = true;
        }

        CustomUser customUser = new CustomUser();
        customUser.setUsername(user.getEmail());
        customUser.setPassword(user.getPassword());
        customUser.setId(user.getId());
        customUser.setName(user.getUserName());
        customUser.setAdmin(admin);

        // customUser 객체 설정...
        return customUser;
    }

    @Transactional(readOnly = true)
    public UserDto customUserFindBy(CustomUser customUser) {
        String userName = customUser.getName();
        return UserDto.of(userRepository.findByUserName(userName));
    }
}