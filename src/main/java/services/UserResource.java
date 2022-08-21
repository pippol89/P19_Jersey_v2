package services;

import model.User;
import model.answer.UserAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.UserRepo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/users")
public class UserResource {
    public UserResource() {
        System.out.println("Вызван конструктор UserResource!");
    }

    @Autowired
    private UserRepo userRepo;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsers() {
        return userRepo.getUsers();
    }

    @GET
    @Path("/{id}")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserById(@PathParam("id") int id) {
        final User user = userRepo.getUserById(id);
        return Response.status(user == null ? Response.Status.NOT_FOUND : Response.Status.OK)
                .entity(user).build();
    }

    @POST
    @Path("/{name}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@PathParam("name") String name) {
        final int id = userRepo.addUser(name);
        return Response.ok(new UserAnswer(id, "Пользователю " + name + " присвоен id " + id))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Integer id, @QueryParam("name") String name) {
        if (userRepo.updateUser(id, name) == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new UserAnswer(0, "Не найден пользователь для обновления с id " + id))
                    .build();
        }
        return Response.ok(new UserAnswer(id, "Пользователь с id " + id + " обновлен"))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeUser(@PathParam("id") Integer id) {
        if (userRepo.removeUser(id) == 0) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new UserAnswer(0, "Не найден пользователь для удаления с id " + id))
                    .build();
        }
        return Response.ok(new UserAnswer(id, "Пользователь с id " + id + " удален"))
                .build();
    }
}
