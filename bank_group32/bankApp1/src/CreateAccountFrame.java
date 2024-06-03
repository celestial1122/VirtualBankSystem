/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: chengxiang.Huang
 * Date: 2024-05-04
 * Time: 15:22
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField;
    private LoginFrame loginFrame; // 添加对登录窗口的引用

    private AccountStorage accountStorage = new AccountStorage();
    private String selectedUserType;
    private ImageIcon backgroundImage = new ImageIcon("login.png"); // 你的背景图片文件路径

    public CreateAccountFrame(String userType, LoginFrame loginFrame) {
        super("Create Account - " + userType);
        this.selectedUserType = userType;
        setLayout(new BorderLayout());
        this.loginFrame = loginFrame;
        setSize(840, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
        gbc.insets = new Insets(1, 130, 5, 130); // top, left, bottom, right padding

        Font boldFont = new Font("Arial", Font.BOLD, 16);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(boldFont);
        usernameLabel.setPreferredSize(new Dimension(30, 20));
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(30, 30));
        backgroundPanel.add(usernameLabel, gbc);
        backgroundPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(boldFont);
        passwordLabel.setPreferredSize(new Dimension(20, 20));
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(20, 30));
        backgroundPanel.add(passwordLabel, gbc);
        backgroundPanel.add(passwordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(boldFont);
        confirmPasswordLabel.setPreferredSize(new Dimension(20, 20));
        passwordConfirmField = new JPasswordField(20);
        passwordConfirmField.setPreferredSize(new Dimension(20, 30));
        backgroundPanel.add(confirmPasswordLabel, gbc);
        backgroundPanel.add(passwordConfirmField, gbc);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        createAccountButton.setFont(boldFont);
        backgroundPanel.add(createAccountButton, gbc);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        cancelButton.setFont(boldFont);
        backgroundPanel.add(cancelButton, gbc);

        getContentPane().add(backgroundPanel, BorderLayout.CENTER);
    }

    private void createAccount() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmedPassword = new String(passwordConfirmField.getPassword());

        // 确认密码是否与之前输入的一致
        if (!password.equals(confirmedPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.", "Password Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Passwords do not match. Please try again.");
            return;
        }

        // 检查用户名是否已存在
        if (accountStorage.loadAccounts(getSelectedUserType().equals("Parent") ? accountStorage.parentAccountsFile : accountStorage.childAccountsFile).stream().anyMatch(a -> a.getUsername().equals(username))) {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different username.", "Username Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        // 创建新账号
        Account newAccount = new Account(username, password);
        accountStorage.saveNewAccount(newAccount, getSelectedUserType().equals("Parent") ? accountStorage.parentAccountsFile : accountStorage.childAccountsFile);
        JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Account created successfully!");
    }

    private void cancel() {
        // 取消创建账号，返回登录界面或关闭创建账号界面
        this.setVisible(false);
        // 这里可以添加代码返回到登录界面，或者新建一个登录界面实例
        loginFrame.setVisible(true); // 重新显示登录界面
    }

    // Getter and setter for selectedUserType
    public String getSelectedUserType() {
        return selectedUserType;
    }

    public void setSelectedUserType(String selectedUserType) {
        this.selectedUserType = selectedUserType;
    }

    // 可以根据需要添加 main 方法或其他方法
}