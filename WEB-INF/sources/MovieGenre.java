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
 * Servlet implementation class MovieGenre
 */
//@WebServlet("/MovieGenre")
public class MovieGenre extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession(true);
		
		int newOffset = 0;
		// whenever user logs in his offset is set to 10 in the LoginServlet
		
		// only way this gets executed is if the user hits Prev or Next in searchResult
		if (request.getParameter("offset") != null) {
			newOffset = Integer.parseInt(request.getParameter("offset"));
			if (request.getParameter("direction").equals("prev")){
				newOffset = newOffset * -1; 
			}
		}
			//session.setAttribute("sessionOffset", newOffset);
		int currentOffset = (Integer) session.getAttribute("sessionOffset");
		currentOffset = currentOffset + newOffset;
		// preventing the user from seeing less than 10 records
		if (currentOffset <= 0) {
			currentOffset = 10; 
		}
		
		// how can i make it so if they keep pressing next it stops
		// how to check the number of records; 
		
		session.setAttribute("sessionOffset", currentOffset);
		
		
			
		
		// the genre name gets set when the user clicks on a genre
		// if the user hits prev or next, or alphabetic order he has to be in the same genre
	
		String genre = request.getParameter("genre");
		if (genre == null) {
			genre = (String)session.getAttribute("genre");
		}
		System.out.println(genre);
		Movies movies = new Movies();
		String query = "select * from movies, genres_in_movies, genres "
              	+ "where movies.id = genres_in_movies.movie_id and "
              	+ "genres_in_movies.genre_id = genres.id and genres.name = '" + genre + "' ";
		System.out.println(query);
		
		
		session.setAttribute("origin","movieByGenre");
		session.setAttribute("query",query);
		session.setAttribute("genre", genre);
		System.out.println("origin = " + (String) session.getAttribute("origin"));
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
