import Encryption.Encryption;
import entities.User;


public class Main {

	public static void main(String[] args) {
		User user = null;
		while(user==null) {
			user = Encryption.logIn();
		}	
		Operations.chooceOption(user);
		
	}
}
