package connectors;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class JDBCConnector implements ConnectionProperties {
    public static Connection getConnection() throws SQLServerException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("1gb_monp");
        ds.setPassword("b7925a08yu");
        ds.setServerName("mssql4.1gb.ua");
        ds.setPortNumber(1433);
        ds.setDatabaseName("1gb_x_rozklad");
        return ds.getConnection();
    }

}
