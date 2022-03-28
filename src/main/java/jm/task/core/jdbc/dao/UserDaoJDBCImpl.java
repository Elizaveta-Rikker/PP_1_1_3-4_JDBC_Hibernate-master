package jm.task.core.jdbc.dao;

import com.mysql.cj.protocol.Resultset;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getConnection();
    private String sqlCommandCreateTable = "CREATE TABLE users (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45) NULL,`lastName` VARCHAR(45) NULL,`age` INT NULL,PRIMARY KEY (`id`),UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
    private String sqlCommandDropTable = "DROP TABLE users;";
    private String sqlCommandInsertFish = "INSERT INTO users (name, lastName, age) VALUES('%s', '%s', %d);";
    private String sqlCommandRemoveById = "DELETE FROM users WHERE id = %d;";
    private String sqlCommandGetAllUsers = "SELECT * FROM users;";
    private String sqlCommandClearTable = "DELETE FROM users;";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommandCreateTable);
        } catch (SQLException e) {
            System.out.println("Такая таблица уже существует");
        }
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommandDropTable);
        } catch (SQLException e) {
            System.out.println("Такой таблицы не существует");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Statement statement = connection.createStatement();
            String command = String.format(sqlCommandInsertFish, name, lastName, age);
            statement.executeUpdate(command);
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try {
            Statement statement = connection.createStatement();
            String command = String.format(sqlCommandRemoveById, id);
            statement.executeUpdate(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet= statement.executeQuery(sqlCommandGetAllUsers);

            while(resultSet.next()){
                User user = new User();
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge((byte) resultSet.getInt(4));
                user.setId((long) resultSet.getInt(1));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCommandClearTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
