/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: chengxiang.Huang
 * Date: 2024-05-04
 * Time: 14:42
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private double savingbalance;
    private double currentbalance; // 新增余额属性
    private TransactionStorage transactionStorage;


    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.savingbalance = 0;
        this.currentbalance = 0;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.currentbalance, currentbalance) == 0 &&
                Double.compare(account.savingbalance, savingbalance) == 0 &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, currentbalance, savingbalance);
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getcurrentBalance() {
        return currentbalance;
    }

    public void setcurrentBalance(double balance) {
        this.currentbalance = balance;
    }

    public double getsavingBalance() {
        return savingbalance;
    }

    public void setsavingBalance(double balance) {
        this.savingbalance = balance;
    }

    // 取款方法
    public void withdraw(double amount, String accounttype) {
        this.transactionStorage = new TransactionStorage("transactions.txt");
        if(accounttype=="current")
        {
            if (amount <= currentbalance) {
                currentbalance -= amount;
                System.out.println("Withdrawal successful. New balance: " + currentbalance);
    
                // 加载已有的交易记录（如果有）
                List<Transaction> transactions = transactionStorage.loadTransactions();
                // 如果没有交易记录，则创建一个新的空列表
                if (transactions == null) {
                    transactions = new ArrayList<>();
                }
                Transaction transaction = new Transaction("Withdraw",amount, accounttype);
                // 将新的交易记录添加到列表中
                transactions.add(transaction);
    
                // 保存更新后的交易记录
                transactionStorage.saveTransactions(transactions);
            } else {
                System.out.println("Insufficient funds.");
            }
        }
        else if(accounttype=="saving")
        {
            if (amount <= savingbalance) {
                savingbalance -= amount;
                System.out.println("Withdrawal successful. New balance: " + savingbalance);
    
                // 加载已有的交易记录（如果有）
                List<Transaction> transactions = transactionStorage.loadTransactions();
                // 如果没有交易记录，则创建一个新的空列表
                if (transactions == null) {
                    transactions = new ArrayList<>();
                }
                Transaction transaction = new Transaction("Withdraw",amount, accounttype);
                // 将新的交易记录添加到列表中
                transactions.add(transaction);
    
                // 保存更新后的交易记录
                transactionStorage.saveTransactions(transactions);
            } else {
                System.out.println("Insufficient funds.");
            }
        }
    }

    // Method to deposit money into the account
    public void deposit(double amount, String accounttype) {
        this.transactionStorage = new TransactionStorage("transactions.txt");
        {
            if(accounttype=="current")
            {
                if (amount > 0) {
                    currentbalance += amount; // Increase the balance by the amount deposited
                    System.out.println("Deposit successful: $" + amount);
                    // 加载已有的交易记录（如果有）
                    List<Transaction> transactions = transactionStorage.loadTransactions();
                    // 如果没有交易记录，则创建一个新的空列表
                    if (transactions == null) {
                        transactions = new ArrayList<>();
                    }
                    Transaction transaction = new Transaction("Deposit",amount, accounttype);
                    // 将新的交易记录添加到列表中
                    transactions.add(transaction);
        
                    // 保存更新后的交易记录
                    transactionStorage.saveTransactions(transactions);
                } else {
                    System.out.println("Deposit amount must be greater than zero.");
                }
            }

            else if(accounttype=="saving")
            {
                if (amount > 0) {
                    savingbalance += amount; // Increase the balance by the amount deposited
                    System.out.println("Deposit successful: $" + amount);
                    // 加载已有的交易记录（如果有）
                    List<Transaction> transactions = transactionStorage.loadTransactions();
                    // 如果没有交易记录，则创建一个新的空列表
                    if (transactions == null) {
                        transactions = new ArrayList<>();
                    }
                    Transaction transaction = new Transaction("Deposit",amount, accounttype);
                    // 将新的交易记录添加到列表中
                    transactions.add(transaction);
        
                    // 保存更新后的交易记录
                    transactionStorage.saveTransactions(transactions);
                } else {
                    System.out.println("Deposit amount must be greater than zero.");
                }
            }
        }

    }

    public void taskfinished(double amount, String accounttype) {
        this.transactionStorage = new TransactionStorage("transactions.txt");

        if(accounttype=="current")
        {
            if (amount > 0) {
                currentbalance += amount; // Increase the balance by the amount deposited
                System.out.println("Task finished successful: $" + amount);
    
                // 加载已有的交易记录（如果有）
                List<Transaction> transactions = transactionStorage.loadTransactions();
                // 如果没有交易记录，则创建一个新的空列表
                if (transactions == null) {
                    transactions = new ArrayList<>();
                }
                Transaction transaction = new Transaction("Finishing Task",amount, accounttype);
                // 将新的交易记录添加到列表中
                transactions.add(transaction);
    
                // 保存更新后的交易记录
                transactionStorage.saveTransactions(transactions);
            } else {
                System.out.println("Task hasn't been finished");
            }
        }
        else if(accounttype=="saving")
        {
            if (amount > 0) {
                savingbalance += amount; // Increase the balance by the amount deposited
                System.out.println("Task finished successful: $" + amount);
    
                // 加载已有的交易记录（如果有）
                List<Transaction> transactions = transactionStorage.loadTransactions();
                // 如果没有交易记录，则创建一个新的空列表
                if (transactions == null) {
                    transactions = new ArrayList<>();
                }
                Transaction transaction = new Transaction("Finishing Task",amount, accounttype);
                // 将新的交易记录添加到列表中
                transactions.add(transaction);
    
                // 保存更新后的交易记录
                transactionStorage.saveTransactions(transactions);
            } else {
                System.out.println("Task hasn't been finished");
            }
        }
    }

}

