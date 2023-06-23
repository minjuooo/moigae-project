package com.moigae.application.component.article.api;

import com.moigae.application.component.article.api.request.ArticleForm;
import com.moigae.application.component.article.api.service.FileService;
import com.moigae.application.component.article.domain.Article;
import com.moigae.application.component.article.domain.enumeration.Category;
import com.moigae.application.component.article.repository.ArticleRepository;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@MockMvcTest
class ArticleControllerTest {
    private static final String ARTICLE_TITLE = "Article Title";
    private static final String ARTICLE_CONTENT = "Article Content";
    private static final String ARTICLE_URL = "https://www.naver.com";

    @MockBean
    private ArticleRepository articleRepository;
    @MockBean
    private FileService fileService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("아티클 생성 폼, 예외 경우 체크")
    public void 아티클_생성_폼() throws Exception {
        //given
        CustomUser customUser = new CustomUser();
        customUser.setUsername("username");
        customUser.setPassword("password");
        customUser.setId("id");
        customUser.setName("name");

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/articles/createArticle")
                        .with(user(customUser)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("아티클 생성")
    public void 아티클_생성() throws Exception {
        //given
        ArticleForm articleForm = new ArticleForm();
        articleForm.setCategory(Category.STORY);
        articleForm.setContent("Test Content");

        //when & then
        when(fileService.getUrl(articleForm.getContent())).thenReturn("http://test.url");

        mockMvc.perform(MockMvcRequestBuilders.post("/articles/createArticle")
                        .flashAttr("articleForm", articleForm))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("아티클 삭제")
    public void deleteArticle() throws Exception {
        //when & then
        doNothing().when(articleRepository).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/articles/delete/{articleId}", 1L))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("아티클 업데이트, 예외 상황 체크")
    @WithMockUser
    public void updateArticle() throws Exception {
        //given
        Article article = Article.of(ARTICLE_TITLE, ARTICLE_CONTENT, Category.STORY, ARTICLE_URL);

        //when & then
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        mockMvc.perform(MockMvcRequestBuilders.post("/articles/updateArticle/{articleId}", 1L)
                        .param("content", "Updated Content"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("이슈 생성")
    public void createIssueTest() throws Exception {
        ArticleForm articleForm = new ArticleForm();
        articleForm.setCategory(Category.ISSUE);
        articleForm.setContent("Test Content");

        when(fileService.getUrl(articleForm.getContent())).thenReturn("http://test.url");

        mockMvc.perform(MockMvcRequestBuilders.post("/articles/createIssue")
                        .flashAttr("articleForm", articleForm))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("aboutUs 조회")
    public void aboutUsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/articles/aboutUs")
                        .with(user(new CustomUser())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("articles/aboutUs"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("customUser"))
                .andDo(MockMvcResultHandlers.print());
    }
}