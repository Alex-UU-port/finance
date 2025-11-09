import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Wallet {

    private List<Operation> operations = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    //private double balance = countBalance();

    public Wallet() {

        //в случае создания нового пользователя, несколько категорий добавляются по умолчанию
        Category salary = new Category("Зарплата");
        Category bonus = new Category("Бонус");
        Category food = new Category("Еда", 10000);
        Category medicine = new Category("Медицина", 10000);

        categories.add(salary);
        categories.add(bonus);
        categories.add(food);
        categories.add(medicine);

        //так же добавляем некоторые операции
        /*
        LocalDate date = LocalDate.of(2025, 11, 1);
        Operation opSalary = new Operation (true, salary, 50000, date);
        Operation opBonus = new Operation (true, bonus, 5000, date);
        date = LocalDate.of(2025, 11,2);
        Operation opFood = new Operation (false, food, 3000, date);
        Operation opMedicine = new Operation (false, salary, 45000, date);

        operations.add(opSalary);
        operations.add(opBonus);
        operations.add(opFood);
        operations.add(opMedicine);
         */

    }

    //public double getBalance() {
    //    return balance;
    //}

    public List<Category> getCategories() {
        return categories;
    }

    public List<Operation> getOperation() {
        return operations;
    }

    public double countBalance() {
        double balance = 0;
        for (Operation operation : operations) {
            if (operation.getIsIncome()) {
                balance += operation.getAmount();
            } else {
                balance -= operation.getAmount();
            }
        }
        return balance;
    }

}

