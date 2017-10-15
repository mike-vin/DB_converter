
import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OldSchoolMain {
    static Connector sql = new Connector(), sqlDB = new Connector();
    private static int TYPE_DB_FROMUSER;
    private static int VINNITSYA = 0;
    static int CHMELNYTSKY = 1;
    private static String[] CITY = {"В И Н Н И Ц А", "Х М Е Л Ь Н И Ц К И Й"};

    public static void main(String[] args) {
        TYPE_DB_FROMUSER = selectDBfromUSER();

        sql.connectServer(TYPE_DB_FROMUSER);
        sqlDB.connectDB(TYPE_DB_FROMUSER, getAbsolutePath());

        Data.copyDATA();

        JOptionPane.showMessageDialog(null, "Исполнено, мой повелитель!");
        System.exit(0);
    }

    public static String getAbsolutePath() {
        String pathString = System.getProperties().getProperty("user.dir");
        Path path = Paths.get(pathString);
        //if (path.getNameCount() > 1) path = path.getParent();
        return path.toString();
    }

    public static int selectDBfromUSER() {
        TYPE_DB_FROMUSER = JOptionPane.showOptionDialog(new JFrame(), "какую базу данных нужно обноаить ?", "Вымерите тип БД:",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, CITY, CITY[0]);
        return TYPE_DB_FROMUSER;
    }
}