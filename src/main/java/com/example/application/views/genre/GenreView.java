package com.example.application.views.genre;

import com.example.application.entity.Genre;
import com.example.application.service.GenreService;
import com.example.application.views.util.LeftMenu;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Optional;

import static com.example.application.views.util.Constant.*;

@Route("genre")
public class GenreView extends HorizontalLayout {

    private final GenreService genreService;
    private Optional<Genre> selectedGenre;
    private Grid<Genre> bookGrid;

    public GenreView(GenreService genreService) {
        this.genreService = genreService;
        initView();
    }

    private void initView() {
        var leftMenu = new LeftMenu();

        var createButton = new Button(ADD_GENRE);
        createButton.addClickListener(e ->
                createButton.getUI().ifPresent(ui ->
                        ui.navigate("genre/edit"))
        );

        var updateButton = new Button(UPDATE_GENRE);
        updateButton.addClickListener(e -> {
                    if (selectedGenre.isPresent()) {
                        updateButton.getUI().ifPresent(ui ->
                                ui.navigate("genre/edit/" + selectedGenre.get().getId().toString()));
                    }
                }
        );

        var deleteButton = new Button(DELETE_GENRE);
        deleteButton.addClickListener(e -> {
                    if (selectedGenre.isPresent()) {
                        genreService.delete(selectedGenre.get());
                        bookGrid.setItems(genreService.findAll());
                    }
                }
        );

        bookGrid = new Grid<>(Genre.class, false);
        bookGrid.addColumn(Genre::getName).setHeader(TITLE_TEXT);
        bookGrid.setItems(genreService.findAll());
        bookGrid.addSelectionListener(selection -> selectedGenre = selection.getFirstSelectedItem());

        var buttons = new HorizontalLayout();
        buttons.add(createButton, updateButton, deleteButton);
        var vertical = new VerticalLayout();
        vertical.add(buttons, bookGrid);
        add(leftMenu, vertical);
        setHeight(100, Unit.PERCENTAGE);
    }
}
