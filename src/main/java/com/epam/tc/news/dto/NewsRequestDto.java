package com.epam.tc.news.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NewsRequestDto {

    private Long id;
    private String title;
    private Date date;
    private String brief;
    private String content;
    private UserResponseDto user;
}
