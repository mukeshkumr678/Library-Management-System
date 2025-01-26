import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class NewStudent implements ActionListener {

    JFrame frame = new JFrame("New Student");
    JLabel stid, name, fname, sem, dep;
    JTextField stidtf, nametf, fnametf, semtf, deptf;
    JButton save, back;
    JTable table;
    DefaultTableModel model;

    NewStudent() {


        ImageIcon backgroundImageIcon = new ImageIcon("fifth.jpg"); // Ensure the image is in the correct path
        Image backgroundImage = backgroundImageIcon.getImage().getScaledInstance(1100, 680, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, 1100, 680);
        backgroundLabel.setLayout(null);


        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(0, 50, 1100, 300); // Full width of the frame
        loginPanel.setBackground(new Color(255, 255, 255, 150)); // More transparent
        loginPanel.setLayout(null);

        // Initialize labels
        stid = new JLabel("Student ID:");
        name = new JLabel("Name:");
        fname = new JLabel("Father's Name:");
        sem = new JLabel("Semester:");
        dep = new JLabel("Department:");

        stid.setForeground(Color.BLACK);
        name.setForeground(Color.BLACK);
        fname.setForeground(Color.BLACK);
        sem.setForeground(Color.BLACK);
        dep.setForeground(Color.BLACK);

        stid.setFont(new Font("Algerian", Font.BOLD, 15));
        name.setFont(new Font("Algerian", Font.BOLD, 15));
        fname.setFont(new Font("Algerian", Font.BOLD, 15));
        sem.setFont(new Font("Algerian", Font.BOLD, 15));
        dep.setFont(new Font("Algerian", Font.BOLD, 13));

        // Initialize text fields
        stidtf = new JTextField();
        nametf = new JTextField();
        fnametf = new JTextField();
        semtf = new JTextField();
        deptf = new JTextField();

        // Initialize buttons
        save = new JButton("Save");
        save.setBackground(Color.GREEN);
        back = new JButton("Back");
        back.setBackground(Color.RED);

        // Initialize table
        table = new JTable();
        model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Student ID");
        model.addColumn("Name");
        model.addColumn("Father's Name");
        model.addColumn("Semester");
        model.addColumn("Department");

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(450, 50, 600, 200);
        frame.add(scrollPane);

        // Set bounds for labels
        stid.setBounds(50, 50, 100, 30);
        name.setBounds(50, 100, 100, 30);
        fname.setBounds(50, 150, 100, 30);
        sem.setBounds(50, 200, 100, 30);
        dep.setBounds(50, 250, 100, 30);

        // Set bounds for text fields
        stidtf.setBounds(200, 50, 200, 30);
        nametf.setBounds(200, 100, 200, 30);
        fnametf.setBounds(200, 150, 200, 30);
        semtf.setBounds(200, 200, 200, 30);
        deptf.setBounds(200, 250, 200, 30);

        // Set bounds for buttons
        save.setBounds(50, 300, 100, 30);
        back.setBounds(200, 300, 100, 30);

        // Add components to frame
        frame.add(stid);
        frame.add(name);
        frame.add(fname);
        frame.add(sem);
        frame.add(dep);
        frame.add(stidtf);
        frame.add(nametf);
        frame.add(fnametf);
        frame.add(semtf);
        frame.add(deptf);
        frame.add(save);
        frame.add(back);

        // Frame settings
        frame.setSize(1100, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        // Add action listeners
        save.addActionListener(this);
        back.addActionListener(this);
        backgroundLabel.add(loginPanel);
        frame.getContentPane().add(backgroundLabel);

        // Load students into table when the frame is opened
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                loadStudents();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            saveStudent();
            loadStudents();
        } else if (e.getSource() == back) {
            frame.dispose();
            // Implement DashBoard logic if needed
            DashBoard d = new DashBoard();
        }
    }

    private void saveStudent() {
        String studentID = stidtf.getText().trim();
        String studentName = nametf.getText().trim();
        String fatherName = fnametf.getText().trim();
        String semester = semtf.getText().trim();
        String department = deptf.getText().trim();

        if (studentID.isEmpty() || studentName.isEmpty() || fatherName.isEmpty() || semester.isEmpty() || department.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!studentID.matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Student ID must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dbUrl = "jdbc:ucanaccess://D:/LibraryManagementSystem/LMS.accdb";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO students (student_id, st_name, father_name, semester, department) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setInt(1, Integer.parseInt(studentID));
            preparedStatement.setString(2, studentName);
            preparedStatement.setString(3, fatherName);
            preparedStatement.setInt(4, Integer.parseInt(semester));
            preparedStatement.setString(5, department);

            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Student saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudents() {
        model.setRowCount(0); // Clear existing rows
        String dbUrl = "jdbc:ucanaccess://D:/LibraryManagementSystem/LMS.accdb";
        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM students")) {

            while (resultSet.next()) {
                String studentID = resultSet.getString("student_id");
                String studentName = resultSet.getString("st_name");
                String fatherName = resultSet.getString("father_name");
                String semester = resultSet.getString("semester");
                String department = resultSet.getString("department");

                model.addRow(new Object[]{studentID, studentName, fatherName, semester, department});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        stidtf.setText("");
        nametf.setText("");
        fnametf.setText("");
        semtf.setText("");
        deptf.setText("");
    }


}
