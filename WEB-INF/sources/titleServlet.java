

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Movies;

/**
 * Servlet implementation class titleServlet
 */
//@WebServlet("/titleServlet")
public class titleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// this request only works once
		// meaning when the user enters the letter
		HttpSession session = request.getSession();
		String letter;
		if (request.getParameter("choice") != null)
			letter = request.getParameter("choice");
		else
			letter = (String) session.getAttribute("letter"); 
		
		
		Movies movies = new Movies();
		String query = "select * from movies where title  like '" + letter + "%'";
		System.out.println(query);
		
		session.setAttribute("letter", letter);
		session.setAttribute("origin","titleServlet");
		session.setAttribute("query",query);
		
		List<Movies> movielist = movies.getMovies(query, 10);
		session.setAttribute("Movies", movielist);
		RequestDispatcher rd = request.getRequestDispatcher("/result");
        rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
