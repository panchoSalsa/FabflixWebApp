import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import shoppingcart.Cart;

/**
 * Servlet implementation class Addtocart
 */
//@WebServlet("/Addtocart")
public class Addtocart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Addtocart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		Cart shoppingCart;
		shoppingCart = (Cart) session.getAttribute("cart");
//		if (shoppingCart == null){
//			shoppingCart = new Cart();
//			session.setAttribute("cart", shoppingCart);
//		}
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		double price = Double.parseDouble(request.getParameter("price"));
		
		String quantityStr = request.getParameter("quantity");

		int quantity; 
		// test when the user does not enter a quantity but still hits checkout
		String servletDispatcher = (String) session.getAttribute("origin");
		if (quantityStr.equals("") || Character.isLetter(quantityStr.charAt(0)) ) {
			response.sendRedirect(request.getContextPath() + "/" + servletDispatcher);
			return; 
		}
		else {
			try {
				quantity = Integer.parseInt(quantityStr);
				if (quantity < 0) { // checking negative input  
					response.sendRedirect(request.getContextPath() + "/" + servletDispatcher);
					return; 
				}	
			} catch (NumberFormatException e){
				response.sendRedirect(request.getContextPath() + "/" + servletDispatcher);
				return; 
			}
			
		}
		if(shoppingCart.ItemExists(id))
			shoppingCart.updateItem(id, quantity);
		else if (quantity > 0 )
			shoppingCart.addToCart(id, name, quantity,price);
		session.setAttribute("cart",shoppingCart);
		
		// this so when the customer wants to go back by clicking add more items go button, he goes back to to the search result
		// i grab this value in checkout.jsp
		
		response.sendRedirect(request.getContextPath() + "/" + servletDispatcher);  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
