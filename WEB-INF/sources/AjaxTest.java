import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.json.JSONArray;
//import org.json.JSONObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import models.Movies;
import mysqljdbc.LogIn;

/**
 * Servlet implementation class AjaxTest
 */
//@WebServlet("/AjaxTest")
public class AjaxTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	System.out.println("ajax call to servlet");

		

//	String json = "[ { \"label\": \"Saving Private Ryan\", \"value\": \"Saving Private Ryan\" }]";
	
	String keyword = request.getParameter("term");
	
	StringBuilder jsonBuilder = new StringBuilder();
	LogIn login = new LogIn();
	String loginUser = login.user;
    String loginPasswd = login.password;
//    String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
    String loginUrl = login.db;
    try
       {
          //Class.forName("org.gjt.mm.mysql.Driver");
          //Class.forName("com.mysql.jdbc.Driver").newInstance();

          Connection dbcon = (Connection) DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
          // Declare our statement
          
          
          //Statement statement = (Statement) dbcon.createStatement();
          
          PreparedStatement ps = null;
          System.out.println("keyword "+keyword);
  		  String[] array = keyword.split("\\s+");
  		  int arrayLength = array.length; 
  		  --arrayLength; 
  		  String againstArg = "'";
  		  
  		  for (int i = 0; i < arrayLength; ++i)
  			  againstArg = againstArg + "+" + array[i] + " ";
  		  
  		  againstArg = againstArg + "+" + array[arrayLength] + "*' ";
//  		  String query = "select distinct(title)  from movies where match(title) against(" + againstArg
//  				  + "IN BOOLEAN MODE);";
  		  String query = "select distinct(title)  from movies where match(title) against( ? IN BOOLEAN MODE);";
  		  System.out.println("my q: " + query);
          //String query = "select distinct(title) from movies where title like '%" + keyword + "%'" ; 
//          statement = (PreparedStatement) dbcon.prepareStatement(query);
          ps = (PreparedStatement) dbcon.prepareStatement(query);
          
          ps.setString(1,againstArg);
          // Perform the query
          
          ResultSet rs = (ResultSet) ps.executeQuery();

          // Iterate through each row of rs
          jsonBuilder.append("[");
          
          while (rs.next())
          {
              String title = rs.getString("title");
              jsonBuilder.append("{\"label\": \""+ title + "\", \"value\": \"" + title + "\"}");
              if (!rs.isLast())
            	  jsonBuilder.append(",");


          }

          jsonBuilder.append("]");

//      	String json = "[ { \"label\": \"Saving Private Ryan\", \"value\": \"Saving Private Ryan\" }]";
          
          rs.close();
          ps.close();
          dbcon.close();
          
          
      	PrintWriter out = response.getWriter();
    	out.println(jsonBuilder.toString());
    	System.out.println("**debug**");
    	System.out.println(jsonBuilder.toString());
    	System.out.println("**debug**");

          
          
          
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
//            out.println("<HTML>" +
//                        "<HEAD><TITLE>" +
//                        "Main: Error" +
//                        "</TITLE></HEAD>\n<BODY>" +
//                        "<P>SQL error in doGet: " +
//                        ex.getMessage() + "</P></BODY></HTML>");
            //return List<Array>;
    	//return starsList; 
        }
   //  out.close();


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
