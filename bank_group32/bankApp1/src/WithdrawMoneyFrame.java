import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// 自定义的面板类，用于背景图片的绘制
class withdrawBackgroundPanel extends JPanel {
    private Image backgroundImage;

    // 传入背景图片路径
    public withdrawBackgroundPanel(String fileName) {
        try {
            backgroundImage = Toolkit.getDefaultToolkit().getImage(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制背景图片，覆盖整个面板
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}

public class WithdrawMoneyFrame extends JFrame {
    private JTextField amountField;
    private JLabel balanceLabel;
    private Account account;
    private String accounttype;

    public WithdrawMoneyFrame(Account account, String accounttype) {
        super("Withdraw Money");
        this.accounttype = accounttype;
        this.account = NowAccount.getInstance().getCurrentAccount();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 470);
        setLocationRelativeTo(null);

        withdrawBackgroundPanel withdrawBackgroundPanel = new withdrawBackgroundPanel("background_withdraw.png");
        withdrawBackgroundPanel.setLayout(new BorderLayout());
        setContentPane(withdrawBackgroundPanel);

        Font initialFont = new Font("SansSerif", Font.BOLD, 20);

        // Input panel using FlowLayout for horizontal alignment
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(initialFont);
        amountField = new JTextField(20);
        amountField.setFont(initialFont);
        amountField.setPreferredSize(new Dimension(150, 30));

        inputPanel.add(amountLabel);
        inputPanel.add(amountField);

        withdrawBackgroundPanel.add(inputPanel, BorderLayout.NORTH);

        if(accounttype=="current")
        {
            balanceLabel = new JLabel("Current Balance: $" + this.account.getcurrentBalance(), JLabel.CENTER);
        }
        else 
        {
            balanceLabel = new JLabel("Current Balance: $" + this.account.getsavingBalance(), JLabel.CENTER);
        }

        balanceLabel.setFont(initialFont);
        withdrawBackgroundPanel.add(balanceLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(initialFont);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(initialFont);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(cancelButton);
        withdrawBackgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        withdrawButton.addActionListener(e -> withdraw());
        cancelButton.addActionListener(e -> cancel());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustComponentSizes();
            }
        });
    }

    private void adjustComponentSizes() {
        int width = getWidth();
        int height = getHeight();
        Font newFont = new Font("SansSerif", Font.BOLD, Math.max(16, width / 40));
        amountField.setFont(newFont);
        balanceLabel.setFont(newFont);
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                for (Component innerComp : ((JPanel) comp).getComponents()) {
                    if (innerComp instanceof JButton || innerComp instanceof JLabel) {
                        innerComp.setFont(newFont);
                    }
                }
            }
        }
    }

    private void withdraw() {

        if(accounttype=="current")
        {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0 && amount<=this.account.getcurrentBalance()) {
                    this.account.withdraw(amount, "current");
                    balanceLabel.setText("Current Balance: $" + String.format("%.2f", this.account.getcurrentBalance()));
                    JOptionPane.showMessageDialog(this, "Withdrawal successful. New balance: " + this.account.getcurrentBalance(), "Withdrawal Success", JOptionPane.INFORMATION_MESSAGE);
                    // 更新文件中的账户信息
                    AccountStorage storage = new AccountStorage();
                    storage.updateAccountBalance(this.account, storage.childAccountsFile, "current"); // 或 childAccountsFile，具体看账户类型
                }
                else if(amount>this.account.getcurrentBalance()){
                    JOptionPane.showMessageDialog(this, "Insufficient funds! Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(amount<=0)
                {
                    JOptionPane.showMessageDialog(this, "Please enter a valid amount greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0 && amount<=this.account.getsavingBalance()) {
                    this.account.withdraw(amount, "saving");
                    balanceLabel.setText("Current Balance: $" + String.format("%.2f", this.account.getsavingBalance()));
                    JOptionPane.showMessageDialog(this, "Withdrawal successful. New balance: " + this.account.getsavingBalance(), "Withdrawal Success", JOptionPane.INFORMATION_MESSAGE);
                    // 更新文件中的账户信息
                    AccountStorage storage = new AccountStorage();
                    storage.updateAccountBalance(this.account, storage.childAccountsFile, "saving"); // 或 childAccountsFile，具体看账户类型
                }
                else if(amount>this.account.getsavingBalance()){
                    JOptionPane.showMessageDialog(this, "Insufficient funds! Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(amount<=0)
                {
                    JOptionPane.showMessageDialog(this, "Please enter a valid amount greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cancel() {
        this.setVisible(false);
        // 这里可以根据需要添加代码返回到父界面
    }
}
