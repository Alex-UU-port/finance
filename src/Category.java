public class Category {
    private String name;
    private double budget;
    //private double spent;

    public Category(String name) {
        this.name = name;
    }

    public Category() {}

    public Category(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }

    //public double getSpent() {
    //    return spent;
    //}

    public void printCategory() {
        System.out.println("Имя категории: " + name + "\t\tМесячный бюджет: " + budget);
    }

}
