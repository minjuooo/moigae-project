package com.moigae.application.component.host.service;

import com.moigae.application.component.host.domain.Account;
import com.moigae.application.component.host.dto.AccountDto;
import com.moigae.application.component.host.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void createAccount(AccountDto accountDto) {
        Account account = Account.builder()
                .bankName(accountDto.getBankName())
                .accountNumber(accountDto.getAccountNumber())
                .build();
        Account result = accountRepository.save(account);
        accountDto.setId(result.getId());
    }

    //이으면 가져와서 빌드만들고 없으면 뉴어카ㅌ,
    public AccountDto getAccount(String id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        return optionalAccount.isPresent() ? AccountDto.builder()
                .id(optionalAccount.get().getId())
                .accountNumber(optionalAccount.get().getAccountNumber())
                .bankName(optionalAccount.get().getBankName())
                .user(optionalAccount.get().getUser())
                .build() : new AccountDto();
    }

    public boolean iscreateAccountExists(String accountNumber) {
        if (accountRepository.existsByAccountNumber(accountNumber)) {
            return true;
        } else {
            return false;
        }
    }

//    public boolean iscreateAccountExists(String accountNumber) {
//        return accountRepository.existsByAccountNumber(accountNumber);
//    }
//
//    public boolean iscreateAccountExists(String accountNumber) {
//        // if로 account존쟈하면 true 없으묜 F
//        return false;
//    }
}