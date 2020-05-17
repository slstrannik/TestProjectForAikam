package database.interfaces;

import java.util.List;
import java.util.Map;

public interface DbConnector {
    Map<String, List<String>> executeQuery(String sql);
}
