package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.AnnouncementRequestDto;
import com.cooksys.groupfinal.entities.Announcement;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.AnnouncementMapper;
import com.cooksys.groupfinal.repositories.AnnouncementRepository;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.services.AnnouncementService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;


    @Override
    public AnnouncementDto createAnnouncement(Long companyId, AnnouncementRequestDto request) {
    	if (request.getAuthorId() == null) {
            throw new IllegalArgumentException("Author ID must not be null");
        }
    	
    	// Validate company existence
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company with ID " + companyId + " not found."));

        // Validate author existence
        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new NotFoundException("User with ID " + request.getAuthorId() + " not found."));
        if (!author.isActive()) {
            throw new NotFoundException("User with ID " + request.getAuthorId() + " is not active.");
        }

        // Create and populate announcement entity
        Announcement announcement = new Announcement();
        announcement.setTitle(request.getTitle());
        announcement.setMessage(request.getMessage());
        announcement.setCompany(company);
        announcement.setAuthor(author);
        announcement.setDate(new java.sql.Timestamp(System.currentTimeMillis())); // Set current timestamp

        // Save announcement
        Announcement savedAnnouncement = announcementRepository.saveAndFlush(announcement);

        // Return mapped DTO
        return announcementMapper.entityToDto(savedAnnouncement);
    }

    @Override
    public AnnouncementDto deleteAnnouncement(Long id) {
        // Fetch the announcement by ID
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Announcement with ID " + id + " not found."));

        // Delete the announcement
        announcementRepository.delete(announcement);

        // Map the deleted announcement to DTO and return it
        return announcementMapper.entityToDto(announcement);
    }


}