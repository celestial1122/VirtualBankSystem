/**
 * Author: Kexin Chen
 * Date: 2024-05-05
 */

 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
 import javax.imageio.ImageIO;
 import java.io.File;
 import java.io.IOException;
 
 class depositBackgroundPanel extends JPanel {
     private Image bg_img;
 
     public depositBackgroundPanel(String file_path) {
         try {
             bg_img = ImageIO.read(new File(file_path));
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 
     @Override
     protected void paintComponent(Graphics img) {
         super.paintComponent(img);
         if (bg_img != null) {
             img.drawImage(bg_img, 0, 0, this.getWidth(), this.getHeight(), this);
         }
     }
 }
 
 public class DepositMoneyFrame extends JFrame {
     private JTextField input_field;
     private JLabel bal_label;
     private Account account;
     private String accounttype;
 
     public DepositMoneyFrame(String accounttype) {
         super("Deposit Money");
         this.accounttype = accounttype;
         initializeAccount();
         setupFrame();
         initComponents();
         setupListeners();
         setSize(750, 470);
         setLocationRelativeTo(null);
     }
 
     private void initializeAccount() {
         this.account = NowAccount.getInstance().getCurrentAccount();
     }
 
     private void setupFrame() {
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         setSize(500, 300);
     }
 
     private void initComponents() {
         setContentPane(createBackgroundPanel());
     }
 
     private withdrawBackgroundPanel createBackgroundPanel() {
         withdrawBackgroundPanel bg_panel = new withdrawBackgroundPanel("background_deposit.png");
         bg_panel.setLayout(new BorderLayout());
 
         bg_panel.add(createInputPanel(), BorderLayout.NORTH);
         bg_panel.add(createBalancePanel(), BorderLayout.CENTER);
         bg_panel.add(createButtonPanel(), BorderLayout.SOUTH);
 
         return bg_panel;
     }
 
     private JPanel createInputPanel() {
         JPanel input_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         JLabel input_label = new JLabel("Amount:");
         input_label.setFont(new Font("SansSerif", Font.BOLD, 20));
         input_field = new JTextField(20);
         input_field.setFont(new Font("SansSerif", Font.BOLD, 20));
         input_field.setPreferredSize(new Dimension(150, 30));
         input_panel.add(input_label);
         input_panel.add(input_field);
         return input_panel;
     }
 
     private JPanel createBalancePanel() {
         JPanel bal_panel = new JPanel(new BorderLayout());
         bal_panel.setOpaque(false);
         if(accounttype=="current") bal_label = new JLabel("Current Balance: $" + this.account.getcurrentBalance());
         else bal_label = new JLabel("Current Balance: $" + this.account.getsavingBalance());
         bal_label.setFont(new Font("SansSerif", Font.BOLD, 20));
         bal_label.setHorizontalAlignment(JLabel.LEFT);
         bal_panel.add(bal_label, BorderLayout.WEST);
         int padding = (int) (getWidth() * 0.25);
         bal_panel.setBorder(BorderFactory.createEmptyBorder(0, padding, 0, 0));
         return bal_panel;
     }
 
     private JPanel createButtonPanel() {
         JPanel button_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
         JButton deposit_button = new JButton("Deposit");
         JButton cancel_button = new JButton("Cancel");
         deposit_button.setFont(new Font("SansSerif", Font.BOLD, 20));
         cancel_button.setFont(new Font("SansSerif", Font.BOLD, 20));
         button_panel.add(deposit_button);
         button_panel.add(cancel_button);
 
         deposit_button.addActionListener(e -> deposit());
         cancel_button.addActionListener(e -> cancel());
 
         return button_panel;
     }
 
     private void setupListeners() {
         addComponentListener(new ComponentAdapter() {
             @Override
             public void componentResized(ComponentEvent e) {
                 adjustComponentSizes();
             }
         });
     }
 
     private void adjustComponentSizes() {
         int width = getWidth();
         // int height = getHeight();
         Font use_font = new Font("SansSerif", Font.BOLD, Math.max(16, width / 40));
         input_field.setFont(use_font);
         bal_label.setFont(use_font);
         for (Component comp : getContentPane().getComponents()) {
             if (comp instanceof JPanel) {
                 for (Component in_comp : ((JPanel) comp).getComponents()) {
                     if (in_comp instanceof JButton || in_comp instanceof JLabel) {
                         in_comp.setFont(use_font);
                     }
                 }
             }
         }
     }
 
     private void deposit() {

        if(accounttype=="current")
        {
            try {
                double amount = Double.parseDouble(input_field.getText());
                if (amount > 0) {
                    System.out.print(this.account.getcurrentBalance());
                    System.out.print(String.format("%.2f", this.account.getcurrentBalance()));
                    this.account.deposit(amount, "current");
                    String aa = String.format("%.2f", this.account.getcurrentBalance());
                    //bal_label.setText("Current Balance: $" +aa);
                    JOptionPane.showMessageDialog(this, "Deposit successful. New balance: $" + this.account.getcurrentBalance(), "Deposit Success", JOptionPane.INFORMATION_MESSAGE);
                    AccountStorage storage = new AccountStorage();
                    storage.updateAccountBalance(this.account, storage.childAccountsFile, "current");
                    
                    // show transaction completed interface
                    //new CompletedFrame().setVisible(true);
                    this.dispose(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a valid amount greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            try {
                double amount = Double.parseDouble(input_field.getText());
                if (amount > 0) {
                    this.account.deposit(amount, "saving");
                    bal_label.setText("Current Balance: $" + String.format("%.2f", this.account.getsavingBalance()));
                    JOptionPane.showMessageDialog(this, "Deposit successful. New balance: $" + this.account.getsavingBalance(), "Deposit Success", JOptionPane.INFORMATION_MESSAGE);
                    AccountStorage storage = new AccountStorage();
                    storage.updateAccountBalance(this.account, storage.childAccountsFile, "saving");
                    
                    // show transaction completed interface
                    //new CompletedFrame().setVisible(true);
                    this.dispose(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a valid amount greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
     }
     
 
     private void cancel() {
         setVisible(false);
     }
 }
 