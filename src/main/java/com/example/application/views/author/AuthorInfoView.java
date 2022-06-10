package com.example.application.views.author;

import com.example.application.entity.Author;
import com.example.application.entity.Book;
import com.example.application.entity.Genre;
import com.example.application.service.AuthorService;
import com.example.application.service.BookService;
import com.example.application.views.util.LeftMenu;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.application.views.util.Constant.*;

@Route("author/info")
public class AuthorInfoView extends HorizontalLayout implements HasUrlParameter<Long> {

    private final AuthorService authorService;
    private final BookService bookService;
    private TextField firstNameField;
    private TextField lastNameField;
    private DatePicker birthField;
    private DatePicker deathField;
    private Grid<Book> bookGrid;
    private Optional<Author> selectedAuthor = Optional.empty();

    public AuthorInfoView(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
        initView();
        setHeight(100, Unit.PERCENTAGE);
    }

    private void initView() {
        var leftMenu = new LeftMenu();
        var editButton = new Button(GO_TO_EDIT_TEXT);
        editButton.addClickListener(e -> {
            editButton.getUI().ifPresent(ui ->
                    ui.navigate("author/edit/" + selectedAuthor.get().getId()));
        });

        var cancelButton = new Button(RETURN_TEXT);
        cancelButton.addClickListener(e ->
                cancelButton.getUI().ifPresent(ui ->
                        ui.getPage().getHistory().back())
        );

        var buttonsLayout = new HorizontalLayout(editButton, cancelButton);
        var authorInfoView = new VerticalLayout(createFormLayout(), buttonsLayout);
        add(leftMenu, authorInfoView);
        leftMenu.setWidth(15, Unit.PERCENTAGE);
        authorInfoView.setWidth(85, Unit.PERCENTAGE);
    }

    private FormLayout createFormLayout() {
        var form = new FormLayout();

        firstNameField = new TextField(FIRST_NAME_TEXT);
        firstNameField.setReadOnly(true);
        lastNameField = new TextField(LAST_NAME_TEXT);
        lastNameField.setReadOnly(true);
        birthField = new DatePicker(BIRTH_TEXT);
        birthField.setReadOnly(true);
        deathField = new DatePicker(DEATH_TEXT);
        deathField.setReadOnly(true);
        bookGrid = new Grid<>();
        bookGrid.setSelectionMode(Grid.SelectionMode.NONE);

        bookGrid.addColumn(Book::getTitle).setHeader(TITLE_TEXT);
        bookGrid.addColumn(Book::getPages).setHeader(PAGES_TEXT);
        bookGrid.addColumn(Book::getReleaseYear).setHeader(RELEASE_YEAR_TEXT);
        bookGrid.addColumn(book -> (
                        book.getGenres().stream()
                                .map(Genre::getName)
                                .collect(Collectors.joining(", "))))
                .setHeader(GENRE_TEXT);

        form.add(firstNameField, lastNameField, birthField, deathField, bookGrid);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        return form;
    }

    private void setForm(Author author) {
        firstNameField.setValue(author.getFirstName());
        lastNameField.setValue(author.getLastName());
        birthField.setValue(author.getDateOfBirth());
        deathField.setValue(author.getDateOfDeath());
        bookGrid.setItems(bookService.findAllByAuthorId(author.getId()));
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter Long id) {
        if (id != null) {
            selectedAuthor = authorService.findById(id);
            selectedAuthor.ifPresent(this::setForm);
        }
    }
}
