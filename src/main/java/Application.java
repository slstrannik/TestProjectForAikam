import core.ProjectCore;
import database.interfaces.DbConnector;
import database.service.PostgreSqlDBConnector;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Application {
    public static void main(String[] args) {
        String properiesFileName = "application.properties";
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(properiesFileName));
        } catch (IOException e) {
            System.out.println("Could not open properties file for setting database:" + properiesFileName);
            return;
        }
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String pass = properties.getProperty("db.pass");
        if (url == null || url.isEmpty()) System.out.println("field 'db.url' not found");
        else if (user == null || user.isEmpty()) System.out.println("field 'db.user' not found");
        else if (pass == null || pass.isEmpty()) System.out.println("field 'db.pass' not found");
        else {
            DbConnector dbconnector = new PostgreSqlDBConnector(url, user, pass);
            ProjectCore projectCore = new ProjectCore(args, dbconnector);
            projectCore.start();
        }
    }
}
