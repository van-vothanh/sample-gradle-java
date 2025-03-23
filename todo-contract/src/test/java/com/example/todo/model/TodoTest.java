package com.example.todo.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class TodoTest {
    
    @Test
    public void testTodoConstructorAndGetters() {
        Long id = 1L;
        String title = "Test Todo";
        String description = "Test Description";
        boolean completed = false;

        Todo todo = new Todo(id, title, description, completed);

        assertEquals("ID should match", id, todo.getId());
        assertEquals("Title should match", title, todo.getTitle());
        assertEquals("Description should match", description, todo.getDescription());
        assertEquals("Completed status should match", completed, todo.isCompleted());
    }

    @Test
    public void testTodoSetters() {
        Todo todo = new Todo();

        Long id = 1L;
        String title = "Test Todo";
        String description = "Test Description";
        boolean completed = true;

        todo.setId(id);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(completed);

        assertEquals("ID should be updated", id, todo.getId());
        assertEquals("Title should be updated", title, todo.getTitle());
        assertEquals("Description should be updated", description, todo.getDescription());
        assertEquals("Completed status should be updated", completed, todo.isCompleted());
    }

    @Test
    public void testDefaultConstructor() {
        Todo todo = new Todo();
        
        assertNull("ID should be null by default", todo.getId());
        assertNull("Title should be null by default", todo.getTitle());
        assertNull("Description should be null by default", todo.getDescription());
        assertFalse("Completed should be false by default", todo.isCompleted());
    }
}
