
/*
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import models.Movies;
import mysqljdbc.LogIn;


//@WebServlet("/MovieDescription")
public class MovieDescription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MovieDescription() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		
		
//		out.println("hello from MovieDescription id : " + request.getParameter("id"));
		
		
		
		
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = (Connection) DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
              
              
              //Statement statement = (Statement) dbcon.createStatement();
              
              Statement statement; 
              
              String query = "select * from movies where id=" + id ; 
              statement = (PreparedStatement) dbcon.prepareStatement(query);

              // Perform the query
              ResultSet rs = (ResultSet) statement.executeQuery(query);

              // Iterate through each row of rs
              while (rs.next())
              { 	  
            	  out.println("<p>");
            	  out.println("title: " + rs.getString("title"));
            	  out.println("<br>");
            	  out.println("director: " + rs.getString("director"));
            	  out.println("<br>");
            	  out.println("year: " + rs.getString("year"));            	  
            	  out.println("</p>");
            	  out.println("<div style='float:right'>");
            	  out.println("<img src='" + rs.getString("banner_url") +"'>");
            	  out.println("</div>");
              }

              rs.close();
              statement.close();
              dbcon.close();

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
//                out.println("<HTML>" +
//                            "<HEAD><TITLE>" +
//                            "Main: Error" +
//                            "</TITLE></HEAD>\n<BODY>" +
//                            "<P>SQL error in doGet: " +
//                            ex.getMessage() + "</P></BODY></HTML>");
            }
		
	

		// make div in search page static give it a size to the right of the page 
		// tell will to fix the table and make it a fix width so the div can display
		// there are problems with search result by keyword. i think the problem is that we have two different servelets now. 
		// keyword prefix on autocomplete
        // other things we need to return in auto popup
        // hovers over the title .....
        //autocomplete click on movie, takes them to single movie page
        // searching servlet and ajax test servlet !!!!! 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
*/





import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import models.Movies;
import models.Stars;
import mysqljdbc.LogIn;


//@WebServlet("/MovieDescription")
public class MovieDescription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MovieDescription() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		
		
		Movies m = new Movies();
		m = m.getMovieById(id);
		  out.println("<p>");
		  out.println("title: " + m.getTitle());
		  out.println("<br>");
		  out.println("director: " + m.getDirector());
		  out.println("<br>");
		  out.println("year: " + m.getYear()); 
		  out.println("<br>");
		  out.println("stars: ");
		  for (Stars stars: m.getStars()) {
			  out.println(stars.getName() + " ");
		  }
		  out.println("<br>");
		  out.println("</p>");
		  out.println("<div style='float:right'>");
		  out.println("<img src='" + m.getBanner_url() +"'>");
		  out.println("</div>");
		



		// make div in search page static give it a size to the right of the page 
		// tell will to fix the table and make it a fix width so the div can display
		// there are problems with search result by keyword. i think the problem is that we have two different servelets now. 
		// keyword prefix on autocomplete
        // other things we need to return in auto popup
        // hovers over the title .....
        //autocomplete click on movie, takes them to single movie page
        // searching servlet and ajax test servlet !!!!! 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
