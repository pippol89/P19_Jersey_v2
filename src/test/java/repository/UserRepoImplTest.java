package repository;

import model.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserRepoImplTest {
    private static UserRepoImpl userRepo;
    private static final String testUserName = "testUser";
    private static final int notExistsUser = 555;

    @BeforeClass
    public static void prepareTestData() {
        userRepo = new UserRepoImpl();

        userRepo.addUser("Екатерина");
        userRepo.addUser("Ирина");
        userRepo.addUser("Женя");
        userRepo.addUser("Надя");
    }

    @Test
    public void getUsers() {
        int size = userRepo.getUsers().size();

        int testUserId = userRepo.addUser(testUserName);
        Assert.assertEquals(userRepo.getUsers().size(), size + 1);

        userRepo.removeUser(testUserId);
        Assert.assertEquals(userRepo.getUsers().size(), size);
    }

    @Test
    public void getUserById() {
        Assert.assertNull(userRepo.getUserById(notExistsUser));

        int testUserId = userRepo.addUser(testUserName);
        User testUser = userRepo.getUserById(testUserId);
        Assert.assertNotNull(testUser);
        Assert.assertEquals(testUser.getId(), testUserId);
        Assert.assertEquals(testUser.getName(), testUserName);

        userRepo.removeUser(testUserId);
    }

    @Test
    public void addUser() {
        int testUserId = userRepo.addUser(testUserName);
        Assert.assertTrue(testUserId > 0);

        userRepo.removeUser(testUserId);
    }

    @Test
    public void updateUser() {
        final String newTestUserName = "testUserNameNew";

        int testUserId = userRepo.addUser(testUserName);
        Assert.assertEquals(userRepo.updateUser(testUserId, newTestUserName), testUserId);
        User testUser = userRepo.getUserById(testUserId);
        Assert.assertEquals(testUser.getName(), newTestUserName);

        userRepo.removeUser(testUserId);

        Assert.assertEquals(userRepo.updateUser(notExistsUser, newTestUserName), 0);
    }

    @Test
    public void removeUser() {
        int testUserId = userRepo.addUser(testUserName);
        Assert.assertEquals(userRepo.removeUser(testUserId), testUserId);
    }
}