# Expense Tracker Java App

## Overview

Expense Tracker is a command-line Java application designed to help users manage personal finances by tracking income and expenses. The application allows users to add transactions, save and load data from files, and generate monthly financial summaries.

- Add new income or expense transactions
- Save transactions to file
- Load transactions from file
- View monthly summaries (net balance, income, expense)

## Prerequisites

- Java 8 or higher installed
- Your terminal or IDE (e.g., VS Code, IntelliJ)

## How to Run

1. **Clone or Download**
    ```
    git clone https://github.com/Moinuddinchhipa/Expense-Tracker-Java-App.git
    cd alephys_assessment
    ```

2. **Compile the code**
    ```
    javac alephys_assessment/ExpenseTracker.java alephys_assessment/Transaction.java
    ```

3. **Run the program**
    ```
    java alephys_assessment.ExpenseTracker
    ```

4. **Try Sample File**
    - When prompted to load data, enter `transaction.csv`
    - This sample file contains example transactions.

## Sample Commands

- Add: Enter type as `income` or `expense`, enter category, amount, and date (`yyyy-MM-dd`)

- Save to File: Provide a file name like `transacrion.csv`
- Load File: Use `sample_data.csv` or saved file


Technical Highlights
Uses Java Collections and LocalDate for data storage and date management.
File I/O implemented with BufferedReader and BufferedWriter for efficiency.
Aggregates data by month and year using HashMaps.
Exception handling to avoid program crashes.
Modular methods separated by functionality.
