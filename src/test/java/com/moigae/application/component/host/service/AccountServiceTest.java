package com.moigae.application.component.host.service;

import com.moigae.application.component.host.domain.Account;
import com.moigae.application.component.host.dto.AccountDto;
import com.moigae.application.component.host.repository.AccountRepository;
import com.moigae.application.core.annotation.MockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockMvcTest
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    private Account account;
    private AccountDto accountDto;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .id("1")
                .bankName("은행명")
                .accountNumber("계좌번호")
                .build();

        accountDto = AccountDto.builder()
                .id(account.getId())
                .bankName(account.getBankName())
                .accountNumber(account.getAccountNumber())
                .build();
    }

    @Test
    @DisplayName("계좌 생성")
    void 계좌_생성() {
        //when
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        accountService.createAccount(accountDto);

        //then
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @DisplayName("계좌 조회")
    void 계좌_조회() {
        //when
        when(accountRepository.findById(anyString())).thenReturn(Optional.of(account));
        AccountDto returnedDto = accountService.getAccount("1");

        //then
        assertThat(returnedDto).isNotNull();
        assertThat(returnedDto.getId()).isEqualTo(account.getId());
    }

    @Test
    @DisplayName("계좌 중복 체크")
    void 계좌_중복_체크() {
        //when
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(true);
        boolean exists = accountService.iscreateAccountExists("12345678");

        //then
        assertThat(exists).isTrue();
    }
}