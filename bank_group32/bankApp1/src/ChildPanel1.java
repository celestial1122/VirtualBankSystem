import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ChildPanel1 extends JFrame {

    private Account account; // 假设有一个 Account 变量来管理孩子的登录帐户信息
    private JButton viewTasksButton;
    private TaskStorage taskStorage;
    private TransactionStorage transactionStorage;
    private SavingsGoalStorage savingsGoalStorage;
    private ImageIcon backgroundImage = new ImageIcon("login.png");

    public ChildPanel1() {
        super("Child Saving Account");

        this.account = NowAccount.getInstance().getCurrentAccount();
        transactionStorage = new TransactionStorage("transactions.txt");
        savingsGoalStorage = new SavingsGoalStorage("saving_goals.txt");
        taskStorage = new TaskStorage("task.txt");

        JPanel backgroundPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10); // 调整插入以获得更好的间距

        Dimension buttonSize = new Dimension(200, 50);
        Dimension returnbuttonSize = new Dimension(120, 35);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        // 创建和定位按钮
        JButton[] buttons = { 
            new JButton("Deposit Money"),
            new JButton("Balance"), 
            new JButton("View Tasks"),
            new JButton("View Transactions")
        };

        ActionListener[] actions = {
            e -> openDepositFrame(),
            e -> openBalanceFrame(),
            e -> showTaskDialog(),
            e -> showTransactions()
        };

        int index = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                if(row==2 && col==1) break;
                gbc.gridx = col;
                gbc.gridy = row;
                buttons[index].setPreferredSize(buttonSize);
                buttons[index].setFont(buttonFont);
                buttons[index].addActionListener(actions[index]);
                backgroundPanel.add(buttons[index], gbc);
                index++;
            }
        }

        // 添加返回按钮到新的 GridBagConstraints 位置
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // 让返回按钮横跨两列
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;

        JButton backButton = new JButton("Return");
        backButton.setFont(buttonFont);
        backButton.setPreferredSize(returnbuttonSize);
        backButton.addActionListener(e -> goBack());
        backgroundPanel.add(backButton, gbc);

        setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 470);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openBalanceFrame() {
        ShowBalanceFrame balanceFrame = new ShowBalanceFrame("saving");
        balanceFrame.setVisible(true);
    }

    private void showTaskDialog() {
        TaskListDialog taskListDialog = new TaskListDialog(taskStorage, "saving");
        taskListDialog.setVisible(true);
        taskListDialog.setLocationRelativeTo(null);
    }

    private void openWithdrawFrame() {
        WithdrawMoneyFrame withdrawFrame = new WithdrawMoneyFrame(account, "saving");
        withdrawFrame.setVisible(true);
    }

    private void openDepositFrame() {
        DepositMoneyFrame depositFrame = new DepositMoneyFrame("saving");
        depositFrame.setVisible(true);
    }

    private void showSavingsGoals() {
        SavingsGoalPanel savingsGoalPanel = new SavingsGoalPanel(savingsGoalStorage, "saving");
        JDialog dialog = new JDialog();
        dialog.add(savingsGoalPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showTransactions() {
        List<Transaction> transactions;
        try {
            transactions = transactionStorage.loadTransactions();
        } catch (Exception e) {
            System.err.println("Error loading transactions: " + e.getMessage());
            transactions = new ArrayList<>();
        }
    
        StringBuilder transactionInfo = new StringBuilder("<html><div style='text-align: left; padding-left: 20px; padding-top: 20px;'>");
        for (Transaction transaction : transactions) {
            transactionInfo.append(transaction.toString()).append("<br>");
        }
        transactionInfo.append("</div></html>");
    
        JLabel recordsTextLabel = new JLabel("<html><div style='padding-left: 20px; padding-top: 15px;'>Transaction record</div></html>");
        recordsTextLabel.setFont(new Font("Arial", Font.BOLD, 20));
    
        JLabel textLabel = new JLabel(transactionInfo.toString());
        textLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        textLabel.setVerticalAlignment(JLabel.TOP);
    
        JScrollPane scrollPane = new JScrollPane(textLabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(375, 200));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
    
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(closeButton);
            topFrame.dispose();
        });
    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 160, 23, 0));
        buttonPanel.add(closeButton);
        // buttonPanel.setOpaque(false);
        buttonPanel.setBackground(Color.WHITE);
    
        JPanel lowerButtonPanel = new JPanel();
        lowerButtonPanel.setLayout(new BorderLayout());
        lowerButtonPanel.add(buttonPanel, BorderLayout.WEST);
        lowerButtonPanel.setBackground(Color.WHITE);
        lowerButtonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 100)); // Adjusting for the new button position
    
        JPanel panel = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("show_transaction.png");
                g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
    
        panel.add(recordsTextLabel, BorderLayout.NORTH);
    
        JPanel scrollPaneContainer = new JPanel(new BorderLayout());
        scrollPaneContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0)); // Adjusting border size
        scrollPaneContainer.add(scrollPane);
        scrollPaneContainer.setOpaque(false);
        panel.add(scrollPaneContainer, BorderLayout.WEST);
    
        panel.add(lowerButtonPanel, BorderLayout.SOUTH);
    
        JFrame frame = new JFrame("Transaction History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setSize(new Dimension(750, 470));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void goBack() {
        dispose();
        AccountSelect select = new AccountSelect();
        select.setVisible(true);
    }
}
