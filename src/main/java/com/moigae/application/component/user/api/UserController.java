package com.moigae.application.component.user.api;

import com.moigae.application.component.user.api.request.UserLoginForm;
import com.moigae.application.component.user.application.UserService;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.dto.UserDto;
import com.moigae.application.component.user.repository.UserRepository;
import com.moigae.application.core.config.PrimaryGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final PrimaryGenerator primaryGenerator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String login(Model model,
                        @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("userLoginForm", new UserLoginForm());
        model.addAttribute("customUser", customUser);
        return "users/login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("userLoginForm") UserLoginForm userLoginForm
    ) {
        userService.login(userLoginForm);
        return "redirect:/";
    }
    @GetMapping("/signup")
    public String signup(Model model,
    @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("customUser", customUser);
        return "users/signup";
    }

    @PostMapping("/signup")
    public String signup(
            @ModelAttribute UserDto userDto
    ) {
       System.out.println(userDto);
       userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
       ModelMapper modelMapper = new ModelMapper();
       User user = modelMapper.map(userDto, User.class);
       System.out.println(user);
       userRepository.save(user);
       return "redirect:/";
    }
}