package com.epam.tc.news.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String email;
    private String password;
}
