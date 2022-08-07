package repository;

import model.User;

import java.util.Map;

public interface UserRepo {
    Map<String, User> getUsers();
    User getUserById(int id);
    int addUser(String name);
    int updateUser(int id, String name);
    int removeUser(int id);
}
