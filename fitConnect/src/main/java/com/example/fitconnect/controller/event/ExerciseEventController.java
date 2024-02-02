package com.example.fitconnect.controller.event;

import com.example.fitconnect.config.annotation.CurrentUserId;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.event.dto.ExerciseEventRegistrationDto;
import com.example.fitconnect.domain.event.dto.ExerciseEventUpdateDto;
import com.example.fitconnect.service.event.ExerciseEventDeleteService;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.event.ExerciseEventRegistrationService;
import com.example.fitconnect.service.event.ExerciseEventUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class ExerciseEventController {

    private final ExerciseEventRegistrationService registrationService;
    private final ExerciseEventFindService exerciseEventFindService;
    private final ExerciseEventUpdateService updateService;

    private final ExerciseEventDeleteService exerciseEventDeleteService;


    @PostMapping("/register")
    public ResponseEntity<ExerciseEvent> registerEvent(
            @RequestBody ExerciseEventRegistrationDto registrationDto,
            @CurrentUserId Long userId) {
        ExerciseEvent registeredEvent = registrationService.registerEvent(userId, registrationDto);
        return ResponseEntity.ok(registeredEvent);
    }

    @GetMapping
    ResponseEntity<Page<ExerciseEvent>> findEvent(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String description) {
        Page<ExerciseEvent> events = exerciseEventFindService.findEvents(category, description,
                page);
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ExerciseEvent> updateEvent(
            @PathVariable Long eventId,
            @RequestBody ExerciseEventUpdateDto updateDto,
            @CurrentUserId Long userId) {
        ExerciseEvent updatedEvent = updateService.updateEvent(eventId, updateDto, userId);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ExerciseEvent> deleteEvent(@PathVariable Long eventId,
            @CurrentUserId Long userId) {
        exerciseEventDeleteService.deleteEvent(eventId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{eventId}/detail")
    public ResponseEntity<ExerciseEvent> getEventDetail(@PathVariable Long eventId) {
        ExerciseEvent event = exerciseEventFindService.findEventDetail(eventId);
        return ResponseEntity.ok(event);
    }

}

