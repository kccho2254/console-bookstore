package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.revature.services.CustomerDAO;
import com.revature.services.ShopDAO;

import Models.Employee;
import Models.Item;
import utilities.Utilities;

/**
 * Hello world! Scanner menus here
 */
public class App {
	static Scanner sc = new Scanner(System.in);
	static ShopDAO shop = Utilities.getBookDAO();
	static CustomerDAO customer = Utilities.getCustomerDAO();
	public static Logger log = Logger.getLogger(App.class);

	public static void qw() {
		log.info("Starting app...");
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure("src/main/resources/Testlogs.log");
		int x;
		do {
			System.out.println("             .--.           .---.        .-.\r\n"
					+ "         .---|--|   .-.     | K |  .---. |~|    .--.\r\n"
					+ "      .--|===|B.|---|_|--.__| e |--|:::| |~|-==-|==|---.\r\n"
					+ "      |  |   |.O|===| |By|--| v |--|   |_|~|.Cho|  |___|-.\r\n"
					+ "      |  |   |O.|===| |  |  | i |  |:::|=| |    |  |---|=|\r\n"
					+ "      |  |   |.K|   |_|__|  | n |__|   | | |    |  |___| |\r\n"
					+ "      |~~|===|S.|===|~|~~|--|~~~|--|:::|=|~|----|==|---|=|\r\n"
					+ "      ^--^---'--^---^-^--^--^---'--^---^-^-^-==-^--^---^-'");
			System.out.println("Enter any value to continue...");
			sc.nextLine();
			System.out.println("Main: ");
			System.out.println("1: Print a random number ");
			System.out.println("2: Log in as a customer ");
			System.out.println("3: Sign up as a customer ");
			System.out.println("4: Log in as an employee ");
			System.out.println("5: Sign up as an employee ");
			System.out.println("6: Quit ");

			x = Integer.parseInt(sc.nextLine());
			switch (x) {
			case 1:
				printRandomNumber(x, sc);
				break;
			case 2:
				customer.logInCustomer();
				break;
			case 3:
				System.out.println("Enter a username: ");
				String a = sc.nextLine();
				System.out.println("Create a password: ");
				String b = sc.nextLine();
				customer.signUpCustomer(a, b, sc);
				break;
			case 4:
				System.out.println("Enter your username: ");
				String y = sc.nextLine();

				System.out.println("Enter your password: ");
				String z = sc.nextLine();
				shop.logInEmployee(y, z);
				break;
			case 5:
				String employeeAuth = "hunter2";
				System.out.println("To sign up as an employee, please enter an authentication password: ");
				String w = sc.nextLine();

				if (w.equals(employeeAuth)) {

					System.out.println("Success! Create a username: ");
					String us = sc.nextLine();

					System.out.println("Create a password: ");
					String ps = sc.nextLine();
					shop.signUpEmployee(us, ps, sc);
				} else {
					System.out.println("Incorrect credentials");
					main(null);
				}

				break;
			case 6:
				System.out.println("Exiting...");
				log.info("Quitting app");
				break;
			default:
				System.out.println("Invalid choice!");
				break;
			}
		} while (x != 6);

		sc.close();
	}

	public static void bookMenuEmps(int x, Scanner sc) {

		System.out.println("1: Enter a book ID to retrieve it's details:");
		System.out.println("2: Return to previous menu");
		System.out.println("3: Return to main menu");

		x = Integer.parseInt(sc.nextLine());
		switch (x) {
		case 1:
			shop.viewAllItems(sc);
			System.out.println("Select a book...");
			int y = Integer.parseInt(sc.nextLine());
			shop.selectBookById(y, sc);
			bookDetailsEmps(sc);
		case 2:
			displayEmployeeMenu(sc);
			break;
		case 3:
			App.main(null);
			break;
		default:
			System.out.println("Invalid choice!");
			bookMenuEmps(x, sc);
			break;
		}
	}

	public static void bookDetailsEmps(Scanner sc) {

		System.out.println("1: Update this book's details");
		System.out.println("2: Return to previous menu");
		System.out.println("3: Return to main menu");

		int x = Integer.parseInt(sc.nextLine());
		switch (x) {
		case 1:
			log.info(shop.update(sc));
			break;
		case 2:
			bookMenuEmps(x, sc);
			break;
		case 3:
			App.main(null);
			break;

		}
	}

