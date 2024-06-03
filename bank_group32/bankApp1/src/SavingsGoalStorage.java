import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SavingsGoalStorage {
    private String savingsGoalFilePath;

    public SavingsGoalStorage(String savingsGoalFilePath) {
        this.savingsGoalFilePath = savingsGoalFilePath;
    }

    public void saveSavingsGoals(List<SavingsGoal> savingsGoals) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(savingsGoalFilePath))) {
            out.writeObject(savingsGoals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SavingsGoal> loadSavingsGoals() {
        List<SavingsGoal> savingsGoals = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(savingsGoalFilePath))) {
            savingsGoals = (List<SavingsGoal>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return savingsGoals;
    }
}