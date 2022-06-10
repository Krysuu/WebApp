package com.example.application.views.util;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.application.views.util.Constant.EMPTY_FIELD_MESSAGE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonComponent {
    public static Notification emptyFieldNotification(String fieldName) {
        var text = new Div(new Text(String.format(EMPTY_FIELD_MESSAGE, fieldName)));
        var notification = new Notification(text);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        return notification;
    }
}
