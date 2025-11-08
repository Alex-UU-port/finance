import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static List<User> users;

    public static void main(String[] args) {

        users = User.fromCSV();
        //инициализация пользователей

        while (true) {

            System.out.println("Введите \"1\" для авторизации; \n" +
                    "Введите \"2\" для регистрации; \n" +
                    "Введите \"0\" для выхода");
            int cmd = scanner.nextInt();

            if (cmd == 0) {
                toExit();
                break;
            } else if (cmd == 1) {
                for (User user : users) {
                    System.out.println(user.getLogin());
                }
            } else if (cmd == 2) {
                toRegister();
            } else {
                System.out.println("Ошибка, неизвестная команда!");
            }
        }
    }

    public static void toRegister() {
        System.out.println("Введите логин: ");
        String log = scanner.next();

        System.out.println("Введите пароль: ");
        String pass = scanner.next();

        User user = new User(log, pass);

        users.add(user);

    }

    public static void toExit() {
        User.saveCSV(users);
    }
}

