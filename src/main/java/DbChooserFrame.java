import javax.swing.*;
import java.awt.*;
import java.io.File;


class DbChooserFrame extends JFrame {
    private static final String TITLE = "Введите данные для подключения к базе данных:";
    private static final String URL = "DB URL (например 'mssql4.1gb'):";
    private static final String DB_NAME = "DB name (например '1gb_x_khm'):";
    private static final String USERNAME = "Enter username (например '1gb_khm'):";
    private static final String PASSWORD = "Enter password (например 'd65c566d4789'):";
    private static final String CONNECT = "Connect and convert >";
    private static final String PUT_PATH = "Куда положить сконвертированую базу данных:";
    private static final String WHERE_TO = "Куда сконвертировать:";
    private static final String LOCAL_DB_NAME = "Введите имя сконвертированого файла:\n( например 'database_CHMELNYTSKY' )";
    private static PropertiesContainer propertiesContainer;

    private DbChooserFrame() {
        super();
        setTitle(TITLE);
        this.setLocation(600, 600);
        setSize(700, 200);
        setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);

// MAIN CONTAINER >>
        JPanel container = new JPanel();
        container.setSize(50, 50);
        container.setLayout(new GridLayout(7, 1));

// URL >>
        container.add(new JLabel(URL));
        final JTextField url = new JTextField();
        url.setSize(50, 50);
        container.add(url);

// DB_NAME >>
        container.add(new JLabel(DB_NAME));
        final JTextField dbName = new JTextField();
        url.setSize(50, 50);
        container.add(dbName);

// USERNAME >>
        container.add(new JLabel(USERNAME));
        final JTextField userName = new JTextField();
        userName.setSize(50, 50);
        container.add(userName);

// PASSWORD >>
        container.add(new JLabel(PASSWORD));
        final JTextField passwordt = new JTextField();
        passwordt.setSize(50, 50);
        container.add(passwordt);

// LOCAL_DB_NAME >>
        container.add(new JLabel(LOCAL_DB_NAME));
        final JTextField localDbName = new JTextField();
        localDbName.setSize(50, 50);
        container.add(localDbName);

// FILE_CHOOSER >>
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle(PUT_PATH);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

// CHOOSERS  TEXT_AREA & BUTTON >>
        JLabel pathText = new JLabel(" /... ");
        JButton pathButton = new JButton(WHERE_TO);
        pathButton.setSize(50, 50);
        pathButton.addActionListener((action) -> {
            fileChooser.showOpenDialog(null);
            pathText.setText(fileChooser.getSelectedFile().getAbsolutePath());
        });
        container.add(pathButton);
        container.add(pathText);

// ADD ELEMENTS >>
        this.add(container, BorderLayout.CENTER);

// BOTTOM BUTTON CONNECT >>
        JButton startButton = new JButton(CONNECT);
        this.add(startButton, BorderLayout.AFTER_LAST_LINE);
        startButton.addActionListener((action) -> {
            String urlText = url.getText();
            String dbNameText = dbName.getText();
            String userNameText = userName.getText();
            String passwordtText = localDbName.getText();
            String localDbNameText = localDbName.getText();
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            propertiesContainer = new PropertiesContainer(urlText, dbNameText, userNameText, passwordtText, path, localDbNameText);
            setVisible(false);
        });
    }

    public static PropertiesContainer getProperties() {
        new DbChooserFrame();
        return propertiesContainer;
    }
}