package com.moigae.application.component.article.api;

import com.moigae.application.component.article.api.request.ArticleForm;
import com.moigae.application.component.article.api.service.FileService;
import com.moigae.application.component.article.domain.Article;
import com.moigae.application.component.article.domain.enumeration.Category;
import com.moigae.application.component.article.repository.ArticleRepository;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    public String getArticleListByCategory(Model model,
                                           @AuthenticationPrincipal CustomUser customUser,
                                           @PageableDefault(size = 10) Pageable pageable,
                                           Category category,
                                           String viewName) {

        Page<Article> articles = articleRepository.findByCategory(category, pageable);

        // Calculate the page group
        int startPage = (pageable.getPageNumber() / 10) * 10;
        int endPage = Math.min(startPage + 10, articles.getTotalPages());

        model.addAttribute("customUser", customUser);
        model.addAttribute("articles", articles);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return viewName;
    }

    @GetMapping("/articleList")
    public String articleList(Model model,
                              @AuthenticationPrincipal CustomUser customUser,
                              @PageableDefault(size = 12) Pageable pageable) {
        return getArticleListByCategory(model, customUser, pageable, Category.STORY, "articles/articleList");
    }

    @GetMapping("/issueList")
    public String issueList(Model model,
                            @AuthenticationPrincipal CustomUser customUser,
                            @PageableDefault(size = 10) Pageable pageable) {
        return getArticleListByCategory(model, customUser, pageable, Category.ISSUE, "articles/articleList");
    }

    @GetMapping("/articleDetail/{articleId}")
    public String getArticleDetail(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable("articleId") Long articleId,
            Model model) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + articleId));

        model.addAttribute("customUser", customUser);
        model.addAttribute("article", article);

        return "articles/articleDetail"; // view의 이름
    }

    @DeleteMapping("/delete/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long articleId) {
        articleRepository.deleteById(articleId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/update/{articleId}")
    public String updateArticle(
            Model model,
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + articleId));
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("customUser", customUser);
        model.addAttribute("article", article);

        return "articles/updateArticle";
    }
}
