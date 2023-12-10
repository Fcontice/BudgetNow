import java.text.SimpleDateFormat;
import java.util.*;

public class BudgetNow {
    private static Map<String, User> users = new HashMap<>();
    private static User loggedInUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("_____________________________\n");
            System.out.println("|-------  Main Menu  -------|\n\n");
            System.out.println("Welcome to the Budget Tracking App!\n");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit\n");
            System.out.println("4. BYPASS LOGIN--TESTING ONLY\n");
            System.out.print("Select an option:");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    User newUser = registerUser(scanner);
                    if (newUser != null) {
                        loggedInUser = newUser; 
                        System.out.println("\nLogged in as: " + newUser.getUsername());
                        System.out.println("\n\n\t\tWelcome to BudgetNow!\n");
                        System.out.println("Let's get started by setting up your initial account balances.\n");
                        setInitialBalance(scanner, newUser); 
                        showBudgetMenu(scanner);
                    }
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("\nGoodbye!");
                    System.exit(0);
                case 4:
                    loggedInUser = new User("test", "test");
                    loggedInUser.setInitialBalance("Checking", 1000);
                    loggedInUser.setInitialBalance("Savings", 5000);
                    loggedInUser.addBudget(new Budget(100, "Food", 1));
                    loggedInUser.addBudget(new Budget(200, "Auto & Transportation", 1));
                    loggedInUser.addBudget(new Budget(300, "Shopping", 1));
                    loggedInUser.addBudget(new Budget(400, "Entertainment", 1));
                    loggedInUser.addBudget(new Budget(500, "Personal", 1));
                    loggedInUser.addBudget(new Budget(600, "Other", 1));
                    showBudgetMenu(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

// register user
    private static User registerUser(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
    
        // Check if the username contains only letters (alphabetic characters)
        if (!username.matches("^[a-zA-Z]+$")) {
            System.out.println("Username should contain only letters. Please choose another one.");
            System.out.println("Hint: You can use your first name or a nickname.");
            System.out.println("Enter a username:");
            String newUsername = scanner.nextLine();
            username = newUsername;
        }
    
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose another one.");
            System.out.println("Enter a username:");
            String newUsername = scanner.nextLine();
            username = newUsername;
        }
    
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
    
        User user = new User(username, password);
        users.put(username, user);
        System.out.println("User registered successfully!");
    
        return user; // newly registered user logged in automatically
    }
    
// login user
    private static void loginUser(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        if (!users.containsKey(username)) {
            System.out.println("Username does not exist. Please register first.");
            return;
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password. Please try again.");
            return;
        }

        loggedInUser = user;
        System.out.println("\nLogged in as: " + user.getUsername());
        showBudgetMenu(scanner);
    }

    private static void setInitialBalance(Scanner scanner, User user) {

        System.out.print("Enter the number of financial accounts you'd like to add: ");
        int numAccounts = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 1; i <= numAccounts; i++) {
            System.out.print("Enter the name of bank account " + i + ": ");
            String accountName = scanner.nextLine();
            System.out.print("Enter the initial balance for account " + i + ": ");
            double initialBalance = scanner.nextDouble();
            scanner.nextLine();
            user.setInitialBalance(accountName, initialBalance);
        }
    }

