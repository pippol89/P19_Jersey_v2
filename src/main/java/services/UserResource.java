package services;

import model.User;
import repository.UserRepo;
import repository.UserRepoImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/users")
public class UserResource {
    private UserRepo userRepo = new UserRepoImpl();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String, User> getUsers() {
        return userRepo.getUsers();
    }

    @GET
    @Path("/{id}")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON})
    public User getUsersById(@PathParam("id") int id) {
        return userRepo.getUserById(id);
    }

    @POST
    @Path("/{name}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUser(@PathParam("name") String name) {
        final int id = userRepo.addUser(name);
        return Response.ok("Пользователю " + name + " присвоен id " + id).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUser(@PathParam("id") Integer id, @QueryParam("name") String name) {
        String mes = userRepo.updateUser(id, name) > 0 ? "Пользователь с id " + id + " обновлен" : "Не найден пользователь для обновления с id " + id;
        return Response.ok(mes).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeUser(@PathParam("id") Integer id) {
        String mes = userRepo.removeUser(id) > 0 ? "Пользователь с id " + id + " удален" : "Не найден пользователь для удаления с id " + id;
        return Response.ok(mes).build();
    }
}
