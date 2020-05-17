package Entity;

public class StatisticPurchase {

    String name;
    String expenses;

    public StatisticPurchase(String name, String expenses) {
        this.name = name;
        this.expenses = expenses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpenses() {
        return expenses;
    }

    @Override
    public String toString() {
        return "StatisticPurchase{" +
                "name='" + name + '\'' +
                ", expenses='" + expenses + '\'' +
                '}';
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }
}