// budget menu options (ADD BUDGET, VIEW/CHANGE BUDGETS, VIEW BANK ACCOUNTS, CHANGE INITIAL BALANCES, ADD EXPENSES, ADD FINANCIAL GOAL, VIEW FINANCIAL GOALS, LOGOUT)
    private static void showBudgetMenu(Scanner scanner) {
        while (loggedInUser != null) {
            System.out.println("\n\n\nBudget Menu:\n");
            System.out.println("1. Add Budget");
            System.out.println("2. View/Change Budgets");
            System.out.println("3. View Bank Accounts");
            System.out.println("4. Change Initial Balances");
            System.out.println("6. Add Expenses");
            System.out.println("7. Add Financial Goal");
            System.out.println("8. View Financial Goals");
            System.out.println("0. Logout");
            System.out.print("\nSelect an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBudget(scanner);
                    break;
                case 2:
                    viewBudgets(scanner);
                    break;
                case 3:
                    viewBankAccounts();
                    break;
                case 4:
                    changeAccountBalance(scanner);
                    break;
                case 5:
                    addExpense(scanner);
                    return;
                case 6:
                    addFinancialGoal(scanner);
                    break;
                case 7:
                    viewFinancialGoals();
                    break;
                case 0:
                    loggedInUser = null;
                    System.out.println("\nLogged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

// add budget options (FOOD, AUTO & TRANSPORTATION, SHOPPING, ENTERTAINMENT, PERSONAL, OTHER)
    private static void addBudget(Scanner scanner) {
        System.out.println("\n\n\nSelect Budget Category:\n");
        System.out.println("1. Food");
        System.out.println("2. Auto & Transportation");
        System.out.println("3. Shopping");
        System.out.println("4. Entertainment");
        System.out.println("5. Personal");
        System.out.println("6. Other (Custom Category)");
        System.out.println("0. Back to Home");
        System.out.print("\nSelect a category: ");
    
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();
    
        String category;
    
        switch (categoryChoice) {
            case 1:
                category = "Food";
                break;
            case 2:
                category = "Auto & Transportation";
                break;
            case 3:
                category = "Shopping";
                break;
            case 4:
                category = "Entertainment";
                break;
            case 5:
                category = "Personal";
                break;
            case 6:
                System.out.print("Enter the custom category name: ");
                category = scanner.nextLine();
                break;
            case 0:
                return; // Return to the budget menu
            default:
                System.out.println("Invalid category choice. Please try again.\n");
                return; // Return to the budget menu
        }

        System.out.print("Enter the month for this budget (1 for January, 2 for February, etc.): ");
        int month = scanner.nextInt();
        scanner.nextLine();
    
        System.out.print("Enter the budget amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
    
        loggedInUser.addBudget(new Budget(amount, category, month));
        System.out.println("Budget added successfully!\n\n");
        
        System.out.print("\nDo you want to add another budget? (yes/no): ");
        String anotherBudget = scanner.nextLine().toLowerCase();

        if (!"yes".equals(anotherBudget)) {
            return; // Return to the budget menu
        }
    }

// view list of budgets
    private static void viewBudgets(Scanner scanner) {
        while (true) {
            System.out.println("\nBudget List:\n");
            Map<Integer, Budget> budgets = loggedInUser.getBudgets();
            if (budgets.isEmpty()) {
                System.out.println("No budgets available. Add a budget first.");
                break; 
            }
            for (Map.Entry<Integer, Budget> entry : budgets.entrySet()) {
                int budgetId = entry.getKey();
                Budget budget = entry.getValue();
                System.out.println(budgetId + ". Category: " + budget.getCategory() + ", Amount: $" + budget.getAmount());
            }

            System.out.println("Enter the budget ID to manage or 0 to go back: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                return; // Return to the budget menu
            } else if (budgets.containsKey(choice)) {
                manageBudget(scanner, budgets.get(choice));
            } else {
                System.out.println("Invalid budget ID. Please try again.");
            }
        }
    }

// manage budget options (CHANGE BUDGET AMOUNT, REMOVE BUDGET)
    private static void manageBudget(Scanner scanner, Budget budget) {
        while (true) {
            System.out.println("\nManage Budget:\n");
            System.out.println("1. Change Existing Budget");
            System.out.println("2. Remove Budget");
            System.out.println("3. Back to Budget List");
            System.out.print("\nSelect an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    changeBudgetAmount(scanner, budget);
                    break;
                case 2:
                    removeBudget(budget);
                    return;
                case 3:
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

// add expense to existing budgets
    private static void addExpense(Scanner scanner) {
        System.out.println("\n     Adding Expense:");
        System.out.println("_____________________________");

        Map<Integer, Budget> budgets = loggedInUser.getBudgets();
        if (budgets.isEmpty()) {
            System.out.println("No budgets available. Add a budget first.");
            return;
        }

        System.out.println("     Available Budgets:\n");
        int index = 1;
        Map<Integer, String> budgetOptions = new HashMap<>();

        for (Map.Entry<Integer, Budget> entry : budgets.entrySet()) {
            Budget budget = entry.getValue();
            System.out.println(index + ". " + budget.getCategory() + ", Amount: $" + budget.getAmount());
            budgetOptions.put(index, budget.getCategory());
            index++;
        }

        System.out.println("0. Other");
        budgetOptions.put(0, "Other");

        System.out.print("Select a budget or enter 0 for Other: ");
        int budgetChoice = scanner.nextInt();
        scanner.nextLine();

        String selectedBudgetCategory = budgetOptions.get(budgetChoice);
        Budget selectedBudget;

        if (budgetChoice == 0) {
            System.out.print("Enter the name of the budget: ");
            String budgetName = scanner.nextLine();
            System.out.print("Enter the budget amount: ");
            double budgetAmount = scanner.nextDouble();
            scanner.nextLine();

            selectedBudget = new Budget(budgetAmount, budgetName, 0);
            loggedInUser.addBudget(selectedBudget);
        } else {
            selectedBudget = budgets.get(budgetChoice);
        }

        System.out.print("Enter the expense name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = sdf.format(new Date());

        Expense expense = new Expense(name, amount, date);

        if (selectedBudget.getAmount() >= amount) {
            selectedBudget.subtractAmount(amount);
            selectedBudget.addExpense(expense);
            System.out.println("Expense added successfully!");
        } else {
            System.out.println("Expense amount exceeds the budget amount.");
        }
    }

// change already created budget amount
    private static void changeBudgetAmount(Scanner scanner, Budget budget) {
        System.out.print("Enter the new budget amount: ");
        double newAmount = scanner.nextDouble();
        scanner.nextLine();
    
        budget.setAmount(newAmount);
        System.out.println("Budget amount updated successfully!");
    }

// remove budget
    private static void removeBudget(Budget budget) {
        loggedInUser.removeBudget(budget);
        System.out.println("Budget removed successfully!");
    }

// view bank accounts
    private static void viewBankAccounts() {
        Map<String, Double> accounts = loggedInUser.getInitialBalances();
        double totalBalance = 0;

        System.out.println("\nBank Accounts:\n");
        for (Map.Entry<String, Double> entry : accounts.entrySet()) {
            String accountName = entry.getKey();
            double balance = entry.getValue();

            System.out.println("Account Name: " + accountName);
            System.out.println("Balance: $" + balance + "\n");

            totalBalance += balance;
        }

        System.out.println("Total Balance Across All Accounts: $" + totalBalance);
        System.out.println("\nPress Enter to go back to the Budget Menu.");
        new Scanner(System.in).nextLine();
    }

// change account balances
    private static void changeAccountBalance(Scanner scanner) {
        Map<String, Double> accounts = loggedInUser.getInitialBalances();

        if (accounts.isEmpty()) {
            System.out.println("You don't have any accounts yet. Please set up your initial account balances first.");
            return;
        }
        System.out.println("\nCurrent Bank Accounts:\n");
        int index = 1;
        Map<Integer, String> accountOptions = new HashMap<>();
    
        for (Map.Entry<String, Double> entry : accounts.entrySet()) {
            String accountName = entry.getKey();
            double balance = entry.getValue();
    
            System.out.println(index + ". Account Name: " + accountName + ", Balance: $" + balance);
            accountOptions.put(index, accountName);
            index++;
        }
        System.out.println("0. Back to Budget Menu");
        accountOptions.put(0, "Back");
    
        System.out.print("\nSelect an account to update or enter 0 to go back: ");
        int accountChoice = scanner.nextInt();
        scanner.nextLine();
    
        if (accountChoice == 0) {
            return; // Return to menu
        } else if (accountOptions.containsKey(accountChoice)) {
            String selectedAccountName = accountOptions.get(accountChoice);
            System.out.print("Enter the new balance for account " + selectedAccountName + ": ");
            double newBalance = scanner.nextDouble();
            scanner.nextLine();
    
            loggedInUser.setInitialBalance(selectedAccountName, newBalance);
            System.out.println("Account balance updated successfully!");

            System.out.println("\nUpdated Bank Accounts:\n");
            for (Map.Entry<String, Double> entry : accounts.entrySet()) {
                String accountName = entry.getKey();
                double balance = entry.getValue();
        
                System.out.println("Account Name: " + accountName);
                System.out.println("Balance: $" + balance + "\n");
            }
            System.out.println("\nPress Enter to go back to the Budget Menu.");
            scanner.nextLine();
        } else {
            System.out.println("Invalid account selection. Please try again.");
        }
    }

// view financial goals
    private static void viewFinancialGoals() {
        List<FinancialGoal> goals = loggedInUser.getFinancialGoals();
        if (goals.isEmpty()) {
            System.out.println("No financial goals available. Add a financial goal first.");
            return;
        }

        System.out.println("\nFinancial Goals:\n");
        for (FinancialGoal goal : goals) {
            System.out.println("Goal Amount: $" + goal.getGoalAmount());
            System.out.println("Target Date: " + goal.getTargetDate() + "\n");
        }

        System.out.println("\nPress Enter to go back to the Budget Menu.");
        new Scanner(System.in).nextLine();
    }

// adding a financial goal
    private static void addFinancialGoal(Scanner scanner) {
        if (loggedInUser == null) {
            System.out.println("Please log in to add a financial goal.");
            return;
        }

        System.out.print("Enter the goal amount: ");
        double goalAmount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter the target date (mm/dd/yyyy): ");
        String targetDateStr = scanner.nextLine();
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
            Date targetDate = sdf.parse(targetDateStr);

            FinancialGoal goal = new FinancialGoal(goalAmount, targetDate);
            loggedInUser.addFinancialGoal(goal);
            System.out.println("Financial goal added successfully!");
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use mm/dd/yyyy.");
        }
    }



// User, Budget, and Expense classes
    public static class User {
        private String username;
        private String password;
        private int budgetId;
        private Map<Integer, Budget> budgets;
        private Map<String, Double> initialBalances;
        private List<FinancialGoal> financialGoals;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
            this.budgets = new HashMap<>();
            this.budgetId = 1;
            this.initialBalances = new HashMap<>();
            this.financialGoals = new ArrayList<>();
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public void setInitialBalance(String accountName, double initialBalance) {
            initialBalances.put(accountName, initialBalance);
        }

        public double getInitialBalance(String accountName) {
            return initialBalances.getOrDefault(accountName, 0.0);
        }

        public void addFinancialGoal(FinancialGoal goal) {
            financialGoals.add(goal);
        }

        public List<FinancialGoal> getFinancialGoals() {
            return financialGoals;
        }

        public Map<String, Double> getInitialBalances() {
            return initialBalances;
        }

        public void addBudget(Budget budget) {
            budgets.put(budgetId++, budget);
        }

        public void removeBudget(Budget budget) {
            budgets.values().removeIf(b -> b == budget);
        }

        public Map<Integer, Budget> getBudgets() {
            return budgets;
        }
    }

    public static class Budget {
        private double amount;
        private String category;
        private int month;
        private List<Expense> expenses;

        public Budget(double amount, String category, int month) {
            this.amount = amount;
            this.category = category;
            this.month = month;
            this.expenses = new ArrayList<>();
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(Scanner scanner) {
            System.out.print("Enter the month for this budget (1 for January, 2 for February, etc.): ");
            this.month = scanner.nextInt();
            scanner.nextLine();
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCategory() {
            return category;
        }

        public void subtractAmount(double expenseAmount) {
            amount -= expenseAmount;
        }

        public void addExpense(Expense expense) {
            expenses.add(expense);
        }

        public List<Expense> getExpenses() {
            return expenses;
        }
    }

    public static class Expense {
        private String name;
        private double amount;
        private String date;

        public Expense(String name, double amount, String date) {
            this.name = name;
            this.amount = amount;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public double getAmount() {
            return amount;
        }

        public String getDate() {
            return date;
        }
    }
    public static class FinancialGoal {
        private double goalAmount;
        private Date targetDate;
    
        public FinancialGoal(double goalAmount, Date targetDate) {
            this.goalAmount = goalAmount;
            this.targetDate = targetDate;
        }
    
        public double getGoalAmount() {
            return goalAmount;
        }
    
        public Date getTargetDate() {
            return targetDate;
        }
    }
}
