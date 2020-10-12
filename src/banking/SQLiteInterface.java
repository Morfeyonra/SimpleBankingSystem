package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLiteInterface {

    String url = "jdbc:sqlite:" + Main.getInputArgument();
    SQLiteDataSource dataSource = new SQLiteDataSource();


    void createCardDB() {

        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "number TEXT UNIQUE, " +
                        "pin TEXT, " +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    boolean verifyAuthorisation(String inputAccountID, String inputPin) {

        boolean result = false;

        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet cardDB = statement.executeQuery("SELECT * FROM Card " +
                        "WHERE " +
                        "number = '" + inputAccountID + "'")) {

                    String DBAccountID = cardDB.getString("number");
                    String DBPin = cardDB.getString("pin");

                    result = inputAccountID.equals(DBAccountID) &&
                            inputPin.equals(DBPin);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    void createBankAccount(String AccountID, String Pin) {
        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("INSERT INTO Card(number, pin)" +
                        " VALUES('" + AccountID + "', '" + Pin + "')");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    int getBalance(String inputAccountID) {

        int result = -1;

        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet cardDB = statement.executeQuery("SELECT * FROM Card " +
                        "WHERE " +
                        "number = '" + inputAccountID + "'")) {

                    result = cardDB.getInt("balance");

                } catch (SQLException exe) {
                    exe.printStackTrace();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    void setBalance(String inputAccountID, int newBalance) {

        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate("UPDATE Card " +
                        "SET balance" + " = " + newBalance +
                        " WHERE number = '" + inputAccountID + "'");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    boolean transferAccountIDVerification(String transferAccountID) {

        boolean result = false;

        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet cardDB = statement.executeQuery("SELECT * FROM Card " +
                        "WHERE " +
                        "number = '" + transferAccountID + "'")) {

                    String DBAccountID = cardDB.getString("number");

                    result = transferAccountID.equals(DBAccountID);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    void deleteBankAccount(String inputAccountID) {

        dataSource.setUrl(url);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                statement.executeUpdate("DELETE FROM Card " +
                        "WHERE number = '" + inputAccountID + "'");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
