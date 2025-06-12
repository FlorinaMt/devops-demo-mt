package via.doc1.devopsdemo.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import via.doc1.devopsdemo.model.Task;
import via.doc1.devopsdemo.model.TeamMember;
import via.doc1.devopsdemo.service.TeamService;

public class TeamControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    private TeamMember member;
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();

        task = new Task("Task1", "IoT Pipeline", "Create CD pipeline for the IoT service");
        member = new TeamMember("Member1", "Chase", "chase@pawpatrol.org", List.of(task));
    }

    @Test
    void getTaskDetails_ShouldReturnTask() throws Exception {
        when(teamService.getTask("Member1", "Task1")).thenReturn(task);

        mockMvc.perform(get("/members/Member1/tasks/Task1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("Task1"))
                .andExpect(jsonPath("$.name").value("IoT Pipeline"));

        verify(teamService, times(1)).getTask("Member1", "Task1");
    }

    @Test
    void getTaskDetails_NonExistingTask_ShouldReturnNotFound() throws Exception {
        when(teamService.getTask("Member1", "UnknownTask")).thenReturn(null);

        mockMvc.perform(get("/members/Member1/tasks/UnknownTask")).andExpect(status().isOk())
                .andExpect(content().string(""));

        verify(teamService, times(1)).getTask("Member1", "UnknownTask");
    }

    @Test
    void getTasks_ShouldReturnTaskList() throws Exception {
        when(teamService.getTasks("Member1")).thenReturn(List.of(task));

        mockMvc.perform(get("/Member1/tasks")).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(teamService, times(1)).getTasks("Member1");
    }

    @Test
    void getTeamMember_ShouldReturnMember() throws Exception {
        when(teamService.getTeamMember("Member1")).thenReturn(member);

        mockMvc.perform(get("/Member1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("Member1"))
                .andExpect(jsonPath("$.name").value("Chase"));

        verify(teamService, times(1)).getTeamMember("Member1");
    }

    @Test
    void addTeamMember_ShouldReturnNewMember() throws Exception {
        when(teamService.addTeamMember(any(TeamMember.class))).thenReturn(member);

        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(
                "{\"id\":\"Member1\",\"name\":\"Chase\",\"email\":\"chase@pawpatrol.org\",\"tasks\":[]}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Chase"));

        verify(teamService, times(1)).addTeamMember(any(TeamMember.class));
    }

    @Test
    void updateTeamMember_ShouldReturnUpdatedMember() throws Exception {
        when(teamService.updateTeamMember(eq("Member1"), any(TeamMember.class))).thenReturn(member);

        mockMvc.perform(put("/Member1").contentType(MediaType.APPLICATION_JSON).content(
                "{\"id\":\"Member1\",\"name\":\"Updated Chase\",\"email\":\"newemail@pawpatrol.org\",\"tasks\":[]}"))
                .andExpect(status().isOk());

        verify(teamService, times(1)).updateTeamMember(eq("Member1"), any(TeamMember.class));
    }

    @Test
    void deleteTeamMember_ShouldReturnNoContent() throws Exception {
        when(teamService.deleteTeamMember("Member1")).thenReturn(true);

        mockMvc.perform(delete("/Member1")).andExpect(status().isNoContent());

        verify(teamService, times(1)).deleteTeamMember("Member1");
    }

    @Test
    void deleteTeamMember_NonExisting_ShouldReturnNotFound() throws Exception {
        when(teamService.deleteTeamMember("Unknown")).thenReturn(false);

        mockMvc.perform(delete("/Unknown")).andExpect(status().isNotFound());

        verify(teamService, times(1)).deleteTeamMember("Unknown");
    }

    @Test
    void testDeleteTeamMember_NotFound() {
        when(teamService.deleteTeamMember("101")).thenReturn(false);
        ResponseEntity<Void> response = teamController.deleteTeamMember("111");
        assertEquals(404, response.getStatusCode().value());
        verify(teamService).deleteTeamMember("111");
    }

    @Test
    void updateTeamMember_ShouldReturnNotFound_WhenMemberDoesNotExist() throws Exception {
        when(teamService.updateTeamMember(eq("UnknownMember"), any(TeamMember.class)))
                .thenReturn(null);

        mockMvc.perform(put("/UnknownMember").contentType(MediaType.APPLICATION_JSON).content(
                "{\"id\":\"UnknownMember\",\"name\":\"Unknown\",\"email\":\"unknown@pawpatrol.org\",\"tasks\":[]}"))
                .andExpect(status().isNotFound());

        verify(teamService, times(1)).updateTeamMember(eq("UnknownMember"), any(TeamMember.class));
    }


    @Test
    void deleteTeamMember_ShouldReturnNotFound_WhenEmpty() {
        when(teamService.deleteTeamMember("NonExistentMember")).thenReturn(false);
        ResponseEntity<Void> response = teamController.deleteTeamMember("NonExistentMember");
        assertEquals(404, response.getStatusCode().value());
        verify(teamService).deleteTeamMember("NonExistentMember");
    }



}
