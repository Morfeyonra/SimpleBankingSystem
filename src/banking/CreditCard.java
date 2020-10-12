package banking;

import java.util.Random;

class BankAccount {

    protected String accountID;

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
    public String getAccountID() {
        return accountID;
    }

}

class CreditCard extends BankAccount {

    private String pin;

    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getPin() {
        return pin;
    }

    public void createAccount() {

        Random random = new Random();
        StringBuilder accountIDSB = new StringBuilder("400000");
        for (int i = 0; i < 9; i++) {
            accountIDSB.append(random.nextInt(10));
        }
        int sum = 0;
        for (int i = 0; i < accountIDSB.length() - 1; i++) {
            if (i % 2 == 0) {
                sum += Integer.parseInt(accountIDSB.substring(i, i + 1)) << 1 > 9 ?
                        (Integer.parseInt(accountIDSB.substring(i, i + 1)) << 1) - 9 :
                        Integer.parseInt(accountIDSB.substring(i, i + 1)) << 1;
            } else {
                sum += Integer.parseInt(accountIDSB.substring(i, i + 1));
            }
        }
        sum += Integer.parseInt(accountIDSB.substring(14)) << 1 > 9 ?
                (Integer.parseInt(accountIDSB.substring(14)) << 1) - 9 :
                Integer.parseInt(accountIDSB.substring(14)) << 1;

        int lastCardDigit = (10 - sum % 10) % 10;
        accountIDSB.append(lastCardDigit);

        setAccountID(accountIDSB.toString());
        System.out.println("\nYour card has been created \n" +
                "Your card number: ");

        System.out.println(getAccountID());
        StringBuilder pinSB = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pinSB.append(random.nextInt(10));
        }
        setPin(pinSB.toString());
        System.out.println("Your card PIN:");
        System.out.println(getPin() + "\n");

        SQLiteInterface create = new SQLiteInterface();
        create.createBankAccount(getAccountID(), getPin());
    }
}