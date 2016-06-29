import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import models.Movies;
//import quicktime.std.movies.Movie;
import mysqljdbc.LogIn;

/**
 * Servlet implementation class SearchingServlet
 */
//@WebServlet("/SearchingServlet")
public class NewSearchingNoPool  extends HttpServlet {
    private static final long serialVersionUID = 1L;
       

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        
        String keyword;
/*      if (request.getParameter("keyword") != null){
            keyword = request.getParameter("keyword");
            session.setAttribute("keyword",keyword);
            System.out.println("beginning keyword " + keyword);
        } */
        keyword = request.getParameter("keyword");
        if (keyword == null) {
            List<Movies> movieList = new ArrayList<Movies>();
            //session.setAttribute("Movies", movieList);
            RequestDispatcher rd = request.getRequestDispatcher("/NewsearchResult");
                rd.forward(request, response);
            return; 
        }
        
        LogIn login = new LogIn();
        String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        response.setContentType("text/html");    // Response mime type
        List<Movies> movieList = new ArrayList<Movies>();
        Movies movie = new Movies();
                
        String[] splitsearch = keyword.split(" ");
        
        String query = "select distinct(movies.id), title, year, director, banner_url, trailer_url from movies, stars, stars_in_movies ";
        query += "where stars.id = stars_in_movies.star_id and"
            + " stars_in_movies.movie_id = movies.id";
        // need to know if all parameters all null so that i can get
        // rid of the where clause in sql statement
        for(int i = 0; i < splitsearch.length; i++) {
/*            query += " and (title LIKE \'%" + splitsearch[i] + "%\' "
                  + "or year = \'" + splitsearch[i] + "\' "
                  + "or director LIKE \'" + splitsearch[i] + "%\' "
                  + "or director LIKE \'%" + splitsearch[i] + "\' "
                  + "or first_name LIKE \'" + splitsearch[i] + "%\' "
                  + "or last_name LIKE \'" + splitsearch[i] + "%\' ) ";
*/
               // this is what we are going to use ....
                  query += " and (title LIKE ? "
                        + "or year = ? "
                        + "or director LIKE ? "
                        + "or director LIKE ? "
                        + "or first_name LIKE ? "
                        + "or last_name LIKE ? ) ";
               
        }
        
       query += " GROUP BY movies.id "; 
      
       
       // for now set to 50 
        movieList = movie.getMoviesNoPool(query,50,splitsearch); 
        
        session.setAttribute("Movies", movieList);

        session.setAttribute("query", query);
        
        //request.setAttribute("origin", request.getRequestURL());
        RequestDispatcher rd = request.getRequestDispatcher("/NewsearchResult");
        rd.forward(request, response);
        session.setAttribute("origin","SearchingServlet");
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

}
