package database.service;

import database.interfaces.DbConnector;
import exception.ResultException;

import java.sql.*;
import java.util.*;

public class PostgreSqlDBConnector implements DbConnector {
    String dbURL;
    String user;
    String pass;

    Connection connection;
    public PostgreSqlDBConnector(String dbURL, String user, String pass) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found");
        }
        this.dbURL = dbURL;
        this.user = user;
        this.pass = pass;

        try {
            connection = DriverManager.getConnection(dbURL, user, pass);
        } catch (SQLException e) {
            System.out.println("Connection failed");
            connection = null;
        }
    }

    public Map<String, List<String>> executeQuery(String sql) throws ResultException {
        if (connection == null) return null;
        PreparedStatement statement = null;
        Map<String, List<String>> result = null;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            result = resultFormation(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            throw new ResultException(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Close error: prepareStatement");
                }
            }
        }
        return result;
    }

    private Map<String, List<String>> resultFormation(ResultSet resultSet) throws SQLException {
        Map<String, List<String>> result = new LinkedHashMap<>();
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            result.put(resultSet.getMetaData().getColumnName(i), new ArrayList<>());
        }
        while (resultSet.next()) {
            for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                entry.getValue().add(
                        resultSet.getString(
                                entry.getKey()));
            }
        }
        return result;
    }
}
