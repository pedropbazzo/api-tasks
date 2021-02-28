package com.spring.task.controller;

import com.spring.task.document.TaskDocument;
import com.spring.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<Page<TaskDocument>> getAllTask(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                          @RequestParam(required = false) String flag){
        Page<TaskDocument> taskPage = taskService.findAll(pageable, flag);
        if(taskPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<Page<TaskDocument>>(taskPage, HttpStatus.OK);
        }
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDocument> getOneTask(@PathVariable(value="id") String id){
        Optional<TaskDocument> taskO = taskService.findById(id);
        if(!taskO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<TaskDocument>(taskO.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDocument> saveTask(@RequestBody @Valid TaskDocument task) {
        task.setRegistrationDate(LocalDateTime.now());
        return new ResponseEntity<TaskDocument>(taskService.save(task), HttpStatus.CREATED);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable(value="id") String id) {
        Optional<TaskDocument> taskO = taskService.findById(id);
        if(!taskO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            taskService.delete(taskO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskDocument> updateTask(@PathVariable(value="id") String id,
                                                      @RequestBody @Valid TaskDocument taskDocument) {
        Optional<TaskDocument> taskO = taskService.findById(id);
        if(!taskO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            taskDocument.setId(taskO.get().getId());
            return new ResponseEntity<TaskDocument>(taskService.save(taskDocument), HttpStatus.OK);
        }
    }
}
