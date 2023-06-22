package com.moigae.application.component.article.domain.enumeration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {
    @Test
    @DisplayName("아티클_카테고리_도메인_생성_테스트")
    void 아티클_카테고리_도메인_생성_테스트() {
        //given
        Category category = Category.STORY;

        //when & then
        assertThat(category.getValue()).isEqualTo("기사");
    }
}