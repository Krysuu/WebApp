package com.example.application.service;

import com.example.application.entity.Book;
import com.example.application.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
