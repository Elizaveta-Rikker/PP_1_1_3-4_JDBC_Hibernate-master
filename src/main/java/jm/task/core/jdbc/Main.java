package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Kisa", "Kisovich", (byte) 100);
        userService.saveUser("Sobaka", "Sobakovich", (byte) 60);
        userService.saveUser("Belk", "Belkovich", (byte) 20);
        userService.saveUser("Zmii", "Zmievich", (byte) 70);
        List<User> users = userService.getAllUsers();
        for (User user: users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
