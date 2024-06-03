import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SavingsGoalCreationPanel extends JDialog {
    private SavingsGoalStorage savingsGoalStorage;
    private Account account;
    private JTextField targetSavingsField;
    private JTextField purposeField;
    private SavingsGoalPanel savingsGoalPanel; // 保存对储蓄目标面板的引用
    private String accounttype;
    public SavingsGoalCreationPanel(SavingsGoalStorage savingsGoalStorage,SavingsGoalPanel savingsGoalPanel, String accounttype) {
        super();
        this.accounttype = accounttype;
        this.savingsGoalStorage = savingsGoalStorage;
        this.account = NowAccount.getInstance().getCurrentAccount();
        this.savingsGoalPanel = savingsGoalPanel; // 传递储蓄目标面板的引用
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        targetSavingsField = new JTextField(10);
        purposeField = new JTextField(20);

        inputPanel.add(new JLabel("Target Savings:"));
        inputPanel.add(targetSavingsField);
        inputPanel.add(new JLabel("Purpose:"));
        inputPanel.add(purposeField);

        JButton createButton = new JButton("Create Goal");
        createButton.addActionListener(this::createGoal);

        add(inputPanel, BorderLayout.CENTER);
        add(createButton, BorderLayout.SOUTH);

        setTitle("Create Savings Goal");
        setSize(750, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createGoal(ActionEvent e) {
        try {
            String targetSavingsStr = targetSavingsField.getText();
            String purposeStr = purposeField.getText();
            if (targetSavingsStr.isEmpty() || purposeStr.isEmpty()) {
                throw new IllegalArgumentException("Target savings and purpose cannot be empty.");
            }

            double targetSavings = Double.parseDouble(targetSavingsStr);
            int id = (int) (Math.random() * 1000); // 简单的ID生成策略

            // 创建储蓄目标
            SavingsGoal newGoal = new SavingsGoal(id, targetSavings, purposeStr);
            List<SavingsGoal> goals = savingsGoalStorage.loadSavingsGoals(); // 加载现有目标
            System.out.println(accounttype);
            newGoal.setCurrentSavings(this.account.getcurrentBalance());
            //System.out.println(this.account.getcurrentBalance());
            goals.add(newGoal); // 添加新目标
            savingsGoalStorage.saveSavingsGoals(goals); // 保存更新后的目标列表

            // 关闭创建对话框
            dispose();

            // 创建储蓄目标后，刷新储蓄目标面板
            savingsGoalPanel.loadSavingsGoals(); // 刷新列表
            JOptionPane.showMessageDialog(this, "Savings goal created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // 关闭对话框
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter a valid target savings amount.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, iae.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
