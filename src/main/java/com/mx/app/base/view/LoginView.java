package com.mx.app.base.view;

import com.mx.app.base.event.AppEvent.UserLoginRequestedEvent;
import com.mx.app.base.event.AppEventBus;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class LoginView extends VerticalLayout {
    private TextField txtUsername;
    private PasswordField txtPassword;
    private Button btnSignIn;

    public LoginView() {
        setSizeFull();
//        setMargin(false);
//        setSpacing(false);

        Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

    }

    private Component buildLoginForm() {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setMargin(false);
        Responsive.makeResponsive(loginPanel);
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());
        loginPanel.addComponent(new CheckBox("Remember me", true));
        return loginPanel;
    }

    private Component buildFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.addStyleName("fields");

        txtUsername = new TextField("Username");
        txtUsername.focus();
        txtUsername.setIcon(FontAwesome.USER);
        txtUsername.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        txtPassword = new PasswordField("Password");
        txtPassword.setIcon(FontAwesome.LOCK);
        txtPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        btnSignIn = new Button("Sign In");
        btnSignIn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        btnSignIn.setClickShortcut(KeyCode.ENTER);
        //btnSignIn.focus();

        fields.addComponents(txtUsername, txtPassword, btnSignIn);
        fields.setComponentAlignment(btnSignIn, Alignment.BOTTOM_LEFT);

        btnSignIn.addClickListener((final ClickEvent event) -> {
            AppEventBus.post(new UserLoginRequestedEvent(txtUsername
                    .getValue().trim(), txtPassword.getValue().trim()));
        });
        return fields;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label title = new Label("File Box");    
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }
    
}
