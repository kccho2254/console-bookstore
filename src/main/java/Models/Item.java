package Models;

import java.time.LocalDate;

public class Item {

	private int id;
	private String name;
	private String author;
	private String desc;
	private int owner;
	private double price;
	private boolean owned;
	private LocalDate publishDate;

	public Item(int id, String name, String author, String desc, int owner, double price, boolean owned) {

		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.desc = desc;
		this.owner = owner;
		this.price = price;
		this.owned = owned;
	}

	public Item() {
		super();
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean getOwned() {
		return owned;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}

	public LocalDate getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	@Override
	public String toString() {
		
		String state;
		if(this.getOwned() == false) {
			state = "Yes";
		} else {
			state = "No"; 
		}
		
		return "\nBook ID: " + this.getID() + ", \n" + "Book Title: " + this.getName() + ", \n" + "Book Author: "
				+ this.getAuthor() + ", \n" + "In stock: " + state + "\nPrice: $" + this.getPrice() + "\n"
				+ "Book Description: \n" + this.getDesc() + ".";
	}
}