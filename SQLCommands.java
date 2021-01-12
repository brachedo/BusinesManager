package JDB;

public class SQLCommands {
	//creating tables, some of them aren't actual
	public static final String CREATE_TABLE_STOCK = "CREATE TABLE stock (id int NOT NULL AUTO_INCREMENT, type varchar(255),"
			+ " price double, quantity int, PRIMARY KEY(id))";
	public static final String CREATE_TABLE_USERS = "CREATE TABLE users (id int, name varchar(255), username varchar(255),"
			+ " password varchar(255), email varchar(255), role varchar(255), PRIMARY KEY(username))";
	public static final String CREATE_TABLE_RCCON = "CREATE TABLE rccon (repusername varchar(255), clientusername varchar(255),"
			+ " PRIMARY KEY(clientusername))";
	public static final String CREATE_TABLE_CATALOGUE = "CREATE TABLE catalogue (id int, stockID int, type varchar(255), price double,"
			+ " quantity int, representative varchar(255), client varchar(255), PRIMARY KEY (id))";

	public static final String SELECT_FROM_USERS = "SELECT * FROM users";
	public static final String SELECT_ID_FROM_USERS_ASC = "SELECT id FROM users ORDER BY id ASC";
	public static final String SELECT_ID_FROM_STOCK_ASC = "SELECT id FROM stock ORDER BY id ASC";
	public static final String SELECT_ID_FROM_CATALOGUE_ASC = "SELECT id FROM catalogue ORDER BY id ASC";

	public static final String INSERT_INTO_RCCON = "INSERT INTO rccon (clientusername, repusername) VALUES ('%s', '%s')";
	public static final String INSERT_INTO_USERS = "INSERT INTO users (id, name, username, password, role) VALUES ('%s', '%s', '%s', '%s', '%s')";	
	
	public static final String REMOVE_USER = "DELETE FROM users WHERE username = '%s'";
	public static final String REMOVE_FROM_RCCON = "DELETE FROM rccon WHERE clientusername = '%s'";
	
	public static final String UPDATE_USER_NAME = "UPDATE users SET name = '%s' WHERE username = '%s'";
	public static final String UPDATE_USER_PASSWORD = "UPDATE users SET password = '%s' WHERE username = '%s'";

	public static final String INSER_INTO_STOCK = "INSERT INTO stock (id, type, price, quantity) VALUES ('%s', '%s', '%s', '%s')";
	public static final String DELETE_FROM_STOCK = "DELETE FROM stock WHERE id = '%s'";
	
	public static final String UPDATE_PRODUCT_ID = "UPDATE stock SET id = '%s' WHERE ID = '%s';";
	public static final String UPDATE_PRODUCT_TYPE = "UPDATE stock SET type = '%s' WHERE ID = '%s';";
	public static final String UPDATE_PRODUCT_PRICE = "UPDATE stock SET price ='%s' WHERE ID = '%s';";
	public static final String UPDATE_PRODUCT_QUANTITY = "UPDATE stock SET quantity = '%s' WHERE ID = '%s';";
	
	public static final String SELECT_FROM_STOCK = "SELECT * FROM stock";
	public static final String BUY_STOCK = "UPDATE stock SET quantity = '%s' WHERE type = '%s';";
	
	public static final String MAKE_ORDER = "INSERT INTO catalogue (id, stockID, type, price, discount, quantity,"
			+ " representative, client, status, final_price, date) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
	
	public static final String SELECT_FROM_RCCON = "SELECT * FROM rccon";
	public static final String SELECT_FROM_CATALOGUE = "SELECT * FROM catalogue";
	
	public static final String REMOVE_FROM_CATALOGUE = "REMOVE FROM catalogue WHERE id = '%s'";
	public static final String PROCEED_ORDER = "UPDATE catalogue SET status = '%s' WHERE id = '%s'";
	public static final String SET_DISCOUNT = "UPDATE catalogue SET discount = '%s' WHERE id = '%s'";
	public static final String SET_FINAL_PRICE = "UPDATE catalogue SET final_price = '%s' WHERE id = '%s'";
	public static final String FINISH_PRODUCT_QUANTITY = "UPDATE stock SET quantity = '%s' WHERE type = '%s'";
	public static final String FINISH_CATALOGUE_STATUS = "UPDATE catalogue SET date = '%s', status = '%s' WHERE status = '%s'";
	public static final String FINISHED_FROM_CATALOGUE = "SELECT * FROM catalogue WHERE status = 'finished'";
	public static final String RETREAVE_FROM_CATALOGUE = "SELECT * FROM catalogue WHERE date >= ?";
	public static final String UPDATE_DATE = "UPDATE catalogue SET date = '%s'";
}
