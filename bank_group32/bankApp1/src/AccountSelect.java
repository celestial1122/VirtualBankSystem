import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AccountSelect extends JFrame { // 修改继承关系为JFrame

    private ImageIcon backgroundImage = new ImageIcon("login.png");

    public AccountSelect() {
        super("Account"); // 设置窗口标题

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
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.insets = new Insets(5, 160, 5, 160);

        
        JButton CurrentButton = new JButton("Current Account");
        CurrentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current();
            }
        });

        JButton SavingButton = new JButton("Saving Account");
        SavingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saving();
            }
        });

        JButton Return = new JButton("Return");
        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });


        Dimension buttonSize = new Dimension(200, 50);
        Dimension returnbuttonSize = new Dimension(120, 35);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        CurrentButton.setFont(buttonFont);
        CurrentButton.setPreferredSize(buttonSize);
        SavingButton.setFont(buttonFont);
        SavingButton.setPreferredSize(buttonSize);
        Return.setFont(buttonFont);
        Return.setPreferredSize(returnbuttonSize);
        gbc.gridy = 0;
        backgroundPanel.add(CurrentButton, gbc);
        gbc.gridy = 1;
        backgroundPanel.add(SavingButton, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 160, 5, 160);
        backgroundPanel.add(Return, gbc);
        getContentPane().add(backgroundPanel, BorderLayout.CENTER);
    }

    private void current()
    {
        ChildPanel childPanel = new ChildPanel();
        childPanel.setVisible(true);
        this.setVisible(false);
    }

    private void saving()
    {
        ChildPanel1 childPanel1 = new ChildPanel1();
        childPanel1.setVisible(true);
        this.setVisible(false);
    }

    private void goBack() {
        dispose();
        VirtualBankApp mainApp = new VirtualBankApp();
        LoginFrame loginFrame = new LoginFrame("Child", mainApp);
        loginFrame.setVisible(true);
    }

}
