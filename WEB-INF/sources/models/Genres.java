package models;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// import com.mysql.jdbc.Connection;
// import com.mysql.jdbc.PreparedStatement;
// import com.mysql.jdbc.ResultSet;
// import com.mysql.jdbc.Statement;
import mysqljdbc.LogIn;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.NamingException;

public class Genres
{
	public List<String> getGenres(String query){	
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        List<String> list = new ArrayList<String>();   


  Context initCtx = null; 
  Context envCtx = null; 
  DataSource ds = null; 
try {
  initCtx =  new InitialContext();
  if(initCtx == null) System.out.println("initCtx is NULL");
    envCtx = (Context) initCtx.lookup("java:comp/env");
  if(envCtx == null) System.out.println("envCtx is NULL");
    ds = (DataSource) envCtx.lookup("jdbc/moviedb4");
  if(ds == null) System.out.println("ds is null");
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
              //Statement statement = (Statement) dbcon.createStatement();
              Statement statement;  
              statement =  (Statement) dbcon.createStatement();
              // Perform the query
              ResultSet rs = (ResultSet) statement.executeQuery(query);

              // Iterate through each row of rs
              while (rs.next())
              {
                  list.add(rs.getString("name"));
              }

              rs.close();
              statement.close();
              dbcon.close();
              return list; 
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
              //return starsList; 
              return list; 
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
                //return List<Array>;
        	//return starsList; 
        	return list; 
            }
       //  out.close();
	}
}
