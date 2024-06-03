import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TaskCreationPanel extends JDialog {
    private Task task;
    private TaskStorage taskStorage; // 实例化 TaskStorage 用于文件操作

    public TaskCreationPanel(TaskStorage taskStorage) {
        super();
        this.taskStorage = taskStorage;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 创建背景标签，并设置背景图片
        ImageIcon backgroundIcon = new ImageIcon("create_task.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());

        // 创建透明的面板来放置输入组件
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false); // 设置面板为透明
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // 创建任务名标签和文本框
        inputPanel.add(new JLabel("Task Name:"), gbc);
        gbc.gridy++;
        final JTextField taskNameField = new JTextField(20);
        inputPanel.add(taskNameField, gbc);

        gbc.gridy++;
        inputPanel.add(new JLabel("Reward:"), gbc);
        gbc.gridy++;
        final JTextField rewardField = new JTextField(20);
        inputPanel.add(rewardField, gbc);

        // 创建按钮并添加到面板
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        JButton createButton = new JButton("Create Task");
        createButton.addActionListener(e -> createTask(taskNameField.getText(), rewardField.getText()));
        inputPanel.add(createButton, gbc);

        backgroundLabel.add(inputPanel, BorderLayout.CENTER);

        add(backgroundLabel, BorderLayout.WEST);

        setTitle("Create Task");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createTask(String name, String reward) {
        try {
            double rewardAmount = Double.parseDouble(reward);
            // 任务ID生成逻辑，这里使用一个随机数作为示例
            int taskId = (int) (Math.random() * 10000);
            task = new Task(taskId, name, rewardAmount);
            // 将任务添加到任务存储中
            taskStorage.saveTask(task);
            // 刷新任务列表（假设有一个全局的任务列表）
            // updateTaskList(task);

            JOptionPane.showOptionDialog(this, "Task created successfully!", "Success", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Confirm"}, "Confirm");


            dispose(); // 关闭对话框
        } catch (NumberFormatException e) {
            JOptionPane.showOptionDialog(this, "Please enter a valid reward amount.", "Error", JOptionPane.ERROR_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Confirm"}, "Confirm");
        }
    }

    // 获取创建的任务对象
    public Task getTask() {
        return task;
    }
}