package services;

import model.User;
import model.answer.UserAnswer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status;


public class UserResourceIT {
    private Client client;
    private WebTarget webTarget;

    @Before
    public void run() {
        client = ClientBuilder.newClient();
        //webTarget = client.target("http://localhost:8080/P19_Jersey_v2_war/users");
        webTarget = client.target("http://localhost:8999/users");
    }

    @After
    public void stop() {
        client.close();
    }

    private int getUsersSize() {
        int size;
        try (final Response respGetUsers = webTarget.request(MediaType.APPLICATION_JSON).get()) {
            Assert.assertEquals(respGetUsers.getStatus(), Status.OK.getStatusCode());

            size = respGetUsers.readEntity(new GenericType<List<User>>() {
            }).size();
        }
        return size;
    }

    private UserAnswer addUser(String name) {
        UserAnswer addUserAnswer;

        try (final Response respAddUser = webTarget.path(name).request(MediaType.APPLICATION_JSON).post(Entity.entity("", MediaType.TEXT_PLAIN_TYPE))) {
            Assert.assertEquals(respAddUser.getStatus(), Status.OK.getStatusCode());
            addUserAnswer = respAddUser.readEntity(UserAnswer.class);
        }
        return addUserAnswer;
    }

    private UserAnswer removeUser(int id) {
        UserAnswer remUserAnswer;

        try (final Response respRemoveUser = webTarget.path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).delete()) {
            remUserAnswer = respRemoveUser.readEntity(UserAnswer.class);
            Assert.assertEquals(respRemoveUser.getStatus(), Status.OK.getStatusCode());
        }
        return remUserAnswer;
    }

    private User getUserById(int id) {
        User user;

        try (final Response respFindUser = webTarget.path(String.valueOf(id)).request(MediaType.APPLICATION_JSON).get()) {
            user = respFindUser.readEntity(User.class);
        }
        return user;
    }

    /***
     * Проверяется актуальный размер списка после добавления/удаления пользователя.
     */
    @Test
    public void getUsers() {
        final int size = getUsersSize();

        final UserAnswer addUserAnswer = addUser("TestUser");
        Assert.assertEquals(getUsersSize(), size + 1);

        removeUser(addUserAnswer.getId());
        Assert.assertEquals(getUsersSize(), size);
    }

    /***
     * Проверяется корректность получения пользователя, после добавления.
     */
    @Test
    public void getUserById() {
        final UserAnswer addUserAnswer = addUser("TestUser");

        final User findUser = getUserById(addUserAnswer.getId());
        Assert.assertEquals(findUser.getId(), addUserAnswer.getId());
        Assert.assertEquals(findUser.getName(), "TestUser");

        Assert.assertEquals(removeUser(addUserAnswer.getId()).getId(), addUserAnswer.getId());
    }

    /***
     * Проверяется корректность возвращаемого значения, после добавления пользователя.
     */
    @Test
    public void addUser() {
        final UserAnswer addUserAnswer = addUser("TestUser");
        Assert.assertTrue(addUserAnswer.getId() > 0);

        removeUser(addUserAnswer.getId());
    }

    /***
     * Проверяется корректность обновления добавленного пользователя
     */
    @Test
    public void updateUser() {
        final UserAnswer addUserAnswer = addUser("TestUser");

        try (final Response respUpdateUser = webTarget.path(String.valueOf(addUserAnswer.getId())).queryParam("name", "newUserName").request(MediaType.APPLICATION_JSON).put(Entity.entity("", MediaType.TEXT_PLAIN_TYPE))) {
            Assert.assertEquals(respUpdateUser.getStatus(), Status.OK.getStatusCode());
            final UserAnswer updateUserAnswer = respUpdateUser.readEntity(UserAnswer.class);
            Assert.assertEquals(updateUserAnswer.getId(), addUserAnswer.getId());
        }
    }

    /***
     * Проверяется корректность удаления добавленного пользователя.
     */
    @Test
    public void removeUser() {
        final UserAnswer addUserAnswer = addUser("TestUser");

        Assert.assertEquals(removeUser(addUserAnswer.getId()).getId(), addUserAnswer.getId());
    }
}
