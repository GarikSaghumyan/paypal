package com.paypal.desk;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int userFrom;
    private int userTo;
    private double transactionAmount;
    private Timestamp date;

    public Transaction(int id, int userFrom, int userTo, double transactionAmount, Timestamp date) {
        this.id = id;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.transactionAmount = transactionAmount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public int getUserTo() {
        return userTo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public Timestamp getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userFrom=" + userFrom +
                ", userTo=" + userTo +
                ", transactionAmount=" + transactionAmount +
                ", date=" + date +
                '}';
    }
}
