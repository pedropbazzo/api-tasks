package com.spring.task.repository;

import com.spring.task.document.TaskDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;


public interface TaskRepository extends MongoRepository<TaskDocument, String> {

    Page<TaskDocument> findByTaskDateAfterOrderByTaskDateAsc(LocalDateTime date, Pageable pageable);
    Page<TaskDocument> findByTaskDateBeforeOrderByTaskDateDesc(LocalDateTime date, Pageable pageable);
}
