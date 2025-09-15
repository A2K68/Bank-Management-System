public class Bank {
    private Customer[] customers = new Customer[100];
    private int count = 0;

    public void addCustomer(Customer c) {
        customers[count++] = c;
    }

    public Customer findCustomer(int acc) {
        for (int i = 0; i < count; i++) {
            if (customers[i].getAccountNumber() == acc) {
                return customers[i];
            }
        }
        return null;
    }

    public boolean removeCustomer(int acc) {
        for (int i = 0; i < count; i++) {
            if (customers[i].getAccountNumber() == acc) {
                customers[i] = customers[count - 1];
                customers[--count] = null;
                return true;
            }
        }
        return false;
    }

    public void viewAllCustomers() {
        for (int i = 0; i < count; i++) {
            System.out.println(customers[i]);
        }
    }
}
