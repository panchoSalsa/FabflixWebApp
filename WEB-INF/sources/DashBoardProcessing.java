import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
// import com.mysql.jdbc.CallableStatement;
// import com.mysql.jdbc.Connection;
// import com.mysql.jdbc.DatabaseMetaData;
// import com.mysql.jdbc.PreparedStatement;
// import com.mysql.jdbc.ResultSet;
// import com.mysql.jdbc.Statement;
import mysqljdbc.LogIn;
import shoppingcart.Cart;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.NamingException;


public class DashBoardProcessing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("inside dashboard_processing");
		
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        response.setContentType("text/html");    // Response mime type
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

        // Output stream to STDOUT
  	    PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              //Class.forName("com.mysql.jdbc.Driver").newInstance();

              //Connection dbcon = (Connection) DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              Connection dbcon =  ds.getConnection();
              // Declare our statement
              
              if (request.getParameter("function").equals("star")) {
            	  session.setAttribute("metadata",null);
            	  
                  String first_Star = request.getParameter("first");
                  String last_Star = request.getParameter("last");
                  addNewStar(first_Star,last_Star,dbcon);
                  response.sendRedirect(request.getContextPath() + "/_dashboard/mainDashboard");
            	  
              } else if (request.getParameter("function").equals("metadata")) {
            	  if (session.getAttribute("metadata") == null) {
                	  String metadata = printMetaData(dbcon);               	  
                   	  session.setAttribute("metadata",metadata);
            	  }
                  response.sendRedirect(request.getContextPath() + "/_dashboard/mainDashboard");
              } else {
            	  session.setAttribute("metadata",null);
            	  String title = request.getParameter("title");
            	  String year = request.getParameter("year");
            	  String director = request.getParameter("director");
            	  String genre = request.getParameter("genre");
            	  String first_name = request.getParameter("first_name");
            	  String last_name = request.getParameter("last_name");
            	
   
            	  if (title.equals("") || year.equals("") || director.equals("")  ||
            			  genre.equals("")  || first_name.equals("")  || last_name.equals("") ) {
            		  response.sendRedirect(request.getContextPath() + "/_dashboard/mainDashboard");
            	  } else {
            		  String outParameter = callProcedure(dbcon, title, year, director, 
                			  genre, first_name, last_name);              	  
                   	  session.setAttribute("add_movie",outParameter);          	  
                   	  response.sendRedirect(request.getContextPath() + "/_dashboard/mainDashboard");

            	  }
            	  
              }
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
    public void addNewStar(String first_Star, String last_Star, Connection dbcon) throws SQLException{
		String updateString = "insert into stars (first_name,last_name) values(?,?)";
		PreparedStatement insertStar = (PreparedStatement) dbcon.prepareStatement(updateString);
		if (!last_Star.equals("")){
		    insertStar.setString(1,first_Star);
		    insertStar.setString(2,last_Star);
		} else if ((!first_Star.equals("")) && last_Star.equals("")){
		    insertStar.setString(1,"");
		    insertStar.setString(2,first_Star);
		} else{
			
		    return; 
		}
		insertStar.executeUpdate();
		insertStar = null;
		dbcon.close();
		dbcon = null; 
    }
    
    public String printMetaData(Connection dbcon) throws SQLException{
		DatabaseMetaData databaseMetaData = (DatabaseMetaData) dbcon.getMetaData();
		String catalog = null;
		String schemaPattern = null; 
		String tableNamePattern = null; 
		String columnNamePattern = null;
		String[] types = null; 
		String out = new String();
		ResultSet dbResult = (ResultSet) databaseMetaData.getTables(catalog,schemaPattern,tableNamePattern,types);
		ResultSet tableResult = null; 
		while (dbResult.next()){
		    out += dbResult.getString(3) + " metadata is :<br>";
		    tableResult = (ResultSet) databaseMetaData.getColumns(catalog,schemaPattern,dbResult.getString(3), columnNamePattern);
		    while (tableResult.next()){
		    	out += tableResult.getString(4) + "  " + JDBCType.valueOf(tableResult.getInt(5)).getName() ;
		    	out += "<br>";
		    }
		    out += "*********************************************************************";
		    out += "<br><br>"; 
		}
		databaseMetaData = null;
		dbResult = null;
		tableResult = null; 
		return out; 
    }

    public String callProcedure(Connection dbcon, String title, String year,
    	String director, String genre, String first_name, String last_name) throws SQLException {
    	String query = "{call add_movie(?,?,?,?,?,?,?)}";
    	CallableStatement callable = (CallableStatement) dbcon.prepareCall(query);
    	
    	callable.setString(1,title);
    	callable.setInt(2,Integer.parseInt(year));
    	callable.setString(3,director);
    	callable.setString(4,first_name);
    	callable.setString(5,last_name);
    	callable.setString(6,genre);
    	callable.registerOutParameter(7, java.sql.Types.VARCHAR);
    	
    	// execute
    	callable.executeUpdate();
    	String out = callable.getString(7);
    
    	
    	return out; 
    }
	

}
