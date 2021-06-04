package com.epam.tc.news.controller;

import com.epam.tc.news.dto.NewsDto;
import com.epam.tc.news.entity.News;
import com.epam.tc.news.service.NewsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NewsController {

    NewsService newsService;

    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        List<News> news = newsService.getAllNews();
        return ResponseEntity.ok(news);
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id") Long id) {
        News news = newsService.getNewsById(id);
        return ResponseEntity.ok(news);
    }

    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody NewsDto newsDto) {
        News news = mapNewsDtoToEntity(newsDto);
        news = newsService.createNews(news);
        return new ResponseEntity<>(news, news == null ? HttpStatus.CONFLICT : HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> createOrUpdateNews(@RequestBody NewsDto newsDto, @PathVariable("id") Long id) {
        News news = mapNewsDtoToEntity(newsDto);
        news = newsService.createOrUpdateNews(news, id);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteNewsById(@PathVariable("id") Long id) {
        newsService.deleteNewsById(id);
    }

    private News mapNewsDtoToEntity(NewsDto newsDto) {
        var news = new News();
        news.setTitle(newsDto.getTitle());
        news.setBrief(newsDto.getBrief());
        news.setContent(newsDto.getContent());
        news.setUser(newsDto.getUser());
        return news;
    }
}
