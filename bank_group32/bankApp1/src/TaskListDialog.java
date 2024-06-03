import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TaskListDialog extends JDialog {
    private List<Task> tasks;
    private JTable taskTable;
    private TaskStorage taskStorage;
    private Account account;
    private TransactionStorage transactionStorage;
    private String accounttype;

    public TaskListDialog(TaskStorage taskStorage, String accounttype) {
        this.taskStorage = taskStorage;
        this.accounttype = accounttype;
        initializeUI();
        this.account = NowAccount.getInstance().getCurrentAccount();
        this.transactionStorage = new TransactionStorage("transactions.txt");
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // 添加背景图片
        ImageIcon backgroundIcon = new ImageIcon("task_background.png");
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        tasks = taskStorage.loadTasks(); // 加载任务列表
        String[] columnNames = {"Name", "Reward", "Accept Task", "Finish"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Task task : tasks) {
            Object[] rowData = new Object[]{task.getName(), "$" + task.getReward(), null, null};
            if (!task.isAcceptedByChild()) {
                rowData[2] = "Accept Task";
            }
            if (!task.isCompleted() && task.isAcceptedByChild()) {
                rowData[3] = "Finish";
            }
            model.addRow(rowData);
        }

        taskTable = new JTable(model);
        taskTable.getColumn("Accept Task").setCellRenderer(new ButtonRenderer());
        taskTable.getColumn("Accept Task").setCellEditor(new ButtonEditor(new JCheckBox()));
        taskTable.getColumn("Finish").setCellRenderer(new ButtonRenderer());
        taskTable.getColumn("Finish").setCellEditor(new ButtonEditor(new JCheckBox()));

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

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        private String label;

        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button = new JButton();
            label = value.toString();
            button.setText(label);
            isPushed = true;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (label.equals("Accept Task")) {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to accept this task?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            tasks.get(row).setAcceptedByChild(true);
                            taskStorage.saveTasks(tasks);
                            refreshTable();
                        }
                    } else if (label.equals("Finish")) {
                        if (!tasks.get(row).isAcceptedByChild()) {
                            JOptionPane.showMessageDialog(null, "You have not accepted this task yet.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            tasks.get(row).setCompleted(true);
                            double reward = tasks.get(row).getReward();
                            if(accounttype=="current") account.taskfinished(reward, "current");
                            else account.taskfinished(reward, "saving"); // 更新任务奖励到账户余额

                            AccountStorage storage = new AccountStorage();
                            if(accounttype=="current") storage.updateAccountBalance(account, storage.childAccountsFile, accounttype);
                            else  storage.updateAccountBalance(account, storage.childAccountsFile, accounttype);// 更新文件中的账户信息
                            taskStorage.saveTasks(tasks);
                            refreshTable();
                        }
                    }
                    fireEditingStopped(); // 停止编辑
                }
            });
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                System.out.println(label + " clicked");
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) taskTable.getModel();
        model.setRowCount(0); // 清空表格模型中的数据
        model.fireTableDataChanged();
        tasks = taskStorage.loadTasks(); // 加载任务列表
        // System.out.print(tasks);
        for (Task task : tasks) {
            Object[] rowData = new Object[]{task.getName(), "$" + task.getReward(), null, null};
            if (!task.isAcceptedByChild()) {
                rowData[2] = "Accept Task";
            }
            if (!task.isCompleted() && task.isAcceptedByChild()) {
                rowData[3] = "Finish";
            }
            model.addRow(rowData);
        }
        
        // 通知表格模型数据已更改，刷新表格显示
        model.fireTableDataChanged();
    }
    
    
}
