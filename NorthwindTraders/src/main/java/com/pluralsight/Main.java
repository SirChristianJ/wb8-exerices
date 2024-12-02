package com.pluralsight;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 1. open a connection to the database
            // use the database URL to point to the correct database
            Connection connection;

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    "root",
                    "root");


        // create statement
        // the statement is tied to the open connection
        PreparedStatement statement = null;

        statement = connection.prepareStatement("SELECT * FROM northwind.products;");


        // 2. Execute your query
        ResultSet results = null;

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

            System.out.println(productId + "\t" + product + "\t" + unitInStock + "\t\t" + unitPrice);
        }

        // 3. Close the connection
        connection.close();
        statement.close();
        results.close();

        }
        catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}