package com.revature.services;

import java.util.List;
import java.util.Scanner;

import Models.Customer;
import Models.Item;
import Models.Offer;
import Models.Payments;

public interface CustomerDAO {
	
	
	public Customer logInCustomer();
	public Customer signUpCustomer(String z, String y, Scanner sc);
	public List<Item> viewPurchaseHistory(Scanner sc);

	// As a customer, I can make an offer for an item.
	public boolean addOffer(int x, Scanner sc);
	public List<Offer> viewMyOffers(Scanner sc);

	// As a customer, I can view the items that I own.
	public List<Item> viewMyItems(Scanner sc);
	
	// As a customer, I can view my remaining payments for an item.
	public List<Payments> viewRemainingPayments(Scanner sc);
	public boolean makePayment(int z, Double a, Scanner sc);
}
