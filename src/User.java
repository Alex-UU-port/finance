import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String login;
    private String password;
    private Wallet wallet;

    public User (String login, String password) {
        this.login = login;
        this.password = password;
        this.wallet = new Wallet();
    }

    // для десереализации из json необходим конструктор по умолчанию
    public User () {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public static void saveJSON (List<User> users) {
        String path = "users.json";

        // Используем try-with-resources, чтобы автоматически закрывать writer
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
            for (User user : users) {
                String jsonString = objectMapper.writeValueAsString(user);

                //строка для теста
                System.out.println(jsonString);

                writer.write(jsonString);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Какие-то проблемы с формированием json: " + e.getMessage());
        }
    }

    public static List<User> fromJSON() {
        String path = "users.json"; // путь к файлу json
        List<User> users = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {

                //строка для теста
                System.out.println(line);

                User user = mapper.readValue(line, User.class);
                users.add(user);
            }
        } catch (IOException e) {
            System.out.println("Какие-то проблемы с чтением json: " + e.getMessage());
        }
        return users;
    }


}

