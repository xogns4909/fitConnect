package com.example.fitconnect.controller.event;

import com.example.fitconnect.global.annotation.CurrentUserId;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.dto.event.request.ExerciseEventRegistrationDto;
import com.example.fitconnect.dto.event.request.ExerciseEventUpdateDto;
import com.example.fitconnect.dto.event.response.EventDetailResponseDto;
import com.example.fitconnect.dto.event.response.EventResponseDto;
import com.example.fitconnect.service.event.ExerciseEventDeleteService;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.event.ExerciseEventRegistrationService;
import com.example.fitconnect.service.event.ExerciseEventUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class ExerciseEventController {

    private final ExerciseEventRegistrationService registrationService;
    private final ExerciseEventFindService exerciseEventFindService;
    private final ExerciseEventUpdateService updateService;

    private final ExerciseEventDeleteService exerciseEventDeleteService;


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> registerEvent(
            @RequestPart ExerciseEventRegistrationDto registrationDto,
            @RequestPart(value = "multipartFileList", required = false) List<MultipartFile> files,
            @CurrentUserId Long userId) {
        registrationService.registerEvent(userId, registrationDto, files);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<Page<EventResponseDto>> findEvent(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) City city,
            @RequestParam(required = false) String searchBy,
            @RequestParam(required = false) String description) {
        Page<EventResponseDto> events = exerciseEventFindService.findEvents(category, city,
                searchBy, description,
                page);
        return ResponseEntity.ok(events);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<Void> updateEvent(
            @PathVariable Long eventId,
            @RequestBody ExerciseEventUpdateDto updateDto,
            @CurrentUserId Long userId) {
        updateService.updateEvent(eventId, updateDto, userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{eventId}/image")
    public ResponseEntity<Void> updateEventImage(@PathVariable Long eventId,
            @RequestPart(required = false) List<MultipartFile> images) {
        updateService.updateEventImage(eventId,images);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId,
            @CurrentUserId Long userId) {
        exerciseEventDeleteService.deleteEvent(eventId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventId}/detail")
    public ResponseEntity<EventDetailResponseDto> getEventDetail(@PathVariable Long eventId) {
        EventDetailResponseDto eventDetail = exerciseEventFindService.findEventDetail(eventId);
        return ResponseEntity.ok(eventDetail);
    }

}

