package com.example.fitconnect.controller.event;

import com.example.fitconnect.config.service.CommonService;
import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import com.example.fitconnect.domain.event.dto.ExerciseEventRegistrationDto;
import com.example.fitconnect.domain.event.dto.ExerciseEventUpdateDto;
import com.example.fitconnect.service.event.ExerciseEventFindService;
import com.example.fitconnect.service.event.ExerciseEventRegistrationService;
import com.example.fitconnect.service.event.ExerciseEventUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class ExerciseEventController {

    private final ExerciseEventRegistrationService registrationService;

    private final ExerciseEventFindService exerciseEventFindService;

    private final ExerciseEventUpdateService updateService;
    private final CommonService commonService;

    @PostMapping("/register")
    public ResponseEntity<ExerciseEvent> registerEvent(
            @RequestBody ExerciseEventRegistrationDto registrationDto,
            HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        ExerciseEvent registeredEvent = registrationService.registerEvent(userId, registrationDto);
        return ResponseEntity.ok(registeredEvent);
    }

    @GetMapping
    ResponseEntity<Page<ExerciseEvent>> findEvent(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String description) {
        Page<ExerciseEvent> events = exerciseEventFindService.findEvents(category, description, page);
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ExerciseEvent> updateEvent(
            @PathVariable Long eventId,
            @RequestBody ExerciseEventUpdateDto updateDto,
            HttpSession session) {
        Long userId = commonService.extractUserIdFromSession(session);
        ExerciseEvent updatedEvent = updateService.updateEvent(eventId, updateDto, userId);
        return ResponseEntity.ok(updatedEvent);
    }
}
