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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category other = (Category) obj;
        if (name.equals(other.getName())) {
            return true;
        } else return false;
    }

}
