package com.example.application.views;

import com.example.application.views.util.TextUtil;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

@PageTitle("Biblioteka")
@Route(value = "")
@Component
public class MainView extends VerticalLayout {

    public static final String TITLE = "Biblioteka";
    private final MenuBarBasic menuBar;
    private final BookView bookView;

    public MainView(MenuBarBasic menuBar, BookView bookView) {
        this.menuBar = menuBar;
        this.bookView = bookView;
        var horizontal = new HorizontalLayout();
        var titleText = TextUtil.getBoldTextComponent(TITLE);

        //name = new TextField("Your name");
/*        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            //Notification.show("Hello " + name.getValue());
        });*/

        horizontal.setMargin(true);
        horizontal.add(titleText, menuBar);
        add(horizontal, bookView);
    }

}
