package com.example.todo.api;

import com.example.todo.model.Todo;
import org.junit.Before;
import org.junit.Test;
import jakarta.ws.rs.WebApplicationException;
import java.util.List;
import static org.junit.Assert.*;

public class TodoResourceTest {
    private TodoResource todoResource;
    private static final String TEST_TITLE = "Test Todo";
    private static final String TEST_DESCRIPTION = "Test Description";

    @Before
    public void setUp() {
        todoResource = new TodoResource();
    }

    @Test
    public void testCreateTodo() {
        Todo todo = new Todo(null, TEST_TITLE, TEST_DESCRIPTION, false);
        Todo created = todoResource.createTodo(todo);

        assertNotNull("Created todo should have an ID", created.getId());
        assertEquals("Title should match", TEST_TITLE, created.getTitle());
        assertEquals("Description should match", TEST_DESCRIPTION, created.getDescription());
        assertFalse("New todo should not be completed", created.isCompleted());
    }

    @Test
    public void testGetTodo() {
        // Create a todo first
        Todo todo = new Todo(null, TEST_TITLE, TEST_DESCRIPTION, false);
        Todo created = todoResource.createTodo(todo);

        // Get the todo
        Todo retrieved = todoResource.getTodo(created.getId());
        assertNotNull("Should retrieve todo", retrieved);
        assertEquals("IDs should match", created.getId(), retrieved.getId());
        assertEquals("Titles should match", created.getTitle(), retrieved.getTitle());
    }

    @Test(expected = WebApplicationException.class)
    public void testGetNonExistentTodo() {
        todoResource.getTodo(999L);
    }

    @Test
    public void testUpdateTodo() {
        // Create a todo first
        Todo todo = new Todo(null, TEST_TITLE, TEST_DESCRIPTION, false);
        Todo created = todoResource.createTodo(todo);

        // Update the todo
        created.setTitle("Updated Title");
        created.setCompleted(true);
        Todo updated = todoResource.updateTodo(created.getId(), created);

        assertEquals("Title should be updated", "Updated Title", updated.getTitle());
        assertTrue("Todo should be marked as completed", updated.isCompleted());
    }

    @Test(expected = WebApplicationException.class)
    public void testUpdateNonExistentTodo() {
        Todo todo = new Todo(999L, TEST_TITLE, TEST_DESCRIPTION, false);
        todoResource.updateTodo(999L, todo);
    }

    @Test
    public void testGetAllTodos() {
        // Create a few todos
        todoResource.createTodo(new Todo(null, "Todo 1", "Description 1", false));
        todoResource.createTodo(new Todo(null, "Todo 2", "Description 2", false));

        List<Todo> todos = todoResource.getAllTodos();
        assertNotNull("Should return a list", todos);
        assertTrue("Should have at least 2 todos", todos.size() >= 2);
    }

    @Test
    public void testDeleteTodo() {
        // Create a todo first
        Todo todo = new Todo(null, TEST_TITLE, TEST_DESCRIPTION, false);
        Todo created = todoResource.createTodo(todo);

        // Delete the todo
        todoResource.deleteTodo(created.getId());

        // Verify deletion
        try {
            todoResource.getTodo(created.getId());
            fail("Should throw WebApplicationException for deleted todo");
        } catch (WebApplicationException e) {
            assertEquals("Should return 404 status", 404, e.getResponse().getStatus());
        }
    }

    @Test(expected = WebApplicationException.class)
    public void testDeleteNonExistentTodo() {
        todoResource.deleteTodo(999L);
    }
}
