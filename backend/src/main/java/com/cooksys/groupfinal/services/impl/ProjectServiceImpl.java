package com.cooksys.groupfinal.services.impl;


import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.repositories.TeamRepository;


import com.cooksys.groupfinal.services.ProjectService;
import lombok.RequiredArgsConstructor;
import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.Project;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.ProjectMapper;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.ProjectRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.entities.Project;
import com.cooksys.groupfinal.mappers.ProjectMapper;
import com.cooksys.groupfinal.repositories.ProjectRepository;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final CompanyRepository companyRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;


    @Override
    public ProjectDto getProjectById(Long companyId, Long teamId, Long projectId) {
        Project project = projectRepository.findByTeam_Company_IdAndTeam_IdAndId(companyId, teamId, projectId);

        if (project == null) {
            throw new RuntimeException("Project not found for companyId " + companyId + ", teamId " + teamId + " and projectId " + projectId);
        }

        return projectMapper.entityToDto(project);
    }

    @Override
    public ProjectDto updateProject(Long companyId, Long teamId, Long projectId, ProjectDto projectDto) {
        Project project = projectRepository.findByTeam_Company_IdAndTeam_IdAndId(companyId, teamId, projectId);

        if (project == null) {
            throw new RuntimeException("Project not found for companyId " + companyId + ", teamId " + teamId + " and projectId " + projectId);
        }

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setActive(projectDto.isActive());

        projectRepository.save(project);

        return projectMapper.entityToDto(project);

    }

    @Override
    public ProjectDto createProject(Long companyId, Long teamId, ProjectDto projectDto) {
        Team team = teamRepository.findByIdAndCompanyId(teamId, companyId)
                .orElseThrow(() -> new RuntimeException("Team not found for company " + companyId + " and team " + teamId));

        Project project = new Project();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setActive(projectDto.isActive());
        project.setTeam(team);

        projectRepository.save(project);

        return projectMapper.entityToDto(project);
    }

}
