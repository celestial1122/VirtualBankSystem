/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: chengxiang.Huang
 * Date: 2024-05-04
 * Time: 15:49
 */

import javax.swing.*;
import java.awt.*;

public class ParentPanel extends JFrame {
    private TaskStorage taskStorage;
    private VirtualBankApp mainApp;
    private ImageIcon backgroundImage = new ImageIcon("login.png");

    public ParentPanel(VirtualBankApp mainApp) {

        this.mainApp = mainApp;

        taskStorage = new TaskStorage("task.txt");
        
        JPanel backgroundPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 160, 5, 160);

        Dimension buttonSize = new Dimension(200, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        // 添加组件，例如任务设置按钮
        JButton createTaskButton = new JButton("Create Task");
        createTaskButton.addActionListener(e -> createTask());
        // 查看任务按钮
        JButton viewTasksButton = new JButton("View Tasks");
        viewTasksButton.addActionListener(e -> viewTasks());

        JButton ReturnButton = new JButton("Return");
        ReturnButton.addActionListener(e -> goBack());

        createTaskButton.setFont(buttonFont);
        createTaskButton.setPreferredSize(buttonSize);
        viewTasksButton.setFont(buttonFont);
        viewTasksButton.setPreferredSize(buttonSize);;
        ReturnButton.setFont(buttonFont);
        ReturnButton.setPreferredSize(buttonSize);
        // 将按钮添加到面板中
        backgroundPanel.add(createTaskButton, gbc);
        backgroundPanel.add(viewTasksButton, gbc);
        backgroundPanel.add(ReturnButton, gbc);
        add(backgroundPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 470);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createTask() {
        // 显示任务创建对话框
        TaskCreationPanel taskCreationPanel = new TaskCreationPanel(taskStorage);
        taskCreationPanel.pack();
        taskCreationPanel.setLocationRelativeTo(null);
        taskCreationPanel.setVisible(true);
    }

    private void viewTasks() {
        // 显示任务查看对话框
        ParentTaskListPanel parentTaskListPanel = new ParentTaskListPanel(taskStorage);
        parentTaskListPanel.pack();
        parentTaskListPanel.setLocationRelativeTo(null);
        parentTaskListPanel.setVisible(true);
    }

    private void goBack() {
        dispose();
        LoginFrame loginFrame = new LoginFrame("Parent", this.mainApp);
        loginFrame.setVisible(true);
    }
}
