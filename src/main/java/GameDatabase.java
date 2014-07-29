import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.sql.*;

/**
 * Created by minh on 7/29/14.
 * This class is a representation of database which includes properties and methods to work with database correctly
 * Database used for the Server side is PostgreSQl
 * Database driver is needed and can be downloaded on PostgreSQL server.
 * Singleton pattern is used for the class because database should only be accessed one by one at a time
 */
public class GameDatabase {

    private final String DATABASE_ADDRESS = "localhost12121";
    private final String DATABASE_NAME = "/"+"postgres";
    private final String DATABASE_PORT = ":" + "5432";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "1234";

    private static GameDatabase instance = null;
    private Connection connection =  null;

    public static GameDatabase getInstance(){
        if (instance == null){
            instance = new GameDatabase();
            instance.openConnection();
        }
        return instance;
    }

    private void openConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://"
                    + DATABASE_ADDRESS + DATABASE_PORT + DATABASE_NAME, USERNAME, PASSWORD);

//            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
//            Statement stm = connection.createStatement();
//            ResultSet rs =  stm.executeQuery("select * from academics.department;");

        } catch (ClassNotFoundException e){
            System.out.println("[ERROR] Problem loading database driver");
        } catch (SQLException e) {
            System.out.println("[ERROR] Cannot open database connection");
        } catch (Exception e){
            System.out.println("[ERROR] Something wrong happened. Please contact people who can fix.");
        }
    }


}
