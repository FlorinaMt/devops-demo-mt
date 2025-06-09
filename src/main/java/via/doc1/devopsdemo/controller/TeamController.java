package via.doc1.devopsdemo.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import via.doc1.devopsdemo.model.Task;
import via.doc1.devopsdemo.model.TeamMember;
import via.doc1.devopsdemo.service.TeamService;

@RestController
public class TeamController {

    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/members/{memberId}/tasks/{taskId}")
    public Task getTaskDetails(@PathVariable String memberId,
                               @PathVariable String taskId) {
        return teamService.getTask(memberId, taskId);
    }

    @GetMapping("/{memberId}/tasks")
    public List<Task> getTasks(@PathVariable String memberId) {
        return teamService.getTasks(memberId);
    }

    @GetMapping("/{memberId}")
    public TeamMember getTeamMember(@PathVariable String memberId) {
        return teamService.getTeamMember(memberId);
    }

    @PostMapping
    public ResponseEntity<TeamMember> addTeamMember(@RequestBody TeamMember teamMember) {
        TeamMember createdMember = teamService.addTeamMember(teamMember);
        return ResponseEntity.ok(createdMember);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<TeamMember> updateTeamMember(@PathVariable String memberId,
                                                       @RequestBody TeamMember teamMember) {
        TeamMember updatedMember = teamService.updateTeamMember(memberId, teamMember);
        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteTeamMember(@PathVariable String memberId) {
        boolean deleted = teamService.deleteTeamMember(memberId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
}

