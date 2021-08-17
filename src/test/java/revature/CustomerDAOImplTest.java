package revature;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import com.revature.services.CustomerDAOImpl;

import Models.Customer;
import Models.Item;
import Models.Offer;
import Models.Payments;

/**
 * Unit test for simple App.
 */
public class CustomerDAOImplTest 
{
	private CustomerDAOImpl o = new CustomerDAOImpl();
	Scanner sc = new Scanner(System.in);
	
    /**
     * Rigorous Test :-)
     */
    
    @Test
    public void verifyAddOfferTest()
    {
    	int x = 0;
    	// book id cannot be false
		assertFalse(o.addOffer(x, sc));
    }
    
    @Test
    public void signUpCustomerTest()
    {
    	// check that the method is returning a customer object with these values
    	Customer customer = new Customer("Bob", "Ross");    	
    }
    
    @Test
    public void logInCustomerTest()
    {
		assertTrue(o.logInCustomer() instanceof Customer);

    }
    
    @Test
    public void viewMyOffersTest()
    {
    	Offer o = new Offer(12, 1, 3, false, 12.33);
    	List<Offer> p = new ArrayList<>();
    	p.add(o);
    	
    	assertThat(p.toString(), containsString("Approved status: false"));
    }
    
    @Test
    public void viewMyItemsTest()
    {
    	Item b = new Item(1234, "aName", "Bob", "This is a book", 1, 12.99, false);
    	List<Item> o = new ArrayList<>();
    	o.add(b);
    	
    	assertThat(o.toString(), containsString("Book ID: 1234"));
    }
    
    @Test
    public void viewRemainingPaymentsTest()
    {
    	Payments p = new Payments(8, 4, 2, 0, 0); 
		List<Payments> o = new ArrayList<>();
		o.add(p);
		
		assertThat(o.toString(), containsString("Item ID: 4"));
    }
    
    @Test
    public void makePaymentTest()
    {
    	assertTrue(o.makePayment(4, 1.0, sc));
    	assertFalse(o.makePayment(4, 1200000.0, sc));
    }
}