import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

class CustomChooser extends JFrame {
    private static final String TITLE = "Введите данные для подключения к базе данных:";
    private static final String URL = "DB URL (например 'mssql4.1gb'):";
    private static final String DB_NAME = "DB name (например '1gb_x_khm'):";
    private static final String USERNAME = "Enter username (например '1gb_khm'):";
    private static final String PASSWORD = "Enter password (например 'd65c566d4789'):";
    private static final String CONNECT = "Connect and convert >";
    private static final String PUT_PATH = "Куда положить сконвертированую базу данных:";
    private static final String WHERE_TO = "Куда сконвертировать:";
    private static final String LOCAL_DB_NAME = "Введите имя сконвертированого файла:\n( например 'database_CHMELNYTSKY' )";

    CustomChooser() {
        super();
        String localDirectory = System.getProperty("user.dir");

// MAIN CONTAINER >>
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(8, 1, 1, 5));

// URL >>
        container.add(new JLabel(URL));
        final JTextField url = new JTextField();
        container.add(url);

// DB_NAME >>
        container.add(new JLabel(DB_NAME));
        final JTextField dbName = new JTextField();
        container.add(dbName);

// USERNAME >>
        container.add(new JLabel(USERNAME));
        final JTextField username = new JTextField();
        container.add(username);

// PASSWORD >>
        container.add(new JLabel(PASSWORD));
        final JTextField password = new JTextField();
        container.add(password);

// LOCAL_DB_NAME >>
        container.add(new JLabel(LOCAL_DB_NAME));
        final JTextField localDbName = new JTextField();
        container.add(localDbName);

// FILE_CHOOSER >>
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(localDirectory));
        fileChooser.setDialogTitle(PUT_PATH);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

// CHOOSERS  TEXT_AREA & BUTTON >>
        JLabel pathText = new JLabel(localDirectory);
        JButton pathButton = new JButton(WHERE_TO);
        pathButton.addActionListener((action) -> {
            fileChooser.showOpenDialog(null);
            pathText.setText(fileChooser.getSelectedFile().getAbsolutePath());
        });
        container.add(pathButton);
        container.add(pathText);

// ADD ELEMENTS >>
        this.add(container, BorderLayout.CENTER);

// CONNECT BUTTON >>
        JButton startButton = new JButton(CONNECT);
        container.add(new JLabel());
        container.add(new JLabel());
        container.add(new JLabel());
        container.add(startButton);

        startButton.addActionListener((action) -> {
            String urlText = url.getText();
            String dbNameText = dbName.getText();
            String usernameText = username.getText();
            String passwordText = password.getText();

            String path = pathText.getText();
            String localDbNameText = localDbName.getText();

            boolean OK = verifyData(urlText, dbNameText, usernameText, passwordText, path, localDbNameText);
            if (OK) {
                try {

                    String microsoftJDBCURL = String.format("jdbc:sqlserver://%s;instance=MSSQLSERVER;database=%s", urlText, dbNameText);
                    String localJDBCPath = String.format("jdbc:sqlite:%s/%s", path, localDbNameText + ".db");

                    System.out.println(localJDBCPath);

                    Statement statementMicrosoft = DriverManager.getConnection(microsoftJDBCURL, usernameText, passwordText).createStatement();
                    Statement statementSQLight = DriverManager.getConnection(localJDBCPath).createStatement();

                    new DataRepository().convertData(statementMicrosoft, statementSQLight);

                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
                    System.exit(0);
                }
                JOptionPane.showMessageDialog(null, "Готово!\nПуть к файлу:\n" + path);
                System.exit(0);
            } else JOptionPane.showMessageDialog(null, "Не заполнены обязательные поля!");
        });


        setTitle(TITLE);
        setSize(1024, 300);
        setMinimumSize(new Dimension(800, 200));
        setMaximumSize(new Dimension(1200, 350));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    private boolean verifyData(String... args) {
        return Arrays.stream(args).noneMatch(e -> e == null || e.isEmpty());
    }
}