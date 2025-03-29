package medium;

public class Bank {
    private long[] balance;
    int size;
    public Bank(long[] balance) {
        this.balance = balance;
        this.size = balance.length;
    }

    public boolean transfer(int account1, int account2, long money) {
        if (!validAccount(account1) || !validAccount(account2))
            return false;
        if (balance[account1-1] < money)
            return false;
        balance[account1-1] -= money;
        balance[account2-1] += money;
        return true;
    }

    public boolean deposit(int account, long money) {
        if (!validAccount(account))
            return false;
        balance[account-1] += money;
        return true;
    }

    public boolean withdraw(int account, long money) {
        if (!validAccount(account))
            return false;
        if (balance[account-1] < money)
            return false;
        balance[account-1] -= money;
        return true;
    }

    private boolean validAccount(int account) {
        if (account < 1 || account > size)
            return false;
        return true;
    }
}
