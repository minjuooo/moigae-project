package com.moigae.application.component.article.api.request;

public class PostForm {
    private String content;

    // 기본 생성자
    public PostForm() {
    }
    // 모든 필드를 초기화하는 생성자
    public PostForm(String content) {
        this.content = content;
    }

    // getter
    public String getContent() {
        return content;
    }

    // setter
    public void setContent(String content) {
        this.content = content;
    }
}
