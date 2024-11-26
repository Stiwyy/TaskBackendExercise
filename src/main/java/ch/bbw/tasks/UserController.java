package ch.bbw.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TaskRestController {
    private final TaskRepositoryMock taskRepository;

    @Autowired
    public TaskRestController(TaskRepositoryMock taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(taskRepository.getTasks());
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable int id){
        Optional<Task> task = taskRepository.getTask(id);
        if(task.isPresent())
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(task.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(null);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        taskRepository.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(task);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable int id) {
        Optional<Task> existingTask = taskRepository.getTask(id);
        if (existingTask.isPresent()) {
            Task updatedTask = existingTask.get();
            updatedTask.setDescription(task.getDescription());
            updatedTask.setCompleted(task.isCompleted());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(updatedTask);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<Task> patchTask(@RequestBody Task task, @PathVariable int id) {
        Optional<Task> existingTask = taskRepository.getTask(id);
        if (existingTask.isPresent()) {
            Task updatedTask = existingTask.get();
            if (task.getDescription() != null) {
                updatedTask.setDescription(task.getDescription());
            }
            if (task.isCompleted() != updatedTask.isCompleted()) {
                updatedTask.setCompleted(task.isCompleted());
            }
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(updatedTask);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        taskRepository.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).body(null);
    }

    @DeleteMapping("/tasks")
    public ResponseEntity<Void> deleteAllTasks() {
        taskRepository.getTasks().clear();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).body(null);
    }
}