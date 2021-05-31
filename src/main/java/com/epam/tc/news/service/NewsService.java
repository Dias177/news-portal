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

    public News getNewsById(Long id) {
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

    public News createNews(News news) {
        Long id = news.getId();
        String title = news.getTitle();
        News n = null;
        if (!newsExists(id, title)) {
            n = newsRepository.save(news);
        }
        return n;
    }

    public News createOrUpdateNews(News news, Long id) {
        return newsRepository.findById(id).map(n -> {
            n.setTitle(news.getTitle());
            n.setBrief(news.getBrief());
            n.setContent(news.getContent());
            return newsRepository.save(n);
        }).orElseGet(() -> {
            news.setId(id);
            return newsRepository.save(news);
        });
    }

    public void deleteNewsById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            newsRepository.deleteById(id);
        } else {
            throw new NewsNotFoundException("News not found id: " + id);
        }
    }

    public boolean newsExists(Long id, String title) {
        Optional<News> newsWithId = newsRepository.findById(id);
        Optional<News> newsWithTitle = newsRepository.findByTitle(title);
        return newsWithId.isPresent() || newsWithTitle.isPresent();
    }
}
