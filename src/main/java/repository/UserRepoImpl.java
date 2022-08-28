package repository;

import model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class UserRepoImpl implements UserRepo {
    private final AtomicInteger currentId = new AtomicInteger();
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public UserRepoImpl() {
        System.out.println("Вызван конструктор UserRepoImpl!");
        addUser("Юля");
        addUser("Вика");
        addUser("Оля");
        addUser("Марина");
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(int id) {
        return users.get(String.valueOf(id));
    }

    @Override
    public int addUser(String name) {
        final int id = currentId.incrementAndGet();
        users.put(String.valueOf(id), new User(id, name));
        return id;
    }

    @Override
    public int updateUser(int id, String name) {
        return users.computeIfPresent(String.valueOf(id), (k, v) -> {
            v.setName(name);
            return v;
        }) == null ? 0 : id;
    }

    @Override
    public int removeUser(int id) {
        return users.remove(String.valueOf(id)) == null ? 0 : id;
    }
}
