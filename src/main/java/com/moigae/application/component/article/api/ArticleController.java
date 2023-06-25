package com.moigae.application.component.article.api;

import com.moigae.application.component.article.api.dto.FileUploadDTO;
import com.moigae.application.component.article.api.request.ArticleForm;
import com.moigae.application.component.article.api.service.FileService;
import com.moigae.application.component.article.api.service.GptService;
import com.moigae.application.component.article.domain.Article;
import com.moigae.application.component.article.domain.enumeration.Category;
import com.moigae.application.component.article.repository.ArticleRepository;
import com.moigae.application.component.user.domain.User;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final FileService fileService;
    private final GptService gptService;
    @GetMapping("/createArticle")
    @PreAuthorize("(#customUser != null and #customUser.admin == true)")
    public String createArticle(Model model,
                        @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("customUser", customUser);
        return "articles/createArticle";
    }
    @PostMapping("/createArticle")
    @PreAuthorize("(#customUser != null and #customUser.admin == true)")
    public String createArticle(
            @ModelAttribute ArticleForm articleForm,
            @AuthenticationPrincipal CustomUser customUser
            ) {
        articleForm.setCategory(Category.STORY);
        articleForm.setImgurl(fileService.getUrl(articleForm.getContent()));
        ModelMapper modelMapper = new ModelMapper();
        Article article = modelMapper.map(articleForm, Article.class);
        articleRepository.save(article);
        return "redirect:/articles/articleList";
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
                              @PageableDefault(size = 12, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        model.addAttribute("customUser", customUser);
        return getArticleListByCategory(model, customUser, pageable, Category.STORY, "articles/articleList");
    }

    @GetMapping("/issueList")
    public String issueList(Model model,
                            @AuthenticationPrincipal CustomUser customUser,
                            @PageableDefault(size = 6, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        model.addAttribute("customUser", customUser);
        return getArticleListByCategory(model, customUser, pageable, Category.ISSUE, "articles/issueList");
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
    @PreAuthorize("(#customUser != null and #customUser.admin == true)")
    public ResponseEntity<?> deleteArticle(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable Long articleId) {
        articleRepository.deleteById(articleId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/update/{articleId}")
    @PreAuthorize("(#customUser != null and #customUser.admin == true)")
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

    @PostMapping("/updateArticle/{articleId}")
    @PreAuthorize("(#customUser != null and #customUser.admin == true)")
    public String updateArticle(
            @PathVariable("articleId") Long articleId,
            @ModelAttribute ArticleForm articleForm,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + articleId));

        article.setArticleTitle(articleForm.getArticleTitle());
        article.setContent(articleForm.getContent());
        article.setImgurl(fileService.getUrl(articleForm.getContent()));
        articleRepository.save(article);

        if(article.getCategory().equals(Category.STORY)){
            return "redirect:/articles/articleList";
        }else{
            return "redirect:/articles/issueList";
        }

    }

    @GetMapping("/createIssue")
    @PreAuthorize("(#customUser != null and #customUser.admin == true)")
    public String createIssue(Model model,
                                @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("articleForm", new ArticleForm());
        model.addAttribute("customUser", customUser);
        return "articles/createIssue";
    }
    @PostMapping("/createIssue")
    @PreAuthorize("(#customUser != null and #customUser.admin == true)")
    public String createIssue(
            @AuthenticationPrincipal CustomUser customUser,
            @ModelAttribute ArticleForm articleForm
    ) {
        articleForm.setCategory(Category.ISSUE);
        articleForm.setImgurl(fileService.getUrl(articleForm.getContent()));
        ModelMapper modelMapper = new ModelMapper();
        Article article = modelMapper.map(articleForm, Article.class);
        articleRepository.save(article);
        return "redirect:/articles/issueList";
    }

    @GetMapping("/issueDetail/{articleId}")
    public String getIssueDetail(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable("articleId") Long articleId,
            Model model) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + articleId));

        model.addAttribute("customUser", customUser);
        model.addAttribute("article", article);

        return "articles/issueDetail"; // view의 이름
    }

    @GetMapping("/aboutUs")
    public String aboutUs(Model model,
                              @AuthenticationPrincipal CustomUser customUser) {
        model.addAttribute("customUser", customUser);
        return "articles/aboutUs";
    }

    @PostMapping("/gpt")
    @ResponseBody
    public Map<String, String> aiArticle(
            @RequestBody Map<String, String> req,
            Principal principal) throws Exception {
        String subject = req.get("subject");
        String text = gptService.getGpt(subject);
        List<String> textList = gptService.listing(text);
        StringBuilder sb = new StringBuilder();
        for (String s : textList){
            String en = gptService.getGptEnglish(s);
            //System.out.println("en : "+en);

            String dalle = gptService.generateImage(en);
            //System.out.println("dalle : "+dalle);

            MultipartFile tmp = gptService.downloadAsMultipartFile(dalle, String.valueOf(en.length())+".jpg");
            //System.out.println("tmp : "+tmp);

            FileUploadDTO fileUploadDTO = fileService.fileUpload(tmp, principal);
            String url = fileUploadDTO.getUrl();
            //System.out.println("url : "+url);

            String detail = gptService.getGptDetail(s);
            //System.out.println(detail);

            sb.append("<p><span style=\"font-size:20px\"><strong>"+s+"</strong></span>&nbsp;<br />").append("\n");
            sb.append("<p><img src=\""+url+"\" /></p>").append("\n").append("\n");
            sb.append("<p>"+detail+"</p>").append("\n").append("\n");
        }
        System.out.println(sb);

        Map<String, String> response = new HashMap<>();
        response.put("status", sb.toString());

        return response;
    }
}
