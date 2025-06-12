package via.doc1.devopsdemo.controller;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import via.doc1.devopsdemo.model.Task;
import via.doc1.devopsdemo.model.TeamMember;
import via.doc1.devopsdemo.service.TeamService;

public class TeamServiceTest {

    private TeamService teamService;

    @BeforeEach
    public void setUp() {
        teamService = new TeamService();  
    }

    @Test
    public void testGetTeamMemberFound() {
        TeamMember member = new TeamMember("Member1", "Chase", "chase@pawpatrol.org", new ArrayList<>());

        teamService.addTeamMember(member);

        TeamMember result = teamService.getTeamMember("Member1");

        assertNotNull(result);
        assertEquals("Chase", result.getName());
      
    }

    @Test
    public void testGetTeamMemberNotFound() {
        TeamMember result = teamService.getTeamMember("NonExistentMember");
        assertNull(result);  
    }

    @Test
    public void testGetTasksForMember() {
        Task task1 = new Task("Task1", "IoT Pipeline", "Create CD pipeline for the IoT service");
        TeamMember member = new TeamMember("Member1", "Chase", "chase@pawpatrol.org", new ArrayList<>(List.of(task1)));
        teamService.addTeamMember(member);

        List<Task> tasks = teamService.getTasks("Member1");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Task1", tasks.get(0).getId());
    }

    @Test
    public void testGetTasksForNonExistentMember() {
        List<Task> tasks = teamService.getTasks("NonExistentMember");
        assertNull(tasks);  
    }

    @Test
    public void testGetTaskById() {
        Task task1 = new Task("Task1", "IoT Pipeline", "Create CD pipeline for the IoT service");
        TeamMember member = new TeamMember("Member1", "Chase", "chase@pawpatrol.org", new ArrayList<>(List.of(task1)));
        teamService.addTeamMember(member);

        Task result = teamService.getTask("Member1", "Task1");

        assertNotNull(result);
        assertEquals("Task1", result.getId());
    }

    @Test
    public void testGetTaskByIdNotFound() {
        TeamMember member = new TeamMember("Member1", "Chase", "chase@pawpatrol.org", new ArrayList<>());
        teamService.addTeamMember(member);

        Task result = teamService.getTask("Member1", "NonExistentTask");

        assertNull(result);  

        result = teamService.getTask("Member12", "NonExistentTask");

        assertNull(result); 
    }

    @Test
    public void testAddTeamMember() {
        TeamMember member = new TeamMember("Member1", "Chase", "chase@pawpatrol.org", new ArrayList<>());
        TeamMember addedMember = teamService.addTeamMember(member);

        assertNotNull(addedMember);
        assertEquals("Chase", addedMember.getName());
        assertEquals("chase@pawpatrol.org", addedMember.getEmail());
    }

    @Test
    public void testUpdateTeamMember() {
        Task task1 = new Task("Task1", "IoT Pipeline", "Create CD pipeline for the IoT service");
        TeamMember member = new TeamMember("Member1", "Chase", "chase@pawpatrol.org", new ArrayList<>(List.of(task1)));
        teamService.addTeamMember(member);

        TeamMember updatedMember = new TeamMember("Member1", "Chase Updated", "chaseUpdated@pawpatrol.org", new ArrayList<>(List.of(task1)));

        TeamMember result = teamService.updateTeamMember("Member1", updatedMember);

        assertNotNull(result);
        assertEquals("Chase Updated", result.getName());
        assertEquals("chaseUpdated@pawpatrol.org", result.getEmail());
    }

    @Test
    public void testUpdateTeamMemberNotFound() {
        TeamMember updatedMember = new TeamMember("NonExistentMember", "Updated", "updated@pawpatrol.org", new ArrayList<>());
        TeamMember result = teamService.updateTeamMember("NonExistentMember", updatedMember);

        assertNull(result);  
    }

    @Test
    public void testDeleteTeamMember() {
        TeamMember member = new TeamMember("Member1", "Chase", "chase@pawpatrol.org", new ArrayList<>());
        teamService.addTeamMember(member);

        boolean result = teamService.deleteTeamMember("Member1");
        assertTrue(result);
    }

    @Test
    public void testDeleteTeamMemberNotFound() {
        boolean result = teamService.deleteTeamMember("NonExistentMember");
        assertFalse(result);  
    }

}
