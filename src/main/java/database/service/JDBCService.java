package database.service;

import Entity.Customer;
import Entity.StatisticPurchase;
import database.interfaces.DbConnector;
import exception.ResultException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class JDBCService {

    private DbConnector connector;

    public JDBCService(DbConnector connector) {
        this.connector = connector;
    }

    // Поиск покупателей с фамилией
    public List<Customer> findByLastName(String lastName) throws ResultException {
        if (connector == null || lastName == null || lastName.isEmpty())
        lastName = lastName.replaceAll("[^0-9a-zA-Zа-яА-Я_-]", "");
        final String SQL_QUERY_FINDBYLASTNAME = "SELECT \"Customers_ID\", \"LastName\", \"FirstName\" " +
                "FROM public.\"Customers\" where \"LastName\" = '" + lastName + "';";
        return resultFormation(connector.executeQuery(SQL_QUERY_FINDBYLASTNAME));
    }

    public List<Customer> findBuyMoreThan(String productName, int minTimes) throws ResultException {
        if (connector == null || productName == null || productName.isEmpty() || minTimes < 0) return null;
        productName = productName.replaceAll("[^0-9a-zA-Zа-яА-Я_-]", "");
        final String SQL_QUERY_FINDBUYMORETHAN = "SELECT \"Customers_ID\", \"LastName\", \"FirstName\" " +
                "FROM ((public.\"Customers\" join public.\"Purchases\" using (\"Customers_ID\")) join public.\"Products\" using (\"Products_ID\")) " +
                "where \"Name\" = '" + productName + "' group by \"Customers_ID\", \"FirstName\", \"LastName\", \"Name\" " +
                "having count(*) >= " + minTimes +  ";";
        return resultFormation(connector.executeQuery(SQL_QUERY_FINDBUYMORETHAN));
    }

    public List<Customer> findByMinMaxPurchaseValue(Double minExpenses, Double maxExpenses) throws ResultException {
        if (connector == null) return null;
        String SQL_QUERY_FINDBYMINMAXPURCHASEVALUE = "with All_Purchases (\"Customers_ID\", \"FirstName\", \"LastName\", Sum_cost) as " +
                "(SELECT \"Customers_ID\", \"FirstName\", \"LastName\", sum(\"Cost\") as Sum_cost " +
                "FROM ((public.\"Customers\" join public.\"Purchases\" using (\"Customers_ID\")) join public.\"Products\" using (\"Products_ID\")) " +
                "group by \"Customers_ID\", \"FirstName\", \"LastName\" order by Sum_cost)" +
                "select \"Customers_ID\", \"LastName\", \"FirstName\" from All_Purchases " +
                "where Sum_cost between " + minExpenses + " and " + maxExpenses + ";";
        return resultFormation(connector.executeQuery(SQL_QUERY_FINDBYMINMAXPURCHASEVALUE));
    }

    public List<Customer> findLeastBought(int limitResult) throws ResultException {
        if (connector == null) return null;
        String SQL_QUERY_FINDLISTBOUGHT = "SELECT \"Customers_ID\", \"LastName\", \"FirstName\" " +
                "FROM ((public.\"Customers\" left join public.\"Purchases\" using (\"Customers_ID\")) left join public.\"Products\" using (\"Products_ID\")) " +
                "group by \"Customers_ID\", \"LastName\", \"FirstName\" order by coalesce(count(\"Products_ID\"),0) limit " + limitResult + ";";
        return resultFormation(connector.executeQuery(SQL_QUERY_FINDLISTBOUGHT));
    }

    public List<Customer> getStatistic(Date startDate, Date endDate) throws ResultException {
        if (startDate == null || endDate == null) return null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String SQL_QUERY__GETSTATISTIC = "select \"Customers_ID\" ,\"LastName\", \"FirstName\", \"Name\" , sum (\"Cost\") as \"Expenses\" " +
                "from ((public.\"Customers\" join public.\"Purchases\" using (\"Customers_ID\")) join public.\"Products\" using (\"Products_ID\")) " +
                "where \"Date_Purchase\" between '" + dateFormat.format(startDate) + "' and '" + dateFormat.format(endDate) + "' " +
                "group by \"Customers_ID\", \"FirstName\", \"LastName\", \"Name\" order by \"FirstName\", \"LastName\", \"Expenses\" desc";
        return resultFormation(connector.executeQuery(SQL_QUERY__GETSTATISTIC));
    }

    private List<Customer> resultFormation(Map<String, List<String>> resultQuery) {
        if (resultQuery == null || resultQuery.isEmpty()) return null;
        Iterator <List<String>> iterator = resultQuery.values().iterator();
        int countCustomer = (iterator.hasNext()) ? iterator.next().size() : 0;
        String customer_ID = "";
        Customer customer = null;
        List <StatisticPurchase> listStatisticPurchase = null;
        List<Customer> result = new ArrayList<>();
        for (int i = 0; i < countCustomer; i++) {
            if (!customer_ID.equals(resultQuery.get("Customers_ID").get(i))) {
                customer_ID = resultQuery.get("Customers_ID").get(i);
                if (customer != null) {
                    if (listStatisticPurchase != null) customer.setStatisticPurchases(listStatisticPurchase);
                    result.add(customer);
                    listStatisticPurchase = null;
                    customer = null;
                }
                if (resultQuery.containsKey("FirstName") && resultQuery.containsKey("LastName")) {
                    customer = new Customer();
                    customer.setFirstName(resultQuery.get("FirstName").get(i));
                    customer.setLastName(resultQuery.get("LastName").get(i));
                }
            }
            if (resultQuery.containsKey("Name") && resultQuery.containsKey("Expenses")) {
                if (listStatisticPurchase == null) listStatisticPurchase = new ArrayList<>();
                String StatisticPurchasesName = resultQuery.get("Name").get(i);
                String StatisticPurchasesExpenses = resultQuery.get("Expenses").get(i);
                listStatisticPurchase.add(
                        new StatisticPurchase(StatisticPurchasesName, Double.valueOf(StatisticPurchasesExpenses)));
            }
        }
        if (customer != null) {
            if (listStatisticPurchase != null) customer.setStatisticPurchases(listStatisticPurchase);
            result.add(customer);
        }
        return result;
    }
}
