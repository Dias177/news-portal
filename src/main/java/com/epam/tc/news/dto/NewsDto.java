package com.epam.tc.news.dto;

import com.epam.tc.news.entity.User;
import lombok.Data;

@Data
public class NewsDto {

    private String title;
    private String brief;
    private String content;
    private User user;
}