	public static void displayEmployeeMenu(Scanner sc) {

		Employee emp = new Employee(null, null);

		System.out.println("       .--.                   .---.\r\n" + "   .---|__|           .-.     |~~~|\r\n"
				+ ".--|===|--|_          |_|     |~~~|--.\r\n" + "|~~|===|m |'\\     .---!~|  .--|.j |--|\r\n"
				+ "|~~|sql|a |.'\\    |===| |--|%%| a |  |\r\n" + "|  |   |g |\\.'\\   |.js| |__|  | v |  |\r\n"
				+ "|  |   |i | \\  \\  |===| |==|  | a |  |\r\n" + "|~~|   |c_|  \\.'\\ |   |_|__|  |~~~|__|\r\n"
				+ "|~~|===|--|   \\.'\\|===|~|--|%%|~~~|--|\r\n" + "^--^---'--^    `-'`---^-^--^--^---'--' ");
		System.out.println("What do you want to do?");
		System.out.println("1: Add a new book");
		System.out.println("2: Remove a book");
		System.out.println("3: View pending offers on books");
		System.out.println("4: View all books");
		System.out.println("5: Log out");

		int x = Integer.parseInt(sc.nextLine());

		switch (x) {
		case 1:
			shop.addItem(x, sc);
			break;
		case 2:
			shop.deleteItem(x, sc);
			break;
		case 3:
			shop.viewCustomerOffers(sc);
			System.out.println("-1: Reject an offer");
			System.out.println("0: Exit");
			System.out.println("Approve an offer by entering it's ID: (e.g., \"Offer ID: 3\"");

			int y = Integer.parseInt(sc.nextLine());

			if (y != 0 && y != -1) {
				shop.updateOffer(y, sc);
			} else if (y == -1) {
				System.out.println("Enter \"Offer ID\" to reject");
				System.out.println("Enter 0 to return to previous menu");
				int z = Integer.parseInt(sc.nextLine());

				if (z == 0) {
					displayEmployeeMenu(sc);
				} else {
					shop.deleteOffer(z, sc);
				}
				return;
			} else if (y == 0) {
				// ApproveOfferById; display offers where approved equals true
				displayEmployeeMenu(sc);
			}
			break;
		case 4:
			shop.viewAllItems(sc);
			App.bookMenuEmps(x, sc);
			break;
		case 5:
			emp.setEmpUser(null);
			emp.setEmpPass(null);
			App.main(null);
			log.info("Employee logging out");
			break;
		default:
			System.out.println("Invalid choice!");
			break;
		}
	}

	public static void customerBookMenu(int x, Scanner sc) {

		System.out.println("\n1: Make a new offer");
		System.out.println("2: Return to previous menu");
		System.out.println("3: Return to main menu");

		x = Integer.parseInt(sc.nextLine());
		switch (x) {
		case 1:
			shop.viewAllItems(sc);
			System.out.println("Enter a book ID..."); // Join on book ID
			customer.addOffer(x, sc);
			break;
		case 2:
			displayCustomerMenu(sc);
			break;
		case 3:
			App.main(null);
			break;
		default:
			System.out.println("Invalid choice!");
			customerBookMenu(x, sc);
			break;
		}
	}

	public static void displayCustomerMenu(Scanner sc) {

		System.out.println("(\\ \r\n" + "\\'\\ \r\n" + " \\'\\     __________  \r\n" + " / '|   ()_________)\r\n"
				+ " \\ '/    \\ ~~~~~~~~ \\\r\n" + "   \\       \\ ~~~~~~   \\\r\n" + "   ==).      \\__________\\\r\n"
				+ "  (__)       ()__________)");
		System.out.println("What do you want to do?");
		System.out.println("1: View all books");
		System.out.println("2: View your offers");
		System.out.println("3: View your books");
		System.out.println("4: Log out");

		int w = Integer.parseInt(sc.nextLine());
		switch (w) {
		case 1:
			shop.viewAllItems(sc);
			App.customerBookMenu(0, sc);
			break;
		case 2:
			customer.viewMyOffers(sc);
			break;
		case 3:
			customer.viewMyItems(sc);
			System.out.println("\n1: View remaining payments");
			System.out.println("2: Make a payment");
			System.out.println("3: Return");
			int y = Integer.parseInt(sc.nextLine());

			if (y == 1) {
				customer.viewRemainingPayments(sc);
				displayCustomerMenu(sc);
			} else if (y == 2) {
				customer.viewRemainingPayments(sc);
				System.out.println("For which book do you want to make a payment? Select by ID");
				System.out.println("Enter 0 to return to previous menu");
				int z = Integer.parseInt(sc.nextLine());

				if (z == 0) {
					displayCustomerMenu(sc);
				} else {
					System.out.println("How much do you want to pay?");
					Double a = Double.parseDouble(sc.nextLine());
					log.info(customer.makePayment(z, a, sc));
					displayCustomerMenu(sc);
				}
				return;
			} else {
				// ApproveOfferById; display offers where approved equals true
				displayCustomerMenu(sc);
			}
			break;
		case 4:
			App.main(null);
			break;
		default:
			displayCustomerMenu(sc);
			break;
		}
	}

	public static void printRandomNumber(int x, Scanner sc) {

		System.out.println("Please enter a choice: ");
		System.out.println("1: Print a random number ");
		System.out.println("2: Print a random number times 100 ");

		x = Integer.parseInt(sc.nextLine());

		switch (x) {
		case 1:
			System.out.println("Here is a random number: " + Math.random());
			break;
		case 2:
			System.out.println("Here is a random number times 100: " + Math.random() * 100);
			break;
		}
	}
}
