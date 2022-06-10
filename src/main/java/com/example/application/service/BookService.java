package com.example.application.service;

import com.example.application.entity.Book;
import com.example.application.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> findAllByAuthorId(Long authorId) {
        return bookRepository.findAllByAuthorId(authorId);
    }

    public Long countBooksByAuthorId(Long authorId) {
        return bookRepository.countBooksByAuthorId(authorId);
    }

}
