import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Data {
    static Connector sql = Main.sql, sqlDB = Main.sqlDB;
    static ResultSet rs_server;
    static Date timeStart;
    static long time;

    static void copyDATA() {
        sqlDB.queryDB("DROP TABLE route");
        createTable("route");
        writeData("route");

        sqlDB.queryDB("DROP TABLE start_time");
        createTable("StartTime");
        writeData("StartTime");
        sqlDB.queryDB("ALTER TABLE StartTime RENAME TO start_time");

        sqlDB.queryDB("DROP TABLE name_of_station");
        createTable("NameOfStation");
        writeData("NameOfStation");
        sqlDB.queryDB("ALTER TABLE NameOfStation RENAME TO name_of_station");
    }

    static void createTable(String nameTable) {
        timeStart = new Date();

        String selectFromTableName = "SELECT * from " + nameTable;
        try {
            rs_server = sql.queryServer(selectFromTableName);

            StringBuilder createTableAndCollomns = new StringBuilder("create table " + nameTable + " (");

            for (int i = 1; i <= rs_server.getMetaData().getColumnCount(); i++) {
                if (i == 1) createTableAndCollomns.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ");

                if (i == rs_server.getMetaData().getColumnCount()) {
                    createTableAndCollomns.append(rs_server.getMetaData().getColumnName(i));
                    createTableAndCollomns.append(" ").append(rs_server.getMetaData().getColumnTypeName(i));
                } else {
                    createTableAndCollomns.append(rs_server.getMetaData().getColumnName(i));
                    createTableAndCollomns.append(" ").append(rs_server.getMetaData().getColumnTypeName(i)).append(", ");
                }
            }
            createTableAndCollomns.append(")");
            System.out.println("\n" + createTableAndCollomns);

            sqlDB.queryDB(createTableAndCollomns.toString());

        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessageDialog(e.getLocalizedMessage());
        }
    }

    private static void showErrorMessageDialog(String exceptionMessage) {
        JOptionPane.showMessageDialog(null, exceptionMessage);

    }

    static void writeData(String name) {
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
            SQL3 = SQL3.substring(0, SQL3.length() - 2) + ";";
            System.out.println("\n" + SQL3);
            sqlDB.queryDB(SQL3);

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessageDialog(e.getLocalizedMessage());
        }
        time = (new Date().getTime() - timeStart.getTime()) / 1000;

        System.out.println("\n=========================================================");
        System.out.println(name + " -- complete !\n TIME: " + time);
        System.out.println("=========================================================");
    }


    private static String getValues() {
        StringBuilder values = new StringBuilder("(null,");
        String change;
        int myCount = 0;
        try {
            for (int i = 1; i <= rs_server.getMetaData().getColumnCount(); i++) {
                myCount++;
                if (rs_server.getString(i).contains("'")) {
                    change = rs_server.getString(i).trim();
                    change = change.replace("'", "`");
                    values.append("\'").append(change).append("\',");
                } else if (myCount == rs_server.getMetaData().getColumnCount()) {
                    values.append("\'").append(rs_server.getString(i).trim()).append("\')");
                    myCount = 0;
                } else {
                    values.append("\'").append(rs_server.getString(i).trim()).append("\'").append(",");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessageDialog(e.getLocalizedMessage());
        }
        return values.toString();
    }
}