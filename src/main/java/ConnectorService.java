import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.util.Objects.nonNull;

public class ConnectorService {
    private PropertiesContainer properties;
    private static final String urlVINNITSYA = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_rozklad";
    private static final String USER_NAME_VINNITSYA = "1gb_monp";
    private static final String USER_PASSWORD_VINNITSYA = "b7925a08yu";

    private static final String urlCHMELNYTSKY = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_khm";
    private static final String USER_NAME_CHMELNYTSKY = "1gb_khm";
    private static final String USER_PASSWORD_CHMELNYTSKY = "d65c566d4789";

    private static final String url_JITOMIR = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_zt-db";
    private static final String USER_NAME_JITOMIR = "1gb_monp";
    private static final String USER_PASSWORD_JITOMIR = "b7925a08yu";


    public Statement getMicrosoftStatement(CityType TYPE_DB) throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Connection microsoftConnection;
        switch (TYPE_DB) {
            case VINNITSA:
                microsoftConnection = DriverManager.getConnection(urlVINNITSYA, USER_NAME_VINNITSYA, USER_PASSWORD_VINNITSYA);
                break;
            case CHMELNITSKY:
                microsoftConnection = DriverManager.getConnection(urlCHMELNYTSKY, USER_NAME_CHMELNYTSKY, USER_PASSWORD_CHMELNYTSKY);
                break;
            case JITOMIR:
                microsoftConnection = DriverManager.getConnection(url_JITOMIR, USER_NAME_JITOMIR, USER_PASSWORD_JITOMIR);
                break;
            case CUSTOM:
                //  properties = (PropertiesContainer) new FutureTask((Callable<PropertiesContainer>) () -> DbChooserFrame.getProperties()).get();
                //   properties = new FutureTask<>(DbChooserFrame::getProperties).get();
                properties = DbChooserFrame.getProperties();
                microsoftConnection = DriverManager.getConnection(properties.getJDBCurl(), properties.getUSER_NAME(), properties.getUSER_PASSWORD());
                break;
            default:
                throw new RuntimeException("NOT NOW IN CURRENT VERSION ");
        }
        return microsoftConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }


    public Statement getSQLightStatement(CityType TYPE_DB) throws Exception {
        Class.forName("org.sqlite.JDBC");

        String jdbcPath = "jdbc:sqlite:%s/%s";
        switch (TYPE_DB) {
            case VINNITSA:
                jdbcPath = String.format(jdbcPath, this.getLocalDbPath(), "database_VINNITSYA.db");
                break;
            case CHMELNITSKY:
                jdbcPath = String.format(jdbcPath, this.getLocalDbPath(), "database_CHMELNYTSKY.db");
                break;
            case JITOMIR:
                jdbcPath = String.format(jdbcPath, this.getLocalDbPath(), "database_JITOMIR.db");
                break;
            case CUSTOM:
                jdbcPath = String.format(jdbcPath, this.getLocalDbPath(), properties.getLOCAL_DB_NAME() + ".db");
                break;
            default:
                throw new RuntimeException("NOT NOW IN CURRENT VERSION ");
        }
        System.out.println("SQLight jdbcPath = " + jdbcPath);

        return DriverManager.getConnection(jdbcPath).createStatement();
    }

    private String getLocalDbPath() {
        if (nonNull(properties)) return properties.getLOCAL_DB_PATH();
        else return new File(".").getAbsolutePath();
    }
}