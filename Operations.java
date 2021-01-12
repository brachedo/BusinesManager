import java.util.Scanner;

import Exceptions.Exceptions;
import entities.User;

public class Operations {

		protected static void chooceOption(User user) {
			while(true) {
				System.out.printf("\n===================================================\n\n");
				System.out.println("1) Print all stock");
				System.out.println("2) Add stock");
				System.out.println("3) Edit stock");
				System.out.println("4) Remove stock");
				System.out.println("5) Add sales representative");
				System.out.println("6) Edit sales representative");
				System.out.println("7) Remove sales representative");
				System.out.println("8) Add client");
				System.out.println("9) Edit client");
				System.out.println("10) Remove client");
				System.out.println("11) Bring order");
				System.out.println("12) Check in box");
				System.out.println("13) Print catalogue (with purchases)");
				System.out.println("14) Print catalogue for a sales representative");
				System.out.println("15) Print catalogue from a date up to now");
				System.out.println("0) To log out and leave");
				
				System.out.print("Choose an option:");
				int ans = getInput();
				if(ans == 1) {
					user.readStock();
				} else if(ans == 2) {
					user.addStock();
				} else if(ans == 3) {
					user.editStock();
				} else if(ans == 4) {
					user.removeStock();
				} else if(ans == 5) {
					user.addSalRep();
				} else if(ans == 6) {
					user.editSalRep();
				} else if(ans == 7) {
					user.removeSalRep();
				} else if(ans == 8) {
					user.addClient();
				} else if(ans == 9) {
					user.editClient();
				} else if(ans == 10) {
					user.removeClient();
				} else if(ans == 11) {
					user.bringOrder();
				} else if(ans == 12) {
					user.checkInbox();
				} else if(ans == 13) {
					user.printCatalogue();
				} else if(ans == 14) {
					user.printCatalogueFromRep();
				} else if(ans == 15) {
					user.printCatalogueForTime();
				} else if(ans == 0) {
					System.out.printf("Loging out\nHave a nice day, Sir!");
					return;
				} else {
					System.err.println("Invalid operation");
				}
			}
		}
		private static int getInput() {

			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			
			String ans = "";
			while(ans.length() == 0) {
				ans = scan.nextLine();
				if(ans.length() == 0)
					System.err.print(Exceptions.NO_EMPTY_INPUT + " ... :");
			}			
			try {
				return Integer.parseInt(ans);
			} catch (NumberFormatException e){
				return 16;
			} catch (Exception e) {
				System.err.println(e);
				return 16;
			}
		}
}
