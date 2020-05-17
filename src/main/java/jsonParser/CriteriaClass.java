package jsonParser;

import java.util.Date;

public class CriteriaClass {
    String lastName;
    String productName;
    Integer minTimes;
    Double minExpenses;
    Double maxExpenses;
    Integer badCustomers;
    Date startDate;
    Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public CriteriaClass() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getMinTimes() {
        return minTimes;
    }

    public void setMinTimes(Integer minTimes) {
        this.minTimes = minTimes;
    }

    public Double getMinExpenses() {
        return minExpenses;
    }

    public void setMinExpenses(Double minExpreses) {
        this.minExpenses = minExpreses;
    }

    public Double getMaxExpenses() {
        return maxExpenses;
    }

    public void setMaxExpenses(Double maxExprenses) {
        this.maxExpenses = maxExprenses;
    }

    public Integer getBadCustomers() {
        return badCustomers;
    }

    public void setBadCustomers(Integer badCustomers) {
        this.badCustomers = badCustomers;
    }

    public boolean isEmpty() {
        return (lastName == null &&
                productName == null &&
                minTimes == null &&
                minExpenses == null &&
                maxExpenses == null &&
                badCustomers == null &&
                startDate == null &&
                endDate == null);
    }
    @Override
    public String toString() {
        return "CriteriaClass{" +
                "lastName='" + lastName + '\'' +
                ", productName='" + productName + '\'' +
                ", minTimes=" + minTimes +
                ", minExpenses=" + minExpenses +
                ", maxExpenses=" + maxExpenses +
                ", badCustomers=" + badCustomers +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
