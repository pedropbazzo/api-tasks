package com.spring.task.service;

import com.spring.task.document.TaskDocument;
import com.spring.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
   TaskRepository taskRepository;

    public Page<TaskDocument> findAll(Pageable pageable, String flag){
        if(flag != null && flag.equals("next")){
            return taskRepository.findByTaskDateAfterOrderByTaskDateAsc(LocalDateTime.now(), pageable);
        }else if(flag != null && flag.equals("previous")){
            return taskRepository.findByTaskDateBeforeOrderByTaskDateDesc(LocalDateTime.now(), pageable);
        }else{
            return taskRepository.findAll(pageable);
        }
    }

    public Optional<TaskDocument> findById(String id){
        return taskRepository.findById(id);
    }

    public TaskDocument save(TaskDocument taskDocument){
        return taskRepository.save(taskDocument);
    }

    public void delete(TaskDocument taskDocument){
        taskRepository.delete(taskDocument);
    }
}
