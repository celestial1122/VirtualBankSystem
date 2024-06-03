import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ParentTaskListPanel extends JDialog {
    private TaskStorage taskStorage;
    private JTable taskTable;
    private List<Task> tasks;

    public ParentTaskListPanel(TaskStorage taskStorage) {
        super();
        this.taskStorage = taskStorage;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 添加背景图片
        ImageIcon backgroundIcon = new ImageIcon("task_background.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        tasks = taskStorage.loadTasks(); // 加载任务列表
        String[] columnNames = {"ID", "Name", "Reward", "Accepted", "Completed"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Task task : tasks) {
            model.addRow(new Object[]{task.getId(), task.getName(), "$" + task.getReward(), task.isAcceptedByChild(), task.isCompleted()});
        }

        taskTable = new JTable(model);

        // 创建一个滚动窗格，并将表格添加到其中
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        backgroundLabel.add(scrollPane);
        add(backgroundLabel, BorderLayout.CENTER);

        setTitle("View Tasks");
        setSize(750, 470); // 设置对话框的大小
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
