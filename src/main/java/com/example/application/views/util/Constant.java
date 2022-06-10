package com.example.application.views.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    //BOOKS
    public static final String TITLE_TEXT = "Tytuł";
    public static final String PAGES_TEXT = "Liczba stron";
    public static final String RELEASE_YEAR_TEXT = "Rok wydania";
    public static final String AUTHOR_TEXT = "Autor";
    public static final String GENRE_TEXT = "Gatunek";
    public static final String SAVE_TEXT = "Zapisz";
    public static final String RETURN_TEXT = "Powrót";

    public static final String ADD_BOOK = "Dodaj książke";
    public static final String UPDATE_BOOK = "Edytuj książke";
    public static final String DELETE_BOOK = "Usuń książke";
    public static final String SHOW_AUTHOR = "Wyświetl szczegóły autora";

    //AUTHORS
    public static final String ADD_AUTHOR = "Dodaj autora";
    public static final String UPDATE_AUTHOR = "Edytuj autora";
    public static final String DELETE_AUTHOR = "Usuń autora (Wraz z książkami)";

    public static final String FIRST_NAME_TEXT = "Imię";
    public static final String LAST_NAME_TEXT = "Nazwisko";
    public static final String BIRTH_TEXT = "Data urodzenia";
    public static final String DEATH_TEXT = "Data śmierci";
    public static final String NUMBER_OF_BOOKS = "Liczba książek";

    public static final String GO_TO_EDIT_TEXT = "Przejdź do edycji";

    //GENRE
    public static final String ADD_GENRE = "Dodaj gatunek";
    public static final String UPDATE_GENRE = "Edytuj gatunek";
    public static final String DELETE_GENRE = "Usuń gatunek (książki nie zostaną usunięte)";

    public static final String GENRE_NAME_TEXT = "Nazwa gatunku";
}
