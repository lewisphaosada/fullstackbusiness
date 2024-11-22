package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.AnnouncementRequestDto;
import com.cooksys.groupfinal.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

	private final AnnouncementService announcementService;

	@PostMapping("/company/{companyId}")
	public AnnouncementDto createAnnouncement(
	    @PathVariable Long companyId,
	    @RequestBody AnnouncementRequestDto request
	) {
	    System.out.println("Received companyId: " + companyId);
	    System.out.println("Received request: " + request);
	    return announcementService.createAnnouncement(companyId, request);
	}


	@DeleteMapping("/{id}")
	public AnnouncementDto deleteAnnouncement(@PathVariable Long id) {

		return announcementService.deleteAnnouncement(id);
	}
}
