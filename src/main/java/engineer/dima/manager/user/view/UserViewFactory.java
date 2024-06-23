package engineer.dima.manager.user.view;

import engineer.dima.manager.user.User;

public class UserViewFactory {
    public static UserView create(User user) {
        return new UserView(user.userId().toString(), user.email(), user.name());
    }
}
