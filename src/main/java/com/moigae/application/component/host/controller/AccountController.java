package com.moigae.application.component.host.controller;

import com.moigae.application.component.host.dto.AccountDto;
import com.moigae.application.component.host.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/create")
    public String createAccount(Model model) {
        model.addAttribute("accountDto", new AccountDto());

        return "host/hostCenterMain";
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute("accountDto") AccountDto accountDto) {
        accountService.createAccount(accountDto);
        return "host/hostCenterMain";
    }
}