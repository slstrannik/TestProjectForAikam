import core.ProjectCore;
import database.interfaces.DbConnector;
import database.service.PostgreSqlDBConnector;
import database.service.JDBCService;
import jsonParser.JsonInputCriteriasParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Application {

    public static void main(String[] args) throws ParseException {
        DbConnector dbconnector =
                new PostgreSqlDBConnector(
                        "jdbc:postgresql://127.0.0.1:5432/dbtest", "postgres", "admin");
        ProjectCore projectCore = new ProjectCore(new String[] {"stat", "input.json", "output.json"}, dbconnector);
        projectCore.start();
    }

}
