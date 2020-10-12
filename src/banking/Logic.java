package banking;

import java.util.Scanner;

public class Logic {
    private CreditCard creditCard = new CreditCard();
    private Scanner scan = new Scanner(System.in);
    private boolean authorization;

    protected void logLogic() {

        System.out.println("1. Create an account \n" +
                "2. Log into account \n" +
                "0. Exit ");

        int userInput;

        try {
            userInput = scan.nextInt();
        } catch(Exception e) {
            return;
        }

        switch (userInput) {

            case 1:
                creditCard.createAccount();
                return;

            case 2:
                System.out.println("\nEnter your card number:");
                String inputAccountID = scan.next();

                System.out.println("Enter your PIN:");
                String inputPin = scan.next();

                SQLiteInterface database = new SQLiteInterface();

                if (database.verifyAuthorisation(inputAccountID, inputPin)) {
                    System.out.println("\nYou have successfully logged in! \n");
                    authorization = true;
                    creditCard.setAccountID(inputAccountID);
                    creditCard.setPin(inputPin);
                    while (authorization) {
                        authorizationLogic();
                    }
                } else {
                    System.out.println("\nWrong card number or PIN!\n");
                }
                return;

            case 0:
                System.out.println("\nBye!\n");
                Main.setPower(false);
                return;

            default:
                return;
        }
    }

    protected void authorizationLogic() {

        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");

        int authorizationInput;

        try {
            authorizationInput = scan.nextInt();
        } catch(Exception e) {
            return;
        }

        SQLiteInterface database = new SQLiteInterface();

        switch (authorizationInput) {

            case 1:
                System.out.println("\n" + database.getBalance(creditCard.getAccountID()) + "\n");
                return;

            case 2:
                System.out.println("Enter income:");
                int income = scan.nextInt();
                database.setBalance(creditCard.getAccountID(),
                        database.getBalance(creditCard.getAccountID()) + income);
                System.out.println("Income was added!\n");
                return;

            case 3:
                System.out.println("Transfer\n" +
                        "Enter card number:");
                String transferCardAccountID = scan.next();
                StringBuilder transferCardAccountIDSB = new StringBuilder(transferCardAccountID);

                try {
                    int sum = 0;
                    for (int i = 0; i < transferCardAccountIDSB.length() - 1; i++) {
                        if (i % 2 == 0) {
                            sum += Integer.parseInt(transferCardAccountIDSB.substring(i, i + 1)) << 1 > 9 ?
                                    (Integer.parseInt(transferCardAccountIDSB.substring(i, i + 1)) << 1) - 9 :
                                    Integer.parseInt(transferCardAccountIDSB.substring(i, i + 1)) << 1;
                        } else {
                            sum += Integer.parseInt(transferCardAccountIDSB.substring(i, i + 1));
                        }
                    }
                    int lastCardDigit = (10 - sum % 10) % 10;

                    if (Integer.parseInt(transferCardAccountIDSB.substring(15)) == lastCardDigit) {
                        if (database.transferAccountIDVerification(creditCard.getAccountID())) {
                            System.out.println("Enter how much money you want to transfer:");
                            int transferAmount = scan.nextInt();
                            if (database.getBalance(creditCard.getAccountID()) >= transferAmount) {
                                database.setBalance(creditCard.getAccountID(),
                                        database.getBalance(creditCard.getAccountID()) - transferAmount);
                                database.setBalance(transferCardAccountID,
                                        database.getBalance(transferCardAccountID) + transferAmount);
                                System.out.println("Success!\n");
                            } else {
                                System.out.println("Not enough money!\n");
                            }
                        } else {
                            System.out.println("Such a card does not exist.\n");
                        }
                    } else {
                        System.out.println("Probably you made mistake in the card number. Please try again!\n");
                    }
                } catch (Exception e) {
                    return;
                }
                return;

            case 4:
                System.out.println("The account has been closed!\n");
                database.deleteBankAccount(creditCard.getAccountID());
                authorization = false;
                return;

            case 5:
                System.out.println("\nYou have successfully logged out! \n");
                authorization = false;
                return;

            case 0:
                Main.setPower(false);
                authorization = false;
                return;
        }
    }
}
