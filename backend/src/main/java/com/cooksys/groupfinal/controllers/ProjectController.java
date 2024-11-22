package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.ProjectDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.services.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
	
	private final ProjectService projectService;

	@PostMapping("/{companyId}/teams/{teamId}/projects")
	public ResponseEntity<ProjectDto> createProject(
			@PathVariable Long companyId,
			@PathVariable Long teamId,
			@RequestBody ProjectDto projectRequest) {

		ProjectDto createdProject = projectService.createProject(companyId, teamId, projectRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
	}
	@PutMapping("/{companyId}/teams/{teamId}/projects/{projectId}")
	public ResponseEntity<ProjectDto> updateProject(
			@PathVariable Long companyId,
			@PathVariable Long teamId,
			@PathVariable Long projectId,
			@RequestBody ProjectDto projectRequest) {

		ProjectDto updatedProject = projectService.updateProject(companyId, teamId, projectId, projectRequest);
		return ResponseEntity.ok(updatedProject);
	}

}
