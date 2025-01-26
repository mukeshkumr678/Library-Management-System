import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdatepicker.impl.*;

public class IssueBook implements ActionListener {

    private static final Log log = LogFactory.getLog(IssueBook.class);
    JFrame frame = new JFrame("Issue Book");
    JLabel bookidLabel, studentidLabel, issuedateLabel, duedateLabel, messageLabel;
    JTextField bookidtf, studentidtf;
    JButton issueButton, backButton;
    JDatePickerImpl issuedatePicker, duedatePicker;

    JTable table;
    DefaultTableModel model;

    IssueBook() {
        // Set up frame
        frame.setSize(1100, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        ImageIcon backgroundImageIcon = new ImageIcon("fifth.jpg");
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(1100, 680, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, 1100, 680);
        backgroundLabel.setLayout(null);

        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(0, 50, 1100, 300);
        loginPanel.setBackground(new Color(255, 255, 255, 150));
        loginPanel.setLayout(null);

        // Initialize components
        bookidLabel = new JLabel("Book ID:");
        bookidLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        studentidLabel = new JLabel("Student ID:");
        studentidLabel.setFont(new Font("Arial", Font.BOLD, 20));
        issuedateLabel = new JLabel("Issue Date:");
        issuedateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        duedateLabel = new JLabel("Due Date:");
        duedateLabel.setFont(new Font("Arial", Font.BOLD, 20));
        messageLabel = new JLabel();

        bookidtf = new JTextField();
        studentidtf = new JTextField();

        issueButton = new JButton("Issue");
        backButton = new JButton("Back");

        // Date picker setup
        issuedatePicker = createDatePicker();
        duedatePicker = createDatePicker();

        // Set bounds for components
        bookidLabel.setBounds(200, 100, 200, 30);
        studentidLabel.setBounds(200, 150, 200, 30);
        issuedateLabel.setBounds(200, 200, 200, 30);
        duedateLabel.setBounds(200, 250, 200, 30);
        messageLabel.setBounds(150, 400, 800, 30);

        bookidLabel.setForeground(Color.BLACK);
        studentidLabel.setForeground(Color.BLACK);
        issuedateLabel.setForeground(Color.BLACK);
        duedateLabel.setForeground(Color.BLACK);

        loginPanel.add(bookidtf);
        loginPanel.add(studentidLabel);
        loginPanel.add(issuedateLabel);
        loginPanel.add(duedateLabel);

        bookidtf.setBounds(400, 100, 250, 30);
        studentidtf.setBounds(400, 150, 250, 30);
        issuedatePicker.setBounds(400, 200, 250, 30);
        duedatePicker.setBounds(400, 250, 250, 30);

        loginPanel.add(bookidtf);
        loginPanel.add(studentidtf);
        loginPanel.add(issuedatePicker);
        loginPanel.add(duedatePicker);

        issueButton.setBounds(400, 300, 100, 30);
        issueButton.setBackground(Color.GREEN);
        backButton.setBounds(550, 300, 100, 30);
        backButton.setBackground(Color.RED);

        loginPanel.add(issueButton);
        loginPanel.add(backButton);

        // Add components to frame
        frame.add(bookidLabel);
        frame.add(studentidLabel);
        frame.add(issuedateLabel);
        frame.add(duedateLabel);
        frame.add(messageLabel);
        frame.add(bookidtf);
        frame.add(studentidtf);
        frame.add(issuedatePicker);
        frame.add(duedatePicker);
        frame.add(issueButton);
        frame.add(backButton);

        // Table setup
        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Book ID");
        model.addColumn("Student ID");
        model.addColumn("Issue Date");
        model.addColumn("Due Date");


        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 400, 1000, 200);
        frame.add(scrollPane);

        // Add action listeners
        issueButton.addActionListener(this);
        backButton.addActionListener(this);

        backgroundLabel.add(loginPanel);
        frame.getContentPane().add(backgroundLabel);

        // Display frame
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                loadIssuedBooks();
            }
        });


    }


    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateComponentFormatter());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueButton) {
            loadIssuedBooks();
            String bookID = bookidtf.getText().trim();
            String studentID = studentidtf.getText().trim();
            Date issueDate = (Date) issuedatePicker.getModel().getValue();
            Date dueDate = (Date) duedatePicker.getModel().getValue();

            if (validateInput(bookID, studentID, issueDate, dueDate)) {
                storeIssueBookData(bookID, studentID, issueDate, dueDate);
            } else {
                messageLabel.setText("Invalid input. Please check your entries.");
            }
        } else if (e.getSource() == backButton) {
            frame.dispose();
            new DashBoard(); // Assuming DashBoard is another class
        }
    }

    private boolean validateInput(String bookID, String studentID, Date issueDate, Date dueDate) {
        return !bookID.isEmpty() && !studentID.isEmpty() && issueDate != null && dueDate != null && issueDate.before(dueDate);
    }

    private void storeIssueBookData(String bookID, String studentID, Date issueDate, Date dueDate) {
        String dbUrl = "jdbc:ucanaccess://D:/LibraryManagementSystem/LMS.accdb";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO issuebooks (b_id, st_id, issue_date, due_date) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setInt(1, Integer.parseInt(bookID));
            preparedStatement.setInt(2, Integer.parseInt(studentID));
            preparedStatement.setDate(3, new java.sql.Date(issueDate.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(dueDate.getTime()));

            int rowsAffected = preparedStatement.executeUpdate();
            clearFields();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(frame, "Book issued successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                model.addRow(new Object[]{bookID, studentID, issueDate, dueDate, ""});


            } else {
                messageLabel.setText("Failed to issue book. Try again.");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            messageLabel.setText("Error connecting to the database.");
        }
    }

    public void loadIssuedBooks() {
        String dbUrl = "jdbc:ucanaccess://D:/LibraryManagementSystem/LMS.accdb";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM issuebooks")) {

            // Clear the existing rows in the table model
            model.setRowCount(0);

            // Load data into the table
            while (resultSet.next()) {
                String bookID = resultSet.getString("b_id");
                String studentID = resultSet.getString("st_id");
                Date issueDate = resultSet.getDate("issue_date");
                Date dueDate = resultSet.getDate("due_date");
                //Date returnDate = resultSet.getDate("return_date");

                model.addRow(new Object[]{bookID, studentID, issueDate, dueDate, });
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void clearFields() {
        bookidtf.setText("");
        studentidtf.setText("");
        issuedatePicker.getModel().setValue(null);
        duedatePicker.getModel().setValue(null);
    }


}
