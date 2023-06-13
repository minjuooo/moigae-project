package com.moigae.application.component.article.api;

import com.moigae.application.component.article.api.request.PostForm;
import com.moigae.application.component.user.api.request.UserLoginForm;
import com.moigae.application.component.user.dto.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Controller
@RequestMapping("/smarteditor")
@RequiredArgsConstructor
@Slf4j
public class SmartEditorController {

    @GetMapping("/smartEditor")
    public String smartEditor(Model model,
                        @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("customUser", customUser);
        model.addAttribute("postForm", new PostForm());
        return "smarteditor/smartEditor";
    }
    @GetMapping("/smartEditor2")
    public String smartEditor2(Model model,
                        @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("customUser", customUser);
        model.addAttribute("postForm", new PostForm());
        return "smarteditor/smartEditor2";
    }

    @PostMapping("/smartEditor2")
    public String smartEditor2(
            @ModelAttribute PostForm postForm
    ) {
        System.out.println(postForm.getContent());
        return "redirect:/";
    }
}
