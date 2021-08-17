package com.revature.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import Models.Employee;
import Models.Item;
import Models.Offer;
import Models.Payments;
import controller.App;
import utilities.Utilities;

// update item to an owned state
// reject all other pending offers for an item
// calculate total amount paid in the span of one week

// add item to the shop
// accept or reject offer for an item
// remove item from the shop
// view all payments made for an item

// view all items
// make an offer for an item
// create account
// view remaining payments for the item

public class ShopDAOImpl implements ShopDAO {

	Connection conn = null;
	PreparedStatement stmt = null;
	Item book = new Item();
	Employee emp = new Employee();
	Offer offer = new Offer();
	Payments payment = new Payments();
	public static Logger log = Logger.getLogger(App.class);

	@Override
	public Employee signUpEmployee(String x, String y, Scanner sc) {
		try {
			conn = Utilities.connect();
			String sql = "INSERT INTO bookschema.employee(username, password) VALUES (?, ?)";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, x);
			stmt.setString(2, y);
			stmt.execute();
			emp.setEmpUser(x);
			emp.setEmpPass(y);
			log.info("Employee signed up as " + emp.getEmpUser() + " with password " + emp.getEmpPass());

			App.displayEmployeeMenu(sc);

		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} finally {
			closeResources();
		}
		return emp;
	}

	@Override
	public Employee logInEmployee(String y, String z) {
		// TODO Auto-generated method stub
		try {
			conn = Utilities.connect();
			stmt = conn.prepareStatement("SELECT * FROM bookschema.employee");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				int numColumns = rs.getMetaData().getColumnCount();
				for (int i = 1; i <= numColumns; i++) {

					if (y.equals(rs.getObject(i)) && z.equals(rs.getObject(i + 1))) {
						System.out.println("Successful log in");

						// Update setters here so that state is persisted
						emp.setEmpId(rs.getInt("employee_id"));
						emp.setEmpUser(y);
						emp.setEmpPass(z);

						log.info("Employee logged in as: " + emp.getEmpUser() + " with password " + emp.getEmpPass());

						Scanner sc = new Scanner(System.in);
						App.displayEmployeeMenu(sc);
					}
				}
			}
			System.out.println("Incorrect combination");
			App.main(null);

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			System.out.println("Server is unresponsive");
			App.main(null);
		} finally {
			closeResources();
		}
		return emp;
	}

	@Override
	public List<Item> viewAllItems(Scanner sc) {
		// TODO Auto-generated method stub

		List<Item> books = new ArrayList<>();

		try {
			conn = Utilities.connect();
			String sql = "SELECT * FROM bookschema.system";
			stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Item book = new Item();

				// Each variable in our Book object maps to a column in a row from our results.
				book.setID(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setAuthor(rs.getString("author"));
				book.setDesc(rs.getString("descr"));
				book.setOwner(rs.getInt("owner"));
//				book.setPublishDate(rs.getDate("publish_date").toLocalDate()); 
				book.setPrice(rs.getDouble("price"));
				book.setOwned(rs.getBoolean("owned"));

				log.info("Employee " + emp.getEmpId() + " with username " + emp.getEmpUser() + " viewed all books:");
				log.info("Book name: " + book.getName());
				log.info("Book author: " + book.getAuthor());
				log.info("Book description: " + book.getDesc());
				log.info("Book owner: " + book.getOwner());
				log.info("Book price: " + book.getPrice());
				// Finally we add it to the list of Book objects returned by this query.
				books.add(book);
			}

			for (int i = 0; i < books.size(); i++) {
				System.out.println(books.get(i).toString());
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
		return books;
	}

	@Override
	public Item selectBookById(int id, Scanner sc) {
		int x = 0;
		try {
			String sql = "SELECT * FROM bookschema.system WHERE id = ?";
			stmt = Utilities.connect().prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				book.setID(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setAuthor(rs.getString("author"));
				book.setDesc(rs.getString("descr"));
				book.setOwner(rs.getInt("owner"));
				book.setPrice(rs.getDouble("price"));
				book.setOwned(rs.getBoolean("owned"));
//				System.out.println("Book was added to models");
//				System.out.println(book.getPrice());
			} else {
				System.out.println("Invalid selection");
				App.bookMenuEmps(x, sc);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return book;
	}

	@Override
	public boolean addItem(int x, Scanner sc) {
		// TODO Auto-generated method stub

		try {

			boolean d = false;

			System.out.println("Item title:");
			String a = sc.nextLine();

			System.out.println("Author:");
			String b = sc.nextLine();

			System.out.println("Set a price:");
			double c = Double.parseDouble(sc.nextLine());

			System.out.println("Write a short description:");
			String e = sc.nextLine();

			conn = Utilities.connect();
			String sql = "INSERT INTO bookschema.system(name, author, price, owned, descr) VALUES (?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, a);
			stmt.setString(2, b);
			stmt.setDouble(3, c);
			stmt.setBoolean(4, d);
			stmt.setString(5, e);

			// If we were able to add our book to the DB, we want to return true.
			// This if statement both executes our query, and looks at the return
			// value to determine how many rows were changed
			if (stmt.executeUpdate() != 0) {

				System.out.println("Book added to inventory");

				log.info("Book was added with name: " + a);
				log.info("Author: " + b);
				log.info("Price: " + c);
				log.info("Description: " + e);
				App.displayEmployeeMenu(sc);
				return true;
			} else {
				return false;
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			System.out.println("Server is unresponsive");
			App.displayEmployeeMenu(sc);
			return false;

		} finally {
			closeResources();
		}
	}

	@Override
	public boolean deleteItem(int id, Scanner sc) {

		viewAllItems(sc);
		System.out.println("Which book do you want to delete? (Enter by ID)");
		id = Integer.parseInt(sc.nextLine());

		try {
			conn = Utilities.connect();
			String sql = "DELETE FROM bookschema.system WHERE id = ?";
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);

			if (stmt.executeUpdate() != 0) {
				System.out.println("Book was deleted from the database");
				log.info("Book was deleted with ID: " + id);
				return true;
			} else {
				System.out.println("No such ID");
				App.displayEmployeeMenu(sc);
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error deleting entry");
			App.displayEmployeeMenu(sc);
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean deleteOffer(int y, Scanner sc) {

		try {
			System.out.println("Enter an offer ID...");
			String sql = "DELETE FROM bookschema.offer WHERE id = ? AND approved = false";
			stmt = Utilities.connect().prepareStatement(sql);
			stmt.setInt(1, y);
			if (stmt.executeUpdate() != 0) {
				System.out.println("Success!");
				App.displayEmployeeMenu(sc);
				return true;
			} else {
				System.out.println("No such ID");
				App.displayEmployeeMenu(sc);
				return false;
			}
		} catch (SQLException se) {
			System.out.println("Interal server error... :(");
			se.getStackTrace();
			App.displayEmployeeMenu(sc);
		} finally {
			closeResources();
		}

		return true;
	}

	@Override
	public Integer update(Scanner sc) {

		// if there's an owner, can't update

		System.out.println(book.getID());
		System.out.println("Update item title:");
		System.out.println(book.getName());
		String a = sc.nextLine();

		System.out.println("Update Author:");
		System.out.println(book.getAuthor());
		String b = sc.nextLine();

		System.out.println("Update price:");
		System.out.println(book.getPrice());
		double c = Double.parseDouble(sc.nextLine());

		System.out.println("Update description:");
		System.out.println(book.getDesc());
		String e = sc.nextLine();

		String sql = "UPDATE bookschema.system SET name=?, author=?, price=?, descr=? WHERE id=?";
		int success = 0;
		try {
			stmt = Utilities.connect().prepareStatement(sql);

			stmt.setString(1, a);
			stmt.setString(2, b);
			stmt.setDouble(3, c);
			stmt.setString(4, e);
			stmt.setInt(5, book.getID());
			if (stmt.executeUpdate() != 0) {
				System.out.println("Success!");

				log.info("Book updated with name (previous): " + book.getName() + " is now " + a);
				log.info("Author(previous): " + book.getAuthor() + " is now " + b);
				log.info("Price(previous): " + book.getPrice() + " is now " + c);
				log.info("Description(previous): " + book.getDesc() + " is now " + e);
				return success = 1;
			} else {
				return success = 0;
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			closeResources();
		}
		return success;
	}

	@Override
	public List<Offer> viewCustomerOffers(Scanner sc) {
		// TODO Auto-generated method stub

		List<Offer> offers = new ArrayList<>();

		try {
			conn = Utilities.connect();
			String sql = "SELECT bookschema.offer.id, sid, cid, approved, remainder, \"name\", author FROM bookschema.offer inner join bookschema.\"system\" on bookschema.offer.sid = bookschema.\"system\".id WHERE approved = false;";
			stmt = conn.prepareStatement(sql);

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

				// Finally we add it to the list of Book objects returned by this query.
				offers.add(offer);
				System.out.println(offers.get(0).toString());
			}
//			System.out.println(offers.get(1));
//			if(offers.get(0) == null) {
//				System.out.println("No offers found...");
//			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// We need to make sure our statements and connections are closed,
			// or else we could wind up with a memory leak
			closeResources();
		}

		// return the list of Book objects populated by the DB.
		return offers;
	}

	@Override
	public boolean updateOffer(int y, Scanner sc) {
		// UPDATE offer SET approved = true WHERE offer.id = ?
		// no need to parse another integer, just run it through with whatever was
		// selected from the integer parse in App.java
		// Nesting all the statements to achieve atomicity
		// Need to set owner to customer ID
		try {
			// update SID so that it can be used in delete statement
			// update CID so that it can be used in updating owner

			String sql = "SELECT id, sid, cid FROM bookschema.offer WHERE id = ?";
			stmt = Utilities.connect().prepareStatement(sql);
			stmt.setInt(1, y);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				offer.setID(rs.getInt("id"));
				offer.setSID(rs.getInt("sid"));
				offer.setCID(rs.getInt("cid"));
			}

			conn = Utilities.connect();
			sql = "UPDATE bookschema.offer SET approved = true WHERE bookschema.offer.id = ?;";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, y);

			if (stmt.executeUpdate() != 0) {

				// insert into payments
				addPaymentInfo(y);

				if (stmt.executeUpdate() != 0) {

					// Necessary successful approval
					// Delete all other pending offers for this item when an offer is accepted.
					// Persist approved item on the offer table

					sql = "DELETE FROM bookschema.offer WHERE ID != ? AND SID = ? AND approved = false";
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, y);
					stmt.setInt(2, offer.getSID());

					if (stmt.executeUpdate() != 0) {
						System.out.println("Deleted other offers");

						// Set owned state for this item because it was approved
						// Owner = Customer ID
						// id = Book ID
						// no need to call a Customer object because it's key can be found on the offer
						// table
						sql = "UPDATE bookschema.\"system\" SET (\"owned\", \"owner\") = (true, ?) WHERE id = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setInt(1, offer.getCID());
						stmt.setInt(2, offer.getSID());

						if (stmt.executeUpdate() != 0) {
							System.out.println("Updated this item's \"owned\" state and owner ID to " + offer.getCID());
							return true;
						} else {
							System.out.println("Problem establishing ownership");
							return false;
						}
					} else {
						System.out.println("No other offers to delete");

						sql = "UPDATE bookschema.\"system\" SET (\"owned\", \"owner\") = (true, ?) WHERE id = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setInt(1, offer.getCID());
						stmt.setInt(2, offer.getSID());

						if (stmt.executeUpdate() != 0) {
							System.out.println("Updated this item to an \"owned\" state and it's owner");
							log.info("Book offer was approved, ID: " + offer.getSID() + " with customer ID "
									+ offer.getCID());
							return true;
						} else {
							System.out.println("Problem establishing ownership");
							return false;
						}
					}
				} else {
					System.out.println("Error adding payment info");
					return false;
				}
			} else {
				System.out.println("Error approving offer");
				return false;
			}
		} catch (SQLException se) {
			se.printStackTrace();
			return false;
		} finally {
			App.displayEmployeeMenu(sc);
			closeResources();
		}
	}

	@Override
	public Payments addPaymentInfo(int id) {
		// y is the book id
		// each row is a book
		// amount will be updated when a payment is made
		// remainder should be remainder - amount

		try {

			conn = Utilities.connect();
			String sqlSelectSidCidPrice = "SELECT id, cid, sid, remainder FROM bookschema.offer WHERE id = ?";
			stmt = conn.prepareStatement(sqlSelectSidCidPrice);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				payment.setID(rs.getInt("id"));
				payment.setCID(rs.getInt("cid"));
				payment.setSID(rs.getInt("sid"));
				payment.setRemainder(rs.getDouble("remainder"));
			}

			String sqlInsertSelectedOfferDetailsIntoPaymentsTable = "INSERT INTO bookschema.payments(remainder, amount, cid, sid) VALUES (?, 0, ?, ?)";
			stmt = conn.prepareStatement(sqlInsertSelectedOfferDetailsIntoPaymentsTable);

			stmt.setDouble(1, payment.getRemainder());
			stmt.setInt(2, payment.getCID());
			stmt.setInt(3, payment.getSID());
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
		return payment;
	}

	@Override
	public List<Payments> viewAllPayments(Scanner sc) {
		List<Payments> payments = new ArrayList<>();

		try {
			conn = Utilities.connect();
			String sql = "SELECT bookschema.\"system\".id, cid, bookschema.\"system\".\"name\", remainder, amount FROM bookschema.payments inner join bookschema.\"system\" on bookschema.payments.sid = bookschema.\"system\".id;";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// Each variable in the object maps to a column in a row from our results.
				payment.setSID(rs.getInt("id"));
				payment.setCID(rs.getInt("cid"));
				payment.setName(rs.getString("name"));
				payment.setAmount(rs.getDouble("amount"));
				payment.setRemainder(rs.getDouble("remainder"));

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

			closeResources();
		}
		return payments;
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
}