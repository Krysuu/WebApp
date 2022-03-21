package com.example.application.views;

import com.example.application.entity.Book;
import com.example.application.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookView extends VerticalLayout {

    public static final String ADD_BOOK = "Dodaj książke";
    public static final String UPDATE_BOOK = "Edytuj książke";
    public static final String DELETE_BOOK = "Usuń książke";
    public static final String SHOW_AUTHOR = "Wyświetl autora";
    public static final String TITLE = "Tytuł";
    public static final String PAGES = "Liczba stron";
    private final BookService bookService;
    private Book selectedBook;

    public BookView(BookService bookService) {
        this.bookService = bookService;

        var createButton = new Button(ADD_BOOK);
        createButton.addClickListener(e -> addBook());

        var updateButton = new Button(UPDATE_BOOK);
        updateButton.addClickListener(e -> updateBook(selectedBook));

        var deleteButton = new Button(DELETE_BOOK);
        deleteButton.addClickListener(e -> deleteBook(selectedBook));

        var showAuthorButton = new Button(SHOW_AUTHOR);
        showAuthorButton.addClickListener(e -> showAuthor(selectedBook));

        Grid<Book> grid = new Grid<>(Book.class, false);
        grid.addColumn(Book::getTitle).setHeader(TITLE);
        grid.addColumn(Book::getPages).setHeader(PAGES);

        grid.setItems(bookService.findAll());

        grid.addSelectionListener(selection -> {
            Optional<Book> optionalPerson = selection.getFirstSelectedItem();
            optionalPerson.ifPresent(book -> selectedBook = book);
        });

        var buttons = new HorizontalLayout();
        buttons.add(createButton, updateButton, deleteButton, showAuthorButton);
        add(buttons, grid);
    }

    private void addBook() {
        //wyświetl ekran tworzenia
    }

    private void updateBook(Book book) {
        //wyświetl ekran tworzenia z aktualnymi danymi
    }

    private void deleteBook(Book book) {
        bookService.delete(book);
    }

    private void showAuthor(Book book) {
        //tylko usuń
    }
}
