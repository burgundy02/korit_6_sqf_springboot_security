package com.study.SpringSecurity.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqSigninDto {
    @NotBlank(message = "응애")
    private String username;
    @NotBlank(message = "비밀번호를 응애")
    private String password;
}
