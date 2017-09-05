import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

class Data {
    private static Connector sql = Main.sql, sqlDB = Main.sqlDB;
    private static ResultSet rs_server;
    private static Date timeStart;

    static void copyDATA(){
        String SQL_DELETE_route = "DROP TABLE route";
        sqlDB.queryDB(SQL_DELETE_route);
        createTable("route");
        writeData("route");

        String SQL_DELETE_start_time = "DROP TABLE start_time";
        sqlDB.queryDB(SQL_DELETE_start_time);
        createTable("StartTime");
        writeData("StartTime");
        String SQL_RENAME_start_time = "ALTER TABLE StartTime RENAME TO start_time";
        sqlDB.queryDB(SQL_RENAME_start_time);

        String SQL_DELETE_name_of_station = "DROP TABLE name_of_station";
        sqlDB.queryDB(SQL_DELETE_name_of_station);
        createTable("NameOfStation");
        writeData("NameOfStation");
        String SQL_RENAME_name_of_station = "ALTER TABLE NameOfStation RENAME TO name_of_station";
        sqlDB.queryDB(SQL_RENAME_name_of_station);
    }

    private static void createTable(String nameTable) {
        timeStart = new Date();

        String SQL1 = "SELECT * from " + nameTable;
        try {
            rs_server = sql.queryServer(SQL1);

            StringBuilder SQL2 = new StringBuilder("create table " + nameTable + " (");

            for (int i = 1; i <= rs_server.getMetaData().getColumnCount(); i++) {
                if (i == 1) SQL2.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ");

                if (i == rs_server.getMetaData().getColumnCount()) {
                    SQL2.append(rs_server.getMetaData().getColumnName(i));
                    SQL2.append(" ").append(rs_server.getMetaData().getColumnTypeName(i));
                } else {
                    SQL2.append(rs_server.getMetaData().getColumnName(i));
                    SQL2.append(" ").append(rs_server.getMetaData().getColumnTypeName(i)).append(", ");
                }
            }
            SQL2.append(")");
            System.out.println();
            System.out.println(SQL2);

            sqlDB.queryDB(SQL2.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void writeData(String name) {
        try {
            String SQL3 = "";
            int whileCount = 0;
            String start = "INSERT INTO " + name + " VALUES ";
            String values;
            while (rs_server.next()) {
                if (whileCount == 0) {
                    SQL3 = start;
                }
                whileCount++;
                values = getValues();
                if (whileCount == 100) {
                    SQL3 += values + ";";
                    sqlDB.queryDB(SQL3);
                    whileCount = 0;
                } else {
                    SQL3 += values + ", ";
                }
            }
            SQL3 = SQL3.substring(0,SQL3.length() - 2)+ ";";
            System.out.println();
            System.out.println(SQL3);
            sqlDB.queryDB(SQL3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        long time = (new Date().getTime() - timeStart.getTime()) / 1000;

        System.out.println();
        System.out.println("=========================================================");
        System.out.println( name +" -- complete !\n TIME: " + time);
        System.out.println("=========================================================");
    }


    private static String getValues() {
        StringBuilder values = new StringBuilder("(null,");
        String change = "";
        int myCount = 0;
        try {
            for (int i = 1; i <= rs_server.getMetaData().getColumnCount(); i++) {
                myCount++;
                if (rs_server.getString(i).contains("'")) {
                    change = rs_server.getString(i);
                    change = change.replace("'", "`");
                    values.append("\'").append(change).append("\',");
                } else if (myCount == rs_server.getMetaData().getColumnCount()) {
                    values.append("\'").append(rs_server.getString(i)).append("\')");
                    myCount = 0;
                } else {
                    values.append("\'").append(rs_server.getString(i)).append("\'").append(",");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values.toString();
    }
    }