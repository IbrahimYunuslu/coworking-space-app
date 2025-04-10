package com.coworking.service;

import com.coworking.model.Workspace;
import com.coworking.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Transactional(readOnly = true)
    public List<Workspace> getAllAvailableWorkspaces() {
        return workspaceRepository.findByAvailableTrue();
    }

    public Workspace saveWorkspace(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }

    public void deleteWorkspace(int id) {
        workspaceRepository.deleteById(id);
    }
}