import javax.swing.*;
import java.nio.file.Paths;

public class Main {
    static Connector sql, sqlDB;
    static CityType CITY_TYPE;
    static String[] CITY = {"В И Н Н И Ц А", "Х М Е Л Ь Н И Ц К И Й", "Ж И Т О М И Р"};

    public static void main(String[] args) {
        try {
            sql = new Connector();
            sqlDB = new Connector();

            CITY_TYPE = selectDBfromUSER();

            sql.connectServer(CITY_TYPE);
            sqlDB.connectDB(CITY_TYPE, getLocalPath());

            Data.copyDATA();

            JOptionPane.showMessageDialog(null, "Исполнено, мой повелитель!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
        System.exit(0);
    }

    private static String getLocalPath() {
        return Paths.get(System.getProperties().getProperty("user.dir")).getParent().toString();
    }

    private static CityType selectDBfromUSER() {
        int city = JOptionPane.showOptionDialog(new JFrame(), "какую базу данных нужно обноаить ?", "Выберите тип БД:",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, CityType.values(), CityType.values()[0]);
        return CityType.values()[city];
    }
}