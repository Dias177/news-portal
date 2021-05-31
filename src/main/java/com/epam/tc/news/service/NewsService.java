package com.epam.tc.news.service;

import com.epam.tc.news.entity.News;
import com.epam.tc.news.repository.NewsRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NewsService {

    NewsRepository newsRepository;

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News getNewsById(long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            return news.get();
        } else {
            throw new NewsNotFoundException("News not found id: " + id);
        }
    }

    public News getNewsByTitle(String title) {
        Optional<News> news = newsRepository.findByTitle(title);
        if (news.isPresent()) {
            return news.get();
        } else {
            throw new NewsNotFoundException("News not found title: " + title);
        }
    }

    public News createOrUpdateNews(News news) {
        Optional<News> n = newsRepository.findById(news.getId());
        News newNews;
        if (n.isPresent()) {
            newNews = n.get();
            newNews.setTitle(news.getTitle());
            newNews.setBrief(news.getBrief());
            newNews.setContent(news.getContent());
            newNews = newsRepository.save(newNews);
        } else {
            newNews = newsRepository.save(news);
        }
        return newNews;
    }

    public void deleteNewsById(long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            newsRepository.deleteById(id);
        } else {
            throw new NewsNotFoundException("News not found id: " + id);
        }
    }
}
