/**
 * Author: Kexin Chen
 * Date: 2024-05-05
 */

import javax.swing.*;
import java.awt.*;

class CompletedFrame extends JFrame {
    public CompletedFrame() {
        super("Transaction Completed");
        setup_frame();
        init_components();
    }

    private void setup_frame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); 
    }

    private void init_components() {
        withdrawBackgroundPanel bg_panel = new withdrawBackgroundPanel("background_deposit.png");
        bg_panel.setLayout(new BorderLayout());
        setContentPane(bg_panel);

        JLabel completed_label = new JLabel("        Transaction Completed!");
        completed_label.setFont(new Font("SansSerif", Font.BOLD, 20));
        completed_label.setHorizontalAlignment(JLabel.LEFT); 

        JPanel label_panel = new JPanel(new BorderLayout());
        label_panel.setOpaque(false); 
        int left_padding = bg_panel.getWidth() / 4; 
        label_panel.setBorder(BorderFactory.createEmptyBorder(0, left_padding, 0, 0)); 
        label_panel.add(completed_label, BorderLayout.WEST); 

        bg_panel.add(label_panel, BorderLayout.CENTER); 

        JButton confirm_button = new JButton("Confirm");
        confirm_button.setFont(new Font("SansSerif", Font.BOLD, 20));
        confirm_button.addActionListener(e -> dispose());
        JPanel button_panel = new JPanel();
        button_panel.add(confirm_button);
        bg_panel.add(button_panel, BorderLayout.SOUTH);
    }
}
