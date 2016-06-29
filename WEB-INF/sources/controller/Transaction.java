package controller;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
// import com.mysql.jdbc.Connection;
// import com.mysql.jdbc.PreparedStatement;
// import com.mysql.jdbc.ResultSet;
// import com.mysql.jdbc.Statement;
import mysqljdbc.LogIn;
import shoppingcart.MovieItem;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.NamingException;

public class Transaction
{

	public void makePurchase(String firstname, String lastname,
		String creditcard, MovieItem item)
	{
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        String customerId = ""; 


          Context initCtx = null; 
          Context envCtx = null; 
          DataSource ds = null; 
        try {
          initCtx =  new InitialContext();
          if(initCtx == null) System.out.println("initCtx is NULL");
            envCtx = (Context) initCtx.lookup("java:comp/env");
          if(envCtx == null) System.out.println("envCtx is NULL");
            ds = (DataSource) envCtx.lookup("jdbc/moviedb4Master");
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
              Statement statement = (Statement) dbcon.createStatement();
              String query = "select id from customers where first_name='"
              	+ firstname + "' and last_name='"+ lastname + "' and cc_id='" + creditcard + "'"; 

              // Perform the query
              ResultSet rs = (ResultSet) statement.executeQuery(query);
              while (rs.next())
              {
            	  customerId = rs.getString("id");
            	  
            	  
              }
              System.out.println("query is: " + query);
              System.out.println("customerId is : " + customerId);
              Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
              DateFormat formatter ; 
              String todaysDate ; 
              formatter = new SimpleDateFormat("yyyy-MM-dd");
             
              todaysDate = formatter.format(now);
              System.out.println("todays date is " + todaysDate);
              if (!customerId.equals("")){
	              query = "INSERT INTO sales (id,customer_id,movie_id,sale_date) VALUES (default,?,?,?)";
	              PreparedStatement p = (PreparedStatement) dbcon.prepareStatement(query);
	              p.setInt(1, Integer.valueOf(customerId));
	              p.setInt(2, Integer.valueOf(item.id));
	              p.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
	              p.executeUpdate();
	              p.close();
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
                return;
            }
    }
	
	public boolean verifyBillingInfo(String firstname, String lastname,
		String creditcard, String exp)
	{
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        boolean verify = false; 
        
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = (Connection) DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
              Statement statement = (Statement) dbcon.createStatement();
              
//              String query = "SELECT * from customers where email = '"
//              	+ email + "' and password = '" + password + "'";
//              DateFormat formatter ; 
//              Date date ; 
//                 formatter = new SimpleDateFormat("yy-MMM-dd");
//                 date = formatter.parse(exp);
              String query = "select * from creditcards where first_name='"
              	+ firstname + "' and last_name='" + lastname + "' and expiration='"
              		+ exp + "' and id='" + creditcard + "'";
              // Perform the query
              System.out.println("query is : " + query);
              ResultSet rs = (ResultSet) statement.executeQuery(query);
              
              if (!rs.isBeforeFirst() ) {    
            	  // empty ResultSet 
            	   verify = false;
            	 } else {   
            		 verify = true; 
            	 }
              rs.close();
              statement.close();
              dbcon.close();
              return verify; 
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
              return false; 
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
                return false;
            }

    }

}
