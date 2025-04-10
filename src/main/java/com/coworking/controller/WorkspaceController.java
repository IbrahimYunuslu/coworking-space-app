package com.coworking.controller;

import com.coworking.model.Workspace;
import com.coworking.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping("/available")
    public List<Workspace> getAvailableWorkspaces() {
        return workspaceService.getAllAvailableWorkspaces();
    }

    @PostMapping
    public Workspace addWorkspace(@RequestBody Workspace workspace) {
        return workspaceService.saveWorkspace(workspace);
    }

    @DeleteMapping("/{id}")
    public void removeWorkspace(@PathVariable int id) {
        workspaceService.deleteWorkspace(id);
    }
}