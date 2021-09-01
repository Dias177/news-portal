package com.epam.tc.news.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponseDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private Date birthday;

}
