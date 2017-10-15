package connectors;

public interface ConnectionProperties {
    String urlVINNITSYA = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_rozklad";
    String USER_NAME_VINNITSYA = "1gb_monp";
    String USER_PASSWORD_VINNITSYA = "b7925a08yu";

    String urlCHMELNYTSKY = "jdbc:sqlserver://mssql4.1gb.ua;instance=MSSQLSERVER;database=1gb_x_khm";
    String USER_NAME_CHMELNYTSKY = "1gb_khm";
    String USER_PASSWORD_CHMELNYTSKY = "d65c566d4789";

    String DIALECT = "org.hibernate.dialect.SQLServerDialect";
}
