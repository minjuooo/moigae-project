package com.moigae.application.component;

import com.moigae.application.component.user.dto.CustomUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(Model model,
                        @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("customUser", customUser);
        return "index";
    }
}
