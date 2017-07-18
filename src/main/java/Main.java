import javax.swing.*;

public class Main {
    static Connector sql, sqlDB;
    static int TYPE_DB_FROMUSER, VINNITSYA = 0, CHMELNYTSKY = 1;
    static String[] CITY = {"В И Н Н И Ц А", "Х М Е Л Ь Н И Ц К И Й"};

    public static void main(String[] args) {
        sql = new Connector();
        sqlDB = new Connector();

        TYPE_DB_FROMUSER = selectDBfromUSER();

        sql.connectServer(TYPE_DB_FROMUSER);
        sqlDB.connectDB(TYPE_DB_FROMUSER);

        Data.copyDATA();
        JOptionPane.showMessageDialog(null, "Исполнено, мой повелитель!");
        System.exit(0);
    }

    private static int selectDBfromUSER() {
        TYPE_DB_FROMUSER = JOptionPane.showOptionDialog(new JFrame(), "какую базу данных нужно обноаить ?", "Вымерите тип БД:",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, CITY, CITY[0]);
        return TYPE_DB_FROMUSER;
    }
}