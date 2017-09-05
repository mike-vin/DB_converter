import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Connector {
    private static Statement stateServ, stateDB;

    private static final String urlVINNITSYA = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_rozklad";
    private static final String USER_NAME_VINNITSYA = "1gb_monp", USER_PASSWORD_VINNITSYA = "b7925a08yu";

    private static final String urlCHMELNYTSKY = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_khm";
    private static final String USER_NAME_CHMELNYTSKY = "1gb_khm", USER_PASSWORD_CHMELNYTSKY = "d65c566d4789";


    void connectServer(int TYPE_DB) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connectionServer;
            if (TYPE_DB == Main.CHMELNYTSKY)
                connectionServer = DriverManager.getConnection(urlCHMELNYTSKY, USER_NAME_CHMELNYTSKY, USER_PASSWORD_CHMELNYTSKY);
            else
                connectionServer = DriverManager.getConnection(urlVINNITSYA, USER_NAME_VINNITSYA, USER_PASSWORD_VINNITSYA);
            stateServ = connectionServer.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void connectDB(int TYPE_DB, String absolutePath) {
        final String CHMELNYTSKY_PATH = String.format("jdbc:sqlite:%s/database_CHMELNYTSKY.db", absolutePath);
        final String VINNITSYA_PATH = String.format("jdbc:sqlite:%s/database_VINNITSYA.db", absolutePath);
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connectDB;
            if (TYPE_DB == Main.CHMELNYTSKY)
                connectDB = DriverManager.getConnection(CHMELNYTSKY_PATH);
            else connectDB = DriverManager.getConnection(VINNITSYA_PATH);
            stateDB = connectDB.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ResultSet queryServer(String query) {
        ResultSet rs_s = null;
        try {
            rs_s = stateServ.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs_s;
    }

    void queryDB(String query) {
        try {
            stateDB.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}