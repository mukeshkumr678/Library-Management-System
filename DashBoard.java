import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class DashBoard implements ActionListener {
    JFrame frame = new JFrame("Dashboard");
    JButton newStudent, newBook, issueBook, returnBook, logout;



    DashBoard(){

        newStudent = new JButton("New Student");
        newBook = new JButton("New Book");
        issueBook = new JButton("Issue Book");
        returnBook = new JButton("Return Book");
        logout = new JButton("Logout   ");

        ImageIcon backgroundImageIcon = new ImageIcon("fourth.jpg"); // Ensure the image is in the correct path
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(1100, 680, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, 1100, 680);
        backgroundLabel.setLayout(null);

        JPanel lmsPanel = new JPanel();
        lmsPanel.setBounds(0, 80, 1100, 100); // Full width of the frame
        lmsPanel.setBackground(new Color(255, 255, 255, 150)); // More transparent
        lmsPanel.setLayout(null);

        // Title Label: "User Login Form"
        JLabel loginTitle = new JLabel("LIBRARY MANAGEMENT SYSTEM", SwingConstants.CENTER);
        loginTitle.setBounds(0, 20, 1100, 40);
        loginTitle.setFont(new Font("Algerian", Font.BOLD, 30));
        loginTitle.setForeground(Color.BLACK);
        lmsPanel.add(loginTitle);


        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 80, 150, 680); // Full width of the frame
        leftPanel.setBackground(new Color(255, 255, 255, 150)); // More transparent
        leftPanel.setLayout(null);

        newStudent.setBounds(0, 360,130,40);
        newStudent.setFocusable(false);
        newStudent.setBackground(Color.ORANGE);
        newBook.setBounds(0,300 ,130,40);
        newBook.setFocusable(false);
        newBook.setBackground(Color.ORANGE);
        issueBook.setBounds(0, 180,130,40);
        issueBook.setFocusable(false);
        issueBook.setBackground(Color.ORANGE);
        returnBook.setBounds(0, 240,130,40);
        returnBook.setFocusable(false);
        returnBook.setBackground(Color.ORANGE);
        logout.setBounds(0, 420,130,40);
        logout.setFocusable(false);
        logout.setBackground(Color.RED);

        leftPanel.add(newStudent);
        leftPanel.add(newBook);
        leftPanel.add(issueBook);
        leftPanel.add(returnBook);
        leftPanel.add(logout);

        newStudent.addActionListener(this);
        newBook.addActionListener(this);
        issueBook.addActionListener(this);
        returnBook.addActionListener(this);
        logout.addActionListener(this);

        frame.add(newBook);
        frame.add(newStudent);
        frame.add(logout);
        frame.add(issueBook);
        frame.add(returnBook);


        backgroundLabel.add(lmsPanel);
        backgroundLabel.add(leftPanel);
        frame.getContentPane().add(backgroundLabel);

        //why we put these things into constructor
        frame.setSize(1100, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);


    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newBook){
         frame.dispose();
            NewBook newBook = new NewBook();

        }
         if(e.getSource() == newStudent){
             frame.dispose();
          NewStudent ns = new NewStudent();
        }
         if(e.getSource() == issueBook){
             frame.dispose();
             IssueBook ib = new IssueBook();


        }
        if(e.getSource() == returnBook){
            frame.dispose();
            ReturnBook rb = new ReturnBook();

        }
        if (e.getSource() == logout) {
            frame.dispose();
            HashMap<String, String> loginInfo = new HashMap<>(); // Or pass an existing hashmap
            LoginPage login = new LoginPage(loginInfo);
            login.setVisible(true);
        }

    }
}