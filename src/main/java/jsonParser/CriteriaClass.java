package jsonParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public String toJson() {
        StringBuilder jsonStringBuilder = new StringBuilder("{");
        if (lastName != null) {
            jsonStringBuilder.append("\"lastName\":");
            jsonStringBuilder.append("\"").append(lastName).append("\"");
            jsonStringBuilder.append(",");
        }
        if (productName != null) {
            jsonStringBuilder.append("\"productName\":");
            jsonStringBuilder.append("\"").append(productName).append("\"");
            jsonStringBuilder.append(",");
        }
        if (minTimes != null) {
            jsonStringBuilder.append("\"minTimes\":");
            jsonStringBuilder.append(minTimes);
            jsonStringBuilder.append(",");
        }
        if (minExpenses != null) {
            jsonStringBuilder.append("\"minExpenses\":");
            jsonStringBuilder.append(minExpenses);
            jsonStringBuilder.append(",");
        }
        if (maxExpenses != null) {
            jsonStringBuilder.append("\"maxExpenses\":");
            jsonStringBuilder.append(maxExpenses);
            jsonStringBuilder.append(",");
        }
        if (badCustomers != null) {
            jsonStringBuilder.append("\"badCustomers\":");
            jsonStringBuilder.append(badCustomers);
            jsonStringBuilder.append(",");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (startDate != null) {
            jsonStringBuilder.append("\"startDate\":");
            jsonStringBuilder.append("\"").append(dateFormat.format(startDate)).append("\"");
            jsonStringBuilder.append(",");
        }
        if (endDate != null) {
            jsonStringBuilder.append("\"endDate\":");
            jsonStringBuilder.append("\"").append(dateFormat.format(endDate)).append("\"");
            jsonStringBuilder.append(",");
        }
        if (!isEmpty()) {
            int index = jsonStringBuilder.lastIndexOf(",");
            jsonStringBuilder.delete(index, index + 1);
        }
        jsonStringBuilder.append("}");
        return jsonStringBuilder.toString();
    }
}
