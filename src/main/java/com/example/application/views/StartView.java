package com.example.application.views;

import com.example.application.views.util.LeftMenu;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class StartView extends HorizontalLayout {

    public StartView() {
        var leftMenu = new LeftMenu();
        add(leftMenu);
    }
}
