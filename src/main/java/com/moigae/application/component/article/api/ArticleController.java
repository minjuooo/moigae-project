package com.moigae.application.component.article.api;

import com.moigae.application.component.article.api.dto.FileUploadDTO;
import com.moigae.application.component.article.api.request.ArticleForm;
import com.moigae.application.component.article.api.request.PostForm;
import com.moigae.application.component.article.api.service.FileService;
import com.moigae.application.component.article.domain.Article;
import com.moigae.application.component.article.domain.enumeration.Category;
import com.moigae.application.component.article.repository.ArticleRepository;
import com.moigae.application.component.user.api.request.UserLoginForm;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.component.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final FileService fileService;
    @GetMapping("/createArticle")
    public String createArticle(Model model,
                        @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("customUser", customUser);
        return "articles/createArticle";
    }
    @PostMapping("/createArticle")
    public String createArticle(
            @ModelAttribute ArticleForm articleForm
            ) {
        articleForm.setCategory(Category.STORY);
        articleForm.setImgurl(fileService.getUrl(articleForm.getContent()));
        ModelMapper modelMapper = new ModelMapper();
        Article article = modelMapper.map(articleForm, Article.class);
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/articleList")
    public String articleList(Model model,
                              @AuthenticationPrincipal CustomUser customUser,
                              @PageableDefault(size = 12) Pageable pageable
    ) {
        Page<Article> articles = articleRepository.findAll(pageable);

        // Calculate the page group
        int startPage = (pageable.getPageNumber() / 10) * 10;
        int endPage = Math.min(startPage + 10, articles.getTotalPages());

        model.addAttribute("customUser", customUser);
        model.addAttribute("articles", articles);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "articles/articleList";
    }
}
