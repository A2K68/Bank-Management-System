public class Customer {
    private String name;
    private int accountNumber;
    private double balance;
    private String password;

    public Customer(String name, int acc, double bal, String pass) {
        this.name = name;
        this.accountNumber = acc;
        this.balance = bal;
        this.password = pass;
    }

    public int getAccountNumber()
    { return accountNumber; }

    public double getBalance()
    { return balance; }

    public void deposit(double amt)
    { balance += amt; }

    public void withdraw(double amt)
    { if (amt <= balance) balance -= amt; }

    public boolean checkPassword(String pass) { return password.equals(pass); }

    public String toString() {
        return "Name: " + name + ", Account: " + accountNumber + ", Balance: $" + balance;
    }
}
