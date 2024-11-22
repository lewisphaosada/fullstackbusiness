package com.cooksys.groupfinal.controllers;

import java.util.List;
import java.util.Set;

import com.cooksys.groupfinal.services.ProjectService;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.services.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final ProjectService projectService;

    @GetMapping("/{id}/users")
    public Set<FullUserDto> getAllUsers(@PathVariable Long id) {
        return companyService.getAllUsers(id);
    }

    @GetMapping("/{id}/announcements")
    public Set<AnnouncementDto> getAllAnnouncements(@PathVariable Long id) {
        return companyService.getAllAnnouncements(id);
    }

    @GetMapping("/{id}/teams")
    public Set<TeamDto> getAllTeams(@PathVariable Long id) {
        return companyService.getAllTeams(id);
    }

    @GetMapping("/{companyId}/teams/{teamId}/projects")
    public Set<ProjectDto> getAllProjects(@PathVariable Long companyId, @PathVariable Long teamId) {
        return companyService.getAllProjects(companyId, teamId);
    }
    @GetMapping
    public List<String> getAllCompanies() {
        return companyService.getAllCompanyNames();
    }

    @GetMapping("/{companyId}/teams/{teamId}/projects/{projectId}")
    public ProjectDto getProject(@PathVariable Long companyId, @PathVariable Long teamId, @PathVariable Long projectId) {
        return projectService.getProjectById(companyId, teamId, projectId);  // Adjusted to remove companyId dependency
    }

    @PutMapping("/{companyId}/teams/{teamId}/projects/{projectId}")
    public ProjectDto updateProject(@PathVariable Long companyId, @PathVariable Long teamId, @PathVariable Long projectId, @RequestBody ProjectDto projectDto) {
        return projectService.updateProject(companyId, teamId, projectId, projectDto);  // Adjusted to remove companyId dependency
    }

    @PostMapping("/{companyId}/teams/{teamId}/projects")
    public ProjectDto createProject(@PathVariable Long companyId, @PathVariable Long teamId, @RequestBody ProjectDto projectDto) {
        return projectService.createProject(companyId, teamId, projectDto);  // You would need to implement this in your service
    }

}
