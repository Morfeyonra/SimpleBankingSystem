package banking;

public class Main {

    private static boolean power = true;
    public static void setPower(boolean power) {
        Main.power = power;
    }
    public static boolean isPower() {
        return power;
    }

    private static String inputArgument;
    public static String getInputArgument() { return inputArgument; }

    public static void main(String[] args) {
        inputArgument = args[1];

        SQLiteInterface createDB = new SQLiteInterface();
        createDB.createCardDB();

        Logic logic = new Logic();
        while (power) {
            logic.logLogic();
        }
    }
}