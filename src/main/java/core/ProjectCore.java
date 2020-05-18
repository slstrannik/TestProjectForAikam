package core;

import Entity.Customer;
import database.interfaces.DbConnector;
import database.service.JDBCService;
import exception.ResultException;
import jsonParser.*;

import java.sql.ResultSet;
import java.util.List;

public class ProjectCore {

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


    public void start() {
        if (jdbcService == null ||
                input == null ||
                output == null ||
                (!arg.equalsIgnoreCase(CriteriaTypeEnum.STAT.toString()) && !arg.equalsIgnoreCase(CriteriaTypeEnum.SEARCH.toString()))) {
            System.out.println("Error in arguments");
            return;
        }
        JsonInputCriteriasParser jsonInputCriteriasParser = new JsonInputCriteriasParser(input);
        List<CriteriaClass> criteriaClassList = null;
        try {
            criteriaClassList = jsonInputCriteriasParser.process();
            JsonOutputDataBuilder jsonOutputDataParser = new JsonOutputDataBuilder(CriteriaTypeEnum.valueOf(arg.toUpperCase()));
            if (arg.equalsIgnoreCase(CriteriaTypeEnum.SEARCH.toString())) {
                for (CriteriaClass criteriaClass : criteriaClassList) {
                    List<Customer> customerList = criteriaProcessing(criteriaClass);
                    if (customerList != null) jsonOutputDataParser.add(criteriaClass, customerList);
                }
            } else if (arg.equalsIgnoreCase(CriteriaTypeEnum.STAT.toString())) {
                CriteriaClass criteriaClass = criteriaClassList.get(0);
                List<Customer> customerList = statisticProcessing(criteriaClass);
                if (customerList != null) jsonOutputDataParser.add(criteriaClass, customerList);
            }
            SaveJsonOutputDataClass saveJsonOutputDataClass = new SaveJsonOutputDataClass(output, jsonOutputDataParser);
            saveJsonOutputDataClass.save();
        } catch (ResultException e) {
            SaveJsonOutputDataClass saveJsonOutputDataClass = new SaveJsonOutputDataClass(
                    output, new JsonErrorOutputBuilder(e.getMessage()));
            saveJsonOutputDataClass.save();
        }
    }

    private List<Customer> criteriaProcessing(CriteriaClass criteriaClass) throws ResultException {
        List<Customer> customerList = null;
        if (criteriaClass.getBadCustomers() != null) {
            customerList = jdbcService.findLeastBought(criteriaClass.getBadCustomers());
        } else if(criteriaClass.getLastName() != null) {
            customerList = jdbcService.findByLastName(criteriaClass.getLastName());
        } else if (criteriaClass.getProductName() != null && criteriaClass.getMinTimes() != null) {
            customerList = jdbcService.findBuyMoreThan(criteriaClass.getProductName(), criteriaClass.getMinTimes());
        } else if (criteriaClass.getMinExpenses() != null && criteriaClass.getMaxExpenses() != null) {
            customerList = jdbcService.findByMinMaxPurchaseValue(criteriaClass.getMinExpenses(), criteriaClass.getMaxExpenses());
        }
        return customerList;
    }

    private List<Customer> statisticProcessing(CriteriaClass criteriaClass) throws ResultException {
        List<Customer> customerList = null;
        if (criteriaClass.getStartDate() != null && criteriaClass.getEndDate() != null) {
            customerList = jdbcService.getStatistic(criteriaClass.getStartDate(), criteriaClass.getEndDate());
        } else throw new ResultException("Time interval is set incorrectly");
        return  customerList;
    }
}
