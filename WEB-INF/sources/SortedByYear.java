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
// import com.mysql.jdbc.Connection;
// import com.mysql.jdbc.ResultSet;
// import com.mysql.jdbc.Statement;
import models.Movies;
import mysqljdbc.LogIn;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.NamingException;

/**
 * Servlet implementation class SortedByYear
 */
//@WebServlet("/SortedByYear")
public class SortedByYear extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
//		String director = request.getParameter("director");
//		String year = request.getParameter("year");
//		String firstname = request.getParameter("firstname");
//		String lastname = request.getParameter("lastname");
		
		
		//System.out.println("director is **" + director); 
			
		//System.out.println("title is " + title + " director is " + director);
		
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        response.setContentType("text/html");    // Response mime type
        List<Movies> movieList = new ArrayList<Movies>();

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
//
//        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
//        out.println("<BODY><H1>MovieDB</H1>");
//         



          Context initCtx = null; 
          Context envCtx = null; 
          DataSource ds = null; 
        try {
          initCtx =  new InitialContext();
          if(initCtx == null) System.out.println("initCtx is NULL");
            envCtx = (Context) initCtx.lookup("java:comp/env");
          if(envCtx == null) System.out.println("envCtx is NULL");
            ds = (DataSource) envCtx.lookup("jdbc/moviedb4");
          if(ds == null) out.println("ds is null");
          } catch  (NamingException e) {
          System.out.println(e.getMessage());
        }        
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              // Class.forName("com.mysql.jdbc.Driver").newInstance();

              // Connection dbcon = (Connection) DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
              Connection dbcon =  ds.getConnection();
              Statement statement = (Statement) dbcon.createStatement();
              
//              String query = "select * from movies where title = '"
//              	+ title + "' or director = '" + director + "'";
//              String query = "select * from movies, stars, stars_in_movies ";
//              query += "where stars.id = stars_in_movies.star_id and"
//              	+ " stars_in_movies.movie_id = movies.id";
//              // need to know if all paramaters all null so that i can get
//              // rid of the where clause in sql statement
//              if (!title.equals(""))
//            	  query += " and title = '" + title + "'";
//              if (!year.equals(""))
//            	  query += " and year = '" + year + "'";
//              if(!director.equals(""))
//            	  query += " and director = '" + director + "'";
//              if ((!firstname.equals("")) && (!lastname.equals("")))
//            	  query += " and stars.first_name ='" + firstname + "'"
//            	  	+ " and stars.last_name = '" + lastname + "'" ;
//              else if (!firstname.equals(""))
//            	  query += " and stars.first_name ='" + firstname + "'";
//              else if (!lastname.equals(""))
//            	  query += " and stars.last_name ='" + lastname + "'";
              String action; 
              //System.out.println("action " + request.getParameter("action"));
              if (request.getParameter("action").equals("1"))
            	  action = "asc";
  
              else 
            	  action = "desc";
               
              //System.out.println("action " + action);
//              String columnn = request.getParameter("column");
//      		HttpSession session = request.getSession();
//    		String keyword = request.getParameter("keyword");

      		  HttpSession session = request.getSession();
    		  String query = (String) session.getAttribute("query");
    		  System.out.println(query);
    		  String limit = Integer.toString((Integer) session.getAttribute("sessionOffset"));
    		  if (request.getParameter("column").equals("title")){
//            	  query = "select * from movies where director = '" + "Steven Spielberg" + "'"
//              	+ " order by title ";
    			  
//            	  query = "select * from movies where director like '%" + keyword + "%'"
//            	  	+ " or title like '%" + keyword + "%'  or year like '%" + keyword + "%'"
//            	  	+ " order by title ";
//            	  String query = "select * from movies where title like '%" + keyword + "%'"
//                        	+ " or director like '%" + keyword + "%'  or year like '%" + keyword + "%'"; 
    			  query = query.concat(" order by title ");
              } else {
//            	  query = "select * from movies where director = '" + "Steven Spielberg" + "'"
//                        	+ " order by year ";

            	  query = query.concat(" order by year ");
            	  
              }
              
              query = query.concat(action + " limit " + limit);
              
              System.out.println("query: " + query);
              System.out.println("action "+ action);
              ResultSet rs = (ResultSet) statement.executeQuery(query);

//              out.println("<TABLE border>");
              // Iterate through each row of rs
              while (rs.next())
              {
            	  Movies movie = new Movies();
                  movie.setDirector(rs.getString("director"));
                  movie.setTitle(rs.getString("title"));
                  movie.setYear(rs.getString("year"));
                 // movie.setFirstname(rs.getString("first_name"));
                  movie.setBanner_url(rs.getString("banner_url"));
                  movie.setGenres(movie.getMovieGenres(rs.getString("movies.id")));
                  movie.setStars(movie.getStars(rs.getString("movies.id")));
                  movie.setId(rs.getString("id"));
                  movieList.add(movie);   
                  movie.setStars(movie.getStars(rs.getString("movies.id")));
                  //System.out.println("title is " + m_FN + " director is " + m_ID);
              }
              
              
              rs.close();
              statement.close();
              dbcon.close();
             
        //     session.setAttribute("query", query);
              
              session.setAttribute("Movies", movieList);
              RequestDispatcher rd = request.getRequestDispatcher("/result");
              rd.forward(request, response);
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
        	
        	// how will we handle this!!!!!
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "Main: Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
         out.close();
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
