import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SavingsGoalPanel extends JPanel {
    private SavingsGoalStorage savingsGoalStorage;
    private Account account; // Used to get current savings
    private ImageIcon background;
    private String accounttype;
    private JPanel goalsPanel; // Panel for displaying goals
    private JScrollPane scrollPane; // Scrollable pane for goals

    public SavingsGoalPanel(SavingsGoalStorage savingsGoalStorage, String accounttype) {
        this.accounttype = accounttype;
        this.savingsGoalStorage = savingsGoalStorage;
        this.account = NowAccount.getInstance().getCurrentAccount();
        this.background = new ImageIcon(getClass().getResource("saving_goal.png"));
        if (background.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Error loading background image.");
            return;
        }
        initializeUI();
        loadSavingsGoals();
    }

    private void initializeUI() {
        setLayout(null); // Use absolute positioning
        setPreferredSize(new Dimension(750, 470));
    
        // Create the goals panel and scroll pane
        goalsPanel = new JPanel();
        goalsPanel.setLayout(null);
        goalsPanel.setPreferredSize(new Dimension(350, 400)); // Adjusted width to match half of the scroll pane's width
        goalsPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(goalsPanel);
        scrollPane.setBounds(30, 75, 400, 330); // Width adjusted to half of 750px (half of the panel width plus a little margin)
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(Color.WHITE);
    
        JButton addButton = new JButton("Add New Goal");
        addButton.setBounds(50, 30, 150, 30);
        addButton.addActionListener(e -> createSavingsGoal());
        add(addButton);
        add(scrollPane);
    }
    

    private void createSavingsGoal() {
        SavingsGoalCreationPanel creationPanel = new SavingsGoalCreationPanel(savingsGoalStorage, this, accounttype);
        creationPanel.pack();
        creationPanel.setLocationRelativeTo(this);
        creationPanel.setVisible(true);
        creationPanel.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                refreshPanel(); // Refresh the panel once the creation panel is closed
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background.getImageLoadStatus() == MediaTracker.COMPLETE) {
            g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Your Savings Goals", 50, 30);
        }
    }

    public void loadSavingsGoals() {
        List<SavingsGoal> savingsGoals = savingsGoalStorage.loadSavingsGoals();
        displayGoals(savingsGoals);
    }

    private void displayGoals(List<SavingsGoal> savingsGoals) {
        goalsPanel.removeAll(); // Clear previous goals
        int y = 10; // Start position for goals in the goalsPanel
        for (SavingsGoal goal : savingsGoals) {
            goal.setCurrentSavings(account.getcurrentBalance());
            JLabel label = new JLabel(goal.toString());
            label.setBounds(50, y, 300, 20);
            goalsPanel.add(label);
            int progressBarWidth = (int) (goal.getCompletionPercentage() * 3);

            JPanel progressBarPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(Color.BLACK);
                    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(1, 1, progressBarWidth, getHeight() - 2);
                }
            };
            progressBarPanel.setBounds(50, y + 25, 300, 10);
            goalsPanel.add(progressBarPanel);

            y += 40;
        }
        goalsPanel.setPreferredSize(new Dimension(700, Math.max(y, 470))); // Adjust panel size based on content
        goalsPanel.repaint();
        goalsPanel.revalidate();
        scrollPane.repaint();
        scrollPane.revalidate();
    }

    public void refreshPanel() {
        removeAll(); // Remove all components
        initializeUI(); // Re-initialize UI components like the Add button
        loadSavingsGoals(); // Reload and display savings goals
    }
}
