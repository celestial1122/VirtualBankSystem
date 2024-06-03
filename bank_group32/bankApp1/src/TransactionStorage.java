import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionStorage {
    private String transactionFilePath;

    public TransactionStorage(String transactionFilePath) {
        this.transactionFilePath = transactionFilePath;
    }

    public void saveTransactions(List<Transaction> transactions) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(transactionFilePath))) {
            out.writeObject(transactions);
            System.out.println("Transaction records saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(transactionFilePath);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(transactionFilePath))) {
                transactions = (List<Transaction>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }
}
