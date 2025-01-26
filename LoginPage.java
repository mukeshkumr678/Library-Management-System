import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LoginPage implements ActionListener {
    JFrame frame = new JFrame("Login Page with Stylish Background");

    JLabel adminLabel = new JLabel("Email ID:");
    JLabel passLabel = new JLabel("Password:");
    JPasswordField passtf = new JPasswordField(10);
    JButton loginButton = new JButton("Login");
    JButton cancelButton = new JButton("Cancel");
    JLabel messageLabel = new JLabel();
    JTextField admintf = new JTextField(10);
    HashMap<String, String> loginInfo = new HashMap<>();

    LoginPage(){

    }

    LoginPage(HashMap<String, String> login) {
        // Load background image
        ImageIcon backgroundImageIcon = new ImageIcon("fourth.jpg"); // Ensure the image is in the correct path
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(1100, 680, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, 1100, 680);
        backgroundLabel.setLayout(null);

        loginInfo = login;

        // Semi-transparent panel for the login form
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(0, 150, 1100, 300); // Full width of the frame
        loginPanel.setBackground(new Color(255, 255, 255, 150)); // More transparent
        loginPanel.setLayout(null);

        // Title Label: "User Login Form"
        JLabel loginTitle = new JLabel("User Login Form", SwingConstants.CENTER);
        loginTitle.setBounds(0, 20, 1100, 40);
        loginTitle.setFont(new Font("Arial", Font.BOLD, 30));
        loginTitle.setForeground(Color.BLACK);
        loginPanel.add(loginTitle);

        // Labels and Text Fields
        adminLabel.setBounds(275, 80, 150, 30);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 18));
        adminLabel.setForeground(Color.BLACK);
        loginPanel.add(adminLabel);

        admintf.setBounds(450, 80, 250, 30);
        admintf.setFont(new Font("Arial", Font.PLAIN, 18));
        loginPanel.add(admintf);

        passLabel.setBounds(275, 140, 150, 30);
        passLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passLabel.setForeground(Color.BLACK);
        loginPanel.add(passLabel);

        passtf.setBounds(450, 140, 250, 30);
        passtf.setFont(new Font("Arial", Font.PLAIN, 18));
        loginPanel.add(passtf);

        // Login Button
        loginButton.setBounds(450, 200, 100, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this);
        loginPanel.add(loginButton);

        // Cancel Button
        cancelButton.setBounds(600, 200, 100, 40);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 18));
        cancelButton.setBackground(new Color(200, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(this);
        loginPanel.add(cancelButton);

        // Message Label
        messageLabel.setBounds(275, 250, 550, 30);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        messageLabel.setForeground(Color.RED);
        loginPanel.add(messageLabel);

        backgroundLabel.add(loginPanel);
        frame.getContentPane().add(backgroundLabel);

        frame.setSize(1100, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String id = admintf.getText();
            String pass = new String(passtf.getPassword());
            if (loginInfo.containsKey(id)) {
                if (loginInfo.get(id).equals(pass)) {
                    frame.dispose();
                    DashBoard d = new DashBoard();
//                    messageLabel.setForeground(Color.GREEN);
//                    messageLabel.setText("Login Successful");
                    // Proceed to the next screen
                } else {
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("Wrong password");
                }
            } else {
                messageLabel.setText("Wrong username");
            }
        } else if (e.getSource() == cancelButton) {
            admintf.setText("");
            passtf.setText("");
            messageLabel.setText("");
        }
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
}
