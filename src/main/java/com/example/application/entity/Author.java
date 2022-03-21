package com.example.application.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Author {
    @Id
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Date brithDay;

    @OneToMany
    private List<Book> books;
}
