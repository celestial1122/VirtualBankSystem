import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 1L; // 用于序列化版本控制

    private int id;
    private String name;
    private double reward;
    private boolean isCompleted;
    private boolean acceptedByChild;

    public Task(int id, String name, double reward) {
        this.id = id;
        this.name = name;
        this.reward = reward;
        this.isCompleted = false;
        this.acceptedByChild = false;

    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    public boolean isAcceptedByChild() {
        return acceptedByChild;
    }
    public void setAcceptedByChild(boolean accepted) {
        acceptedByChild = accepted;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Reward: " + reward + ", Completed: " + isCompleted + ", Accepted: " + acceptedByChild;
    }

}