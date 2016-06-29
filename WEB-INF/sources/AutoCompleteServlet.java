import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Movies;
import mysqljdbc.LogIn;




public class AutoCompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();	
		// first time searching 
		String keyword;
		if (request.getParameter("keyword") != null){
			keyword = request.getParameter("keyword");
			session.setAttribute("keyword",keyword);
			System.out.println("beginning keyword " + keyword);
		} else {	
			keyword = (String) session.getAttribute("keyword");
			System.out.println(keyword);
		}
		
		System.out.println("inside autcomplete servelet");
		System.out.println("keyword =**" + keyword + "**");
		keyword = keyword.replace("'","\\'"); 
		System.out.println("keyword =**" + keyword + "**");
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        response.setContentType("text/html");    // Response mime type
		Movies movie = new Movies();
		movie = movie.getMovieByTitle(keyword);
		
		if (movie == null) {

			 session.setAttribute("error","that title does not exist");
			 response.sendRedirect(request.getContextPath() + "/autocomplete");
			return; 
		}

        session.setAttribute("error", null);
        request.setAttribute("Movie", movie);
        System.out.println("before request dispatcher");
        RequestDispatcher rd = request.getRequestDispatcher("/singleMovieTitle");
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

























