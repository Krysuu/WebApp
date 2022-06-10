package com.example.application.views.util;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class LeftMenu extends VerticalLayout {
    public static final String INFO = "Strona główna";
    public static final String BOOKS = "Książki";
    public static final String AUTHORS = "Autorzy";
    public static final String GENRES = "Gatunki";

    public LeftMenu() {
        var infoButton = new Button(INFO);
        infoButton.setWidth(100, Unit.PERCENTAGE);
        infoButton.addClickListener(e ->
                infoButton.getUI().ifPresent(ui ->
                        ui.navigate(""))
        );


        var booksButton = new Button(BOOKS);
        booksButton.setWidth(100, Unit.PERCENTAGE);
        booksButton.addClickListener(e ->
                booksButton.getUI().ifPresent(ui ->
                        ui.navigate("book"))
        );

        var authorsButton = new Button(AUTHORS);
        authorsButton.setWidth(100, Unit.PERCENTAGE);
        authorsButton.addClickListener(e ->
                authorsButton.getUI().ifPresent(ui ->
                        ui.navigate("author"))
        );

        var genresButton = new Button(GENRES);
        genresButton.setWidth(100, Unit.PERCENTAGE);
        genresButton.addClickListener(e ->
                genresButton.getUI().ifPresent(ui ->
                        ui.navigate("genre"))
        );

        add(infoButton, booksButton, authorsButton, genresButton);
        setWidth(15, Unit.PERCENTAGE);
        setSpacing(false);
    }
}
