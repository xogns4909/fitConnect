package com.example.fitconnect.repository.event;

import com.example.fitconnect.domain.event.domain.ExerciseEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseEventRepository extends JpaRepository<ExerciseEvent,Long>,CustomExerciseEventRepository {

}
