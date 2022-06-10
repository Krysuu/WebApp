package com.example.application.views.book;

import com.example.application.entity.Author;
import com.example.application.entity.Book;
import com.example.application.entity.Genre;
import com.example.application.service.AuthorService;
import com.example.application.service.BookService;
import com.example.application.service.GenreService;
import com.example.application.views.util.CommonComponent;
import com.example.application.views.util.LeftMenu;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.application.views.util.Constant.*;

@Route("book/edit")
public class BookEditView extends HorizontalLayout implements HasUrlParameter<Long> {

    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private TextField titleField;
    private NumberField pagesField;
    private NumberField yearField;
    private Optional<Book> selectedBook = Optional.empty();
    private Select<Author> authorSelect;
    private MultiSelectListBox<Genre> genreSelect;

    public BookEditView(AuthorService authorService, GenreService genreService, BookService bookService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
        initView();
        setHeight(100, Unit.PERCENTAGE);
    }

    private void initView() {
        var leftMenu = new LeftMenu();

        var saveButton = new Button(SAVE_TEXT);
        saveButton.addClickListener(e -> saveButtonAction(saveButton));

        var cancelButton = new Button(RETURN_TEXT);
        cancelButton.addClickListener(e ->
                cancelButton.getUI().ifPresent(ui ->
                        ui.getPage().getHistory().back())
        );


        var buttons = new HorizontalLayout(saveButton, cancelButton);
        var bookEditView = new VerticalLayout(initFormLayout(), buttons);
        add(leftMenu, bookEditView);
        leftMenu.setWidth(15, Unit.PERCENTAGE);
        bookEditView.setWidth(85, Unit.PERCENTAGE);
    }

    private FormLayout initFormLayout() {
        var form = new FormLayout();

        titleField = new TextField(TITLE_TEXT);
        titleField.setRequired(true);
        pagesField = new NumberField(PAGES_TEXT);
        yearField = new NumberField(RELEASE_YEAR_TEXT);

        authorSelect = new Select<>();
        authorSelect.setLabel(AUTHOR_TEXT);
        authorSelect.setItems(authorService.findAll());

        var genreLabel = new Label(GENRE_TEXT);

        genreSelect = new MultiSelectListBox<>();
        genreSelect.setItems(genreService.findAll());

        form.add(titleField,
                pagesField,
                yearField,
                authorSelect,
                genreLabel,
                genreSelect
        );
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );
        return form;
    }

    private void updateBook(Book book, String title, Integer pages, Integer releaseYear, Author author, List<Genre> genres) {
        book.setTitle(title);
        book.setPages(pages);
        book.setReleaseYear(releaseYear);
        book.setAuthor(author);
        book.setGenres(genres);
        bookService.save(book);
    }

    private void saveNewBook(String title, Integer pages, Integer releaseYear, Author author, List<Genre> genres) {
        var book = new Book(null, title, pages, releaseYear, author, genres);
        bookService.save(book);
    }

    private void fillFields(Book book) {
        var pages = book.getPages() != null ? book.getPages().doubleValue() : null;
        var year = book.getReleaseYear() != null ? book.getReleaseYear().doubleValue() : null;

        titleField.setValue(book.getTitle());
        pagesField.setValue(pages);
        yearField.setValue(year);
        authorSelect.setValue(book.getAuthor());
        genreSelect.setValue(Set.copyOf(book.getGenres()));
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter Long id) {
        if (id != null) {
            selectedBook = bookService.findById(id);
            selectedBook.ifPresent(this::fillFields);
        }
    }

    private void saveButtonAction(Button saveButton) {
        if (titleField.isEmpty()) {
            CommonComponent.emptyFieldNotification(TITLE_TEXT).open();
            return;
        }
        if (authorSelect.isEmpty()) {
            CommonComponent.emptyFieldNotification(AUTHOR_TEXT).open();
            return;
        }

        if (selectedBook.isPresent()) {
            updateBook(selectedBook.get(),
                    titleField.getValue(),
                    pagesField.getValue() != null ? yearField.getValue().intValue() : null,
                    yearField.getValue() != null ? yearField.getValue().intValue() : null,
                    authorSelect.getValue(),
                    new ArrayList<>(genreSelect.getSelectedItems()));
        } else {
            saveNewBook(titleField.getValue(),
                    pagesField.getValue() != null ? yearField.getValue().intValue() : null,
                    yearField.getValue() != null ? yearField.getValue().intValue() : null,
                    authorSelect.getValue(),
                    new ArrayList<>(genreSelect.getSelectedItems()));
        }
        saveButton.getUI().ifPresent(ui ->
                ui.navigate("book"));
    }
}
