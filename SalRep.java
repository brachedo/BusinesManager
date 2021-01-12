package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Encryption.Encryption;
import Exceptions.Exceptions;
import JDB.JDBConnection;
import JDB.SQLCommands;

public class SalRep implements User {
	public void addStock() {//perfect
		System.err.println("You can't do this!");
	}
	public void readStock() {//can't see something to be improved
		ResultSet rs = null;
		String read = "SELECT * FROM stock";
		String format = "ID:%-10sType:%-25sPrice:%-20sQuantity:%-20s%n";
		try (
			Connection conn = JDBConnection.getConnection();
			PreparedStatement pr = conn.prepareStatement(read);
				){
			rs = pr.executeQuery();
			while(rs.next()) {

				System.out.printf(format, rs.getString("id"), rs.getString("type"), rs.getString("price"), rs.getString("quantity"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void editStock() {//perfect
		System.err.println("You can't do this!");
	}
	public void removeStock() {//perfect
		System.err.println("You can't do this!");
	}
	public void addAdmin() {//perfect
		System.err.println("You can't do this!");
	}
	public void addSalRep() {//perfect
		System.err.println("You can't do this!");
	}
	public void editSalRep() {//perfect
		System.err.println("You can't do this!");
	}
	public void removeSalRep() {//perfect
		System.err.println("You can't do this!");
	}

	public void addClient() {//seems good
		int id = getPersonID();

		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.INSERT_INTO_USERS);
				PreparedStatement stmt2 = conn.prepareStatement(SQLCommands.INSERT_INTO_RCCON);
			){
				System.out.print("Input name:");
				String name = getInput();
				System.out.print("Input username:");
				String username = getInput();
				System.out.print("input password:");
				String password = getInput();				
				password = Encryption.encrypt(password) ;
				final String role = "client";
				if(stmt.executeUpdate(
						String.format(SQLCommands.INSERT_INTO_USERS, id, name, username, password, role)			
				) == 1) {
					System.out.println("New user was added successfully");
				}else {
					System.out.println("An error occured while adding new user");
				}
				if(stmt2.executeUpdate(
						String.format(SQLCommands.INSERT_INTO_RCCON, username, Encryption.returnUsername())			
				) == 1) {
						
				}else {
					System.err.println("Something went wrong while adding the user into RCCON DataBase");
				}
					
		} catch (Exception e) {
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
	public void editClient() {//seems good
		System.out.printf("What do you want to change, Name or Password\nAnswer:");
		String ans = getInput();	
		try	{	
				if(ans.equalsIgnoreCase("name")) {					
					updateClientName();
				}else if(ans.equalsIgnoreCase("password")) {
					updateClientPassword();
				}else {
					System.err.println(Exceptions.NOT_EXISTING_DBobject);
				}
				
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	private void updateClientName() {//perfect
		System.out.print("Username of the client you want to update:");
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
			}else {
				System.err.format(Exceptions.NO_SUCH_USERNAME,old);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void updateClientPassword() {//perfect
		System.out.print("Username of the client you want to update:");
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
	public void removeClient() {//seems ready
		System.out.print("Input Client's username(login) to be removed:");
		String username = getInput();
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try {
			conn = JDBConnection.getConnection();
			stmt = conn.prepareStatement(SQLCommands.REMOVE_USER);
			stmt2 = conn.prepareStatement(SQLCommands.REMOVE_FROM_RCCON);
			
			if(stmt.executeUpdate(
				String.format(SQLCommands.REMOVE_USER, username)
			) == 1  &&  stmt2.executeUpdate(
				String.format(SQLCommands.REMOVE_FROM_RCCON, username)
			) == 1) {
				System.out.println("Client was removed successfully");
			}else {
				System.err.println("No client was removed!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void printCatalogue() {//perfect
		System.err.println("You can't do this");
	}
	public void printCatalogueForTime() {//perfect
		System.err.println("You can't do this");
	}
	public void printCatalogueFromRep() {//perfect
		System.err.println("You can't do this");
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
	public void bringOrder() {
		System.err.println("Clients should make their orders, not you :D");
	}
	public void checkInbox() {
		printAllOrders();
		System.out.printf("Do you wanna process some of them?\nAnswer:");
		String ans = getInput();
		if(ans.equalsIgnoreCase("yes")) {
			
		}else if(ans.equalsIgnoreCase("no")){
			System.err.println("It's not good to leave your work for tomorrow...");
			return;
		}else {
			System.err.println("I don't understand you ...");
		}
		
		System.out.print("Which one (choose by ID): ");
		int id = 0;
		try {
			id = Integer.parseInt(getInput());
		} catch (NumberFormatException e) {
			System.err.println("Invalid ID");
			return;
		} catch (Exception e) {
			System.err.println(e);
		}

		String ans2;

		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_FROM_CATALOGUE);
				ResultSet rs = stmt.executeQuery();
			)
		{
			while(rs.next()) {
				if(rs.getInt("id") == id) {
					System.out.printf("Do you accept the order?\nAnswer:");
					ans2 = getInput();
					if(ans2.equalsIgnoreCase("yes")) {
						acceptOrder(id);
					}else if(ans2.equalsIgnoreCase("no")){
						cancelOrder(id);
					}
				}
			}
		}  catch (Exception e) {
			System.err.println(e);
		}
	}
	private void printAllOrders() {
		int orderNumber = 1;
		String format = "ID:%-5sType:%-15sQuantity:%-10sFrom:%-10s%n";
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_FROM_CATALOGUE);
				ResultSet rs = stmt.executeQuery();
			)
		{
			System.out.println("All orders to you:");
			while(rs.next()) {
				if(rs.getString("representative").equals(Encryption.returnUsername())) {
					if(rs.getString("status").equals("processing")) {
						System.out.print("Order number " + orderNumber + " : ");
						System.out.printf(format, rs.getString("id"), rs.getString("type"), 
								rs.getString("quantity"), findName(rs.getString("client")));
						orderNumber++;
					}
				}
			}
			if(orderNumber == 1) {
				System.err.println("It seems that you dont have any orders :(");
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	private String findName(String username) {
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_FROM_USERS);
				ResultSet rs = stmt.executeQuery();
			) 
		{
			while(rs.next()) {
				if(rs.getString("username").equals(username))
					username = rs.getString("name");
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return username;
	}
	private void acceptOrder(int id) {
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.PROCEED_ORDER);
			)
		{
			if(stmt.executeUpdate(
					String.format(SQLCommands.PROCEED_ORDER,"accepted", id)
				) == 1) {
					checkForDiscount(id);
					System.out.println("Order was accepted successfully");
				}else {
					System.err.println("Order wasn't accepted!");
				}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	private void checkForDiscount(int id) {
		int discount = 0; 
		double finalPrice = 0;
		String type = "";
		
		try (
				Connection conn = JDBConnection.getConnection();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(SQLCommands.SELECT_FROM_CATALOGUE)
			){
			while(rs.next()) {
				if(rs.getInt("id") == id) {
					discount = rs.getInt("quantity");
					finalPrice = rs.getDouble("price");
					type = rs.getString("type");
					break;
				}
			}
			
			int quantity = discount;
			getQuantity(quantity);
			if(discount > 25)
				discount = 25;

			double tmp = (int) Math.round(finalPrice*(100-discount));
			finalPrice = tmp/100;
			System.out.println("final price for each product: "+finalPrice);
			setDiscount(discount, id);
			updatePrice(finalPrice, id);
			updateQuantity(quantity, type);
			
		} catch (Exception e) {
			System.err.println(e);
		}		
	}
	private void setDiscount(int discount, int id) {
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.SET_DISCOUNT);

			){

			if(stmt.executeUpdate(
					String.format(SQLCommands.SET_DISCOUNT, (discount-1), id)
				) == 1) {
				}else {
					System.err.println("Discount wasn't given!");
				}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	private void updatePrice(double finalPrice, int id) {
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.SET_FINAL_PRICE);
			){

			if(stmt.executeUpdate(
					String.format(SQLCommands.SET_FINAL_PRICE, finalPrice, id)
				) == 1) {
				}else {
					System.err.println("An error ocured while giving new price!");
				}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	private void cancelOrder(int id) {
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.PROCEED_ORDER);
			)
		{
			if(stmt.executeUpdate(
					String.format(SQLCommands.PROCEED_ORDER,"canceled", id)
				) == 1) {
					System.out.println("Order was canceled successfully");
				}else {
					System.err.println("Order wasn't canceled!");
				}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	private int getQuantity(int quantity) {
		return quantity;
	}
	private void updateQuantity(int quantity, String type) {
		int oldQuantity = 0;
		try (
				Connection conn = JDBConnection.getConnection();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(SQLCommands.SELECT_FROM_STOCK);
				
				PreparedStatement stmt2 = conn.prepareStatement(SQLCommands.FINISH_PRODUCT_QUANTITY);
			){
			while(rs.next()) {
				if(rs.getString("type").equals(type)) {
					oldQuantity = rs.getInt("quantity");
				}
			}
			
			if(stmt2.executeUpdate(
					String.format(SQLCommands.FINISH_PRODUCT_QUANTITY, (oldQuantity-quantity), type)
					) == 1) {				
			}else {
				System.err.println("Error with updating stock!");
			}
				
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
