package com.example.application.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
public class Genre {
    @Id
    private Long id;

    private String name;

    @ManyToMany
    private List<Book> books;
}
