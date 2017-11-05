import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectorService {

    private static final String urlVINNITSYA = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_rozklad";
    private static final String USER_NAME_VINNITSYA = "1gb_monp";
    private static final String USER_PASSWORD_VINNITSYA = "b7925a08yu";

    private static final String urlCHMELNYTSKY = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_khm";
    private static final String USER_NAME_CHMELNYTSKY = "1gb_khm";
    private static final String USER_PASSWORD_CHMELNYTSKY = "d65c566d4789";

    private static final String url_JITOMIR = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_zt-db";
    private static final String USER_NAME_JITOMIR = "1gb_monp";
    private static final String USER_PASSWORD_JITOMIR = "b7925a08yu";


    public void convert(CityType city_type) throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Class.forName("org.sqlite.JDBC");

        // String localDbPath = new File(".").getAbsolutePath();
        String localDbPath = System.getProperty("user.dir");

        String jdbcPath = "jdbc:sqlite:%s/%s";
        Connection microsoftConnection;

        switch (city_type) {
            case VINNITSA:
                microsoftConnection = DriverManager.getConnection(urlVINNITSYA, USER_NAME_VINNITSYA, USER_PASSWORD_VINNITSYA);
                jdbcPath = String.format(jdbcPath, localDbPath, "database_VINNITSYA.db");
                break;
            case CHMELNITSKY:
                microsoftConnection = DriverManager.getConnection(urlCHMELNYTSKY, USER_NAME_CHMELNYTSKY, USER_PASSWORD_CHMELNYTSKY);
                jdbcPath = String.format(jdbcPath, localDbPath, "database_CHMELNYTSKY.db");
                break;
            case JITOMIR:
                microsoftConnection = DriverManager.getConnection(url_JITOMIR, USER_NAME_JITOMIR, USER_PASSWORD_JITOMIR);
                jdbcPath = String.format(jdbcPath, localDbPath, "database_JITOMIR.db");
                break;
            case CUSTOM:
                new CustomChooser();
                return;
            default:
                throw new RuntimeException("UNKNOWN FORMAT CityType = " + city_type);
        }

        Statement microsoftStatement = microsoftConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        Statement SQLightStatement = DriverManager.getConnection(jdbcPath).createStatement();

        new DataRepository().convertData(microsoftStatement, SQLightStatement);

        JOptionPane.showMessageDialog(null, "Готово!\nПуть к файлу:\n" + localDbPath);
        System.exit(0);
    }
}