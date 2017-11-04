public class PropertiesContainer {
    private final String URL;
    private final String DB_NAME;
    private final String USER_NAME;
    private final String USER_PASSWORD;
    private final String LOCAL_DB_PATH;

    private final String LOCAL_DB_NAME;

    public PropertiesContainer(String url, String db_name, String user_name, String user_password, String local_db_path, String local_db_name) {
        URL = url;
        DB_NAME = db_name;
        USER_NAME = user_name;
        USER_PASSWORD = user_password;

        LOCAL_DB_PATH = local_db_path;
        LOCAL_DB_NAME = local_db_name;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getUSER_PASSWORD() {
        return USER_PASSWORD;
    }

    public String getLOCAL_DB_PATH() {
        return LOCAL_DB_PATH;
    }

    public String getJDBCurl() {
        return String.format("jdbc:sqlserver://%s;instance=MSSQLSERVER;database=%s", URL, DB_NAME);
    }

    public String getLOCAL_DB_NAME() {
        return LOCAL_DB_NAME;
    }
}