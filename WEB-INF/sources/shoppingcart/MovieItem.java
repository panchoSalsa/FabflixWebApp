package shoppingcart;

public class MovieItem
{
	public String title;
	public int quantity;
	public double price; 
	public String id;
	
	public MovieItem(double price, int quantity, String title, String id) {
		this.price = price;
		this.quantity = quantity;
		this.title = title;
		this.id = id; 
	}
}
