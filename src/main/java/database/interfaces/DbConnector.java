package database.interfaces;

import exception.ResultException;

import java.util.List;
import java.util.Map;

public interface DbConnector {
    Map<String, List<String>> executeQuery(String sql) throws ResultException;
}
