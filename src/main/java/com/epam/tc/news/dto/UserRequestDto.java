package com.epam.tc.news.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserRequestDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private Date birthday;

}
