package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.TeamMapper;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import com.cooksys.groupfinal.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.services.TeamService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final CompanyRepository companyRepository;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final UserRepository userRepository;

    @Override
    public TeamDto createTeam(Long companyId, TeamDto teamDto) {

     
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company with ID " + companyId + " not found."));


        Team team = new Team();
        team.setName(teamDto.getName());
        team.setDescription(teamDto.getDescription());
        team.setCompany(company);

   
        if (teamDto.getTeammates() != null && !teamDto.getTeammates().isEmpty()) {
            Set<User> teammates = teamDto.getTeammates().stream()
                    .map(teammateDto -> userRepository.findById(teammateDto.getId())
                            .orElseThrow(() -> new NotFoundException("User with ID " + teammateDto.getId() + " not found.")))
                    .filter(User::isActive)
                    .collect(Collectors.toSet());
            team.setTeammates(teammates);
        }

  
        Team savedTeam = teamRepository.save(team);


        return teamMapper.entityToDto(savedTeam);
    }


    @Override
    public TeamDto addTeammatesToTeam(Long teamId, Set<Long> userIds) {
        // Validate the team
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("Team with ID " + teamId + " not found."));

        // Validate and retrieve users
        Set<User> users = userRepository.findAllById(userIds).stream()
                .filter(User::isActive)
                .collect(Collectors.toSet());

        if (users.isEmpty()) {
            throw new NotFoundException("No valid active users found for the provided IDs.");
        }

        // Add teammates to the team
        team.getTeammates().addAll(users);

        // Save and map to DTO
        return teamMapper.entityToDto(teamRepository.save(team));
    }
}
