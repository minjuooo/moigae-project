package com.moigae.application.component.user.api;

import com.moigae.application.component.user.api.request.UserLoginForm;
import com.moigae.application.component.user.api.service.MailSendService;
import com.moigae.application.component.user.application.UserService;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.domain.enumeration.Gender;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.dto.UserDto;
import com.moigae.application.component.user.repository.UserRepository;
import com.moigae.application.core.config.PrimaryGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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
    private final MailSendService mailSendService;

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
    @ResponseBody
    public void signup(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String gender,
            @RequestParam String phone
    ) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserName(name);
        user.setGender(Gender.valueOf(gender.toUpperCase()));
        user.setPhone(phone);
        userRepository.save(user);
    }

    @GetMapping("/findId")
    public String findId(
            Model model,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        model.addAttribute("customUser", customUser);
        return "users/findId";
    }

    @PostMapping("/findId")
    @ResponseBody
    public Map<String, String> findId(
            @RequestParam String name,
            @RequestParam String phone
    ) {
        User user = userRepository.findByUserNameAndPhone(name, phone);
        String message = "";
        if (user == null) {
            message = "해당하는 아이디를 찾을 수 없습니다.";
        } else {
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

    @PostMapping("/existEmail")
    @ResponseBody
    public Map<String, Object> existEmail(
            @RequestBody Map<String, String> req) {

        Map<String, Object> map = new HashMap<>();

        User user = userRepository.findByEmail(req.get("email"));

        if (user == null) {
            map.put("status", "not exist");
            map.put("message", "사용가능한 이메일입니다.");
        } else {
            map.put("status", "exist");
            map.put("message", "이미 존재하는 이메일입니다.");
        }

        return map;
    }

    @GetMapping("/findPassWord")
    public String findPassWord(
            Model model,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        model.addAttribute("customUser", customUser);
        return "users/findPassWord";
    }

    @PostMapping("/findPassWord")
    @ResponseBody
    public Map<String, String> findPassWord(
            @RequestBody Map<String, String> req) {
        User user = userRepository
                .findByEmailAndUserNameAndPhone(req.get("email"), req.get("name"), req.get("phone"));
        String message = "";
        if (user == null) {
            message = "empty";
        } else {
            message = "present";
        }

        Map<String, String> response = new HashMap<>();
        response.put("status", message);

        return response;
    }

    @GetMapping("/permission/{email}")
    public String permission(
            Model model,
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable String email) {
        model.addAttribute("customUser", customUser);
        model.addAttribute("email");
        return "users/permission";
    }

    @PostMapping("/permission")
    @ResponseBody
    public Map<String, String> permission(
            @RequestBody Map<String, String> req) {
        System.out.println(req.get("email"));
        String auth = mailSendService.joinEmail(req.get("email"));

        Map<String, String> response = new HashMap<>();
        response.put("number", auth);

        return response;
    }

    @PostMapping("/newPassword")
    @ResponseBody
    @Transactional
    public Map<String, String> newPassword(
            @RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");

        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return response;
    }
}