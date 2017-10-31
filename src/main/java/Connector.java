import java.sql.*;

public class Connector {
    static Connection connectionServer, connectDB;
    static Statement stateServ, stateDB;

    private static final String urlVINNITSYA = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_rozklad";
    private static final String USER_NAME_VINNITSYA = "1gb_monp";
    private static final String USER_PASSWORD_VINNITSYA = "b7925a08yu";

    private static final String urlCHMELNYTSKY = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_khm";
    private static final String USER_NAME_CHMELNYTSKY = "1gb_khm";
    private static final String USER_PASSWORD_CHMELNYTSKY = "d65c566d4789";

    private static final String url_JITOMIR = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_zt-db";
    private static final String USER_NAME_JITOMIR = "1gb_monp";
    private static final String USER_PASSWORD_JITOMIR = "b7925a08yu";


    public void connectServer(CityType TYPE_DB) throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        switch (TYPE_DB) {
            case VINNITSA:
                connectionServer = DriverManager.getConnection(urlVINNITSYA, USER_NAME_VINNITSYA, USER_PASSWORD_VINNITSYA);
                break;
            case CHMELNITSKY:
                connectionServer = DriverManager.getConnection(urlCHMELNYTSKY, USER_NAME_CHMELNYTSKY, USER_PASSWORD_CHMELNYTSKY);
                break;
            case JITOMIR:
                connectionServer = DriverManager.getConnection(url_JITOMIR, USER_NAME_JITOMIR, USER_PASSWORD_JITOMIR);
                break;
            default:
                throw new RuntimeException("NOT NOW IN CURRENT VERSION ");
        }
        stateServ = connectionServer.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }


    public void connectDB(CityType TYPE_DB, String localPath) throws Exception {
        Class.forName("org.sqlite.JDBC");

        String jdbcPath = "jdbc:sqlite:%s/%s";
        switch (TYPE_DB) {
            case VINNITSA:
                jdbcPath = String.format(jdbcPath, localPath, "database_VINNITSYA.db");
                break;
            case CHMELNITSKY:
                jdbcPath = String.format(jdbcPath, localPath, "database_CHMELNYTSKY.db");
                break;
            case JITOMIR:
                jdbcPath = String.format(jdbcPath, localPath, "database_JITOMIR.db");
                break;
            default:
                throw new RuntimeException("NOT NOW IN CURRENT VERSION ");
        }
        connectDB = DriverManager.getConnection(jdbcPath);
        stateDB = connectDB.createStatement();
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