package com.example.application.views.book;

import com.example.application.entity.Author;
import com.example.application.entity.Book;
import com.example.application.entity.Genre;
import com.example.application.service.AuthorService;
import com.example.application.service.BookService;
import com.example.application.service.GenreService;
import com.example.application.views.util.LeftMenu;
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
    private Optional<Book> selectedBook;
    private Select<Author> authorSelect;
    private MultiSelectListBox<Genre> genreSelect;

    public BookEditView(AuthorService authorService, GenreService genreService, BookService bookService) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
        initView();
    }

    private void initView() {
        var leftMenu = new LeftMenu();

        titleField = new TextField(TITLE_TEXT);
        pagesField = new NumberField(PAGES_TEXT);
        yearField = new NumberField(RELEASE_YEAR_TEXT);

        var saveButton = new Button(SAVE_TEXT);
        saveButton.addClickListener(e -> {
            if (selectedBook.isPresent()) {
                updateBook(selectedBook.get(),
                        titleField.getValue(),
                        pagesField.getValue().intValue(),
                        yearField.getValue().intValue(),
                        authorSelect.getValue(),
                        new ArrayList<>(genreSelect.getSelectedItems()));
            } else {
                saveNewBook(titleField.getValue(),
                        pagesField.getValue().intValue(),
                        yearField.getValue().intValue(),
                        authorSelect.getValue(),
                        new ArrayList<>(genreSelect.getSelectedItems()));
            }
            saveButton.getUI().ifPresent(ui ->
                    ui.navigate("book"));
        });

        var cancelButton = new Button(RETURN_TEXT);
        cancelButton.addClickListener(e ->
                cancelButton.getUI().ifPresent(ui ->
                        ui.getPage().getHistory().back())
        );


        var buttons = new HorizontalLayout(saveButton, cancelButton);
        var bookEditView = new VerticalLayout(initFormLayout(), buttons);
        add(leftMenu, bookEditView);
    }

    private FormLayout initFormLayout() {
        var form = new FormLayout();

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
        titleField.setValue(book.getTitle());
        pagesField.setValue(book.getPages().doubleValue());
        yearField.setValue(book.getReleaseYear().doubleValue());
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
}
