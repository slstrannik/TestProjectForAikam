package Entity;

import java.util.List;

public class Customer {
    String firstName;
    String lastName;
    List<StatisticPurchase> statisticPurchases;

    public Customer(String firstName, String lastName, List<StatisticPurchase> statisticPurchases) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.statisticPurchases = statisticPurchases;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", statisticPurchases=" + statisticPurchases +
                '}';
    }

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<StatisticPurchase> getStatisticPurchases() {
        return statisticPurchases;
    }

    public void setStatisticPurchases(List<StatisticPurchase> statisticStatisticPurchases) {
        this.statisticPurchases = statisticStatisticPurchases;
    }
}
