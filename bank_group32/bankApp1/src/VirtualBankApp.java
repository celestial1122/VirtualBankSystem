/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: chengxiang.Huang
 * Date: 2024-05-04
 * Time: 14:34
 */
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

public class VirtualBankApp extends JFrame {
    private TaskStorage taskStorage = new TaskStorage("task.txt");

    public VirtualBankApp() {
        setTitle("Virtual Bank Application for Kids");
        setSize(740, 470);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        BackgroundPanel BackgroundPanel = new BackgroundPanel("start.png");
        BackgroundPanel.setLayout(null);
        setContentPane(BackgroundPanel);

        // Use a border layout for the frame
        setLayout(new BorderLayout());

        // Empty space panel to move userSelectionPanel down
        JPanel emptySpacePanel = new JPanel();
        emptySpacePanel.setPreferredSize(new Dimension(50, 250)); // Adjust height as needed
        emptySpacePanel.setOpaque(false);

        // Create a panel for the radio buttons with a flow layout
        JPanel userSelectionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //userSelectionPanel.setBorder(BorderFactory.createTitledBorder("Select User Type"));
        userSelectionPanel.setOpaque(false);

        // Create radio buttons with group
        ButtonGroup group = new ButtonGroup();
        JRadioButton parentButton = new JRadioButton("Parent", true);
        JRadioButton childButton = new JRadioButton("Child");

        
        JPanel gapPanel = new JPanel();
        gapPanel.setPreferredSize(new Dimension(50, 10));
        gapPanel.setOpaque(false);

        Dimension buttonSize = new Dimension(120, 50); // Adjust width and height as needed
        parentButton.setPreferredSize(buttonSize);
        childButton.setPreferredSize(buttonSize);

        
        group.add(parentButton);
        group.add(childButton);

        userSelectionPanel.add(parentButton);
        userSelectionPanel.add(gapPanel); 
        userSelectionPanel.add(childButton);

        // Add the empty space panel above userSelectionPanel
        add(emptySpacePanel, BorderLayout.NORTH);
        // Add the userSelectionPanel to the frame's center
        add(userSelectionPanel, BorderLayout.CENTER);

        // Create and configure the login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parentButton.isSelected()) {
                    showParentInterface();
                } else if (childButton.isSelected()) {
                    showChildInterface();
                }
            }
        });

        // Create a panel for the login button with a flow layout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);

        // Add the button panel to the frame's south region
        add(buttonPanel, BorderLayout.SOUTH);

        // Set a nicer font for all components
        Font defaultFont = new Font("SansSerif", Font.BOLD, 18);
        parentButton.setFont(defaultFont);
        childButton.setFont(defaultFont);
        loginButton.setFont(defaultFont);

        // Customize the look and feel if desired
        // For example, use the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showParentInterface() {
        showLoginFrame("Parent");

    }

    private void showChildInterface() {
        showLoginFrame("Child");
    }

    private void showParentOrChildInterface(String userType) {
        LoginFrame loginFrame = new LoginFrame(userType, this);
        loginFrame.setVisible(true);
        this.setVisible(false); // 隐藏 VirtualBankApp 主界面
    }

    // 用于从主界面选择用户类型的按钮事件
    public void onUserTypeSelected(String userType) {
        if ("Parent".equals(userType)) {
            showParentOrChildInterface("Parent");
        } else if ("Child".equals(userType)) {
            showParentOrChildInterface("Child");
        }
    }
    public void showUserInterface(String userType) {
        if ("Parent".equals(userType)) {

            // 显示家长界面
            ParentPanel parentPanel = new ParentPanel(this);
            parentPanel.setVisible(true);
            setVisible(false);
        } else if ("Child".equals(userType)) {
            // 显示孩子界面
            // ChildPanel childPanel = new ChildPanel();
            AccountSelect accountSelect = new AccountSelect();
            accountSelect.setVisible(true);
            dispose(); // 关闭当前窗口
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VirtualBankApp app = new VirtualBankApp();
                app.setVisible(true);
            }
        });
    }

    // 新增的方法，用于显示登录界面
    private void showLoginFrame(String userType) {
        // 隐藏选择用户类型的界面
        VirtualBankApp.this.setVisible(false);
        // 创建登录界面，传递用户类型和对 VirtualBankApp 的引用
        LoginFrame loginFrame = new LoginFrame(userType, this);
        loginFrame.setVisible(true);
    }

}