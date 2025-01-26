import java.util.HashMap;

public class Main {
    public static void main(String[] args){

        Idpassword idpass = new Idpassword();
        LoginPage login = new LoginPage(idpass.getLoginInfo());
    }

}
