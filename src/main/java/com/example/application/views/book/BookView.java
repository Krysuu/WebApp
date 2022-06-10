package com.example.application.views.book;

import com.example.application.entity.Book;
import com.example.application.entity.Genre;
import com.example.application.service.BookService;
import com.example.application.views.util.LeftMenu;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.application.views.util.Constant.*;

@Route("book")
public class BookView extends HorizontalLayout {

    private final BookService bookService;
    private Optional<Book> selectedBook = Optional.empty();
    private Grid<Book> bookGrid;

    public BookView(BookService bookService) {
        this.bookService = bookService;
        initView();
        setHeight(100, Unit.PERCENTAGE);
        setWidth(100, Unit.PERCENTAGE);
    }

    private void initView() {
        var leftMenu = new LeftMenu();
        var createButton = new Button(ADD_BOOK);
        createButton.addClickListener(e ->
                createButton.getUI().ifPresent(ui ->
                        ui.navigate("book/edit"))
        );

        var updateButton = new Button(UPDATE_BOOK);
        updateButton.addClickListener(e -> {
                    if (selectedBook.isPresent()) {
                        updateButton.getUI().ifPresent(ui ->
                                ui.navigate("book/edit/" + selectedBook.get().getId().toString()));
                    }
                }
        );

        var deleteButton = new Button(DELETE_BOOK);
        deleteButton.addClickListener(e -> {
                    if (selectedBook.isPresent()) {
                        bookService.delete(selectedBook.get());
                        bookGrid.setItems(bookService.findAll());
                    }
                }
        );

        var showAuthorButton = new Button(SHOW_AUTHOR);
        showAuthorButton.addClickListener(e -> {
                    if (selectedBook.isPresent()) {
                        showAuthorButton.getUI().ifPresent(ui ->
                                ui.navigate("author/info/" + selectedBook.get().getAuthor().getId().toString()));
                    }
                }
        );

        bookGrid = new Grid<>(Book.class, false);
        bookGrid.addColumn(Book::getTitle).setHeader(TITLE_TEXT);
        bookGrid.addColumn(Book::getPages).setHeader(PAGES_TEXT);
        bookGrid.addColumn(Book::getReleaseYear).setHeader(RELEASE_YEAR_TEXT);
        bookGrid.addColumn(Book::getAuthor).setHeader(AUTHOR_TEXT);
        bookGrid.addColumn(book -> (
                        book.getGenres().stream()
                                .map(Genre::getName)
                                .collect(Collectors.joining(", "))))
                .setHeader(GENRE_TEXT);
        bookGrid.setItems(bookService.findAll());
        bookGrid.addSelectionListener(selection -> selectedBook = selection.getFirstSelectedItem());

        var buttons = new HorizontalLayout();
        buttons.add(createButton, updateButton, deleteButton, showAuthorButton);
        var vertical = new VerticalLayout();
        vertical.add(buttons, bookGrid);
        add(leftMenu, vertical);
        leftMenu.setWidth(15, Unit.PERCENTAGE);
        vertical.setWidth(85, Unit.PERCENTAGE);
    }
}
