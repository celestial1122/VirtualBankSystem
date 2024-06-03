import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account("John Doe", "123");
    }

    @Test
    public void testAccountCreation() {
        // Test if the account is created correctly and if the username and password are set
        assertEquals("John Doe", account.getUsername());
        assertEquals("123", account.getPassword());
        // Test if the initial balance is set to 0
        assertEquals(0, account.getcurrentBalance(), 0.0);
        assertEquals(0, account.getsavingBalance(), 0.0);
    }

    @Test
    public void testDepositToCurrentAccount() {
        // Test depositing money into the current account
        account.deposit(100, "current");
        assertEquals(100, account.getcurrentBalance(), 0.0);
    }

    @Test
    public void testDepositToSavingAccount() {
        // Test depositing money into the savings account
        account.deposit(200, "saving");
        assertEquals(200, account.getsavingBalance(), 0.0);
    }

    @Test
    public void testDepositNegativeAmount() {
        // Test that depositing a negative amount is not allowed
        account.deposit(-50, "current");
        assertEquals(0, account.getcurrentBalance(), 0.0);
    }

    @Test
    public void testWithdrawFromCurrentAccount() {
        // Assume some money is deposited into the current account first
        account.deposit(200, "current");
        // Test withdrawing money from the current account
        account.withdraw(50, "current");
        assertEquals(150, account.getcurrentBalance(), 0.0);
    }

    @Test
    public void testWithdrawFromSavingAccount() {
        // Assume some money is deposited into the savings account first
        account.deposit(300, "saving");
        // Test withdrawing money from the savings account
        account.withdraw(100, "saving");
        assertEquals(200, account.getsavingBalance(), 0.0);
    }

    @Test
    public void testWithdrawExceedingBalance() {
        // Test that withdrawal amount cannot exceed the account balance
        account.withdraw(1000, "current");
        assertEquals(0, account.getcurrentBalance(), 0.0);
    }

    @Test
    public void testTaskFinished() {
        // Test increasing the account balance after completing a task
        account.taskfinished(150, "current");
        assertEquals(150, account.getcurrentBalance(), 0.0);
    }

    @Test
    public void testTaskFinishedNegativeAmount() {
        // Test that completing a task with a negative amount is not allowed
        account.taskfinished(-50, "current");
        assertEquals(0, account.getcurrentBalance(), 0.0);
    }
}