package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import Exceptions.Exceptions;
import JDB.JDBConnection;
import JDB.SQLCommands;
import Encryption.Encryption;

public class Admin implements User {

	public void addStock() {

		int id = 0;
		String type;
		String price;
		String quantity;
		System.out.print("Input type:");
		type = getInput();
		System.out.print("input price:");
		price = getInput();
		System.out.print("Input quantity:");
		quantity = getInput();
		
		try (
				Connection conn = JDBConnection.getConnection();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(SQLCommands.SELECT_ID_FROM_STOCK_ASC);
			){
				rs.last();
				 id = Integer.parseInt(rs.getString("id"))+1;
		} catch (SQLException e) {
			System.err.println("It seems that there is no stock");
		}

		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSER_INTO_STOCK);
			){
			
			if(stmt.executeUpdate(
				String.format(SQLCommands.INSER_INTO_STOCK, id, type, price, quantity)			
			) == 1) {
				System.out.printf("Stock with ID: '%s' was successfully added",id);
			}else {
				System.err.println("No new stock was added");
			}
		} catch (SQLException e) {
			System.err.println(Exceptions.WRONG_DATA_TYPE_INPUT + " or already existing product");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	public void readStock() {//can't see something to be improved
		ResultSet rs = null;
		String format = "ID:%-10sType:%-25sPrice:%-20sQuantity:%-20s%n";
		try (
			Connection conn = JDBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_FROM_STOCK);
				){
			rs = stmt.executeQuery();
			while(rs.next()) {

				System.out.printf(format, rs.getString("id"), rs.getString("type"), rs.getString("price"), rs.getString("quantity"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void editStock() {//seems perfect
		System.out.print("What do you want to change ID/Type/Price/Quantity\nAnswer:");
		String ans = getInput();	
		try	{	
				/*if(ans.equalsIgnoreCase("id")) {					
					updateProductID();
				}else */if(ans.equalsIgnoreCase("type")) {
					updateProductType();
				}else if(ans.equalsIgnoreCase("price")) {
					updateProductPrice();
				}else if(ans.equalsIgnoreCase("quantity")) {
					updateProductQuantity();
				}else {
					System.err.println(Exceptions.NO_SUCH_COLUMN);
				}
				
		} catch(Exception e) {
			System.out.println(e);
		}
	}
/*	private void updateProductID() {//seems perfect
		System.out.print("ID you want to change:");
		String old = getInput();
		System.out.print("Input new ID:");
		String neww = getInput();	
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_PRODUCT_ID);
			)
		{
			if(stmt.executeUpdate(
			String.format(SQLCommands.UPDATE_PRODUCT_ID, neww, old)
			) == 1) {
				System.out.println("Product was updated successfully");
			}else {
				System.err.printf("No product with id '%s' was found\nNothing wasn updated!",old);
			}
		} catch (SQLException e) {
			System.err.println("Input valid ID!");
		}
	}*/
	private void updateProductType() {//seems perfect
		System.out.print("ID of the type you want to change:");
		String old = getInput();
		System.out.print("Input new TYPE:");
		String neww = getInput();	
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_PRODUCT_TYPE);
			)
		{
			if(stmt.executeUpdate(
			String.format(SQLCommands.UPDATE_PRODUCT_TYPE, neww, old)
			) == 1) {
				System.out.println("Product was updated successfully");
			}else {
				System.err.printf("No product with ID '%s' was found\nNothing wasn updated!",old);
			}
		} catch (SQLException e) {
			System.err.println(Exceptions.WRONG_DATA_TYPE_INPUT);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void updateProductPrice() {//seems perfect
		System.out.print("ID of the Price you want to change:");
		String old = getInput();
		System.out.print("Input new price:");
		String neww = getInput();	
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_PRODUCT_PRICE);
			)
		{
			if(stmt.executeUpdate(
			String.format(SQLCommands.UPDATE_PRODUCT_PRICE, neww, old)
			) == 1) {
				System.out.println("Product was updated successfully");
			}else {
				System.err.printf("No product with ID '%s' was found\nNothing wasn updated!",old);
			}
		} catch (SQLException e) {
			System.err.println(Exceptions.WRONG_DATA_TYPE_INPUT);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	private void updateProductQuantity() {//seems perfect
		System.out.print("ID of the Quantity you want to change:");
		String old = getInput();
		System.out.print("Input new quantity:");
		String neww = getInput();	
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_PRODUCT_QUANTITY);
			)
		{
			if(stmt.executeUpdate(
			String.format(SQLCommands.UPDATE_PRODUCT_QUANTITY, neww, old)
			) == 1) {
				System.out.println("Product was updated successfully");
			}else {
				System.err.printf("No product with id '%s' was found\nNothing wasn updated!",old);
			}
		} catch (SQLException e) {
			System.err.println(Exceptions.WRONG_DATA_TYPE_INPUT);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	public void removeStock() {//perfect
		System.out.print("Input ID:");
		String id = getInput();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JDBConnection.getConnection();
			stmt = conn.prepareStatement(SQLCommands.DELETE_FROM_STOCK);
			
			if(stmt.executeUpdate(
				String.format(SQLCommands.DELETE_FROM_STOCK, id)
			) == 1) {
				System.out.println("Stock was removed successfully");
			}else {
				System.err.println("No stock was removed!");
			}
		} catch (SQLException e) {
			System.err.println(Exceptions.WRONG_DATA_TYPE_INPUT);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void addAdmin() {//perfect
		int id = getPersonID();

		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_INTO_USERS);
			){
				System.out.print("Input name:");
				String name = getInput();
				System.out.print("Input username:");
				String username = getInput();
				System.out.print("input password:");
				String password = getInput();				
				password = Encryption.encrypt(password) ;
				final String role = "admin";
				if(stmt.executeUpdate(
						String.format(SQLCommands.INSERT_INTO_USERS, id, name, username, password, role)			
				) == 1) {
					System.out.println("Admin was added successfully");
				}else {
					System.err.println("No user was added!");
				}
		} catch (SQLException e) {
			System.err.println(Exceptions.WRONG_DATA_TYPE_INPUT);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void addSalRep() {//perfect
		int id = getPersonID();

		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_INTO_USERS);
			){
				System.out.print("Input name:");
				String name = getInput();
				System.out.print("Input username:");
				String username = getInput();
				System.out.print("input password:");
				String password = getInput();			
				
				password = Encryption.encrypt(password) ;
				
				final String role = "representative";
				
				if(stmt.executeUpdate(
				String.format(SQLCommands.INSERT_INTO_USERS, id, name, username, password, role)			
				) == 1) {
					System.out.println("New Sales Representative was added successfully!");
				}else {
					System.err.println("No user was added");
				}
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	private int getPersonID() {
		int id = 0;
		try (
				Connection conn = JDBConnection.getConnection();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(SQLCommands.SELECT_ID_FROM_USERS_ASC);
			){
				rs.last();
				 id = Integer.parseInt(rs.getString("id"))+1;
				System.out.println(id);			
		} catch (SQLException e) {
			System.err.println("It seems that there is no one");
		}
		return id;
	}
	public void editSalRep() {//perfect
		System.out.printf("What do you want to change, Name or Password\nAnswer:");
		String ans = getInput();	
		try	{	
				if(ans.equalsIgnoreCase("name")) {					
					updateSalRepName();
				}else if(ans.equalsIgnoreCase("password")) {
					updateSalRepPassword();
				}else {
					System.err.println(Exceptions.NOT_EXISTING_DBobject);
				}
				
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	private void updateSalRepName() {//perfect
		System.out.print("Username of the representative you want to update:");
		String old = getInput();
		System.out.print("Input new Name:");
		String neww = getInput();	
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_USER_NAME);
			)
		{
			if(stmt.executeUpdate(
			String.format(SQLCommands.UPDATE_USER_NAME, neww, old)
			) == 1) {
				System.out.println("Name changed successfully!");
			}else if(stmt.executeUpdate(
			String.format(SQLCommands.UPDATE_USER_NAME, neww, old)
			) == 0) {
				System.err.format(Exceptions.NO_SUCH_USERNAME,old);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void updateSalRepPassword() {//perfect
		System.out.print("Username of the representative you want to update:");
		String old = getInput();
		System.out.print("Input new Password:");
		String neww = getInput();
		neww = Encryption.encrypt(neww);
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.UPDATE_USER_PASSWORD);
			)
		{
			if(stmt.executeUpdate(
			String.format(SQLCommands.UPDATE_USER_PASSWORD, neww, old)
			) == 1) {
				System.out.println("Password changed successfully!");
			}else if(stmt.executeUpdate(
					String.format(SQLCommands.UPDATE_USER_PASSWORD, neww, old)
					) == 0){
				System.err.format(Exceptions.NO_SUCH_USERNAME,old);
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	public void removeSalRep() { //this it's good
		System.out.print("Input Representative's username(login):");
		String username = getInput();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = JDBConnection.getConnection();
			stmt = conn.prepareStatement(SQLCommands.REMOVE_USER);
			
			if(stmt.executeUpdate(
				String.format(SQLCommands.REMOVE_USER, username)
			) == 1) {
				System.out.println("Representative was removed successfully");
			}else {
				System.err.println("No representative was removed!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void addClient() { //perfect
		System.err.println("You can't do this!");
	}
	public void editClient() { //perfect
		System.err.println("You can't do this!");
	}
	public void removeClient() { //perfect
		System.err.println("You can't do this!");
	}

	public void printCatalogue() { //ready
		String format = "OrderID:%-8sStockID:%-8sType:%-15sPrice:%-8sDiscount:%-8sQuantity:%-8sRepresentative:%-12s"
				+ "Client:%-12sFinal Price:%-10sDate:%s%n";
		try (
				Connection conn = JDBConnection.getConnection();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(SQLCommands.FINISHED_FROM_CATALOGUE);
			){
				while(rs.next()) {
					System.out.printf(format,rs.getString("id"), rs.getString("stockID"), rs.getString("type"), rs.getString("price")
						, rs.getString("discount"), rs.getString("quantity"), rs.getString("representative"), rs.getString("client")
						, rs.getString("final_price"), rs.getString("date"));
				}
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	public void printCatalogueForTime() { //Am I done?
		Date firstDate = null;
		String format = "OrderID:%-8sStockID:%-8sType:%-15sPrice:%-8sDiscount:%-8sQuantity:%-8sRepresentative:%-12s"
				+ "Client:%-12sFinal Price:%-10sDate:%s%n";
		try{
			firstDate = getDate("Enter first date: ");
		} catch (ParseException e) {
			System.err.println(Exceptions.INVALID_DATE);
		} catch(Exception e) {
			System.err.println(e);
			return;
		}
		
		
		try {
			Connection conn = JDBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(SQLCommands.RETREAVE_FROM_CATALOGUE,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			stmt.setDate(1, (java.sql.Date) firstDate);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				System.out.printf(format,rs.getString("id"), rs.getString("stockID"), rs.getString("type"), rs.getString("price")
					, rs.getString("discount"), rs.getString("quantity"), rs.getString("representative"), rs.getString("client")
					, rs.getString("final_price"), rs.getString("date"));
			}

		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	public java.sql.Date getDate(String date) throws NumberFormatException,SQLException, ParseException {
		System.out.print(date);
		String value = getInput();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(value);
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
		return sql;
	}
	
	public void printCatalogueFromRep() { //this maybe works correctly
		System.out.println("For which representative you want to see history of purchases");
		String rep = getInput();
		String format = "OrderID:%-8sStockID:%-8sType:%-15sPrice:%-8sDiscount:%-8sQuantity:%-8sRepresentative:%-12sClient:%-12sFinal Price:%-10s%n";
		try (
				Connection conn = JDBConnection.getConnection();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(SQLCommands.FINISHED_FROM_CATALOGUE);
			){
				while(rs.next()) {
					if(rs.getString("representative").equalsIgnoreCase(rep)) {
						System.out.printf(format,rs.getString("id"), rs.getString("stockID"), rs.getString("type"), rs.getString("price")
							, rs.getString("discount"), rs.getString("quantity"), rs.getString("representative"), rs.getString("client")
							, rs.getString("final_price"));
					}
				}
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private String getInput() {//perfect?
//		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
//		System.out.flush();
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		String ans = "";
		while(ans.length() == 0) {
			ans = scan.nextLine();
			if(ans.length() == 0)
				System.err.print(Exceptions.NO_EMPTY_INPUT + " ... :");
		}			
		return ans;
	}
	public void bringOrder() {//perfect
		System.err.println("You can't do this");
	}
	public void checkInbox() {//perfect
		System.err.println("You can't do this");
	}
}
