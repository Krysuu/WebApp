package com.example.application.views.util;

import com.vaadin.flow.component.Html;

public class TextUtil {
    public static Html getBoldTextComponent(String text) {
        String boldText = "<b> " + text + " </b>";
        return new Html("<text>" + boldText + "</text>");
    }
}
