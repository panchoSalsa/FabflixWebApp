package shoppingcart;

import java.util.HashMap;

public class Cart
{
	public HashMap<String,MovieItem> cartItems;
	public double total; 
	public Cart(){
		cartItems = new HashMap<String,MovieItem>();
	}
	public HashMap<String,MovieItem> getCartItems(){
		return cartItems;
	}
	public void addToCart(String id, String title, int quantity,
		double price){
		MovieItem item = new MovieItem(price,quantity,title,id);
		cartItems.put(id, item);
		total += price * quantity; 
	}
	public boolean ItemExists(String id){
		return cartItems.containsKey(id);
	}
	public void updateItem(String id, int quantity){
		total = total - cartItems.get(id).quantity * cartItems.get(id).price;
		cartItems.get(id).quantity = quantity;
		total = total + cartItems.get(id).quantity * cartItems.get(id).price;
		
		if (quantity == 0){
			cartItems.remove(id);
		}
	}
}
