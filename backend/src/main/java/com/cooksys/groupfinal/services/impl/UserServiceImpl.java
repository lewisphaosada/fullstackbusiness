package com.cooksys.groupfinal.services.impl;

import java.util.Optional;

import com.cooksys.groupfinal.dtos.UserRequestDto;
import com.cooksys.groupfinal.entities.Announcement;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.mappers.ProfileMapper;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.entities.Credentials;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.exceptions.NotAuthorizedException;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.CredentialsMapper;
import com.cooksys.groupfinal.mappers.FullUserMapper;
import com.cooksys.groupfinal.repositories.UserRepository;
import com.cooksys.groupfinal.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FullUserMapper fullUserMapper;
    private final CredentialsMapper credentialsMapper;
    private final CompanyRepository companyRepository;
    private final ProfileMapper profileMapper;

    private User findUser(String username) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndActiveTrue(username);
        if (user.isEmpty()) {
            throw new NotFoundException("The username provided does not belong to an active user.");
        }
        return user.get();
    }

    @Override
    public FullUserDto login(CredentialsDto credentialsDto) {
        if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null) {
            throw new BadRequestException("A username and password are required.");
        }
        Credentials credentialsToValidate = credentialsMapper.dtoToEntity(credentialsDto);
        User userToValidate = findUser(credentialsDto.getUsername());
        if (!userToValidate.getCredentials().equals(credentialsToValidate)) {
            throw new NotAuthorizedException("The provided credentials are invalid.");
        }
        if (userToValidate.getStatus().equals("PENDING")) {
            userToValidate.setStatus("JOINED");
            userRepository.saveAndFlush(userToValidate); // Update the status to JOINED
        }
        return fullUserMapper.entityToFullUserDto(userToValidate);
    }


    @Override
    public FullUserDto createUser(Long companyId, UserRequestDto request) {
        // Validate the company
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company with ID " + companyId + " not found."));

        // Create and set up the user
        User user = new User();
        user.setCredentials(credentialsMapper.dtoToEntity(request.getCredentials()));
        user.setProfile(profileMapper.dtoToEntity(request.getProfile()));
        user.setActive(true); // Assume new users are active by default
        user.setAdmin(request.isAdmin());
        user.setStatus("PENDING"); // Default status is set to PENDING

        // Save the user
        User savedUser = userRepository.save(user);

        // Associate the user with the company
        company.getEmployees().add(savedUser);
        companyRepository.save(company);

        // Map and return the user DTO
        return fullUserMapper.entityToFullUserDto(savedUser);
    }


    @Override
    public FullUserDto deleteUser(Long userId) {

        // Validate user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found."));



        // Cleanup relationships with teams
        user.getTeams().forEach(team -> team.getTeammates().remove(user));

        // Cleanup relationships with companies
        user.getCompanies().forEach(company -> company.getEmployees().remove(user));

        // Map the user to a DTO before deletion
        FullUserDto deletedUserDto = fullUserMapper.entityToFullUserDto(user);

        // Delete the user
        userRepository.delete(user);

        return deletedUserDto; // Return the deleted user's details
    }



}
