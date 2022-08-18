package repository;

import model.User;

import java.util.List;

public interface UserRepo {
    List<User> getUsers();
    User getUserById(int id);
    int addUser(String name);
    int updateUser(int id, String name);
    int removeUser(int id);
}
