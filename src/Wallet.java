import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet {
    private double balance;
    private List<Operation> operations = new ArrayList<>();
    private Map<String, Category> categories = new HashMap<>();
}
