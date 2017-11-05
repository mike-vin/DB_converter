import javax.swing.*;

public class Main {
    private static String[] CITY = {" ВИННИЦА ", " ХМЕЛЬНИЦКИЙ ", " ЖИТОМИР ", " ДРУГУЮ > "};

    public static void main(String[] args) {
        try {

            CityType CITY_TYPE = getCityFromUser();

            new ConnectorService().convert(CITY_TYPE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        }
    }

    private static CityType getCityFromUser() {
        int city = JOptionPane.showOptionDialog(
                new JFrame(),
                "какую базу данных нужно конвертировать ?",
                "Выберите базу данных:",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                CITY,
                CITY[0]);

        assert CITY.length == CityType.values().length;

        return CityType.values()[city];
    }
}