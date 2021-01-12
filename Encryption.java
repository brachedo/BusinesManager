package Encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Encryption.Encryption;
import JDB.JDBConnection;
import JDB.SQLCommands;
import entities.User;
import entities.Admin;
import entities.Client;
import entities.SalRep;

public class Encryption {
	 
    private static SecretKeySpec secretKey;
    private static byte[] key;  
	private static final String myKey = "NoOneShouldKnewThat";
	private static String login;

    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static String encrypt(String strToEncrypt) {
    	
        try
        {
            setKey(myKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt) {
        try
        {
            setKey(myKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static User logIn() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.print("login:");
		login = scan.nextLine();
		System.out.print("password:");
		String password = scan.nextLine();
		String role = null;
		
		try (
				Connection conn = JDBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SQLCommands.SELECT_FROM_USERS);
				ResultSet rs = stmt.executeQuery();
			)
		{		
			password= Encryption.encrypt(password);
			while(rs.next()) {
				if(rs.getString("username").equals(login) && rs.getString("password").equals(password)) {
					System.out.println("Loged in succesfully");
					role = rs.getString("role");
					System.out.println("You logged as "+role);
				}
			}			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		try {
			return createPerson(role);
		} catch (NullPointerException e) {
			System.err.println("Wrong login data");
		}
		return null;
	}
    public static String returnUsername() {
    	return login;
    }
	private static User createPerson(String role) {

		if(role.equals("admin")) {
			User admin  = new Admin();
			return admin = new Admin();
		}else if(role.equals("representative")) {
			User salRep = new SalRep();
			return salRep = new SalRep();
		}else if(role.equals("client")) {
			User client = new Client();
			return client = new Client();
		}
		return null;
		
	}	
}