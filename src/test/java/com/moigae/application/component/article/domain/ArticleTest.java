package com.moigae.application.component.article.domain;

import com.moigae.application.component.article.domain.enumeration.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ArticleTest {
    private static final String ARTICLE_TITLE = "Article Title";
    private static final String ARTICLE_CONTENT = "Article Content";
    private static final String ARTICLE_URL = "https://www.naver.com";

    @Test
    @DisplayName("아티클 도메인 생성 테스트")
    void 아티클_도메인_생성_테스트() {
        //given & when
        Article article = Article.of(ARTICLE_TITLE, ARTICLE_CONTENT, Category.STORY, ARTICLE_URL);
        article.setId(1L);
        //then
        아티클_도메인_setter_검증(article, ARTICLE_TITLE, ARTICLE_CONTENT, Category.STORY, ARTICLE_URL);
    }

    @Test
    @DisplayName("아티클 도메인 setter 테스트")
    void 아티클_도메인_setter_테스트() {
        // given
        Article article = Article.of(ARTICLE_TITLE, ARTICLE_CONTENT, Category.STORY, ARTICLE_URL);
        article.setId(1L);
        String newTitle = "New Title";
        String newContent = "New Content";
        Category newCategory = Category.ISSUE;
        String newUrl = "https://www.google.com";

        // when
        article.setArticleTitle(newTitle);
        article.setContent(newContent);
        article.setCategory(newCategory);
        article.setImgurl(newUrl);

        // then
        아티클_도메인_setter_검증(article, newTitle, newContent, newCategory, newUrl);
    }

    private static void 아티클_도메인_setter_검증(Article article, String newTitle, String newContent, Category newCategory, String newUrl) {
        assertThat(article.getId()).isEqualTo(1L);
        assertThat(article.getArticleTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
        assertThat(article.getCategory()).isEqualTo(newCategory);
        assertThat(article.getImgurl()).isEqualTo(newUrl);
    }
}