
public class User {
    private String login;
    private String password;
    private Wallet wallet;

    public User (String login, String password) {
        this.login = login;
        this.password = password;
        this.wallet = new Wallet();
    }

}

