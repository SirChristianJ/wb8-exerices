package com.pluralsight;
import java.sql.*;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        choiceAction(menu());
    }

    public static int menu(){
        String prompt = 
                """
                What do you want to do?
                    1)Display all products
                    2)Display all customers
                    0)Exit
                """;
        System.out.println(prompt);
        String ans = scanner.nextLine();
        return Integer.parseInt(ans);
    }
    
    public static void choiceAction(int userChoice){
        Connection connection = null;

        // create statement
        PreparedStatement statement = null;

        // 2. Execute your query
        ResultSet results = null;

        try {
            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 1. open a connection to the database
            // use the database URL to point to the correct database

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    "root",
                    "root");


            // the statement is tied to the open connection
            if(userChoice == 1) {

                statement = connection.prepareStatement("SELECT * FROM northwind.products;");

                results = statement.executeQuery();

                System.out.println("ID" + "|" + "Product_Name" + "|" + "Units_in_Stock" + "|" + "Unit_Price");
                System.out.println("------------------------------------------");

                // process the results
                while (true) {

                    if (!results.next()) break;

                    String product = null;
                    String productId = null;
                    int unitInStock = 0;
                    double unitPrice = 0;

                    product = results.getString("ProductName");
                    productId = results.getString("ProductID");
                    unitInStock = results.getInt("UnitsInStock");
                    unitPrice = results.getDouble("UnitPrice");

                    System.out.println(productId + "\t" + product + "\t" + unitInStock + "\t" + unitPrice);
                }
            }
            else if (userChoice == 2) {
                statement = connection.prepareStatement("SELECT * FROM northwind.customers;");

                results = statement.executeQuery();

                System.out.println("ID" + "|" + "Company_Name" + "|" + "Contact_Name" + "|" + "Contact_Title");
                System.out.println("------------------------------------------");

                // process the results
                while (true) {

                    if (!results.next()) break;

                    String customerId  = null;
                    String companyName = null;
                    String contactName = null;
                    String contactTitle = null;

                    customerId = results.getString("CustomerID");
                    companyName = results.getString("CompanyName");
                    contactName = results.getString("ContactName");
                    contactTitle = results.getString("ContactTitle");

                    System.out.println(customerId + "\t" + companyName + "\t" + contactName + "\t" + contactTitle);
                }
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            // 3. Close the connection
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                results.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}