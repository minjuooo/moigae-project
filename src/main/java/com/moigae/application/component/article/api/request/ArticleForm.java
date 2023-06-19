package com.moigae.application.component.article.api.request;

import com.moigae.application.component.article.domain.enumeration.Category;
import lombok.Data;
import lombok.Getter;

@Data
public class ArticleForm {
    private String articleTitle;
    private String content;
    private Category category;
    private String imgurl;

    public ArticleForm() {}

    public ArticleForm(String articleTitle, String content, Category category, String imgurl) {
        this.articleTitle = articleTitle;
        this.content = content;
        this.category = category;
        this.imgurl = imgurl;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
