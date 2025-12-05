import java.sql.*;


public class Jdbc {
    static Connection con;

    public Jdbc(String url, String user, String password, String driver) {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false); // prevents misc data from automatically getting saved
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (con != null) {
                con.rollback(); // undoes anything we didn't save prior
                con.close();
                con = null;
            }
        } catch (SQLException se) {
            System.out.println("Error closing connection");
            se.printStackTrace();
        }
    }

    public boolean tableIDExists(int id) {
        String str = "SELECT * FROM Dining_Table WHERE TableID = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            pstmt = con.prepareStatement(str);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (SQLException e) {
            System.out.println("Error: Not able to tell if table id " + id + " exists.");
            e.printStackTrace();
        }
        return exists;
    }

    public boolean employeeIDExists(int id) {
        String str = "SELECT * FROM Employee WHERE EmployeeID = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            pstmt = con.prepareStatement(str);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (SQLException e) {
            System.out.println("Error: Not able to tell if employee id " + id + " exists.");
            e.printStackTrace();
        }
        return exists;
    }

    public boolean menuExists(String name) {
        String str = "SELECT * FROM Menu WHERE MenuName = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            pstmt = con.prepareStatement(str);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            System.out.println("Error: Not able to tell if menu already exists.");
            e.printStackTrace();
        }
        return exists;
    }

    public boolean menuItemIsInMenu(String menuName, String item) {
        String str = "SELECT * FROM Menu_Connector WHERE MenuName = ? AND ItemName = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            pstmt = con.prepareStatement(str);
            pstmt.setString(1, menuName);
            pstmt.setString(2, item);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            System.out.println("Error: Not able to tell if item is in menu.");
            e.printStackTrace();
        }
        return exists;
    }

    public boolean tableIsAvailable(int id) {
        String str = "SELECT * FROM Dining_Table WHERE TableID = ? AND Status = 'Available'";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;
        try {
            pstmt = con.prepareStatement(str);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (SQLException e) {
            System.out.println("Error: Not able to tell if table id " + id + " is available.");
            e.printStackTrace();
        }
        return exists;
    }

    public boolean seeAvailableTables(int partySize){
        String str = "SELECT * FROM Dining_Table WHERE Capacity >= ? AND Status = 'Available'";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean success = false;
        try{
            pstmt = con.prepareStatement(str);
            pstmt.setInt(1, partySize);
            rs = pstmt.executeQuery();

            System.out.println("Table Number, Capacity");
            while(rs.next()){
                System.out.println(rs.getInt("TableID") + ", " + rs.getInt("Capacity"));
            }
            success = true;

        } catch( SQLException e) {
            System.out.println("See tables that are available and are above " + partySize + " capacity failed");
            e.printStackTrace();
        }catch(NullPointerException e){
            System.out.println("Rs failed to initalized in Reciept method");
            e.printStackTrace();
        }finally{
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }

                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }catch (SQLException e){
                System.out.println("Error closing prepared statement or ResultSet for availableTable(int)");
                e.printStackTrace();
            }
        }
        return success;

    }

    public boolean seeOccupiedTables(){
        String str = "SELECT * FROM Dining_Table WHERE Status = 'Occupied'";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean success = false;
        try{
            pstmt = con.prepareStatement(str);
            rs = pstmt.executeQuery();

            System.out.println("Table Number");
            while(rs.next()){
                System.out.println(rs.getInt("TableID"));
            }
            success = true;

        } catch( SQLException e) {
            System.out.println("See tables that are occupied failed");
            e.printStackTrace();
        }catch(NullPointerException e){
            System.out.println("Rs failed to initalized in Reciept method");
            e.printStackTrace();
        }finally{
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }

                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }catch (SQLException e){
                System.out.println("Error closing prepared statement or ResultSet for availableTable(int)");
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean listAllTablesWithAssignedEmployee(){
        String str = "SELECT TableID, EmployeeID FROM Dining_Table";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean success = false;
        try{
            pstmt = con.prepareStatement(str);
            rs = pstmt.executeQuery();

            System.out.println("Table Number\tEmployee ID");
            while(rs.next()){
                System.out.println(rs.getInt("TableID") + "\t" + rs.getInt("EmployeeID"));
            }
            success = true;

        } catch( SQLException e) {
            System.out.println("List tables with server failed");
            e.printStackTrace();
        }catch(NullPointerException e){
            System.out.println("Rs failed to initalized in listAllTablesWithAssignedEmployees method");
            e.printStackTrace();
        }finally{
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }

                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }catch (SQLException e){
                System.out.println("Error closing prepared statement or ResultSet for availableTable(int)");
                e.printStackTrace();
            }
        }
        return success;

    }

    public boolean listAllEmployees(){
        String str = "SELECT * FROM Employee";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean success = false;
        try{
            pstmt = con.prepareStatement(str);
            rs = pstmt.executeQuery();

            System.out.println("EmployeeID\tEmployeeName");
            while(rs.next()){
                System.out.print(rs.getInt("EmployeeID") + "\t");
                System.out.println(rs.getString("EmployeeName"));
            }
            success = true;

        } catch( SQLException e) {
            System.out.println("See tables that are occupied failed");
            e.printStackTrace();
        }catch(NullPointerException e){
            System.out.println("Rs failed to initalized in Reciept method");
            e.printStackTrace();
        }finally{
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }

                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }catch (SQLException e){
                System.out.println("Error closing prepared statement or ResultSet for availableTable(int)");
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean addFoodToTable(int tableNum, String itemName, int quantity){
        boolean success = false;
        String str = "INSERT INTO Ordered_Item (OrderID, ItemName, MQuantity) VALUES ((SELECT OrderID FROM Dining_Table WHERE TableID = ?), '?', ?)";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if(itemName.contains(";")){
            System.out.println("Error itemName can not have semiColons");
            return success;
        }else if (itemName.toLowerCase().contains("where")){
            System.out.println("What are you doing with a where in your name?");
            return success;
        }else if (itemName.toLowerCase().contains("select")){
            System.out.println("What are you doing with a SELECT in your name?");
            return success;
        }


        try {

            pstmt = con.prepareStatement(str);
            pstmt.setInt(1, tableNum);
            pstmt.setString(2, itemName);
            pstmt.setInt(2, quantity);
            int rows = pstmt.executeUpdate();

            if(rows > 0 ){
                System.out.println("Table " + tableNum + " has had their order updated.");
                success = true;
                con.commit();
            }

        }catch (SQLException e) {
            System.out.println("Error regarding addFoodToTable sql updates");
            e.printStackTrace();
        }catch(NullPointerException e){
            System.out.println("Rs failed to initalized in Reciept method");
            e.printStackTrace();
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }
            }catch (SQLException e){
                System.out.println("Error closing rs and prepared statment in addFoodToTable");
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean getReciept(int tableNum){
        boolean success = false;
        String str = "SELECT MQuantity, Menu_Item.ItemName, Price FROM Ordered_Item, Dining_Table, Menu_Item WHERE TableID = ? AND Ordered_Item.OrderID = Dining_Table.OrderID AND Menu_Item.ItemName = Ordered_Item.ItemName";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
        pstmt= con.prepareStatement(str);
        pstmt.setInt(1, tableNum);
        rs = pstmt.executeQuery();
        System.out.println("=== Reciept For Table " + tableNum + " ===");
        System.out.println("Quantity, Price, Name of Item");
        while(rs.next()){
            String itemName = rs.getString("ItemName");
            int quantity = rs.getInt("MQuantity");
            double price = rs.getDouble("Price");
            System.out.printf("%d, %.2f, %-64s\n", quantity, price * quantity, itemName);
        }

        success = true;
        }catch(SQLException e) {
            System.out.println("Error with query in getReciept");
            e.printStackTrace();
        }catch(NullPointerException e){
            System.out.println("Rs failed to initalized in Reciept method");
            e.printStackTrace();
        } finally {
            try{
                if(pstmt != null){
                    pstmt.close();
                    pstmt = null;
                }
                if(rs != null){
                    rs.close();
                    rs = null;
                }
            }catch (SQLException e){
                System.out.println("Error closing prepared statement or ResultSet for reciept method.");
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean updateServer(int tableNum, int EmployeeID) {
        boolean success = false;
        String str = "UPDATE Dining_Table SET EmployeeID = ? WHERE TableID = ?";
        PreparedStatement psmt = null;

        try{
            psmt= con.prepareStatement(str);
            psmt.setInt(1, EmployeeID);
            psmt.setInt(2, tableNum);

            int rows = psmt.executeUpdate();
            if(rows != 0){
                System.out.println(rows + " row(s) were updated");
                success = true;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(psmt != null){
                    psmt.close();
                    psmt = null;
                }
            }catch (SQLException e){
                System.out.println("Error closing psmt");
                e.printStackTrace();
            }
        }

        return success;
    }

    public boolean updateIngredients(String name, double quantity){
        boolean success = false;
        String str = "UPDATE Ingredient SET QuantityInStock = QuantityInStock + ? WHERE IngredientName = ?";
        PreparedStatement psmt = null;

        try {
            psmt = con.prepareStatement(str);
            psmt.setDouble(1, quantity);
            psmt.setString(2, name);
            int rows = psmt.executeUpdate();

            if (rows > 0){
                System.out.println("Ingredient " + name + " updated to quantity " + quantity);
                success = true;
                con.commit();
            }

        } catch(SQLException e) {
            System.out.println("Error updating ingredient " + name + " to quantity " + quantity);
            e.printStackTrace();
        }

        return success;
    }

    public boolean updateOrder(int tableNum, String dishName, boolean addition) {
        boolean success = false;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            // check if item exists
            String check = "SELECT MQuantity FROM Ordered_Item WHERE OrderID = (SELECT OrderID FROM Dining_Table WHERE TableID = ?) AND ItemName = ?";
            psmt = con.prepareStatement(check);
            psmt.setInt(1, tableNum);
            psmt.setString(2, dishName);
            rs = psmt.executeQuery();

            if (rs.next()) { // if item exists
                int currentQuantity = rs.getInt("MQuantity");

                if (addition) { // if adding
                    // add one to quantity
                    String update = "UPDATE Ordered_Item SET MQuantity = MQuantity + 1 WHERE OrderID = (SELECT OrderID FROM Dining_Table WHERE TableID = ?) AND ItemName = ?";
                    psmt = con.prepareStatement(update);
                    psmt.setInt(1, tableNum);
                    psmt.setString(2, dishName);
                    int rows = psmt.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Added one " + dishName + " to table " + tableNum + "'s order.\n" + dishName + " Quantity: " + (currentQuantity + 1));
                        success = true;
                        con.commit();
                    }

                } else { // if removing
                    if (currentQuantity > 1) { // if quantity is more than 1
                        // remove one from quantity
                        String update = "UPDATE Ordered_Item SET MQuantity = MQuantity - 1 WHERE OrderID = (SELECT OrderID FROM Dining_Table WHERE TableID = ?) AND ItemName = ?";
                        psmt = con.prepareStatement(update);
                        psmt.setInt(1, tableNum);
                        psmt.setString(2, dishName);
                        int rows = psmt.executeUpdate();

                        if (rows > 0) {
                            System.out.println("Removed one " + dishName + " from table " + tableNum + "'s order.\n" + dishName + " Quantity: " + (currentQuantity - 1));
                            success = true;
                            con.commit();
                        }

                    } else { // else if quantity is 1
                        // remove item from order (no more quantity)
                        String delete = "DELETE FROM Ordered_Item WHERE OrderID = (SELECT OrderID FROM Dining_Table WHERE TableID = ?) AND ItemName = ?";
                        psmt = con.prepareStatement(delete);
                        psmt.setInt(1, tableNum);
                        psmt.setString(2, dishName);
                        int rows = psmt.executeUpdate();

                        if (rows > 0) {
                            System.out.println("Removed " + dishName + " from table " + tableNum + "'s order.");
                            success = true;
                            con.commit();
                        }
                    }
                }
            } else { // else if item doesn't exist in order
                if (addition) { // if adding
                    // insert new item with quantity 1
                    String insert = "INSERT INTO Ordered_Item (OrderID, ItemName, MQuantity) VALUES ((SELECT OrderID FROM Dining_Table WHERE TableID = ?), ?, 1)";
                    psmt = con.prepareStatement(insert);
                    psmt.setInt(1, tableNum);
                    psmt.setString(2, dishName);
                    int rows = psmt.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Added one " + dishName + " to table " + tableNum + "'s order.\n" + dishName + " Quantity: 1");
                        success = true;
                        con.commit();
                    }
                } else {// else if removing
                    // do nothing, send message that user is being silly
                    System.out.println("Item " + dishName + " not found in table " + tableNum + "'s order.");
                    success = true; // technically success (?)
                }
            }

        } catch(SQLException e) {
            System.out.println("Error updating table " + tableNum + "'s order.");
            e.printStackTrace();
        }

        return success;
    }

    public String getMenus(){
        String res = "";
        String str = "SELECT MenuName FROM Menu";

        try{
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(str);
            int count = 1;
            while(rs.next()){
                res += count + ") " + rs.getString("MenuName") + "\n";
            }
        }catch (SQLException e) {
            System.out.println("Error getting menus");
            e.printStackTrace();
            return res;
        }
        return res;
    }

    public boolean dropMenu(String menuName){
        String str = "DELETE FROM Menu_Item WHERE ItemName = '?'";
        PreparedStatement psmt = null;
        boolean success = false;

        try{
            psmt = con.prepareStatement(str);
            int rows = psmt.executeUpdate();
            if(rows >0){
                System.out.println(rows + " row(s) were updated.");
                con.commit();
                success = true;
            } else {
                System.out.println("Something didn't go right or the menu didn't exist");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return success;
    }

    public boolean dropItemFromMenu(String menuName, String itemName){
        boolean success = false;
        String str = "Drop FROM Menu_Connector WHERE ItemName = '?' AND MenuName = ?";
        try{
            PreparedStatement psmt = con.prepareStatement(str);
            psmt.setString(1, itemName);
            psmt.setString(2, menuName);

            int rows = psmt.executeUpdate();
            if(rows >0){
                success = true;
                con.commit();
            }
        }catch (SQLException e){
            System.out.println("Error dropItemFromMenu");
            e.printStackTrace();
        }
        return success;
    }

    public boolean assignServerToTable(int employeeID, int tableID) {
        boolean success = false;
        String str = "UPDATE Dining_Table SET EmployeeID = ? WHERE TableID = ?";
        PreparedStatement psmt = null;

        try {
            psmt = con.prepareStatement(str);
            psmt.setInt(1, employeeID);
            psmt.setInt(2, tableID);
            int rows = psmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Successfully assigned employee " + employeeID + " to table " + tableID);
                success = true;
                con.commit();
            } else {
                System.out.println("Error assigning employee " + employeeID + " to table " + tableID);
            }


        } catch (SQLException e) {
            System.out.println("Error assignServerToTable");
            e.printStackTrace();
        }
        return success;
    }

    public boolean seatTable(int tableID) { // change table status from Available to Occupied
        boolean success = false;
        String str = "UPDATE Dining_Table SET Status = 'Occupied' WHERE TableID = ? AND Status = 'Available'";
        PreparedStatement psmt = null;

        try {
            psmt = con.prepareStatement(str);
            psmt.setInt(1, tableID);
            int rows = psmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Successfully seated table " + tableID);
                success = true;
                con.commit();
            } else {
                System.out.println("Table " + tableID + " is not available or does not exist.");
            }

        } catch(SQLException e) {
            System.out.println("Error seatTable");
            e.printStackTrace();
        }

        return success;
    }

    public boolean showMenu(String menuName) {
        boolean success = false;
        String str = "SELECT m.ItemName, m.Description, m.Price, m.Category " +
                "FROM Menu_Item m " +
                "JOIN Menu_Connector mc ON m.ItemName = mc.ItemName " +
                "WHERE mc.MenuName = ?";
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            psmt = con.prepareStatement(str);
            psmt.setString(1, menuName);
            rs = psmt.executeQuery();

            System.out.println("\nItem Name\tDescription\tPrice\tCategory"); // TODO: Change Formatting
            System.out.println("----------");


            boolean hasItems = false;
            while(rs.next()) {
                String itemName = rs.getString("ItemName");
                String description = rs.getString("Description");
                double price = rs.getDouble("Price");
                String category = rs.getString("Category");

                System.out.println(itemName + description + price + category); // TODO: Change Formatting
                hasItems = true;
            }

            if (hasItems) {
                System.out.println("---------");
            } else {
                System.out.println("No items found for menu " + menuName);
            }
            success = true;


        } catch(SQLException e) {
            System.out.println("Error showMenu");
            e.printStackTrace();
        }
        return success;
    }

    public boolean listAllIngredientStock() {
        boolean success = false;

        return success;
    }

    public String getUnitOfIngredient(String ingredient) {
        String str = "SELECT UnitOfQuantity FROM Ingredient WHERE IngredientName = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(str);
            pstmt.setString(1, ingredient);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("UnitOfQuantity");
            }
        } catch (SQLException e) {
            System.out.println("Error: Not able to get unit of ingredient.");
            e.printStackTrace();
        }
        return null;
    }

    public boolean ingredientExists(String ingredientName) {
        boolean success = false;

        return success;
    }






















}
