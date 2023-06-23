package com.moigae.application.component.article.api;

import com.moigae.application.component.article.api.request.PostForm;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class SmartEditorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("스마트 에디터 2, 수정 컨트롤러 테스트")
    @WithMockUser
    void smartEditor2PostTest() throws Exception {
        //given
        PostForm postForm = new PostForm();
        postForm.setContent("Test Content");

        //when & then
        mockMvc.perform(post("/smarteditor/smartEditor2").flashAttr("postForm", postForm))
                .andExpect(status().is3xxRedirection());
    }
}