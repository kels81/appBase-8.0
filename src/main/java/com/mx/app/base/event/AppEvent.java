package com.mx.app.base.event;

import com.mx.app.base.view.AppViewType;


public abstract class AppEvent {

    public static final class UserLoginRequestedEvent {
        private final String userName, password;

        public UserLoginRequestedEvent(final String userName,
                final String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class BrowserResizeEvent {

    }

    public static class UserLoggedOutEvent {

    }
    
    public static final class PostViewChangeEvent {
        private final AppViewType view;

        public PostViewChangeEvent(final AppViewType view) {
            this.view = view;
        }

        public AppViewType getView() {
            return view;
        }
    }
   
    public static class CloseOpenWindowsEvent {
    }

    public static class ProfileUpdatedEvent {
    }

}
