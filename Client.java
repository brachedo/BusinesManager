package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Scanner;

import Encryption.Encryption;
import Exceptions.Exceptions;
import JDB.JDBConnection;
import JDB.SQLCommands;

public class Client implements User {
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
	public void addClient() {//perfect
		System.err.println("You can't do this!");
	}
	public void editClient() {//perfect
		System.err.println("You can't do this!");
	}
	public void removeClient() {//perfect
		System.err.println("You can't do this!");
	}

	public void printCatalogue() {//perfect
		System.err.println("You can't do this!");
	}
	public void printCatalogueForTime() {//perfect
		System.err.println("You can't do this!");
	}
	public void printCatalogueFromRep() {//perfect
		System.err.println("You can't do this!");
	}
	
	private String getInput() {//perfect?

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		String ans = "";
		while(ans.length() == 0) {
			ans = scan.nextLine();
			if(ans.length() == 0) {
				System.err.print(Exceptions.NO_EMPTY_INPUT + " ... :");
				System.out.flush();
			}
		}			
		return ans;
	}
	public void bringOrder() {//think done
		System.out.print("What do you want to buy:");
		String ans = getInput();
		int currStock = 0;
		int toBuy = 0;
		int stockID = 0;
		int id = getID();
		double price = 0;
		String client = Encryption.returnUsername();
		String rep = getRepresentative(client);
		boolean available = false;
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_FROM_STOCK);
				ResultSet rs = stmt.executeQuery();				

			)
		{			
			while(rs.next()) {
				if(rs.getString("type").equalsIgnoreCase(ans) == true) {
					stockID = rs.getInt("id");
					ans = rs.getString("type");
					price = rs.getDouble("price");
					System.out.println("Available stock of this type:" + rs.getString("quantity"));
					currStock = rs.getInt("quantity");
					available = true;
					break;
				}
			}

			if(available == false) {
				System.err.println("Stock type not found!");
				return;
			}
			System.out.print("How much you want to buy:");
			toBuy = Integer.parseInt(getInput());
			if(!(toBuy <= currStock)) {
				System.err.println("You can't buy more than available stock!");
				return;
			}
		} catch (NumberFormatException e) {
			System.err.println("Wrong input!");
			return;
		} catch (SQLException e1) {
			System.err.println(e1);
		}
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.MAKE_ORDER);
			)
		{
			Calendar calendar = Calendar.getInstance();
		    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
			if(stmt.executeUpdate(
					String.format(SQLCommands.MAKE_ORDER, id, stockID, ans, price, 0, toBuy, rep, client, "processing", price, date)
			) == 1) {
				System.out.println("Your order is on way to be processed");
			}else {
				System.err.printf("An error occured with your order");
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	private String getRepresentative(String client) {//think done
		String rep = "";
		
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_FROM_RCCON); 
				ResultSet rs = stmt.executeQuery();
			)
		{
			while(rs.next()) {
				if(rs.getString("clientusername").equalsIgnoreCase(client) == true) {
					rep = rs.getString("repusername");
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		
		return rep;
	}
	private int getID() {//think done
		int id = 0;
		try (
				Connection conn = JDBConnection.getConnection();
				Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs2 = stmt2.executeQuery(SQLCommands.SELECT_ID_FROM_CATALOGUE_ASC);
			){
				rs2.last();
				id = (rs2.getInt("id")+1);
		} catch (Exception e) {
			System.err.println(e);
		}
		return id;
	}

	public void checkInbox() {//maybe finished
		boolean empty = true;
		Calendar calendar = Calendar.getInstance();
	    java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_FROM_CATALOGUE);
				PreparedStatement stmt2 = conn.prepareStatement(SQLCommands.FINISH_CATALOGUE_STATUS);
				ResultSet rs = stmt.executeQuery();
			){
			while(rs.next()) {
				if(rs.getString("client").equals(Encryption.returnUsername())) {
					if(stmt2.executeUpdate(
						String.format(SQLCommands.FINISH_CATALOGUE_STATUS,date, "finished", "accepted")) != 0) {
						System.out.println("An order was accepted, soon will be delivered");	
						empty = false;
					}
				}
			}
			if(empty == true) {
				System.err.println("Your message box is empty");
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
