import javax.swing.*;
import java.awt.*;

public class ShowBalanceFrame extends JFrame{
    private Account a;


    public ShowBalanceFrame(String accounttype){
        // 设置窗口的宽度和高度
        final int WINDOW_WIDTH = 750;
        final int WINDOW_HEIGHT = 470;
    
        this.a = NowAccount.getInstance().getCurrentAccount();
    
        setTitle("Balance");
        ImageIcon bg = new ImageIcon("background_balance(1).png");
        setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); // 更新窗口大小
        JLabel label = new JLabel(bg);
        label.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        JPanel jp = (JPanel)this.getContentPane();
        jp.setOpaque(false);
        jp.setLayout(null);
        
        setSize(750, 470);
        setLocationRelativeTo(null);

        JButton backButton = new JButton("Return");
        backButton.setFont(new Font("Balance", Font.BOLD, 20));
        //backButton.setPreferredSize(returnbuttonSize);
        backButton.setSize(130, 40);
        backButton.setLocation(450, 300);
        backButton.addActionListener(e -> goBack());
        label.add(backButton);
    
        JLabel title = new JLabel("Balance");
        title.setFont(new Font("Balance", Font.BOLD, 45));
        title.setSize(700, 60);
        title.setLocation(450, 50); // 居中位置调整
        label.add(title);
    
        String account = accounttype + " account";
        JLabel title1 = new JLabel(account);
        title1.setFont(new Font("Arial", Font.BOLD, 30)); // 修正字体名称，字体名应与实际存在的字体相符
        title1.setSize(700, 60);
        title1.setLocation(450, 130); // 调整位置
        label.add(title1);
    
        JLabel title2;
        if (accounttype.equals("current")) { // 使用.equals()进行字符串比较
            title2 = new JLabel("$" + a.getcurrentBalance());
        } else {
            title2 = new JLabel("$" + a.getsavingBalance());
        }
        title2.setFont(new Font("Arial", Font.BOLD, 40));
        title2.setForeground(Color.RED);
        title2.setSize(700, 80);
        title2.setLocation(450, 200); // 调整位置
        label.add(title2);
    
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void goBack()
    {
        dispose();
    }
}    
