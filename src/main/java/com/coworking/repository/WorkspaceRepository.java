package com.coworking.repository;

import com.coworking.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {
    List<Workspace> findByAvailableTrue();
}
