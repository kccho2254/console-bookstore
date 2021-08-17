package com.revature.services;

import java.util.List;
import java.util.Scanner;

import Models.Customer;
import Models.Employee;
import Models.Item;
import Models.Offer;
import Models.Payments;

public interface ShopDAO {
	
	
	// As an employee, I can view all payments.
	// As the system, I can calculate the weekly payment.
	
	public Employee logInEmployee(String y, String z);
	public Employee signUpEmployee(String x, String y, Scanner sc);
	
	public List<Item> viewAllItems(Scanner sc);
	public Item selectBookById(int id, Scanner sc);
	
	public boolean updateOffer(int y, Scanner sc);
	public boolean deleteOffer(int y, Scanner sc);
	// As an employee, I can accept or reject a pending offer for an item.
	// As the system, I reject all other pending offers for an item when an offer is accepted.
		// When an offer is updated, TRUNCATE OFFERS WHERE ITEM ID = this.ID
	// As the system, I update an item to an owned state when an offer is accepted. 
	
	public List<Offer> viewCustomerOffers(Scanner sc);
	public List<Payments> viewAllPayments(Scanner sc);
	public boolean addItem(int x, Scanner sc);
	public boolean deleteItem(int id, Scanner sc);  
	public Integer update(Scanner sc);
	public Payments addPaymentInfo(int y);
} 