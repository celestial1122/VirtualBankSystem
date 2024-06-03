import java.util.ArrayList;
import java.util.List;

public class NowAccount {
    private static NowAccount instance = new NowAccount();
    private Account currentAccount;
    private List<BalanceListener> listeners = new ArrayList<>();

    private NowAccount() {}

    public static NowAccount getInstance() {
        return instance;
    }

    public void login(Account account) {
        this.currentAccount = account;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void addBalanceListener(BalanceListener listener) {
        listeners.add(listener);
    }

    public void updateBalance(double newBalance, String accounttype) {
        if (currentAccount != null) {
            if(accounttype=="current") currentAccount.setcurrentBalance(newBalance);
            else currentAccount.setsavingBalance(newBalance);
            listeners.forEach(listener -> listener.onBalanceChanged(newBalance));
        }
    }

    // BalanceListener 接口定义
    public interface BalanceListener {
        void onBalanceChanged(double newBalance);
    }
}
