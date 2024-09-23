package com.study.SpringSecurityMybatis.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqSigninDto {
    @NotBlank(message = "사용자 이름을 체크해 주세요.")
    private String username;
    @NotBlank(message = "비밀번호를 입력해달라느뇽.")
    private String password;
    
}
