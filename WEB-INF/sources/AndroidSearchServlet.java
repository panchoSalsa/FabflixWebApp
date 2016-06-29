import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import models.Movies;
import mysqljdbc.LogIn;

public class AndroidSearchServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
       
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub

    String keyword = request.getParameter("keyword");
    response.setContentType("application/json");
  
    StringBuilder jsonBuilder = new StringBuilder();
    LogIn login = new LogIn();
    String loginUser = login.user;
    String loginPasswd = login.password;
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

  		 String query = "select distinct(title)  from movies where match(title) against( ? IN BOOLEAN MODE);";
  		  System.out.println("my q: " + query);         


          ps = (PreparedStatement) dbcon.prepareStatement(query);
          ps.setString(1,againstArg);
          
          // Perform the query          
          
          ResultSet rs = (ResultSet) ps.executeQuery();

          
          // Iterate through each row of rs
          jsonBuilder.append("{\"listOfMovies\":[");
          while (rs.next())
          {
              String title = rs.getString("title");
              jsonBuilder.append("\"" + title + "\"");
              if (!rs.isLast())
                jsonBuilder.append(",");


          }
          jsonBuilder.append("]}");
          
          rs.close();
          ps.close();
          dbcon.close();
          
          
      PrintWriter out = response.getWriter();
      out.print(jsonBuilder.toString());
      //System.out.print(jsonBuilder.toString());
        }
    catch (SQLException ex) {
          while (ex != null) {
                System.out.println ("SQL Exception:  " + ex.getMessage ());
                ex = ex.getNextException ();
            }  // end while
        }  // end catch SQLException

    catch(java.lang.Exception ex)
        {
          return;  
        }
   //  out.close();


  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}
