package com.example.application.views.author;

import com.example.application.entity.Author;
import com.example.application.service.AuthorService;
import com.example.application.service.BookService;
import com.example.application.views.util.LeftMenu;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Optional;

import static com.example.application.views.util.Constant.*;

@Route("author")
public class AuthorView extends HorizontalLayout {

    private final AuthorService authorService;
    private final BookService bookService;
    private Optional<Author> selectedAuthor = Optional.empty();
    private Grid<Author> authorGrid;

    public AuthorView(AuthorService authorService, BookService bookService) {
        this.bookService = bookService;
        this.authorService = authorService;
        initView();
        setHeight(100, Unit.PERCENTAGE);
    }

    private void initView() {
        var leftMenu = new LeftMenu();
        var createButton = new Button(ADD_AUTHOR);
        createButton.addClickListener(e ->
                createButton.getUI().ifPresent(ui ->
                        ui.navigate("author/edit"))
        );

        var updateButton = new Button(UPDATE_AUTHOR);
        updateButton.addClickListener(e -> {
                    if (selectedAuthor.isPresent()) {
                        updateButton.getUI().ifPresent(ui ->
                                ui.navigate("author/edit/" + selectedAuthor.get().getId().toString()));
                    }
                }
        );

        var deleteButton = new Button(DELETE_AUTHOR);
        deleteButton.addClickListener(e -> {
                    if (selectedAuthor.isPresent()) {
                        authorService.delete(selectedAuthor.get());
                        authorGrid.setItems(authorService.findAll());
                    }
                }
        );

        var showAuthorInfoButton = new Button(SHOW_AUTHOR);
        showAuthorInfoButton.addClickListener(e -> {
                    if (selectedAuthor.isPresent()) {
                        showAuthorInfoButton.getUI().ifPresent(ui ->
                                ui.navigate("author/info/" + selectedAuthor.get().getId().toString()));
                    }
                }
        );

        authorGrid = new Grid<>(Author.class, false);
        authorGrid.addColumn(Author::getFirstName).setHeader(FIRST_NAME_TEXT);
        authorGrid.addColumn(Author::getLastName).setHeader(LAST_NAME_TEXT);
        authorGrid.addColumn(Author::getDateOfBirth).setHeader(BIRTH_TEXT);
        authorGrid.addColumn(Author::getDateOfDeath).setHeader(BIRTH_TEXT);
        authorGrid.addColumn(author -> (this.bookService.countBooksByAuthorId(author.getId()))).setHeader(NUMBER_OF_BOOKS);
        authorGrid.setItems(authorService.findAll());
        authorGrid.addSelectionListener(selection -> selectedAuthor = selection.getFirstSelectedItem());

        var buttons = new HorizontalLayout();
        buttons.add(createButton, updateButton, deleteButton, showAuthorInfoButton);
        var vertical = new VerticalLayout();
        vertical.add(buttons, authorGrid);
        add(leftMenu, vertical);
        leftMenu.setWidth(15, Unit.PERCENTAGE);
        vertical.setWidth(85, Unit.PERCENTAGE);
    }
}
