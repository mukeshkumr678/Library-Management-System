import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Idpassword {
    private HashMap<String, String> loginInfo = new HashMap<>();

    public Idpassword() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String dbUrl = "jdbc:ucanaccess://D:/LibraryManagementSystem/LMS.accdb";

            try (Connection connection = DriverManager.getConnection(dbUrl);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT username, password FROM Admin")) {

                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    loginInfo.put(username, password);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getLoginInfo() {
        return loginInfo;
    }
}
