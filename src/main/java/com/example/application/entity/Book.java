package com.example.application.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@Data
public class Book {
    @Id
    private Long id;

    private String title;

    private Integer pages;

    //datawydania

    //kategoria wiekowa

    @ManyToOne
    private Author author;

    @ManyToMany
    private List<Genre> genre;
}
