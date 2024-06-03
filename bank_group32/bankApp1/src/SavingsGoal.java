import java.io.Serializable;

public class SavingsGoal implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private double targetSavings;
    private String purpose;
    private double currentSavings;

    public SavingsGoal(int id, double targetSavings, String purpose) {
        this.id = id;
        this.targetSavings = targetSavings;
        this.purpose = purpose;
        this.currentSavings = 0; // 初始当前存款为0
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTargetSavings() {
        return targetSavings;
    }

    public void setTargetSavings(double targetSavings) {
        this.targetSavings = targetSavings;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public double getCurrentSavings() {
        return currentSavings;
    }

    public void setCurrentSavings(double currentSavings) {
        this.currentSavings = currentSavings;
    }

    // 计算完成的百分比
    public double getCompletionPercentage() {
        return currentSavings / targetSavings * 100;
    }

    @Override
    public String toString() {
        return String.format("%s:  %s / %s, Progress: %.2f%%", 
                purpose, currentSavings, targetSavings, getCompletionPercentage());
    }

}