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
import controller.Transaction;
import shoppingcart.Cart;
import shoppingcart.MovieItem;

/**
 * Servlet implementation class pay
 */
public class pay extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("im in pay servlet");
		PrintWriter out = response.getWriter();  
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String creditcard = request.getParameter("creditcard");
		String exp = request.getParameter("exp");

		if (firstname == "" || lastname == "" || creditcard == "" || exp == "") {
			// *** I CAN USE INCLUDE HERE TO LET THE USER KNOW WHAT HAPPEN 
			response.sendRedirect(request.getContextPath() +"/pay.html");
			return; 
		}
		
		// compare this parameters with the database; 
		
		// verifyBillingInfo(firstname,lastname,creditcard,exp);
		// simple query to database credit cards
		// if true 
			// makePurchase(firstname,lastname,creditcard,sessionMovieItem);
			// inside make purchase ill need to query the customers id 
			// then create a date () object 
			// and update the sales table
		
		
		
		HttpSession session = request.getSession();	
		Transaction transaction = new Transaction();
		Cart shoppingCart = (Cart) session.getAttribute("cart");
		HashMap<String,MovieItem> items = shoppingCart.getCartItems();
		if (transaction.verifyBillingInfo(firstname,lastname,creditcard,exp)){
			System.out.println("transaction succesful");
			if (!items.isEmpty()) {
	 			for(MovieItem item: items.values()){
	 				transaction.makePurchase(firstname,lastname,creditcard,item);
				} 
			}
			// clear shopping cart
			shoppingCart.cartItems.clear();
			shoppingCart.total = 0.00; 
			session.setAttribute("cart", shoppingCart);
		} else
			System.out.println("transaction failed");
		
		// this will send the user to the thank you page!
		//response.sendRedirect(request.getContextPath() + "/main");
		response.sendRedirect(request.getContextPath() + "/finalize");
					
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}