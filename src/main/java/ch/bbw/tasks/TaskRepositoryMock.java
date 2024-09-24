package ch.bbw.tasks;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryMock {
    private final List<Task> tasks;

    public TaskRepositoryMock() {
        tasks = new ArrayList<Task>() {{
            add(new Task(1, "Lunch with Teo", true));
            add(new Task(2, "read, moder Java receipies", false));
            add(new Task(3, "change GUI on Tasks", true));
            add(new Task(4, "business Logic", false));
            add(new Task(5, "Lunch with Jane", false));
        }};
    }

    public List<Task> getTasks() {
        return tasks;
    }
    public Optional<Task> getTask(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst();
    }
    public void addTask(Task task) {
        tasks.add(task);
    }
    public void deleteTask(int id) {
        tasks.removeIf(t -> t.getId() == id);
    }
}
