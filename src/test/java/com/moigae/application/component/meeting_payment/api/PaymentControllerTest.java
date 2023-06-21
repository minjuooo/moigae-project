package com.moigae.application.component.meeting_payment.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.moigae.application.component.meeting_payment.application.MeetingPaymentService;
import com.moigae.application.component.user.dto.CustomUser;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class PaymentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MeetingPaymentService meetingPaymentService;

    @MockBean
    RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("토스페이먼츠_유료모임_결제_테스트")
    void confirmPayment() throws Exception {
        //given
        String meetingId = "testMeetingId";
        String paymentKey = "testPaymentKey";
        String orderId = "testOrderId";
        Long amount = 100L;

        CustomUser customUser = new CustomUser();
        customUser.setName("testUser");
        customUser.setUsername("testUser");

        Map<String, String> payloadMap = new HashMap<>();
        payloadMap.put("orderId", orderId);
        payloadMap.put("amount", String.valueOf(amount));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(("SECRET_KEY" + ":").getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(payloadMap), headers);

        JsonNode responseJson = objectMapper.createObjectNode();
        ((ObjectNode) responseJson).put("orderId", orderId);
        ((ObjectNode) responseJson).put("secret", "testSecret");
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(responseJson, HttpStatus.OK);

        //when
        when(restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey, request, JsonNode.class))
                .thenReturn(responseEntity);

        //then
        mockMvc.perform(get("/meetings/{meetingId}/success", meetingId)
                        .param("paymentKey", paymentKey)
                        .param("orderId", orderId)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("유료모임 결제 실패 메시지")
    void 유료모임_결제_실패_메시지() throws Exception {
        //given
        Long meetingId = 1L;
        String message = "testMessage";
        String code = "testCode";

        //when & then
        mockMvc.perform(get("/meetings/{meetingId}/fail", meetingId)
                        .param("message", message)
                        .param("code", code))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("중복 주문 확인")
    void 중복_주문_확인() throws Exception {
        //given
        String orderId = "testOrderId";
        Map<String, String> payload = new HashMap<>();
        payload.put("orderId", orderId);
        boolean exists = false;

        //when
        when(meetingPaymentService.checkOrderId(orderId)).thenReturn(exists);

        //then
        mockMvc.perform(post("/check-orderId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());
    }
}