package com.epam.tc.news.controller;

import com.epam.tc.news.dto.NewsRequestDto;
import com.epam.tc.news.entity.News;
import com.epam.tc.news.service.NewsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/news")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NewsController {

    NewsService newsService;
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<NewsRequestDto>> getAllNews() {
        List<News> news = newsService.getAllNews();
        List<NewsRequestDto> newsRequestDtos = new ArrayList<>();
        news.forEach(n -> newsRequestDtos.add(convertToDto(n)));
        return ResponseEntity.ok(newsRequestDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsRequestDto> getNewsById(@PathVariable("id") Long id) {
        var news = newsService.getNewsById(id);
        return ResponseEntity.ok(convertToDto(news));
    }

    @PostMapping
    public ResponseEntity<NewsRequestDto> createNews(@RequestBody NewsRequestDto newsRequestDto) {
        var news = convertToEntity(newsRequestDto);
        news = newsService.createNews(news);
        return new ResponseEntity<>(newsRequestDto, news == null ? HttpStatus.CONFLICT : HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#newsRequestDto.user.id == authentication.principal.id")
    public ResponseEntity<NewsRequestDto> createOrUpdateNews(@RequestBody NewsRequestDto newsRequestDto, @PathVariable("id") Long id,
                                                   Authentication authentication) {
        var news = convertToEntity(newsRequestDto);
        newsService.createOrUpdateNews(news, id);
        return new ResponseEntity<>(newsRequestDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#newsService.getNewsById(id).user.id == authentication.principal.id")
    public void deleteNewsById(@PathVariable("id") Long id) {
        newsService.deleteNewsById(id);
    }

    private News convertToEntity(NewsRequestDto newsRequestDto) {
        return modelMapper.map(newsRequestDto, News.class);
    }

    private NewsRequestDto convertToDto(News news) {
        return modelMapper.map(news, NewsRequestDto.class);
    }
}
