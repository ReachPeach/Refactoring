package ru.akirakozov.sd.refactoring.servlet;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDBEnvironment {
    public static final String DATABASE_PATH = "test.db";
    public static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_PATH;

    public void setup() throws SQLException {
        createProductTable();
    }

    public void clear() throws SQLException, IOException {
        dropProductTable();
        Files.delete(Paths.get(DATABASE_PATH));
    }

    public void createProductTable() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }
    }

    public void dropProductTable() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            String sql = "DROP TABLE IF EXISTS PRODUCT";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }

    }

    public void addProductInTable(final String name, final int price) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }
    }

    public List<Product> getProductsFromTable() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            String sql = "SELECT * FROM PRODUCT";
            Statement statement = connection.createStatement();
            ResultSet productsResult = statement.executeQuery(sql);
            List<Product> products = new ArrayList<>();
            while (productsResult.next()) {
                String name = productsResult.getString("name");
                int price = productsResult.getInt("price");
                products.add(new Product(name, price));
            }
            return products;
        }

    }
}