package com.epam.tc.news;

import com.epam.tc.news.entity.News;
import com.epam.tc.news.entity.User;
import com.epam.tc.news.exception.NewsNotFoundException;
import com.epam.tc.news.repository.NewsRepository;
import com.epam.tc.news.service.NewsService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NewsServiceTest {

    @InjectMocks
    private NewsService newsService;

    @Mock
    private NewsRepository newsRepository;

    @Test
    public void getAllNewsWhenExistTest() {
        List<News> news = new ArrayList<>();

        long id1 = 1L;
        User user1 = new User();
        String title1 = "Title 1";
        Date date1 = new Date();
        String brief1 = "Brief 1";
        String content1 = "Content 1";
        News news1 = new News(id1, user1, title1, date1, brief1, content1);

        long id2 = 2L;
        User user2 = new User();
        String title2 = "Title 2";
        Date date2 = new Date();
        String brief2 = "Brief 2";
        String content2 = "Content 2";
        News news2 = new News(id2, user2, title2, date2, brief2, content2);

        news.add(news1);
        news.add(news2);

        when(newsRepository.findAll()).thenReturn(news);
        List<News> newsList = newsService.getAllNews();

        Assertions.assertEquals(2, newsList.size());
    }

    @Test
    public void getAllNewsWhenDoNotExistTest() {
        List<News> emptyNewsList = new ArrayList<>();
        Assertions.assertEquals(emptyNewsList, newsService.getAllNews());
    }

    @Test
    public void getNewsByExistingIdTest() {
        long id = 3L;
        User user = new User();
        String title = "Title 3";
        Date date = new Date();
        String brief = "Brief 3";
        String content = "Content 3";
        News news = new News(id, user, title, date, brief, content);

        when(newsRepository.findById(id)).thenReturn(java.util.Optional.of(news));
        News foundNews = newsService.getNewsById(id);

        Assertions.assertEquals(foundNews, news);
    }

    @Test(expected = NewsNotFoundException.class)
    public void getNewsByNonExistentIdTest() {
        long nonExistentId = 321L;
        newsService.getNewsById(nonExistentId);
    }

    @Test(expected = NewsNotFoundException.class)
    public void getNewsByNullIdTest() {
        Long nullId = null;
        newsService.getNewsById(nullId);
    }

    @Test
    public void getNewsByExistingTitleTest() {
        long id = 4L;
        User user = new User();
        String title = "Title 4";
        Date date = new Date();
        String brief = "Brief 4";
        String content = "Content 4";
        News news = new News(id, user, title, date, brief, content);

        when(newsRepository.findByTitle(title)).thenReturn(java.util.Optional.of(news));
        News foundNews = newsService.getNewsByTitle(title);

        Assertions.assertEquals(foundNews, news);
    }

    @Test(expected = NewsNotFoundException.class)
    public void getNewsByNonExistentTitleTest() {
        String title = "Title 5";
        newsService.getNewsByTitle(title);
    }

    @Test(expected = NewsNotFoundException.class)
    public void getNewsByNullTitleTest() {
        newsService.getNewsByTitle(null);
    }

    @Test
    public void createNewsTest() {
        long id = 5L;
        User user = new User();
        String title = "Title 5";
        Date date = new Date();
        String brief = "Brief 5";
        String content = "Content 5";
        News news = new News(id, user, title, date, brief, content);

        newsService.createNews(news);
        verify(newsRepository, times(1)).save(news);
    }
}
