package com.moigae.application.component.host.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {
    @Test
    @DisplayName("계좌_도메인_생성_테스트")
    void 계좌_도메인_생성_테스트() {
        //given & when
        Account account = createAccount();

        //then
        assertThat(account.getId()).isEqualTo("AccountId");
        assertThat(account.getAccountNumber()).isEqualTo("계좌 번호");
        assertThat(account.getBankName()).isEqualTo("은행명");
    }

    private static Account createAccount() {
        return Account.builder()
                .id("AccountId")
                .accountNumber("계좌 번호")
                .bankName("은행명")
                .build();
    }
}