package com.moigae.application.component;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private final UserRepository userRepository;

    @GetMapping("/mypage")
    public String myPage(Model model, @AuthenticationPrincipal CustomUser customUser) {
        User user = userRepository.findById(customUser.getId())
                        .orElseThrow(()-> new ResourceNotFoundException("User not found with id"));
        System.out.println(user);

        model.addAttribute("customUser", customUser);
        model.addAttribute("user", user);
        return "users/mypage";
    }
    @GetMapping("/mypageMoim")
    public String myPageMoim(Model model, @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("customUser", customUser);
        return "users/mypageMoim";
    }

    @GetMapping("/mypageCart")
    public String myPageCart(Model model, @AuthenticationPrincipal CustomUser customUser){
        model.addAttribute("customUser", customUser);
        return "users/mypageCart";
    }
    @GetMapping("/mypageUnJoin")
    public String myPageUnJoin(Model model, @AuthenticationPrincipal CustomUser customUser){
        model.addAttribute("customUser", customUser);
        return "users/mypageUnJoin";
    }

}
