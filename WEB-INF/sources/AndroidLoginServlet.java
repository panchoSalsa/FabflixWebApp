import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
// import com.mysql.jdbc.Connection;
// import com.mysql.jdbc.ResultSet;
// import com.mysql.jdbc.Statement;
import mysqljdbc.LogIn;
import shoppingcart.Cart;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.NamingException;

public class AndroidLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        response.setContentType("text/html");    // Response mime type
        
        
        // Output stream to STDOUT
        PrintWriter out = response.getWriter();
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
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            //Connection dbcon = (Connection) DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Connection dbcon =  ds.getConnection();
            Statement statement = (Statement) dbcon.createStatement();
            
            String email = request.getParameter("username");
            String password = request.getParameter("password");
            
            String query = "SELECT * from customers where email = '"
          	+ email + "' and password = '" + password + "'";
            
            // Perform the query
            ResultSet rs = (ResultSet) statement.executeQuery(query);
            
            
            if (!rs.isBeforeFirst() ) {
                // empty ResultSet
                out.print("invalid");
            } else {
                out.print("valid");
            }
            rs.close();
            statement.close();
            dbcon.close();
        }
        catch (SQLException ex) {
            while (ex != null) {
                ex = ex.getNextException ();
            }  // end while
        }  // end catch SQLException
        
        catch(java.lang.Exception ex)
        {
            return;
        }
        out.close();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
    
}
