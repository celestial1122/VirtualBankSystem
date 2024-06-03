/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: chengxiang.Huang
 * Date: 2024-05-04
 * Time: 14:42
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountStorage {

    public String parentAccountsFile = "parents.txt";
    public String childAccountsFile = "children.txt";

    public AccountStorage()
    {

    };


    public List<Account> loadAccounts(String filename) {
        List<Account> accounts = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                try {
                    Object obj = in.readObject();
                    if (obj instanceof Account) {
                        accounts.add((Account) obj);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public void saveNewAccount(Account account, String filename) {
        List<Account> accounts = loadAccounts(filename);
        if (accounts.stream().noneMatch(a -> a.getUsername().equals(account.getUsername()))) {
            accounts.add(account);
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                for (Account acc : accounts) {
                    out.writeObject(acc);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Username already exists. Please choose a different username.");
        }
    }

    public void updateAccountBalance(Account account, String filename, String accounttype) {
        List<Account> accounts = loadAccounts(filename);
        boolean found = false;
        for (Account acc : accounts) {
            if (acc.getUsername().equals(account.getUsername())) {
                if(accounttype=="current") acc.setcurrentBalance(account.getcurrentBalance());
                else if(accounttype=="saving") acc.setsavingBalance(account.getsavingBalance());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Account not found, cannot update.");
            return;
        }
        // 使用false来覆盖旧文件，确保文件是全新写入
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename, false))) {
            for (Account acc : accounts) {
                out.writeObject(acc);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to write accounts to file: " + e.getMessage());
        }
    }
}