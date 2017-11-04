import javax.swing.*;

public class Main {
    private static CityType CITY_TYPE;
    private static String[] CITY = {" ВИННИЦА ", " ХМЕЛЬНИЦКИЙ ", " ЖИТОМИР ", " ДРУГУЮ > "};

    public static void main(String[] args) {
        try {

            CITY_TYPE = getCityFromUser();
            ConnectorService connector = new ConnectorService();
            DataRepository repository = new DataRepository();

            repository.convertData(connector.getMicrosoftStatement(CITY_TYPE), connector.getSQLightStatement(CITY_TYPE));

            JOptionPane.showMessageDialog(null, "Исполнено, мой повелитель!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
        System.exit(0);
    }

    private static CityType getCityFromUser() {
        int city = JOptionPane.showOptionDialog(new JFrame(), "какую базу данных нужно конвертировать ?", "Выберите базу данных:",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, CITY, CITY[0]);
        assert CITY.length == CityType.values().length;
        return CityType.values()[city];
    }
}