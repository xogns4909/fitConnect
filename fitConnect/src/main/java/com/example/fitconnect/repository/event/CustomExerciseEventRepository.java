package com.example.fitconnect.repository.event;

import com.example.fitconnect.domain.event.domain.Category;
import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomExerciseEventRepository {

    Page<ExerciseEvent> findEventsWithConditions(Category category, City city,String searchBy, String content, int page);

    Page<ExerciseEvent> findEventsByOrganizerId(Long userId, Pageable pageable);
}
