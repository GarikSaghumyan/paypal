package com.paypal.desk;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {

    private static final Connection connection = getConnection();

    private static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/connection",
                    "root",
                    ""
            );

            System.out.println("Connection successful");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static int createUser(String firstName, String lastName) {
        String sql = "insert into users " +
                "(first_name, last_name)" +
                " values (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.executeUpdate();

            String idSql = "select max(id) from users";
            Statement idStatement = connection.createStatement();
            ResultSet resultSet = idStatement.executeQuery(idSql);

            resultSet.next();

            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    static void cashFlow(int userId, double amount) {
        String sql = "update users set balance = balance + ? where id = ?" ;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return ;
        }

    }

    static void transaction(int userFrom, int userTo, double amount) {
       String sql = "select * from users where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userFrom);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()){
                return;
            }
            double userAmount = resultSet.getDouble("balance");
            if (userAmount >= amount){
                cashFlow(userFrom, -amount);
                cashFlow(userTo, amount);
                String sql1 = "insert into transactions (user_from, user_to, transaction_amount) values (?, ?, ?)";
                PreparedStatement ps1 = getConnection().prepareStatement(sql1);
                ps1.setInt(1, userFrom);
                ps1.setInt(2, userTo);
                ps1.setDouble(3,amount);
                ps1.execute();
                System.out.println("Transaction succesfull");
            }else {
                System.out.println("Money are not enough");
            }

        } catch (SQLException e ) {
            e.printStackTrace();
            return ;
        }
    }

    static List<User> listUsers() {
        String sql = "select * from users";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            List<User> userList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                double balance = resultSet.getDouble("balance");

                userList.add(new User(
                        id, firstName, lastName, balance
                ));
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    static List<Transaction> listTransaction(){
        String sql = "select * from transactions ";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<Transaction> tr = new ArrayList<>();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int userFrom = resultSet.getInt("user_from");
                int userTo = resultSet.getInt("user_to");
                double transactionAmount = resultSet.getDouble("transaction_amount");
                Timestamp timestamp = resultSet.getTimestamp("transaction_date");
                tr.add(new Transaction(id, userFrom, userTo, transactionAmount, timestamp));
            }
            return tr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
