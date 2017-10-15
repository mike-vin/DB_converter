import connectors.ConnectionProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Connector implements ConnectionProperties {
    private static Statement stateServ, stateDB;

    void connectServer(int TYPE_DB) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connectionServer;
            if (TYPE_DB == OldSchoolMain.CHMELNYTSKY)
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
            if (TYPE_DB == OldSchoolMain.CHMELNYTSKY)
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