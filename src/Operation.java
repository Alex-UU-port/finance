import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Operation {
    private double amount;
    private boolean isIncome;
    private Category category;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    public Operation() {}
    
    public Operation(boolean isIncome, Category category, double amount, LocalDate date) {
        this.isIncome = isIncome;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public boolean getIsIncome() {
        return isIncome;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void printOperation() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String strIsIncome = isIncome ? "Доход" : "Расход";
        System.out.println("Тип операции: " + strIsIncome + "\t\tСумма операции: " + amount +
                "\t\tКатегория: " + category.getName() +
                "\t\tДата совершения операции: " + date.format(formatter));
    }
}