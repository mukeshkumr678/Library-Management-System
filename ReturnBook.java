import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ReturnBook implements ActionListener {

    JFrame frame = new JFrame("Return Book");
    JLabel bookidLabel, studentidLabel, issuedateLabel, duedateLabel, messageLabel;
    JTextField bookidtf, studentidtf, issuedatetf, duedatetf;
    JButton searchButton, saveButton, backButton;
    DefaultTableModel model;
    JTable table;

    ReturnBook() {
        // Set up frame
        frame.setSize(1100, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        ImageIcon backgroundImageIcon = new ImageIcon("fifth.jpg"); // Ensure the image is in the correct path
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(1100, 680, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, 1100, 680);
        backgroundLabel.setLayout(null);


        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(0, 50, 1100, 300); // Full width of the frame
        loginPanel.setBackground(new Color(255, 255, 255, 150)); // More transparent
        loginPanel.setLayout(null);


        // Initialize components
        bookidLabel = new JLabel("Book ID:");
        studentidLabel = new JLabel("Student ID:");
        issuedateLabel = new JLabel("Issue Date:");
        duedateLabel = new JLabel("Due Date:");
        messageLabel = new JLabel();

        bookidLabel.setForeground(Color.BLACK);
        issuedateLabel.setForeground(Color.BLACK);
        duedateLabel.setForeground(Color.BLACK);
        studentidLabel.setForeground(Color.BLACK);

        bookidLabel.setFont(new Font("Algerian",Font.BOLD,15));
        studentidLabel.setFont(new Font("Algerian",Font.BOLD,15));
        issuedateLabel.setFont(new Font("Algerian",Font.BOLD,15));
        duedateLabel.setFont(new Font("Algerian",Font.BOLD,15));

        backgroundLabel.add((bookidLabel));

        backgroundLabel.add((studentidLabel));

        backgroundLabel.add((issuedateLabel));

        backgroundLabel.add((duedateLabel));

        backgroundLabel.add((messageLabel));



        bookidtf = new JTextField();
        studentidtf = new JTextField();
        issuedatetf = new JTextField();
        duedatetf = new JTextField();

        backgroundLabel.add((bookidtf));
        backgroundLabel.add((bookidLabel));
        backgroundLabel.add((issuedatetf));
        backgroundLabel.add((duedatetf));


        searchButton = new JButton("Search");
        searchButton.setBackground(Color.BLUE);
        saveButton = new JButton("Save");
        saveButton.setBackground(Color.GREEN);
        backButton = new JButton("Back");
        backButton.setBackground(Color.RED);


        backgroundLabel.add((searchButton));
        backgroundLabel.add((saveButton));
        backgroundLabel.add((backButton));

        // Set bounds for components
        bookidLabel.setBounds(200, 100, 200, 30);
        studentidLabel.setBounds(200, 150, 200, 30);
        issuedateLabel.setBounds(200, 200, 200, 30);
        duedateLabel.setBounds(200, 250, 200, 30);
        messageLabel.setBounds(150, 400, 800, 30);

        bookidtf.setBounds(400, 100, 250, 30);
        studentidtf.setBounds(400, 150, 250, 30);
        issuedatetf.setBounds(400, 200, 250, 30);
        duedatetf.setBounds(400, 250, 250, 30);

        searchButton.setBounds(700, 100, 100, 30);
        saveButton.setBounds(400, 300, 100, 30);
        backButton.setBounds(550, 300, 100, 30);

        // Disable issueDate and dueDate text fields to make them uneditable
        issuedatetf.setEditable(false);
        duedatetf.setEditable(false);

        // Initialize the table and its model
        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Book ID");
        model.addColumn("Student ID");
        model.addColumn("Issue Date");
        model.addColumn("Due Date");
        model.addColumn("Return Date");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 400, 1000, 200);

        // Add components to frame
        frame.add(bookidLabel);
        frame.add(studentidLabel);
        frame.add(issuedateLabel);
        frame.add(duedateLabel);
        frame.add(messageLabel);
        frame.add(bookidtf);
        frame.add(studentidtf);
        frame.add(issuedatetf);
        frame.add(duedatetf);
        frame.add(searchButton);
        frame.add(saveButton);
        frame.add(backButton);
        frame.add(scrollPane);

        // Add action listeners
        searchButton.addActionListener(this);
        saveButton.addActionListener(this);
        backButton.addActionListener(this);

        // Load returned books into the table
        loadReturnedBooks();
        backgroundLabel.add(loginPanel);
        frame.getContentPane().add(backgroundLabel);
        // Display frame
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String bookID = bookidtf.getText().trim();
            String studentID = studentidtf.getText().trim();

            if (!bookID.isEmpty() && !studentID.isEmpty()) {
                searchAndFillDetails(bookID, studentID);
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter both book ID abd student ID","error",JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == saveButton) {
            saveReturnDetails();
        } else if (e.getSource() == backButton) {
            frame.dispose();
            // Navigate back to the previous screen, e.g., DashBoard
            DashBoard dashboard = new DashBoard();
        }
    }

    private void searchAndFillDetails(String bookID, String studentID) {
        String dbUrl = "jdbc:ucanaccess://D:/LibraryManagementSystem/LMS.accdb";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT issue_date, due_date FROM issuebooks WHERE b_id = ? AND st_id = ?")) {

            preparedStatement.setInt(1, Integer.parseInt(bookID));
            preparedStatement.setInt(2, Integer.parseInt(studentID));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                issuedatetf.setText(resultSet.getDate("issue_date").toString());
                duedatetf.setText(resultSet.getDate("due_date").toString());
                JOptionPane.showMessageDialog(frame, "Book and Student details found.","success",JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No matching record found","error",JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            messageLabel.setText("Error connecting to the database.");
        }
    }

    private void saveReturnDetails() {
        String bookID = bookidtf.getText().trim();
        String studentID = studentidtf.getText().trim();
        String issueDate = issuedatetf.getText().trim();
        String dueDate = duedatetf.getText().trim();

        if (bookID.isEmpty() || studentID.isEmpty() || issueDate.isEmpty() || dueDate.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dbUrl = "jdbc:ucanaccess://D:/LibraryManagementSystem/LMS.accdb";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO returns (book_id, student_id, issue_date, due_date, return_date) VALUES (?, ?, ?, ?, CURRENT_DATE)")) {

            preparedStatement.setInt(1, Integer.parseInt(bookID));
            preparedStatement.setInt(2, Integer.parseInt(studentID));
            preparedStatement.setDate(3, Date.valueOf(issueDate));
            preparedStatement.setDate(4, Date.valueOf(dueDate));

            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadReturnedBooks();  // Refresh the table with the latest data
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadReturnedBooks() {
        model.setRowCount(0); // Clear existing rows
        String dbUrl = "jdbc:ucanaccess://D:/LibraryManagementSystem/LMS.accdb";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM returns")) {

            while (resultSet.next()) {
                String bookID = resultSet.getString("book_id");
                String studentID = resultSet.getString("student_id");
                String issueDate = resultSet.getString("issue_date");
                String dueDate = resultSet.getString("due_date");
                String returnDate = resultSet.getString("return_date");

                model.addRow(new Object[]{bookID, studentID, issueDate, dueDate, returnDate});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        bookidtf.setText("");
        studentidtf.setText("");
        issuedatetf.setText("");
        duedatetf.setText("");
    }
}
