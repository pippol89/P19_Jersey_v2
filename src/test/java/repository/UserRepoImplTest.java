package repository;

import model.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserRepoImplTest {
    private static UserRepoImpl userRepo;
    private static final String testUserName = "testUser";
    private static final int notExistsUserId = 555;

    @BeforeClass
    public static void prepareTestData() {
        userRepo = new UserRepoImpl();

        userRepo.addUser("Екатерина");
        userRepo.addUser("Ирина");
        userRepo.addUser("Женя");
        userRepo.addUser("Надя");
    }

    /***
     * Проверяется актуальный размер списка после добавления/удаления пользователя.
     */
    @Test
    public void getUsers() {
        final int size = userRepo.getUsers().size();

        int testUserId = userRepo.addUser(testUserName);
        Assert.assertEquals(userRepo.getUsers().size(), size + 1);

        userRepo.removeUser(testUserId);
        Assert.assertEquals(userRepo.getUsers().size(), size);
    }

    /***
     * Проверяется поведение при поиске несуществующего пользователя,
     * добавлении пользователя и затем поиск добавленного пользователя.
     */
    @Test
    public void getUserById() {
        Assert.assertNull(userRepo.getUserById(notExistsUserId));

        int testUserId = userRepo.addUser(testUserName);
        User testUser = userRepo.getUserById(testUserId);
        Assert.assertNotNull(testUser);
        Assert.assertEquals(testUser.getId(), testUserId);
        Assert.assertEquals(testUser.getName(), testUserName);

        userRepo.removeUser(testUserId);
    }

    /***
     * Проверяется корректность возвращаемого значения, после добавления пользователя.
     */
    @Test
    public void addUser() {
        int testUserId = userRepo.addUser(testUserName);
        Assert.assertTrue(testUserId > 0);

        userRepo.removeUser(testUserId);
    }

    /***
     * Проверяется добавление, затем обновление добавленного пользователя,
     * корректность при обновление несуществующего пользователя.
     */
    @Test
    public void updateUser() {
        final String newTestUserName = "testUserNameNew";

        int testUserId = userRepo.addUser(testUserName);
        Assert.assertEquals(userRepo.updateUser(testUserId, newTestUserName), testUserId);
        User testUser = userRepo.getUserById(testUserId);
        Assert.assertEquals(testUser.getName(), newTestUserName);

        userRepo.removeUser(testUserId);

        Assert.assertEquals(userRepo.updateUser(notExistsUserId, newTestUserName), 0);
    }

    /***
     * Проверяется корректность добавления, затем удаление добавленного пользователя.
     */
    @Test
    public void removeUser() {
        int testUserId = userRepo.addUser(testUserName);
        Assert.assertEquals(userRepo.removeUser(testUserId), testUserId);
    }
}