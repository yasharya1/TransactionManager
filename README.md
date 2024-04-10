# Personal Finance Manager (PFM)

## Description
Personal Finance Manager (PFM) is a Java desktop application designed to assist individuals in managing their personal finances. This application allows users to track expenses and incomes, manage investments, and plan budgets. PFM is designed with a focus on user-friendliness and practicality, offering a range of features from basic transaction tracking to detailed financial reports.

### What will the application do?
The Personal Finance Manager (PFM) application will provide users with comprehensive tools to manage their personal finances effectively. It will allow users to track their incomes and expenses, categorize financial transactions for better insight, set and monitor budgets across various categories, and keep an eye on their financial transactions.

### Who will use it?
PFM is designed for individuals looking to gain better control over their personal finances, from students managing their allowances to professionals tracking their income and investments. Its user-friendly approach makes it accessible to users with varying levels of financial knowledge and technical expertise, ensuring that anyone interested in improving their financial health can benefit from the application.

### Why is this project of interest to you?
This project resonates with me deeply as it offers a practical solution to a common challenge: achieving effective budgeting and financial control. By developing a financial tracker, I am creating a tool that can significantly enhance personal financial management. The project promises a rich learning experience in crucial areas like data storage, user interaction, and maintaining application continuity, all driven by the real need to foster better financial discipline and awareness.

### Features
- **Transaction Management:** Add, view, edit, and delete income and expense transactions.
- **Categorization:** Organize transactions into categories for better tracking.
- **Financial Summaries:** View summaries of financial data, including total expenses and incomes.
- **Budget Planning:** Set and track monthly budgets for various categories.


## User Stories
- **Adding Transactions:** As a user, I want to be able to add a financial transaction (Class X) to my ledger (Class Y) and specify the amount, date, category (e.g., groceries, salary), type (income or expense), and a brief description, so that I can keep detailed records of my financial activities.

- **Viewing Transactions:** As a user, I want to be able to view a list of all my financial transactions, so I can quickly see my financial history and understand where my money is going.

- **Categorizing Transactions:** As a user, I want to be able to categorize my transactions, so that I can organize my financial data more effectively and generate meaningful insights into my spending and income patterns.

- **Budget Management:** As a user, I want to be able to set monthly budgets for different categories and track my spending against these budgets, so I can manage my finances better and avoid overspending.

- **Saving and Managing Data:** As a user, I want to be able to save my transactions so far, so I can continue managing my finances seamlessly.

- **Loading My Finances:** As a user, I want to be able to load my transactions from previous sessions, so I can manager my transactions throghout the month rather than having to do everything in one session at the end of the month.


## Instructions For Grader
- You can generate the first required action related to the user story "adding multiple transactions to a ledger" by navigating to the Add Transaction Panel at the top and filling in the form with the transaction details (amount, date, category, type, and description) and click "Submit" to add the transaction to the ledger and clicking the "Add Transaction" buttton.

- You can generate the ability to filter transactions by applying the filter options provided (such as date range, category, or type) and clicking "Filter" button to refine the displayed list of transactions according to your criteria. 

- You can locate my visual component by navigating to the GuiManagerApp class which runs through the Main class method, where you will find the option to run the GUI component of the transaction manager.

- You can save the state of my application by clicking the "Save" button located in the bottom of the application window. This will save all current transactions and budgets to a file.

- You can reload the state of my application by clicking the "Load" button next to the "Save" button. This will load all transactions back into the application.


## Phase 4: Task 2
**Sample Event Log**

Wed Apr 03 15:12:20 PDT 2024

Transaction added: af

Wed Apr 03 15:12:20 PDT 2024

Transaction added: xyz

Wed Apr 03 15:12:50 PDT 2024

Transaction added: Lunch



## Phase 4: Task 3 ##

In enhancing the Personal Finance Manager (PFM), focusing on better error handling, increased cohesion, and reduced coupling can significantly improve the application's design. Implementing a more robust error handling strategy, such as centralized exception management, can make the application more user-friendly and resilient. Enhancing cohesion by ensuring each class or module focuses on a single responsibility can simplify maintenance and scalability. Reducing coupling through interfaces and dependency injection promotes modular design, making the system easier to modify and test. These refactoring efforts aim to create a more reliable, maintainable, and adaptable application, setting a strong foundation for future enhancements without directly adding new features.
