import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date date;
    private String type;
    private double amount;
    private String accounttype;

    public Transaction(String type, double amount, String accounttype) {
        this.date = new Date();
        this.type = type;
        this.amount = amount;
        this.accounttype = accounttype;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        String color = (type.equalsIgnoreCase("withdraw")) ? "green" : "red";
        return "<font color='" + color + "'>     " +  accounttype +" account: " + type + " $" + amount + "<br></font>           ---- <font color='black'>" + date + "</font><br>";
    }
}
