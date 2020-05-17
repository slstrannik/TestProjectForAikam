package core;

import Entity.Customer;
import database.interfaces.DbConnector;
import database.service.JDBCService;
import jsonParser.CriteriaClass;
import jsonParser.JsonInputCriteriasParser;

import java.util.List;

public class ProjectCore {

    private final String STAT = "stat";
    private final String SEARCH = "search";

    private JDBCService jdbcService;
    private String input;
    private String output;
    private String arg;

    public ProjectCore(String[] args, DbConnector connector) {
        if (connector != null) this.jdbcService = new JDBCService(connector);
        if (args.length == 3) {
            this.input = args[1];
            this.output = args[2];
            this.arg = args[0];
        }
    }


    public boolean start() {
        if (jdbcService == null ||
                input == null ||
                output == null ||
                (!arg.equalsIgnoreCase(STAT) && !arg.equalsIgnoreCase(SEARCH))) {
            System.out.println("Error in arguments");
            return false;
        }
        JsonInputCriteriasParser jsonInputCriteriasParser = new JsonInputCriteriasParser(input);
        List<CriteriaClass> criteriaClassList = jsonInputCriteriasParser.process();
        if (arg.equals(SEARCH)) {
            criteriaClassList.forEach(criteriaClass -> {
                List<Customer> customerList = criteriaProcessing(criteriaClass);
                if (customerList != null) customerList.forEach(customer -> System.out.println(customer.toString()));
            });
        } else if (arg.equals(STAT)) {
            criteriaClassList.forEach(criteriaClass -> {
                List<Customer> customerList = statisticProcessing(criteriaClass);
                if (customerList != null) customerList.forEach(customer -> System.out.println(customer.toString()));
            });
        }
        return false;
    }

    private List<Customer> criteriaProcessing(CriteriaClass criteriaClass) {
        List<Customer> customerList = null;
        if (criteriaClass.getBadCustomers() != null) {
            customerList = jdbcService.findLeastBought(criteriaClass.getBadCustomers());
        } else if(criteriaClass.getLastName() != null) {
            customerList = jdbcService.findByLastName(criteriaClass.getLastName());
        } else if (criteriaClass.getProductName() != null && criteriaClass.getMinTimes() == null) {
            customerList = jdbcService.findBuyMoreThan(criteriaClass.getProductName(), criteriaClass.getMinTimes());
        } else if (criteriaClass.getMinExpenses() != null && criteriaClass.getMaxExpenses() != null) {
            customerList = jdbcService.findByMinMaxPurchaseValue(criteriaClass.getMinExpenses(), criteriaClass.getMaxExpenses());
        }
        return customerList;
    }

    private List<Customer> statisticProcessing(CriteriaClass criteriaClass) {
        List<Customer> customerList = null;
        if (criteriaClass.getStartDate() != null && criteriaClass.getEndDate() != null) {
            customerList = jdbcService.getStatistic(criteriaClass.getStartDate(), criteriaClass.getEndDate());
        }
        return  customerList;
    }
}
