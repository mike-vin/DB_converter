import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DataRepository {
    private Statement MICROSOFT;
    private Statement SQLight;
    private ResultSet resultSetMicrosoft;
    private Date timeStart;


    public void convertData(Statement microsoftStatement, Statement sqLightStatement) throws SQLException {
        this.MICROSOFT = microsoftStatement;
        this.SQLight = sqLightStatement;

        SQLight.executeUpdate("DROP TABLE IF EXISTS route");
        createTable("route");
        writeData("route");

        SQLight.executeUpdate("DROP TABLE IF EXISTS start_time");
        createTable("StartTime");
        writeData("StartTime");
        SQLight.executeUpdate("ALTER TABLE StartTime RENAME TO start_time");

        SQLight.executeUpdate("DROP TABLE IF EXISTS name_of_station");
        createTable("NameOfStation");
        writeData("NameOfStation");
        SQLight.executeUpdate("ALTER TABLE NameOfStation RENAME TO name_of_station");
    }

    private void createTable(String nameTable) {
        timeStart = new Date();

        String selectFromTableName = "SELECT * from " + nameTable;
        try {
            resultSetMicrosoft = MICROSOFT.executeQuery(selectFromTableName);

            StringBuilder createTableAndCollomns = new StringBuilder("create table " + nameTable + " (");

            for (int i = 1; i <= resultSetMicrosoft.getMetaData().getColumnCount(); i++) {
                if (i == 1) createTableAndCollomns.append("_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ");

                if (i == resultSetMicrosoft.getMetaData().getColumnCount()) {
                    createTableAndCollomns.append(resultSetMicrosoft.getMetaData().getColumnName(i));
                    createTableAndCollomns.append(" ").append(resultSetMicrosoft.getMetaData().getColumnTypeName(i));
                } else {
                    createTableAndCollomns.append(resultSetMicrosoft.getMetaData().getColumnName(i));
                    createTableAndCollomns.append(" ").append(resultSetMicrosoft.getMetaData().getColumnTypeName(i)).append(", ");
                }
            }
            createTableAndCollomns.append(")");
            System.out.println("\n" + createTableAndCollomns);

            SQLight.executeUpdate(createTableAndCollomns.toString());

        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessageDialog(e.getLocalizedMessage());
        }
    }

    private void showErrorMessageDialog(String exceptionMessage) {
        JOptionPane.showMessageDialog(null, exceptionMessage);
    }

    private void writeData(String name) {
        try {
            String SQL3 = "";
            int whileCount = 0;
            String start = "INSERT INTO " + name + " VALUES ";
            String values;
            while (resultSetMicrosoft.next()) {
                if (whileCount == 0) {
                    SQL3 = start;
                }
                whileCount++;
                values = getValues();
                if (whileCount == 100) {
                    SQL3 += values + ";";
                    SQLight.executeUpdate(SQL3);
                    whileCount = 0;
                } else {
                    SQL3 += values + ", ";
                }
            }
            SQL3 = SQL3.substring(0, SQL3.length() - 2) + ";";
            System.out.println("\n" + SQL3);
            SQLight.executeUpdate(SQL3);

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessageDialog(e.getLocalizedMessage());
        }
        long time = (new Date().getTime() - timeStart.getTime()) / 1000;

        System.out.println("\n=========================================================");
        System.out.println(name + " -- complete !\n TIME: " + time);
        System.out.println("=========================================================\n");
    }


    private String getValues() {
        StringBuilder values = new StringBuilder("(null,");
        String change;
        int myCount = 0;
        try {
            for (int i = 1; i <= resultSetMicrosoft.getMetaData().getColumnCount(); i++) {
                myCount++;
                if (resultSetMicrosoft.getString(i).contains("'")) {
                    change = resultSetMicrosoft.getString(i).trim();
                    change = change.replace("'", "`");
                    values.append("\'").append(change).append("\',");
                } else if (myCount == resultSetMicrosoft.getMetaData().getColumnCount()) {
                    values.append("\'").append(resultSetMicrosoft.getString(i).trim()).append("\')");
                    myCount = 0;
                } else {
                    values.append("\'").append(resultSetMicrosoft.getString(i).trim()).append("\'").append(",");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorMessageDialog(e.getLocalizedMessage());
        }
        return values.toString();
    }
}