package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.TeamDto;

import java.util.Set;

public interface TeamService {
    TeamDto createTeam(Long companyId, TeamDto teamDto);

    TeamDto addTeammatesToTeam(Long teamId, Set<Long> userIds);
}
