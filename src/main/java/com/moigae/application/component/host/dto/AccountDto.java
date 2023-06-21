package com.moigae.application.component.host.dto;

import com.moigae.application.component.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private String id;
    private String accountNumber;
    private String bankName;
    private User user;
}