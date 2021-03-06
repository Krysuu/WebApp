package com.example.application.views.genre;

import com.example.application.entity.Genre;
import com.example.application.service.GenreService;
import com.example.application.views.util.CommonComponent;
import com.example.application.views.util.LeftMenu;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;

import static com.example.application.views.util.Constant.*;

@Route("genre/edit")
public class GenreEditView extends HorizontalLayout implements HasUrlParameter<Long> {

    private final GenreService genreService;
    private TextField nameField;
    private Optional<Genre> selectedGenre = Optional.empty();

    public GenreEditView(GenreService genreService) {
        this.genreService = genreService;
        initView();
        setHeight(100, Unit.PERCENTAGE);
    }

    private void initView() {
        var leftMenu = new LeftMenu();
        nameField = new TextField(GENRE_NAME_TEXT);
        nameField.setRequired(true);

        var saveButton = new Button(SAVE_TEXT);
        saveButton.addClickListener(e -> saveButtonAction(saveButton));

        var cancelButton = new Button(RETURN_TEXT);
        cancelButton.addClickListener(e ->
                cancelButton.getUI().ifPresent(ui ->
                        ui.getPage().getHistory().back())
        );


        var buttons = new HorizontalLayout(saveButton, cancelButton);
        var genreEditView = new VerticalLayout(initFormLayout(), buttons);
        add(leftMenu, genreEditView);
        leftMenu.setWidth(15, Unit.PERCENTAGE);
        genreEditView.setWidth(85, Unit.PERCENTAGE);
    }

    private FormLayout initFormLayout() {
        var form = new FormLayout();
        form.add(nameField);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );
        return form;
    }

    private void updateGenre(Genre genre, String name) {
        genre.setName(name);
        genreService.save(genre);
    }

    private void saveGenre(String name) {
        var genre = new Genre(null, name, List.of());
        genreService.save(genre);
    }

    private void fillFields(Genre genre) {
        nameField.setValue(genre.getName());
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter Long id) {
        if (id != null) {
            selectedGenre = genreService.findById(id);
            selectedGenre.ifPresent(this::fillFields);
        }
    }

    private void saveButtonAction(Button saveButton) {
        if (nameField.isEmpty()) {
            CommonComponent.emptyFieldNotification(GENRE_NAME_TEXT).open();
            return;
        }

        if (selectedGenre.isPresent()) {
            updateGenre(selectedGenre.get(), nameField.getValue());
        } else {
            saveGenre(nameField.getValue());
        }
        saveButton.getUI().ifPresent(ui ->
                ui.navigate("genre"));
    }
}
