package jsonParser;

import Entity.Customer;
import Entity.StatisticPurchase;
import core.CriteriaTypeEnum;

import javax.json.*;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JsonOutputDataBuilder implements JsonOutputBuilder {
    CriteriaTypeEnum type;

    JsonObjectBuilder rootJsonBuilder;
    JsonArrayBuilder resultsArrayJsonBuilder;

    public JsonOutputDataBuilder(CriteriaTypeEnum type) {
        this.type = type;
        rootJsonBuilder = Json.createObjectBuilder();
        rootJsonBuilder.add("type", type.getValue());
        resultsArrayJsonBuilder = Json.createArrayBuilder();
    }

    private void addStatResult(CriteriaClass criteriaClass, List<Customer> customerList) {
        long intervalInMilliseconds = criteriaClass.getEndDate().getTime() - criteriaClass.getStartDate().getTime();
        long intervalInDays = TimeUnit.DAYS.convert(intervalInMilliseconds, TimeUnit.MILLISECONDS);
        rootJsonBuilder.add("totalDays", intervalInDays);
        JsonArrayBuilder customersJsonArray = Json.createArrayBuilder();
        double sumAllPurchasesCustomer = 0.00;
        double sumPurchasesAllCustomers = 0.00;
        for (Customer customer: customerList) {
            JsonObjectBuilder customersJsonBuilder = Json.createObjectBuilder();
            customersJsonBuilder.add("name", customer.getLastName() + " " + customer.getFirstName());
            JsonArrayBuilder statisticPurchaseJsonBuilder = Json.createArrayBuilder();
            for (StatisticPurchase statisticPurchase: customer.getStatisticPurchases()) {
                JsonObjectBuilder purchaseArrayJsonBuilder = Json.createObjectBuilder();
                purchaseArrayJsonBuilder.add("name", statisticPurchase.getName());
                purchaseArrayJsonBuilder.add("expenses", statisticPurchase.getExpenses());
                sumAllPurchasesCustomer += statisticPurchase.getExpenses();
                statisticPurchaseJsonBuilder.add(purchaseArrayJsonBuilder.build());
            }
            customersJsonBuilder.add("purchases", statisticPurchaseJsonBuilder.build());
            customersJsonBuilder.add("totalExpenses", sumAllPurchasesCustomer);
            sumPurchasesAllCustomers += sumAllPurchasesCustomer;
            sumAllPurchasesCustomer = 0.00;
            customersJsonArray.add(customersJsonBuilder.build());
        }
        rootJsonBuilder.add("customers", customersJsonArray.build());
        rootJsonBuilder.add("totalExpenses", sumPurchasesAllCustomers);
        sumPurchasesAllCustomers /= customerList.size();
        rootJsonBuilder.add("avgExpenses", sumPurchasesAllCustomers);
    }

    private void addCriteriaResult(CriteriaClass criteriaClass, List<Customer> customerList) {
        JsonObjectBuilder criteriaObjectBuilder = Json.createObjectBuilder();
        JsonReader JsonReader = Json.createReader(new StringReader(criteriaClass.toJson()));
        criteriaObjectBuilder.add("criteria", JsonReader.readObject());
        JsonArrayBuilder resultsArrayBuilder = Json.createArrayBuilder();
        for (Customer customer : customerList) {
            JsonObjectBuilder customerObjectBuilder = Json.createObjectBuilder();
            customerObjectBuilder.add("lastName", customer.getLastName());
            customerObjectBuilder.add("firstName", customer.getFirstName());
            resultsArrayBuilder.add(customerObjectBuilder.build());
        }
        criteriaObjectBuilder.add("results", resultsArrayBuilder.build());
        resultsArrayJsonBuilder.add(criteriaObjectBuilder);
    }

    public void add(CriteriaClass criteriaClass, List<Customer> customerList) {
        if (type == CriteriaTypeEnum.STAT) {
            addStatResult(criteriaClass, customerList);
        } else if (type == CriteriaTypeEnum.SEARCH) {
            addCriteriaResult(criteriaClass, customerList);
        }
    }

    @Override
    public JsonValue build() {
        JsonValue result = null;
        if (type == CriteriaTypeEnum.STAT) {
            result = rootJsonBuilder.build();
        } else if (type == CriteriaTypeEnum.SEARCH) {
            rootJsonBuilder.add("results", resultsArrayJsonBuilder.build());
            result = rootJsonBuilder.build();
        }
        return result;
    }
}
