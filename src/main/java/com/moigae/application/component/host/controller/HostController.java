package com.moigae.application.component.host.controller;

import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.repository.UserRepository;
import com.moigae.application.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/host")
@RequiredArgsConstructor
@Slf4j
public class HostController {

    private final UserRepository userRepository;

    @GetMapping("/hostCenterMain")
    public String hostCenterMain(Model model, @AuthenticationPrincipal CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id"));
        System.out.println(user);

        model.addAttribute("customUser", customUser);
        model.addAttribute("user", user);
        return "host/hostCenterMain";
    }


}