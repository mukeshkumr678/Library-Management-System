import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class NewBook implements ActionListener {




    JFrame frame = new JFrame("New Book");
    JButton back, save;
    JLabel bookid, name, publisher, price, publishyear;
    JTextField bookidtf, nametf, publishertf, pricetf, publishyeartf;
    JTable table;
    DefaultTableModel model;

    NewBook() {

        ImageIcon backgroundImageIcon = new ImageIcon("fifth.jpg"); // Ensure the image is in the correct path
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(1100, 680, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, 1100, 680);
        backgroundLabel.setLayout(null);

        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(0, 50, 1100, 300); // Full width of the frame
        loginPanel.setBackground(new Color(255, 255, 255, 150)); // More transparent
        loginPanel.setLayout(null);

        // Initialize buttons
        back = new JButton("Back");
        save = new JButton("Save");

        // Initialize labels
        bookid = new JLabel("Book ID:");
        name = new JLabel("Name:");
        publisher = new JLabel("Publisher:");
        price = new JLabel("Price:");
        publishyear = new JLabel("Publish Year:");

        // Initialize text fields
        bookidtf = new JTextField();
        nametf = new JTextField();
        publishertf = new JTextField();
        pricetf = new JTextField();
        publishyeartf = new JTextField();

        // Set bounds for labels
        bookid.setBounds(50, 50, 100, 30);
        name.setBounds(50, 100, 100, 30);
        publisher.setBounds(50, 150, 100, 30);
        price.setBounds(50, 200, 100, 30);
        publishyear.setBounds(50, 250, 100, 30);

        bookid.setForeground(Color.BLACK);
        name.setForeground(Color.BLACK);
        publisher.setForeground(Color.BLACK);
        price.setForeground(Color.BLACK);
        publishyear.setForeground(Color.BLACK);

        bookid.setFont(new Font("Algerian", Font.BOLD, 15));
        name.setFont(new Font("Algerian", Font.BOLD, 15));
        publisher.setFont(new Font("Algerian", Font.BOLD, 15));
        price.setFont(new Font("Algerian", Font.BOLD, 15));
        publishyear.setFont(new Font("Algerian", Font.BOLD, 13));

        // Set bounds for text fields
        bookidtf.setBounds(200, 50, 200, 30);
        nametf.setBounds(200, 100, 200, 30);
        publishertf.setBounds(200, 150, 200, 30);
        pricetf.setBounds(200, 200, 200, 30);
        publishyeartf.setBounds(200, 250, 200, 30);

        // Set bounds for buttons
        back.setBounds(50, 300, 100, 30);
        back.setBackground(Color.RED);
        save.setBounds(200, 300, 100, 30);
        save.setBackground(Color.GREEN);
        // Set button properties
        back.setFocusable(false);

        save.setFocusable(false);

        // Initialize table
        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Book ID");
        model.addColumn("Name");
        model.addColumn("Publisher");
        model.addColumn("Price");
        model.addColumn("Publish Year");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(450, 50, 600, 200);
        frame.add(scrollPane);



        // Add components to frame
        frame.add(bookid);
        frame.add(name);
        frame.add(publisher);
        frame.add(price);
        frame.add(publishyear);
        frame.add(bookidtf);
        frame.add(nametf);
        frame.add(publishertf);
        frame.add(pricetf);
        frame.add(publishyeartf);
        frame.add(back);
        frame.add(save);

        // Frame settings
        frame.setSize(1100, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        // Add action listeners
        back.addActionListener(this);
        save.addActionListener(this);
        backgroundLabel.add(loginPanel);
        frame.getContentPane().add(backgroundLabel);

        // Load books into table when the frame is opened
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                loadBooks();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            frame.dispose();
            // Implement DashBoard logic if needed
            DashBoard d = new DashBoard();
        } else if (e.getSource() == save) {
            saveBook();
            loadBooks();
        }
    }

    private void saveBook() {
        String bookID = bookidtf.getText().trim();
        String bookName = nametf.getText().trim();
        String publisher = publishertf.getText().trim();
        String price = pricetf.getText().trim();
        String publishYear = publishyeartf.getText().trim();

        // Check for empty fields
        if (bookID.isEmpty() || bookName.isEmpty() || publisher.isEmpty() || price.isEmpty() || publishYear.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate Book ID and ensure it is a number
        if (!bookID.matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Book ID must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate Price and ensure it is a valid decimal number
        if (!price.matches("\\d+(\\.\\d{1,2})?")) {
            JOptionPane.showMessageDialog(frame, "Price must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate Publish Year and ensure it is a number
        if (!publishYear.matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Publish Year must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dbUrl = "jdbc:ucanaccess://D:/LibraryManagementSystem/LMS.accdb";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO books (book_id, book_name, publisher, price, publish_year) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setInt(1, Integer.parseInt(bookID));
            preparedStatement.setString(2, bookName);
            preparedStatement.setString(3, publisher);
            preparedStatement.setDouble(4, Double.parseDouble(price));
            preparedStatement.setInt(5, Integer.parseInt(publishYear));

            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Book saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBooks() {
        model.setRowCount(0); // Clear existing rows
        String dbUrl = "jdbc:ucanaccess://D:/LibraryManagementSystem/LMS.accdb";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM books")) {

            while (resultSet.next()) {
                String bookID = resultSet.getString("book_id");
                String bookName = resultSet.getString("book_name");
                String publisher = resultSet.getString("publisher");
                String price = resultSet.getString("price");
                String publishYear = resultSet.getString("publish_year");

                model.addRow(new Object[]{bookID, bookName, publisher, price, publishYear});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        bookidtf.setText("");
        nametf.setText("");
        publishertf.setText("");
        pricetf.setText("");
        publishyeartf.setText("");
    }

}
