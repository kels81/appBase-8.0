package com.mx.app.base;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.mx.app.base.data.DataProvider;
import com.mx.app.base.data.dummy.DummyDataProvider;
import com.mx.app.base.domain.User;
import com.mx.app.base.event.AppEvent.BrowserResizeEvent;
import com.mx.app.base.event.AppEvent.CloseOpenWindowsEvent;
import com.mx.app.base.event.AppEvent.UserLoggedOutEvent;
import com.mx.app.base.event.AppEvent.UserLoginRequestedEvent;
import com.mx.app.base.event.AppEventBus;
import com.mx.app.base.view.LoginView;
import com.mx.app.base.view.MainView;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import static com.vaadin.ui.UI.getCurrent;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;


@Theme("apptheme")
@Title("appBase 8.0")
@SuppressWarnings("serial")
public final class AppUI extends UI {

    /*
     * This field stores an access to the dummy backend layer. In real
     * applications you most likely gain access to your beans trough lookup or
     * injection; and not in the UI but somewhere closer to where they're
     * actually accessed.
     */
    private final DataProvider dataProvider = new DummyDataProvider();
    private final AppEventBus appEventbus = new AppEventBus();

    @Override
    protected void init(final VaadinRequest request) {
        //setLocale(Locale.US);

        AppEventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent();

        // Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event bus on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
                    @Override
                    public void browserWindowResized(
                            final BrowserWindowResizeEvent event) {
                        AppEventBus.post(new BrowserResizeEvent());
                    }
                });
    }

    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private void updateContent() {
        User user = (User) VaadinSession.getCurrent()
                .getAttribute(User.class.getName());
        if (user != null && "admin".equals(user.getRole())) {
            // Authenticated user
            setContent(new MainView());
            removeStyleName("loginview");
            getNavigator().navigateTo(getNavigator().getState());
        } else {
            setContent(new LoginView());
            addStyleName("loginview");
        }
    }

    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) {
        User user = getDataProvider().authenticate(event.getUserName(),
                event.getPassword());
        VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
        updateContent();
    }

    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close();
        Page.getCurrent().setLocation("");
    }

    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    /**
     * @return An instance for accessing the (dummy) services layer.
     */
    public static DataProvider getDataProvider() {
        return ((AppUI) getCurrent()).dataProvider;
    }

    public static AppEventBus getAppEventbus() {
        return ((AppUI) getCurrent()).appEventbus;
    }
}
