package services;

import model.User;
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
import java.io.IOException;
import java.util.List;


public class UserResourceIT {
    private Client client;
    private WebTarget webTarget;

    @Before
    public void run() {
        client = ClientBuilder.newClient();
        /*webTarget = client.target("http://localhost:8080/P19_Jersey_v2_war/users"); // для отладки*/
        webTarget = client.target("http://localhost:8999/users");
    }

    @After
    public void stop() {
        client.close();
    }

    private int getUsersSize() {
        Response respGetUsers = webTarget.request(MediaType.APPLICATION_JSON).get();
        Assert.assertTrue(respGetUsers.getStatus() == 200);

        List<User> users = respGetUsers.readEntity(new GenericType<List<User>>() {
        });
        final int size = users.size();
        respGetUsers.close();

        return size;
    }

    @Test
    public void getUsers() throws IOException {
        final int size = getUsersSize();

        Response respAddUser = webTarget.path("newTestUser").request(MediaType.TEXT_PLAIN_TYPE).post(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
        Assert.assertTrue(respAddUser.getStatus() == 200);
        respAddUser.close();

        Assert.assertEquals(getUsersSize(), size + 1);
    }
}
