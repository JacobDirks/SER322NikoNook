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
        } finally{
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
        } finally{
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
        } finally{
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
        } finally{
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
        } finally{
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

            System.out.println("Table Number | Capacity");
            boolean availableTables = false;
            while(rs.next()){
                String tableID = rs.getString("TableID");
                String capacity = rs.getString("Capacity");
                System.out.printf("%-12s | %-5s\n", tableID, capacity);
                availableTables = true;
            }

            if (!availableTables) {
                System.out.printf("There are no tables currently available for a party of %d\n", partySize);
            } else {
                success = true;
            }

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

            System.out.println("Table Number | Current Employee");
            while(rs.next()){
                String formatted = String.format("%-12s | %-16s", Integer.toString(rs.getInt("TableID")), Integer.toString(rs.getInt("EmployeeID")));
                System.out.println(formatted);
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

            System.out.println("Employee ID|\tEmployee Name");
            while(rs.next()){
                System.out.printf("%-11s|\t%-64s\n", Integer.toString(rs.getInt("EmployeeID")), rs.getString("EmployeeName"));
            }
            success = true;

        } catch( SQLException e) {
            System.out.println("See tables that are occupied failed");
            e.printStackTrace();
        }catch(NullPointerException e){
            System.out.println("Rs failed to initalized in Receipt method");
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

/*
    // TO DO: Delete? I think the updateOrder method now handles everything
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
    }//*/

    public boolean getReciept(int tableNum){
        
        boolean success = false;
        String str = "SELECT MQuantity, Menu_Item.ItemName, Price FROM Ordered_Item, Dining_Table, Menu_Item WHERE TableID = ? AND Ordered_Item.OrderID = Dining_Table.OrderID AND Menu_Item.ItemName = Ordered_Item.ItemName";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
        pstmt= con.prepareStatement(str);
        pstmt.setInt(1, tableNum);
        rs = pstmt.executeQuery();
        System.out.println("===== Receipt For Table " + tableNum + " =====");
        //System.out.println(" Name of Item, Quantity, Total Price");
        System.out.printf("%-64s %-9s %-5s\n", "Name of Item", "Quantity", "Total Price");
        while(rs.next()){
            int quantity  = rs.getInt("MQuantity");
            String formatted = String.format("%-64s %-9s $%-5s", rs.getString("ItemName"), Integer.toString(quantity), Double.toString(quantity * rs.getDouble("Price")));

            /*String itemName = rs.getString("ItemName");
            int quantity = rs.getInt("MQuantity");
            double price = rs.getDouble("Price");
            System.out.printf("%d, %.2f, %-64s\n", quantity, price * quantity, itemName);
             */

            System.out.println(formatted);
        }

        success = true;
        }catch(SQLException e) {
            System.out.println("Error with query in getReceipt");
            e.printStackTrace();
        }catch(NullPointerException e){
            System.out.println("Rs failed to initialized in Receipt method");
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
                System.out.println("Error closing prepared statement or ResultSet for receipt method.");
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
                System.out.println("Error closing prepared statement.");
                e.printStackTrace();
            }
        }

        return success;
    }

    public boolean updateIngredients(String name, double quantity){
        boolean success = false;
        String updateStr = "UPDATE Ingredient SET QuantityInStock = QuantityInStock + ? WHERE IngredientName = ?";
        String queryStr = "SELECT QuantityInStock FROM Ingredient WHERE IngredientName = ?";
        PreparedStatement updateSmt = null;
        PreparedStatement querySmt = null;
        ResultSet rs = null;

        try {
            updateSmt = con.prepareStatement(updateStr);
            updateSmt.setDouble(1, quantity);
            updateSmt.setString(2, name);

            int updateRows = updateSmt.executeUpdate();

            querySmt = con.prepareStatement(queryStr);
            querySmt.setString(1, name);

            rs = querySmt.executeQuery();

            if (updateRows > 0 && rs.next()) {
                System.out.println("Ingredient " + name + " updated to quantity " + rs.getString("QuantityInStock") + "\n");
                success = true;
                con.commit();
            }

        } catch(SQLException e) {
            System.out.println("Error updating ingredient " + name + " to quantity " + quantity);
            e.printStackTrace();
        } finally{
            try {
                if (updateSmt != null) {
                    updateSmt.close();
                    updateSmt = null;
                }

                if (querySmt != null) {
                    querySmt.close();
                    querySmt = null;
                }

                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException e){
                System.out.println("Error closing prepared statement or ResultSet for availableTable(int)");
                e.printStackTrace();
            }
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
        } finally {
            try {
                if(psmt != null){
                    psmt.close();
                    psmt = null;
                }
                if (rs != null){
                    rs.close();
                    rs = null;
                }
            }catch (SQLException e){
                System.out.println("Error closing prepared statement or ResultSet for receipt method.");
                e.printStackTrace();
            }
        }


        return success;
    }

    public void getMenus(){
        String res = "";
        String str = "SELECT MenuName FROM Menu";
        Statement stm = null;

        try {
            stm = con.createStatement();
            ResultSet rs = stm.executeQuery(str);
            int count = 1;
            while(rs.next()){
                res += count + ") " + rs.getString("MenuName") + "\n";
                count++;
            }
            System.out.println(res);

        } catch (SQLException e) {
            System.out.println("Error getting menus");
            e.printStackTrace();
        } finally {
            try{
                if (stm != null){
                    stm.close();
                    stm = null;
                }
            } catch (SQLException e) {
                System.out.println("Error closing statement");
                e.printStackTrace();
            }
        }
    }

    public boolean dropMenu(String menuName){
        String str1 = "DELETE FROM Menu_Connector WHERE MenuName = ?";
        String str = "DELETE FROM Menu WHERE MenuName = ?";
        PreparedStatement psmt = null;
        PreparedStatement psmt1 = null;
        boolean success = false;

        try{
            psmt1 = con.prepareStatement(str1);
            psmt1.setString(1, menuName);
            psmt1.executeUpdate();

            psmt = con.prepareStatement(str);
            psmt.setString(1, menuName);

            int rows = psmt.executeUpdate();
            if(rows > 0){
                System.out.println("Menu " + menuName + " deleted.\n");
                con.commit();
                success = true;
            } else {
                System.out.println("Something didn't go right or the menu didn't exist");
            }
        } catch (SQLException e){
            System.out.println("Error dropMenu");
            e.printStackTrace();
        } finally {
            try {
                if (psmt != null){
                    psmt.close();
                    psmt = null;
                }
                if (psmt1 != null){
                    psmt1.close();
                    psmt1 = null;
                }
            } catch (SQLException e){
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean dropItemFromMenu(String menuName, String itemName){
        boolean success = false;
        String str = "DELETE FROM Menu_Connector WHERE ItemName = ? AND MenuName = ?";
        PreparedStatement psmt = null;
        try {
            psmt = con.prepareStatement(str);
            psmt.setString(1, itemName);
            psmt.setString(2, menuName);

            int rows = psmt.executeUpdate();
            if(rows >0){
                success = true;
                con.commit();
            }
        } catch (SQLException e){
            System.out.println("Error dropItemFromMenu");
            e.printStackTrace();
        } finally {
            try {
                if (psmt != null){
                    psmt.close();
                    psmt = null;
                }
            } catch (SQLException e){
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
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
        } finally {
            try{
                if (psmt != null){
                    psmt.close();
                    psmt = null;
                }
            } catch (SQLException e){
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
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
        finally {
            try{
                if (psmt != null){
                    psmt.close();
                    psmt = null;
                }
            } catch (SQLException e){
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
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

            //System.out.println("\nItem Name\tPrice\tCategory\nDescription");
            System.out.printf("%-64s %-7s %-64s \n%-11s\n", "Item Name", "Price", "Category","Description");
            System.out.println("------------------------------------------------------------");


            boolean hasItems = false;
            while(rs.next()) {
                String itemName = rs.getString("ItemName");
                String description = rs.getString("Description");
                double price = rs.getDouble("Price");
                String category = rs.getString("Category");
                /*

                System.out.println(itemName + description + price + category);
                // */
                System.out.printf("%-64s $%-7s %-64s \n", itemName, Double.toString(price), category);
                System.out.println(cosmetic(description, 55));
                System.out.println();

                hasItems = true;
            }

            if (hasItems) {
                System.out.println("------------------------------------------------------------");
            } else {
                System.out.println("No items found for menu " + menuName);
            }
            success = true;


        } catch(SQLException e) {
            System.out.println("Error showMenu");
            e.printStackTrace();
        } finally {
            try{
                if (psmt != null){
                    psmt.close();
                    psmt = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException e){
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
        }
        return success;
    }

    public String cosmetic(String alter, int numberForWrap){
        if(alter == null || alter.isEmpty()) {
            return alter;
        } // done with type check
        StringBuilder wrapped = new StringBuilder();
        int currentLength = 0;
        String[] words = alter.split(" ");

        for(String word : words){
            if(currentLength + word.length() +1 <= numberForWrap){
                wrapped.append(word).append(" ");
                currentLength += word.length()+1;
            } else {
                wrapped.append("\n").append(word).append(" ");
                currentLength = word.length() +1;
            }
        }
        return wrapped.toString().trim();
    }

    public boolean listAllIngredientStock() {
        // List all current ingedients and how much (of what unit) is in stock
        String str = "SELECT * FROM Ingredient";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean success = false;
        try{
            pstmt = con.prepareStatement(str);
            rs = pstmt.executeQuery();

            System.out.printf("%-64s %-17s \t%-64s\n","Ingredient Name", "Quantity in Stock", "Unit of Quantity");
            while(rs.next()){
                String ingredient = rs.getString("IngredientName");
                String quantity = Double.toString(rs.getDouble("QuantityInStock"));
                String unit = rs.getString("UnitOfQuantity");
                System.out.printf("%-64s %-17s \t%-64s\n", ingredient, quantity, unit);
            }
            success = true;

        } catch( SQLException e) {
            System.out.println("Listing all ingredients");
            e.printStackTrace();
        }catch(NullPointerException e){
            System.out.println("Null Pointer Exception");
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
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
        }
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
        }finally {
            try{
                if (pstmt != null){
                    pstmt.close();
                    pstmt = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }catch (SQLException e) {
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean ingredientExists(String ingredientName) {
        // check if the given ingredient exists in the Ingredient table
        String str = "SELECT * FROM Ingredient WHERE IngredientName = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean success = false;


        try {
            pstmt = con.prepareStatement(str);
            pstmt.setString(1, ingredientName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                success = true;
            }

        } catch (SQLException e) {
            System.out.println("Error: Not able to tell if ingredient " + ingredientName + " exists.");
            e.printStackTrace();
        } finally {
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
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }finally {
                try{
                    if (pstmt != null){
                        pstmt.close();
                        pstmt = null;
                    }
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                }catch (SQLException e){
                    System.out.println("Error closing prepared statement");
                    e.printStackTrace();
                }
            }
        }

        return success;
    } 

    public boolean addItemToMenu(String itemName, String menuName) {
        // add the item to the menu
        boolean success = false;

        String str = "INSERT INTO Menu_Connector(MenuName, ItemName) Values (?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(str);
            pstmt.setString(1, menuName);
            pstmt.setString(2, itemName);

            int updateRows = pstmt.executeUpdate();

            if (updateRows > 0) {
                System.out.println("Added item to menu.");
                success = true;
                con.commit();
            }

        } catch (SQLException e) {
            System.out.println("Error: Not able to to add item to menu.");
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null){
                    pstmt.close();
                    pstmt = null;
                }
            }catch (SQLException e){
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
        }
        
        return success;
    }

    public boolean showAllItems() {
        // list out all menu items, even if they are not attached to any menu currently
        String str = "SELECT ItemName, Description, Price, Category FROM Menu_Item ORDER BY Category";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            pstmt = con.prepareStatement(str);
            rs = pstmt.executeQuery();

            System.out.printf("%-64s %-7s %-64s \n%-11s\n", "Item Name", "Price", "Category","Description");
            System.out.println("------------------------------------------------------------");

            boolean hasItems = false;
            while (rs.next()) {
                String itemName = rs.getString("ItemName");
                String description = rs.getString("Description");
                double price = rs.getDouble("Price");
                String category = rs.getString("Category");

                System.out.printf("%-64s $%-7s %-64s \n", itemName, Double.toString(price), category);
                System.out.println(cosmetic(description, 55));
                hasItems = true;
            }

            if (hasItems) {
                System.out.println("------------------------------------------------------------");
            } else {
                System.out.println("No items found.");
            }
            success = true;

        } catch (SQLException e) {
            System.out.println("Error: Failed to retrieve all menu items.");
            e.printStackTrace();
        } finally {
            try{
                if (pstmt != null){
                    pstmt.close();
                    pstmt = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException e){
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
        }

        return success;
    }

    public boolean menuItemExists(String itemName) {
        // determine if the given menu item exists in the menu item table
        boolean exists = false;

        String str = "SELECT * FROM Menu_Item WHERE ItemName = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(str);
            pstmt.setString(1, itemName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            System.out.println("Error: Not able to tell if menu item already exists.");
            e.printStackTrace();
        }
        finally {
            try{
                if (pstmt != null){
                    pstmt.close();
                    pstmt = null;
                }
            } catch (SQLException e){
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
        }

        return exists;
    }

    public boolean createNewMenu(String menuName) {
        boolean success = false;

        String str = "INSERT INTO Menu(MenuName) Values (?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(str);
            pstmt.setString(1, menuName);

            int updateRows = pstmt.executeUpdate();
            if (updateRows > 0) {
                System.out.println("Created menu.");
                success = true;
                con.commit();
            }

        } catch (SQLException e) {
            System.out.println("Error: Not able to create menu.");
            e.printStackTrace();
        }
        finally {
            try{
                if(pstmt != null){
                    pstmt.close();
                    pstmt = null;
                }
            }catch (SQLException e){
                System.out.println("Error closing prepared statement");
                e.printStackTrace();
            }
        }
        return success;
    }
}
