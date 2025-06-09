package via.doc1.devopsdemo.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import via.doc1.devopsdemo.model.Task;

class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("1", "Test Task", "This is a test task");
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("1", task.getId());
        assertEquals("Test Task", task.getName());
        assertEquals("This is a test task", task.getDescription());
    }

    @Test
    void testSetters() {
        task.setId("2");
        task.setName("Updated Task");
        task.setDescription("Updated description");

        assertEquals("2", task.getId());
        assertEquals("Updated Task", task.getName());
        assertEquals("Updated description", task.getDescription());
    }

    @Test
    void testToString() {
        String expectedString = "Task{id='1', name='Test Task', description='This is a test task'}";
        assertEquals(expectedString, task.toString());
    }

    @Test
    void testHashCode() {
        Task sameTask = new Task("1", "Another Task", "Different description");
        assertEquals(task.hashCode(), sameTask.hashCode());
    }


    @Test
    void testConstructor() {
        Task newTask = new Task("2", "New Task", "A different task");
        assertEquals("2", newTask.getId());
        assertEquals("New Task", newTask.getName());
        assertEquals("A different task", newTask.getDescription());
    }


    @Test
    void testEquals() {
        Task task1 = new Task("1", "Task One", "Description One");
        Task task2 = new Task("1", "Task Two", "Description Two"); 
        Task task3 = new Task("2", "Task Three", "Description Three"); 
        
        assertEquals(task1, task2, "Tasks with the same ID should be equal");
        assertNotEquals(task1, task3, "Tasks with different IDs should not be equal");
        assertNotEquals(task1, null, "Task should not be equal to null");
        assertNotEquals(task1, new Object(), "Task should not be equal to an unrelated object");

        assertEquals(task1, task1, "Task should be equal to itself");
    }

    @Test void testEmptyConstructor()
    {
        Task emptyTask = new Task();
        assertNull(emptyTask.getId());
        assertNull(emptyTask.getName());
        assertNull(emptyTask.getDescription());
    }

}

