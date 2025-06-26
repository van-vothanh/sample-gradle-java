package com.example.todo.api;

import com.example.todo.model.Todo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {
    private static final ConcurrentHashMap<Long, Todo> todos = new ConcurrentHashMap<>();
    private static final AtomicLong idGenerator = new AtomicLong();

    @GET
    public List<Todo> getAllTodos() {
        return new ArrayList<>(todos.values());
    }

    @GET
    @Path("/{id}")
    public Todo getTodo(@PathParam("id") Long id) {
        Todo todo = todos.get(id);
        if (todo == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return todo;
    }

    @POST
    public Todo createTodo(Todo todo) {
        todo.setId(idGenerator.incrementAndGet());
        todos.put(todo.getId(), todo);
        return todo;
    }

    @PUT
    @Path("/{id}")
    public Todo updateTodo(@PathParam("id") Long id, Todo todo) {
        if (!todos.containsKey(id)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        todo.setId(id);
        todos.put(id, todo);
        return todo;
    }

    @DELETE
    @Path("/{id}")
    public void deleteTodo(@PathParam("id") Long id) {
        if (todos.remove(id) == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
