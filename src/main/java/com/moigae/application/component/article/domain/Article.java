package com.moigae.application.component.article.domain;

import com.moigae.application.component.article.domain.enumeration.Category;
import com.moigae.application.core.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "content", length = 14000)
    private String content;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "imgurl")
    private String imgurl;

    private Article(String articleTitle, String content, Category category, String imgurl) {
        this.articleTitle = articleTitle;
        this.content = content;
        this.category = category;
        this.imgurl = imgurl;
    }

    public static Article of(String articleTitle, String content, Category category, String imgurl) {
        return new Article(articleTitle, content, category, imgurl);
    }
}