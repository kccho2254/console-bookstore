package com.revature.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import Models.Customer;
import Models.Item;
import Models.Offer;
import Models.Payments;
import controller.App;
import utilities.Utilities;

// jUnit testing of the java services not the SQL

public class CustomerDAOImpl implements CustomerDAO {

	Connection conn = null;
	PreparedStatement stmt = null;
	Customer customer = new Customer();
	Offer offer = new Offer();
	Payments payment = new Payments();
	Item item = new Item();
	Scanner sc = new Scanner(System.in);

	public static Logger log = Logger.getLogger(App.class);

	@Override
	public Customer logInCustomer() {
		System.out.println("Enter your username: ");
		String y = sc.nextLine();
		System.out.println("Enter your password: ");
		String z = sc.nextLine();
		try {
			conn = Utilities.connect();
			String sql = "SELECT * FROM bookschema.customer";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				int numColumns = rs.getMetaData().getColumnCount();
				for (int i = 1; i < numColumns; i++) {

					if (y.equals(rs.getObject(i)) && z.equals(rs.getObject(i + 1))) {
						System.out.println("\nLogged in\n");

						customer.setCID((int) rs.getObject(i - 1));
						customer.setUserUser(y);
						customer.setUserPass(z);
				
						System.out.println("User ID: " + customer.getCID());
						System.out.println("Username: " + customer.getUserUser());
						System.out.println("User Password: " + customer.getUserPass());
						
						log.info("User was logged in!");
						log.info("User ID: " + customer.getCID());
						log.info("Username: " + customer.getUserUser());
						log.info("User password: " + customer.getUserPass());
						App.displayCustomerMenu(sc);
					}
				}
			}
			System.out.println("Incorrect combination");
			App.main(null);

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			System.out.println("Server is unresponsive");
			App.displayCustomerMenu(sc);

		} finally {
			closeResources();
		}
		return customer;
	}

	@Override
	public Customer signUpCustomer(String z, String y, Scanner sc) {

		try {

			conn = Utilities.connect();
			String sql = "INSERT INTO bookschema.customer(username, password) VALUES (?,?)";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, z);
			stmt.setString(2, y);
			stmt.execute();
			int j = 0;

			stmt = conn.prepareStatement("SELECT * FROM bookschema.customer");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

			
				int numColumns = rs.getMetaData().getColumnCount();

				for (j = 1; j <= numColumns; j++) {
					if (z.equals(rs.getObject(j)) && y.equals(rs.getObject(j + 1))) {
						customer.setCID((int) rs.getObject(j - 1));

						customer.setCID(rs.getInt("id"));
						System.out.println("Customer ID" + customer.getCID());
						customer.setUserUser(z);
						System.out.println("Customer username: " + z);
						customer.setUserPass(y);
						System.out.println("Customer password: " + y);
						
					}
				}
			}
		
			App.displayCustomerMenu(sc);
			
			log.info("User Signed up as: " + customer.getUserPass() + " with password " + customer.getUserPass());

		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} finally {
			closeResources();
		}
		return customer;
	}

	@Override
	public boolean addOffer(int x, Scanner sc) {
		// As a customer, I can make an offer for an item.
		// If owned = false, add item

		x = Integer.parseInt(sc.nextLine());
		// Need to first retrieve the price from the other table
		// Calling getPrice() on item, new Item is zero unless the variable is made
		

		try {
			String sql = "SELECT price, \"name\", owned FROM bookschema.\"system\" WHERE id=?";
			// INNER JOIN on SID include owner
			// if owner != null return to menu 

			stmt = Utilities.connect().prepareStatement(sql);
			stmt.setInt(1, x);
			ResultSet frs = stmt.executeQuery();
			while (frs.next()) {
				item.setPrice(frs.getDouble("price"));
				item.setOwned(frs.getBoolean("owned"));
				item.setName(frs.getString("name"));

			}

			System.out.println(x);
			int y = customer.getCID();
			double z = item.getPrice();

			if (item.getOwned() == false) {
				sql = "INSERT INTO bookschema.offer (sid, cid, approved, remainder) VALUES (?, ?, false, ?)";
				stmt = Utilities.connect().prepareStatement(sql);
				stmt.setInt(1, x);
				stmt.setInt(2, y);
				stmt.setDouble(3, z);

				if (stmt.executeUpdate() != 0) {
					
					log.info("User Id" + customer.getCID() + " added an offer on book name: " + item.getName());
					log.info("Of the amount: " + item.getPrice());
					return true;
				} else {
					return false;
				}
			} else {
				// If the book is owned, can't make an offer for it
				System.out.println("This item is out of stock...");
				App.displayCustomerMenu(sc); 
				return false;
			}
		} catch (SQLException se) {
			System.out.println("Internal server error");
			se.printStackTrace();
			return false;
		} finally {
			App.displayCustomerMenu(sc);
			closeResources();
		}
	}

	@Override
	public List<Offer> viewMyOffers(Scanner sc) {
		List<Offer> offers = new ArrayList<>();

		try {
			conn = Utilities.connect();
			String sql = "SELECT bookschema.offer.id, sid, cid, approved, remainder, \"name\", author FROM bookschema.offer" +
			" inner join bookschema.\"system\" on bookschema.offer.sid = bookschema.\"system\".id where bookschema.offer.approved = false AND bookschema.offer.cid = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, customer.getCID());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// Each variable in the object maps to a column in a row from our results.
				offer.setID(rs.getInt("id"));
				offer.setSID(rs.getInt("sid"));
				offer.setCID(rs.getInt("cid"));
				offer.setApproved(rs.getBoolean("approved"));
				offer.setPrice(rs.getDouble("remainder"));
				offer.setName(rs.getString("name"));
				offer.setAuthor(rs.getString("author"));
				// if offer is approved, say pending approval
				// finally we add it to the list of Offer objects returned by this query
				log.info("Current offers for user ID: " + customer.getCID());
				log.info("Offer ID: " + offer.getID());
				log.info("Item name: " + offer.getName());
				log.info("Book author: " + offer.getAuthor());
				log.info("Price: " + offer.getPrice());
				
				offers.add(offer);
				System.out.println(offers.get(0).toString());
			}

			if (offers.isEmpty()) {
				System.out.println("No offers found...");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed,
			// or else we could wind up with a memory leak
			App.displayCustomerMenu(sc);
			closeResources();
		}

		// return the list of Book objects populated by the DB.
		return offers;
	}

	@Override
	public List<Item> viewMyItems(Scanner sc) {
		// As a customer, I can view the items that I own.
		// Display remaining payments
		// SELECT * FROM offer WHERE approved = true AND user = customer.getID
		
		List<Item> items = new ArrayList<>();

		try {
			conn = Utilities.connect();
			// finding the books where owned is true and customer equals customer id set from logging in
			String sql = "SELECT bookschema.\"system\".id, \"name\", descr, author, owner, price, owned FROM bookschema.offer"
					+ " inner join bookschema.\"system\" on bookschema.offer.sid = bookschema.\"system\".id where bookschema.system.\"owned\" = true AND bookschema.offer.cid = ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, customer.getCID());
			ResultSet rs = stmt.executeQuery();
			log.info("Approved items for User ID: " + customer.getCID());

			while (rs.next()) {
				// Each variable in the object maps to a column in a row from our results.
				item.setID(rs.getInt("id"));
				item.setName(rs.getString("name"));
				item.setAuthor(rs.getString("author"));
				item.setDesc(rs.getString("descr"));
				item.setOwner(rs.getInt("owner"));
				item.setPrice(rs.getDouble("price"));
				item.setOwned(rs.getBoolean("owned"));
				
				log.info("Name: " + item.getName());
				log.info("Author: " + item.getAuthor());
				log.info("Description: " + item.getDesc());
				log.info("Price: " + item.getPrice());
				// if offer is approved, say pending approval
				// finally we add it to the list of Offer objects returned by this query
				
				items.add(item);
				System.out.println(items.get(0).toString());
			}

			if (items.isEmpty()) {
				System.out.println("No books found...");
				App.displayCustomerMenu(sc);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		// return the list of Book objects populated by the DB.
		return items;
	}

	@Override
	public List<Payments> viewRemainingPayments(Scanner sc) {
		List<Payments> payments = new ArrayList<>();

		try {
			conn = Utilities.connect();
			String sql = "select bookschema.\"system\".id, cid, name, amount, remainder from bookschema.payments inner join bookschema.\"system\" on bookschema.payments.sid = bookschema.\"system\".id where cid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, customer.getCID());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// Each variable in the object maps to a column in a row from our results.
				payment.setSID(rs.getInt("id"));
				payment.setCID(rs.getInt("cid"));
				payment.setName(rs.getString("name")); 
				payment.setAmount(rs.getDouble("amount"));
				payment.setRemainder(rs.getDouble("remainder"));
				
				
				log.info("Remaining payments for User ID: " + customer.getCID());
				log.info("Item name: " + payment.getName());
				log.info("Amount paid: " + payment.getAmount());
				log.info("Amount outstanding: " + payment.getRemainder());
				// if offer is approved, say pending approval
				// finally we add it to the list of Offer objects returned by this query
				
				payments.add(payment);
				System.out.println(payments.get(0).toString());
			}

			if (payments.isEmpty()) {
				System.out.println("No offers found...");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed,
			// or else we could wind up with a memory leak
			
			closeResources();
		}

		// return the list of Book objects populated by the DB.
		return payments;
	}
	
	@Override
	public boolean makePayment(int z, Double a, Scanner sc) {
		String sql = "UPDATE bookschema.payments SET amount = amount + ? WHERE sid = ?; UPDATE bookschema.payments SET remainder = (remainder - ?) WHERE sid = ?;";

		// z is book id
		// a is amount paid
		try {
			stmt = Utilities.connect().prepareStatement(sql);
			stmt.setDouble(1, a);
			stmt.setInt(2, z);
			stmt.setDouble(3, a);
			stmt.setInt(4, z);
		
			if (stmt.executeUpdate() != 0) {
				log.info("User made a payment of the amount: " + a + " for book ID: " + z);
				System.out.println("Success!");
				return true;
			} else {
				return false;
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			closeResources();
		}
		return false;
	}

	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

	@Override
	public List<Item> viewPurchaseHistory(Scanner sc) {
		// TODO Auto-generated method stub
		return null;
	}
}