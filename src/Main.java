import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static List<User> users;

    public static void main(String[] args) {

        users = User.fromJSON();
        //инициализация пользователей

        while (true) {

            System.out.println("\n\n\nВведите \"1\" для авторизации; \n" +
                    "Введите \"2\" для регистрации; \n" +
                    "Введите \"0\" для выхода.");
            String cmd = scanner.next();

            if (cmd.equals("0")) {
                toExit();
                break;
            } else if (cmd.equals("1")) {

                User activUser = toAuthorization();

                if (activUser!=null) {
                    System.out.println("\n\n\nПривет, " + activUser.getLogin());
                    toRun(activUser);
                } else {
                    System.out.println("Ошибка! Неверный логин или пароль!");
                }

            } else if (cmd.equals("2")) {
                toRegister();
            } else {
                System.out.println("Ошибка, неизвестная команда!");
            }
        }
    }

    public static void toRegister() {
        System.out.println("\nВведите логин: ");
        String log = scanner.next();

        System.out.println("\nВведите пароль: ");
        String pass = scanner.next();

        //проверка на наличие пользователя с таким же именем
        boolean isBe = false;
        for (User user : users) {
            if (log.equals(user.getLogin())) {
                System.out.println("\nПользователь с таким именем уже существует!");
                isBe = true;
                break;
            }
        }
        if (!isBe) {
            User user = new User(log, pass);
            users.add(user);
        }
    }

    public static void toExit() {
        User.saveJSON(users);
    }

    public static double toCountBalance(List<Operation> operation) {
        double balance = 0;
        for (Operation oper : operation) {
            if (oper.getIsIncome()) {
                balance += oper.getAmount();
            } else {
                balance -= oper.getAmount();
            }
        }
        return balance;
    }

    public static List<Operation> toFilter(User user, int year, int month) {
        // Отфильтровываем по году и месяцу
        List<Operation> filteredOperation = new ArrayList<>();
        for (Operation operation : user.getWallet().getOperation()) {
            if ((operation.getDate().getYear() == year) && (operation.getDate().getMonthValue() == month)) {
                filteredOperation.add(operation);
            }
        }
        return filteredOperation;
    }

    public static List<Operation> toFilter(User user, int year, int month, String category) {
        List<Operation> varFiterOperation = toFilter(user, year, month);
        List<Operation> filteredOperation = new ArrayList<>();
        for (Operation operation : varFiterOperation) {
            if (operation.getCategory().equals(category)) {
                filteredOperation.add(operation);
            }
        }
        return filteredOperation;
    }

    public static User toAuthorization() {
        System.out.println("\nВведите логин: ");
        String log = scanner.next();

        System.out.println("\nВведите пароль: ");
        String pass = scanner.next();

        User activUser = null;

        for (User user : users) {
            if (log.equals(user.getLogin()) && pass.equals(user.getPassword())) {
                System.out.println("\nПользователь найден!");
                activUser = user;
                break;
            }
        }
        return activUser;
    }

    private static void toRun(User activUser) {
        while(true) {
            System.out.println("\n\n\n" + activUser.getLogin() + ", \n" +
                    "введите \"1\" для перехода в меню операций; \n" +
                    "введите \"2\" для просмотра и редактирования категорий; \n" +
                    "введите \"0\" для выхода из профиля.");
            String cmd = scanner.next();

            if (cmd.equals("1")) {
                    toOperation(activUser);
            } else if (cmd.equals("2")) {
                    toCategories(activUser);
            } else if (cmd.equals("0")) {
                break;
            } else System.out.println("\nНеизвестная команда");
        }
    }

    private static void toCategories(User activUser) {
        while(true) {
            System.out.println( "\n\n\n" + activUser.getLogin() + ", \n" +
                    "введите \"1\" для просмотра категорий; \n" +
                    "введите \"2\" для добавления категории; \n" +
                    "введите \"3\" для удаления категории; \n" +
                    "введите \"0\" для выхода из меню управления категориями.");
            String cmd = scanner.next();

            if (cmd.equals("1")) {
                for ( Category category : activUser.getWallet().getCategories()) {
                    category.printCategory();
                }
            } else if (cmd.equals("2")) {

                //добавление категории
                System.out.println("Введите имя категории: ");
                String name = scanner.next();

                System.out.println("Введите бюджет: ");
                double budget = 0;
                //ПРОВЕРКА НА ЧИСЛО!!!
                while (true) {
                    String strBudget = scanner.next();
                    try {
                        budget = Double.parseDouble(strBudget);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: не является корректным числом, введите в формате 500.50");
                    }
                }

                //проверка на существование категории
                boolean isBe = false;
                for (Category category : activUser.getWallet().getCategories()) {
                    if (name.equals(category.getName())) {
                        System.out.println("Категория с таким именем уже существует!");
                        isBe = true;
                        break;
                    }
                }
                if (!isBe) {
                    Category category = new Category(name, budget);
                    activUser.getWallet().getCategories().add(category);
                }
            } else if (cmd.equals("3")) {

                //Удаление категории
                System.out.println("Введите имя категории: ");
                String name = scanner.next();

                boolean isBe = false;
                for (Category category : activUser.getWallet().getCategories()) {
                    if (name.equals(category.getName())) {

                        activUser.getWallet().getCategories().remove(category);
                        System.out.println("Категория " + name + " удалена!");
                        isBe = true;
                        break;
                    }
                }
                if (!isBe) {System.out.println("Категория " + name + " не найдена!");}

            } else if (cmd.equals("0")) {
                break;
            } else System.out.println("Неизвестная команда");
        }
    }

    private static void toOperation(User activUser) {
        while(true) {
            System.out.println("\n\n\n" + activUser.getLogin() + ", \n" +
                    "введите \"1\" для добавления операции; \n" +
                    "введите \"2\" для просмотра операций; \n" +
                    "введите \"3\" для отправки денег другу; \n" +
                    "введите \"0\" для выхода из меню управления операциями.");
            String cmd = scanner.next();

            boolean isIncome = false;
            Category category = null;
            double budget = 0;
            if (cmd.equals("1")) {
                //добавление операции
                //выбор типа операции
                while (true) {
                    System.out.println("Введите тип операции \"расход\" или \"доход\" ");
                    String str1isIncome = scanner.next();

                    if (str1isIncome.equals("расход")) {
                        isIncome = false;
                        break;
                    } else if (str1isIncome.equals("доход")) {
                        isIncome = true;
                        break;
                    }
                    System.out.println("\n\n\nНеизвестное значение, введите \"расход\" или \"доход\" ");
                }

                //выбор категрии операции
                while (true) {
                    System.out.println("\n\n\nВведите категорию операции (в соответствии с пользовательскими настройками)");
                    String str2Category = scanner.next();

                    boolean isBe = false;
                    category = null;
                    for (Category cat : activUser.getWallet().getCategories()) {
                        if (str2Category.equals(cat.getName())) {
                            category = cat;
                            isBe = true;
                            break;
                        }

                    }
                    if (isBe) {
                        break;
                    } else {
                        System.out.println("Неизвестная категория!");
                    }
                }

                System.out.println("Введите сумму операции: ");
                //ПРОВЕРКА НА ЧИСЛО!!!
                while(true) {
                    String strBudget = scanner.next();
                    try {
                        budget = Double.parseDouble(strBudget);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: не является корректным числом, введите в формате 500.50");
                    }
                }

                //Ввод даты операции
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date;
                while (true) {
                    System.out.println("\n\n\nВведите дату совершения операции, в формате дд-мм-гггг ");
                    String strDate = scanner.next();
                    try {
                        date = LocalDate.parse(strDate, formatter);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Некорректный формат даты");
                    }
                }

            //добавление операции в список
            Operation operatoin = new Operation(isIncome, category, budget, date);
            activUser.getWallet().getOperation().add(operatoin);

        } else if (cmd.equals("2")) {
                toPrintOperation(activUser);
            } else if (cmd.equals("3")) {

            } else if (cmd.equals("0")) {
                break;
            } else System.out.println("Неизвестная команда");
        }
    }

    private static void toPrintOperation(User activUser) {
        while(true) {
            System.out.println("\n\n\n" + activUser.getLogin() + ", \n" +
                    "введите \"1\" для просмотра всех операций; \n" +
                    "введите \"2\" для сортировки по месяцу и году; \n" +
                    "введите \"3\" для сортировки по категории и месяцу и году; \n" +
                    "введите \"0\" для выхода из меню управления операциями.");
            String cmd = scanner.next();
            if (cmd.equals("1")) {
                //вывод всех операций
                for (Operation operation : activUser.getWallet().getOperation()) {
                    operation.printOperation();
                }
                double balance = activUser.getWallet().countBalance();
                if (balance >= 0) {
                    System.out.println("Итого у Вас " + activUser.getWallet().countBalance() + " рублей ");
                } else System.out.println("Итого у Вас " + activUser.getWallet().countBalance() + " рублей\n" +
                        "ВНИМАНИЕ!!!\nОТРИЦАТЕЛЬНЫЙ БАЛАНС!!!");
            }
            else if (cmd.equals("2")) {

                int year = -1;
                int month = -1;

                //ввод года и месяца
                while(true) {

                    System.out.println("Введите год: ");
                    String strYear = scanner.next();

                    System.out.println("Введите номер месяца: ");
                    String strMonth = scanner.next();

                    //Проверка введенного года на число
                    if (strYear != null && strYear.matches("\\d{4}")) {
                        try {
                            year = Integer.parseInt(strYear);
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    //Проверка введенного месяца на число
                    if (strMonth != null && strMonth.matches("\\d{2}")) {
                        try {
                            month = Integer.parseInt(strMonth);
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    if ((month >= 0) && (month <= 12)) {
                     break;
                    } else {System.out.println("Неверный формат года или месяца!");}
                }

                List<Operation> filterOperation = toFilter(activUser, year, month);
                if (filterOperation.size() != 0) {
                for (Operation operation : filterOperation) {
                    operation.printOperation();
                    }
                    double balance = toCountBalance(filterOperation);
                    if (balance >= 0) {
                        System.out.println("Итого у Вас на " + balance + " рублей доходы превышают расходы в "
                        + month + " месяце " + year + " года");
                    } else System.out.println("Итого у Вас на " + activUser.getWallet().countBalance() +
                            " рублей расходы превышают доходы в " + month + " месяце " +
                            year + " года!" +
                            "\nВНИМАНИЕ!!!\nЖИЗНЬ НЕ ПО СРЕДСТВАМ!!!");
                } else System.out.println("По заданным значениям операций не найдено");
            }
            else if (cmd.equals("3")) {
                int year = -1;
                int month = -1;
                String strCategory;
                Category category = null;

                //ввод года и месяца
                while(true) {

                    System.out.println("Введите год: ");
                    String strYear = scanner.next();

                    System.out.println("Введите номер месяца: ");
                    String strMonth = scanner.next();

                    System.out.println("Введите категорию в соответствии с пользовательскими настройками: ");
                    strCategory = scanner.next();

                    //Проверка введенного года на число
                    if (strYear != null && strYear.matches("\\d{4}")) {
                        try {
                            year = Integer.parseInt(strYear);
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    //Проверка введенного месяца на число
                    if (strMonth != null && strMonth.matches("\\d{2}")) {
                        try {
                            month = Integer.parseInt(strMonth);
                        } catch (NumberFormatException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    boolean isBe = false;
                    for (Category cat : activUser.getWallet().getCategories()) {
                        if (strCategory.equals(cat.getName())) {
                            isBe = true;
                            category = cat;
                        }
                    }

                    if ((month >= 0) && (month <= 12) && isBe) {
                        break;
                    } else {System.out.println("Неверный формат года, или месяца, или категории !");}
                }

                List<Operation> filterOperation = toFilter(activUser, year, month, category.getName());

                if (filterOperation.size() != 0) {
                    for (Operation operation : filterOperation) {
                        operation.printOperation();
                    }
                    double balance = toCountBalance(filterOperation);
                        System.out.println("Итого у Вас " + balance);
                        System.out.println("Месячный бюджет: " + category.getBudget());

                } else System.out.println("По заданным значениям операций не найдено");
            }
            else if (cmd.equals("0")) {
                break;
            } else {System.out.println("Неизвестная команда");}
        }
    }
}

