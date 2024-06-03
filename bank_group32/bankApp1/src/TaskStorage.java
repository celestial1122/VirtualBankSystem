import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {
    private String taskFilePath;

    public TaskStorage(String taskFilePath) {
        this.taskFilePath = taskFilePath;
    }

    public void saveTask(Task task) {
        List<Task> tasks = loadTasks(); // 加载现有任务列表
        tasks.add(task); // 添加新任务
        saveTasks(tasks); // 保存更新后的任务列表
    }

    public void saveTasks(List<Task> tasks) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(taskFilePath))) {
            out.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(taskFilePath))) {
            tasks = (List<Task>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
