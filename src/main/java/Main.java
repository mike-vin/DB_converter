import config.RootConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        System.out.println("selectDBfromUSER = " + OldSchoolMain.selectDBfromUSER());
        System.out.println("getAbsolutePath = " + OldSchoolMain.getAbsolutePath());
    }
}
