package com.example.application.views;

import com.example.application.views.util.LeftMenu;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class StartView extends HorizontalLayout {

    public StartView() {
        String info = "<h3>Ten projekt przedstawia prostą aplikację internetową stworzoną za pomocą Vaadin oraz Spring Boot.</h1>\n" +
                "<h3>Wykorzystane narzędzia i technologie</h1>\n" +
                "<ul>\n" +
                "<li>Java 11 (najwyższa wersja kompatybilna z Vaadin)</li>\n" +
                "<li>Vaadin 23</li>\n" +
                "<li>Spring Boot</li>\n" +
                "<li>Spring Data</li>\n" +
                "<li>Baza danych H2 (w pamięci)</li>\n" +
                "<li>Liquibase (wersjonowanie bazy danych z możliwością przepięcia na dowolną inną bazę SQL)</li>\n" +
                "<li>Lombok</li>\n" +
                "</ul>\n" +
                "<h3>Autor - Krystian Nowakowski</h1>";


        var leftMenu = new LeftMenu();
        var content = new Div();
        content.getElement().setProperty("innerHTML", info);
        add(leftMenu, content);
        setHeight(100, Unit.PERCENTAGE);
        setWidth(100, Unit.PERCENTAGE);
        leftMenu.setWidth(15, Unit.PERCENTAGE);
        content.setWidth(85, Unit.PERCENTAGE);
    }
}
