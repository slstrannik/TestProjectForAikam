package Entity;

public class StatisticPurchase {

    String name;
    Double expenses;

    public StatisticPurchase(String name, Double expenses) {
        this.name = name;
        this.expenses = expenses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getExpenses() {
        return expenses;
    }

    @Override
    public String toString() {
        return "StatisticPurchase{" +
                "name='" + name + '\'' +
                ", expenses='" + expenses + '\'' +
                '}';
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }
}
