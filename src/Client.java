import java.util.Scanner;


public class Client {
    Jdbc jdbc;
    Scanner scanner;
    String currentMenu;

    public static void main(String[] args){
        Client c = new Client();

        if (args.length != 4) {
            System.out.println("USAGE: java Client <url> <user> <password> <driver>");
            System.exit(1);
        }

        c.run(args[0], args[1], args[2], args[3]);
    }

    public void run(String url, String username, String password, String driver) {
        jdbc = new Jdbc(url, username, password, driver);
        scanner = new Scanner(System.in);
        currentMenu = "Lunch";

        while (true) {
            displayMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    seatPartyAtTable();
                    break;
                case "2":
                    orderFoodAtTable();
                    break;
                case "3":
                    getReceiptForTable();
                    break;
                case "4":
                    assignEmployeeToTable();
                    break;
                case "5":
                    updateIngredientStock();
                    break;
                case "6":
                    editMenus();
                    break;
                case "7": // Exit
                    System.out.println("Exiting...");
                    jdbc.closeConnection();
                    return;
                default: // Outside of 1-7
                    System.out.println("Invalid choice. Please try again!");
                    break;
            }

        }
    }

    public void displayMenu() {
        System.out.println("     .-') _        .-. .-')                    .-')              .-') _                           .-. .-')   \n" +
                "    ( OO ) )       \\  ( OO )             ,--. ( OO ).           ( OO ) )                          \\  ( OO )  \n" +
                ",--./ ,--,' ,-.-') ,--. ,--.  .-'),-----.\\  |(_)---\\_)      ,--./ ,--,'  .-'),-----.  .-'),-----. ,--. ,--.  \n" +
                "|   \\ |  |\\ |  |OO)|  .'   / ( OO'  .-.  '`-'/    _ |       |   \\ |  |\\ ( OO'  .-.  '( OO'  .-.  '|  .'   /  \n" +
                "|    \\|  | )|  |  \\|      /, /   |  | |  |   \\  :` `.       |    \\|  | )/   |  | |  |/   |  | |  ||      /,  \n" +
                "|  .     |/ |  |(_/|     ' _)\\_) |  |\\|  |    '..`''.)      |  .     |/ \\_) |  |\\|  |\\_) |  |\\|  ||     ' _) \n" +
                "|  |\\    | ,|  |_.'|  .   \\    \\ |  | |  |   .-._)   \\      |  |\\    |    \\ |  | |  |  \\ |  | |  ||  .   \\   \n" +
                "|  | \\   |(_|  |   |  |\\   \\    `'  '-'  '   \\       /      |  | \\   |     `'  '-'  '   `'  '-'  '|  |\\   \\  \n" +
                "`--'  `--'  `--'   `--' '--'      `-----'     `-----'       `--'  `--'       `-----'      `-----' `--' '--'");
        System.out.println("1. Seat Party at Table");
        System.out.println("2. Order Food at Table");
        System.out.println("3. Get Reciept for Table");
        System.out.println("4. Assign Employee to Table");
        System.out.println("5. Update Ingredient Stock");
        System.out.println("6. Edit Menus");
        System.out.println("7. Exit");
        System.out.print("Enter a number: ");
    }

    //Seat party at table
    public void seatPartyAtTable() {
        // Get Size of Party
        int party_size = getPartySize();

        // List all available tables that will fit party
        if (!jdbc.seeAvailableTables(party_size)) {
            // No tables were available for the given party size
            return;
        }

        // Get table to seat them at
        int table = getAvailableTableID();

        // Seat Them (mark table as occupied)
        jdbc.seatTable(table);
    }

    // Order Food at Table
    public void orderFoodAtTable() {
        // List tables that are seated
        jdbc.seeOccupiedTables();

        // Get selection from user
        int tableID = getTableID();

        // Repeatedly get items until user is done
        while (true) {
            // List items on current menu
            jdbc.showMenu(currentMenu);
            String item = getMenuItem(currentMenu);
            if (item.equalsIgnoreCase("done")) {
                break;
            }
            jdbc.updateOrder(tableID, item, true);
        }
    }

    // Get Receipt for Table
    public void getReceiptForTable() {
        // List all tables that are seated
        jdbc.seeOccupiedTables();

        // Get selection from user
        int tableID = getTableID();

        // Pretty print out all items w/ quanity and total price
        jdbc.getReciept(tableID);
    }

    // Assign Employee to Table
    public void assignEmployeeToTable() {
        // List all employees
        jdbc.listAllEmployees();

        // Get selection from user
        int employeeID = getEmployeeID();

        // List all tables and current server assigned
        jdbc.listAllTablesWithAssignedEmployee();

        // Get selection from user
        int tableID = getTableID();

        // Assign server to table in DB
        jdbc.assignServerToTable(employeeID, tableID);
    }

    // Update Ingredient Stock
    public void updateIngredientStock() {
        while (true) {
            // List out all current ingredients and their stock levels
            jdbc.listAllIngredientStock();

            // Get selection from user
            String ingredient = getIngredientName();

            if (ingredient.equalsIgnoreCase("done")) {
                break;
            }

            // Ask how much to add to the restock
            String unit = jdbc.getUnitOfIngredient(ingredient);
            double restockAmount = getIngredientRestockAmount(ingredient, unit);

            // Update database
            jdbc.updateIngredients(ingredient, restockAmount);
        }
    }

    // Edit Menus
    public void editMenus() {
        System.out.println("\n\n");
        while (true) {
            System.out.println("1. Change menu");
            System.out.println("2. Add Item to Menu");
            System.out.println("3. Remove Item from Menu");
            System.out.println("4. Create Menu");
            System.out.println("5. Delete Menu");
            System.out.println("6. Cancel");
            System.out.print("Enter a number: ");

            // THESE ARE THE LEAST IMPORTANT METHODS RN, TRY TO SAVE FOR LAST
            String choice = scanner.nextLine();
            System.out.println();
            switch (choice) {
                case "1":
                    changeMenu();
                    break;
                case "2":
                    addItemToMenu();
                    break;
                case "3":
                    removeItemFromMenu();
                    break;
                case "4":
                    createMenu();
                    break;
                case "5":
                    deleteMenu();
                    break;
                case "6": // Return
                    return;
                default: // Outside of 1-6
                    System.out.println("Invalid choice. Please try again!");
                    break;
            }
        }
    }

    public void changeMenu() {
        // List out all available menus
        jdbc.getMenus();
        // Get selection from user
        currentMenu = getMenuName();
    }

    public void addItemToMenu() {
        // List out all available menus
        jdbc.getMenus();
        // Get selection from user
        String menuToAddTo = getMenuName();
        // List out all available items from anywhere
        jdbc.showAllItems();
        // Get selection from user and add to menu in db
        String item = getValidItem();
        jdbc.addItemToMenu(item,  menuToAddTo);
    }

    public void removeItemFromMenu() {
        // List out all available menus
        jdbc.getMenus();
        // Get selection from user
        String menuToRemoveFrom = getMenuName();
        // List out all item in the menu
        jdbc.showMenu(menuToRemoveFrom);
        // Get selection from user and remove from menu in db
        String item = getMenuItem(menuToRemoveFrom);
        jdbc.dropItemFromMenu(menuToRemoveFrom, item);
    }

    public void createMenu() {
        // get menu name/ handle
        String name = getNewMenuName();
        jdbc.createNewMenu(name);


        // Continually list out items and have user select which to add to new menu until they are done
        while (true) {
            // List items on current menu
            jdbc.showAllItems();
            String item = getValidItemNotInMenu(name);
            if (item.equalsIgnoreCase("done")) {
                break;
            }
            jdbc.addItemToMenu(item, name);
        }
    }

    public void deleteMenu() {
        // List all menus
        jdbc.getMenus();
        // Get selection from user
        String menuToDelete = getMenuName();
        // Remove menu from db
        jdbc.dropMenu(menuToDelete);
    }

    public int getPartySize() {
        System.out.print("Please enter party size: ");
        if (scanner.hasNextInt()) {
            int partySize = scanner.nextInt();
            scanner.nextLine();
            return partySize;
        }
        else {
            scanner.nextLine();
            System.out.println("Invalid input. Please try again!");
            return getPartySize();
        }
    }

    public double getIngredientRestockAmount(String ingredient, String unit) {
        System.out.printf("How many %s of %s do you want to restock?\n", unit, ingredient);
        if (scanner.hasNextDouble()) {
            double restockAmount = scanner.nextDouble();
            scanner.nextLine();
            return restockAmount;
        }
        else {
            scanner.nextLine();
            System.out.println("Invalid input. Please try again!");
            return getIngredientRestockAmount(ingredient, unit);
        }
    }

    public int getTableID() {
        System.out.println("Please enter the table id for the table you would like to select.");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine();

            // check if id actually exists through jdbc
            if (jdbc.tableIDExists(id)) {
                return id;
            }
            else {
                System.out.println("Invalid table id. Please try again!");
                return getTableID();
            }

        }
        else {
            scanner.nextLine();
            System.out.println("Invalid input. Please try again!");
            return getTableID();
        }
    }

    public int getAvailableTableID() {
        int tableID = getTableID();

        if (jdbc.tableIsAvailable(tableID)) {
            return tableID;
        } else {
            System.out.printf("Table %d is occupied.\n", tableID);
            return getAvailableTableID();
        }
    }

    public String getMenuName() {
        // Get menu name from user
        System.out.println("Please enter the name of the menu you would like to select.");
        String name = scanner.nextLine();

        // ensure menuName exists menu
        if (jdbc.menuExists(name)) {
            return name;
        } else {
            System.out.println("Invalid menu name. Please try again!");
            return getMenuName();
        }
    }

    public String getNewMenuName() {
        // Get menu name from user
        System.out.println("Please enter the name of the new menu.");
        String name = scanner.nextLine();

        // ensure menuName is not a duplicate of an existing menu
        if (!jdbc.menuExists(name)) { // menu name does not already exist
            return name;
        } else {
            System.out.println("Menu name already exists. Please try again!");
            return getNewMenuName();
        }
    }

    public String getIngredientName() {
        // Get ingredient name from user
        System.out.println("Please enter the ingredient name you would like to update the stock of. Enter \"Done\" when you are done.");
        String name = scanner.nextLine();

        if (name.equalsIgnoreCase("Done")) {
            return "Done";
        }

        // ensure menu item is on the menu
        if (jdbc.ingredientExists(name)) { // item name does not already exist in menu
            return name;
        } else {
            System.out.printf("%s is not on the menu. Please try again!\n", name);
            return getIngredientName();
        }
    }

    public String getMenuItem(String menu) {
        // Get item name from user
        System.out.println("Please enter the item name you would like to order. Enter \"Done\" when you are done.");
        String name = scanner.nextLine();

        if (name.equalsIgnoreCase("Done")) {
            return "Done";
        }

        // ensure menu item is on the menu
        if (jdbc.menuItemIsInMenu(menu, name)) { // item name does not already exist in menu
            return name;
        } else {
            System.out.printf("%s is not on the menu. Please try again!\n", name);
            return getMenuItem(menu);
        }
    }

    public String getValidItem() {
        // Get item name from user
        System.out.println("Please enter an item name. Enter \"Done\" when you are done.");
        String name = scanner.nextLine();

        if (name.equalsIgnoreCase("Done")) {
            return "Done";
        }

        // ensure menu item is on the menu
        if (jdbc.menuItemExists(name)) {
            return name;
        } else {
            System.out.printf("%s does not exist. Please try again!\n", name);
            return getValidItem();
        }
    }

    public String getValidItemNotInMenu(String menu) {
        // Get item name from user
        System.out.println("Please enter an item name. Enter \"Done\" when you are done.");
        String name = scanner.nextLine();

        if (name.equalsIgnoreCase("Done")) {
            return "Done";
        }

        // ensure menu item is on the menu
        if (jdbc.menuItemExists(name) && !jdbc.menuItemIsInMenu(menu, name)) {
            return name;
        } else {
            System.out.printf("%s is already ont he menu, or does not exist. Please try again!\n", name);
            return getValidItemNotInMenu(menu);
        }
    }

    public int getEmployeeID() {
        System.out.println("Please enter the employee id for the employee you would like to select.");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine();

            // check if id actually exists through jdbc
            if (jdbc.employeeIDExists(id)) {
                return id;
            }
            else {
                System.out.println("Invalid employee id. Please try again!");
                return getEmployeeID();
            }

        }
        else {
            scanner.nextLine();
            System.out.println("Invalid input. Please try again!");
            return getEmployeeID();
        }
    }
}
