package com.coworking.repository;

import com.coworking.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {
    List<Workspace> findByAvailableTrue();
}