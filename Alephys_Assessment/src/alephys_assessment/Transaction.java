package alephys_assessment;

import java.time.LocalDate;

class Transaction {
    String type; // income or expense
    String category;
    double amount;
    LocalDate date;

    public Transaction(String type, String category, double amount, LocalDate date) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return type + "," + category + "," + amount + "," + date;
    }
}