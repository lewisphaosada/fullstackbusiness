package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.ProjectDto;

public interface ProjectService {

    ProjectDto getProjectById(Long companyId, Long teamId, Long projectId);

    ProjectDto updateProject(Long companyId, Long teamId, Long projectId, ProjectDto projectDto);

    ProjectDto createProject(Long companyId, Long teamId, ProjectDto projectDto);
}
