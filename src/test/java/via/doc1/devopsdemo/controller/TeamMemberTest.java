package via.doc1.devopsdemo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import via.doc1.devopsdemo.model.Task;
import via.doc1.devopsdemo.model.TeamMember;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamMemberTest {

    private TeamMember teamMember;
    private Task mockTask1;
    private Task mockTask2;

    @BeforeEach
    void setUp() {
        mockTask1 = Mockito.mock(Task.class);
        mockTask2 = Mockito.mock(Task.class);

        teamMember = new TeamMember("1", "Alice", "alice@example.com", Arrays.asList(mockTask1, mockTask2));
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("1", teamMember.getId(), "ID should be correctly set");
        assertEquals("Alice", teamMember.getName(), "Name should be correctly set");
        assertEquals("alice@example.com", teamMember.getEmail(), "Email should be correctly set");
        assertEquals(2, teamMember.getTasks().size(), "Task list should contain 2 tasks");
    }

    @Test
    void testSetId() {
        teamMember.setId("2");
        assertEquals("2", teamMember.getId(), "ID should be updated");
    }

    @Test
    void testSetName() {
        teamMember.setName("Bob");
        assertEquals("Bob", teamMember.getName(), "Name should be updated");
    }

    @Test
    void testSetEmail() {
        teamMember.setEmail("bob@example.com");
        assertEquals("bob@example.com", teamMember.getEmail(), "Email should be updated");
    }

    @Test
    void testSetTasks() {
        Task mockTask3 = Mockito.mock(Task.class);
        List<Task> newTasks = Arrays.asList(mockTask3);

        teamMember.setTasks(newTasks);
        assertEquals(1, teamMember.getTasks().size(), "Task list should be updated");
        assertEquals(mockTask3, teamMember.getTasks().get(0), "New task should be set correctly");
    }

    @Test
    void testToString() {
        String result = teamMember.toString();
        assertTrue(result.contains("id=1"), "toString() should include ID");
        assertTrue(result.contains("name=Alice"), "toString() should include Name");
        assertTrue(result.contains("email=alice@example.com"), "toString() should include Email");
    }
}
