package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.AnnouncementRequestDto;

public interface AnnouncementService {

	AnnouncementDto createAnnouncement(Long companyId, AnnouncementRequestDto request);


    AnnouncementDto deleteAnnouncement(Long id);
}
