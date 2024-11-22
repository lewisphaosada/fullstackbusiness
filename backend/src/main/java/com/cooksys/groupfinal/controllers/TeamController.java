package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.services.CompanyService;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.services.TeamService;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
	
	private final TeamService teamService;

	@PostMapping("/{companyId}/teams")
	public TeamDto createTeam(@PathVariable Long companyId, @RequestBody TeamDto teamDto) {
		return teamService.createTeam(companyId, teamDto);
	}

	@PostMapping("/{teamId}/teammates")
	public TeamDto addTeammatesToTeam(@PathVariable Long teamId, @RequestBody Set<Long> userIds) {
		return teamService.addTeammatesToTeam(teamId, userIds);
	}

}
