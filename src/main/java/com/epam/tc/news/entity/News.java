package com.epam.tc.news.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @ManyToOne
    private User user;

    @Column(name = "title", unique = true, nullable = false, length = 100)
    private String title;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", length = 10)
    private Date date;

    @Column(name = "brief", length = 500)
    private String brief;

    @Column(name = "content", length = 2048)
    private String content;
}
