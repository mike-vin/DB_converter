import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {
    static Connection connectionServer,  connectDB;
    static Statement stateServ,  stateDB;

    static int  VINNITSYA = 0, CHMELNYTSKY = 1;

    static final String urlVINNITSYA = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_rozklad";
    static final String USER_NAME_VINNITSYA = "1gb_monp", USER_PASSWORD_VINNITSYA = "b7925a08yu";

    static final String urlCHMELNYTSKY = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_khm";
    static final String USER_NAME_CHMELNYTSKY = "1gb_khm", USER_PASSWORD_CHMELNYTSKY = "d65c566d4789";


    public void connectServer(int TYPE_DB) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            if (TYPE_DB == CHMELNYTSKY)
            connectionServer = DriverManager.getConnection(urlCHMELNYTSKY, USER_NAME_CHMELNYTSKY, USER_PASSWORD_CHMELNYTSKY);
            else connectionServer = DriverManager.getConnection(urlVINNITSYA, USER_NAME_VINNITSYA, USER_PASSWORD_VINNITSYA);
            stateServ = connectionServer.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void connectDB(int TYPE_DB) {
        try {
            Class.forName("org.sqlite.JDBC");
            if (TYPE_DB == CHMELNYTSKY)
            connectDB = DriverManager.getConnection("jdbc:sqlite:/home/mike/Desktop/database_CHMELNYTSKY.db");
            else connectDB = DriverManager.getConnection("jdbc:sqlite:/home/mike/Desktop/database_VINNITSYA.db");
            stateDB = connectDB.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet queryServer(String query) {
        ResultSet rs_s = null;
        try {
            rs_s = stateServ.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs_s;
    }

    public void queryDB(String query) {
        try {
            stateDB.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}