import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createAccountButton;
    private VirtualBankApp mainApp;
    private AccountStorage accountStorage = new AccountStorage();
    private String selectedUserType; // "Parent" or "Child"
    private ImageIcon backgroundImage = new ImageIcon("login.png"); // 你的背景图片文件路径

    public LoginFrame(String userType, VirtualBankApp mainApp) {
        super("Login - " + userType);
        this.selectedUserType = userType;
        this.mainApp = mainApp;
        setLayout(new BorderLayout());
        setSize(750, 470);
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
        gbc.insets = new Insets(5, 160, 5, 160); // top, left, bottom, right padding

        JPanel emptyPanel = new JPanel();
        emptyPanel.setPreferredSize(new Dimension(20, 10));
        emptyPanel.setOpaque(false);
        backgroundPanel.add(emptyPanel, gbc);

        Font boldFont = new Font("Arial", Font.BOLD, 16);


        JLabel usernameLabel = new JLabel("Username:"); 
        usernameLabel.setFont(boldFont);
        usernameLabel.setPreferredSize(new Dimension(20, 20));
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(20, 30));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(boldFont);
        passwordLabel.setPreferredSize(new Dimension(30, 20));
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(30, 30));

        backgroundPanel.add(usernameLabel, gbc);
        backgroundPanel.add(usernameField, gbc);
        backgroundPanel.add(passwordLabel, gbc);
        backgroundPanel.add(passwordField, gbc);

        JPanel emptyPanel1 = new JPanel();
        emptyPanel1.setPreferredSize(new Dimension(20, 30));
        emptyPanel1.setOpaque(false);
        backgroundPanel.add(emptyPanel1, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccountPage();
            }
        });


        loginButton.setFont(boldFont);
        createAccountButton.setFont(boldFont);
        cancelButton.setFont(boldFont);

        backgroundPanel.add(loginButton, gbc);
        backgroundPanel.add(createAccountButton, gbc);
        backgroundPanel.add(cancelButton, gbc);

        JPanel emptyPanel2 = new JPanel();
        emptyPanel2.setPreferredSize(new Dimension(20, 10));
        emptyPanel2.setOpaque(false);
        backgroundPanel.add(emptyPanel2, gbc);

        getContentPane().add(backgroundPanel, BorderLayout.CENTER);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        List<Account> accounts = accountStorage.loadAccounts(getSelectedUserType().equals("Parent") ? accountStorage.parentAccountsFile : accountStorage.childAccountsFile);

        Account loggedInAccount = accounts.stream()
            .filter(a -> a.getUsername().equals(username) && a.getPassword().equals(password))
            .findFirst().orElse(null);

        if (loggedInAccount != null) {
            System.out.println("Login successful!");
            NowAccount.getInstance().login(loggedInAccount);
            this.setVisible(false);
            mainApp.showUserInterface(selectedUserType);
        } else {
            System.out.println("Login failed: Incorrect username or password.");
            JOptionPane.showMessageDialog(this, "Login failed: Incorrect username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAccountPage() {
        CreateAccountFrame createAccountFrame = new CreateAccountFrame(selectedUserType, this);
        createAccountFrame.setVisible(true);
        this.setVisible(false);
    }

    private void cancel() {
        this.setVisible(false);
        mainApp.setVisible(true);
    }

    public String getSelectedUserType() {
        return selectedUserType;
    }

    public void setSelectedUserType(String selectedUserType) {
        this.selectedUserType = selectedUserType;
    }
}
