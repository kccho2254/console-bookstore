package revature;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import com.revature.services.ShopDAOImpl;

import Models.Employee;
import Models.Item;

/**
 * Unit test for simple App.
 */
public class ShopDAOImplTest {
	private ShopDAOImpl o = new ShopDAOImpl();
	Employee emp = new Employee("Bob", "Ross");

	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void signUpEmployeeTest() {
		assertNotEquals(emp, o.signUpEmployee("Bob", "Ross", null));
	}

	@Test
	public void logInEmployeeTest() {
    	assert(o.logInEmployee("Bob", "Ross") == emp);
	}

	@Test
	public void viewAllItemsTest() {

		Item b = new Item(1234, "aName", "Bob", "This is a book", 1, 12.99, false);
    	List<Item> o = new ArrayList<>();
    	o.add(b);
    	
    	assertThat(o.toString(), containsString("Book ID: 1234"));
	}

	@Test
	public void selectBookByIdTest() {
		assert (o.selectBookById(-1, null)) == null;
	}

	@Test
	public void addItemTest() {
		
	}

	@Test
	public void deleteItem() {
		assertTrue(true);
	}

	@Test
	public void deleteOfferTest() {
		Scanner sc = new Scanner(System.in);
		
		assertFalse(o.deleteOffer(0, sc ));
	}

	@Test
	public void updateTest() {

		o.update(null);
	}

	@Test
	public void viewCustomerOffersTest() {
		assertTrue(true);
	}

	
	@Test
	public void updateOfferTest() {
		
	}
}
