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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/findId")
    public String findId(
            Model model,
            @AuthenticationPrincipal CustomUser customUser
    ){
        model.addAttribute("customUser", customUser);
        return "users/findId";
    }
    @PostMapping("/findId")
    @ResponseBody
    public Map<String, String> findId(
            @RequestParam String name,
            @RequestParam String phone
    ){
        User user = userRepository.findByUserNameAndPhone(name, phone);
        String message = "";
        if(user == null){
            message = "해당하는 아이디를 찾을 수 없습니다.";
        }else{
            message = "안녕하세요!<br/>" +
                    "회원님의 아이디는<br/><br/>" +
                    "<span style=\"color:red;\">" + user.getEmail() + "</span>" +
                    "<br/><br/>" +
                    "입니다!";
        }

        Map<String, String> response = new HashMap<>();
        response.put("status", message);

        return response;
    }
}