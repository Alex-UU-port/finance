import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String login;
    private String password;
    //private Wallet wallet;

    public User (String login, String password) {
        this.login = login;
        this.password = password;
        //this.wallet = new Wallet();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String toCSVString() {
        return login + ";" + password + ";";
    }

    public static void saveCSV(List<User> users) {
        String path = "users.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Записываем данные
            for (User user : users) {
                writer.write(user.toCSVString());
                writer.newLine();
            }
            System.out.println("Данные успешно сохранены в " + path);
        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }
    }

    public static List<User> fromCSV() {
        String path = "users.csv"; // путь к вашему файлу CSV
        List<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = br.readLine()) != null) {

                String[] fields = line.split(";"); // разделяем по точке с запятой
                if (fields.length == 2) {
                    String login = fields[0];
                    String password  = fields[1];

                    User user = new User(login, password);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return users;
    }

}

