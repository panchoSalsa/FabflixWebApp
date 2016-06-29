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

public class Stars
{
	public String id;
	public String name;
	public String dob;
	public String picture;
	public List<Movies> movies;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDob()
	{
		return dob;
	}
	public void setDob(String dob)
	{
		this.dob = dob;
	}
	public String getPicture()
	{
		return picture;
	}
	public void setPicture(String picture)
	{
		this.picture = picture;
	}
	public List<Movies> getMovies()
	{
		return movies;
	}
	public void setMovies(List<Movies> movies)
	{
		this.movies = movies;
	}
	
	public List<Movies> getMoviesIn(String id){
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        //List<Movies> list = new ArrayList<Movies>(); 
        List<Movies> movies = new ArrayList<Movies>();


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
              
              String query = "select * from stars, stars_in_movies, movies"
              	+ " where stars.id = stars_in_movies.star_id and stars_in_movies.movie_id = movies.id and stars.id=" + id; 
              statement = (PreparedStatement) dbcon.prepareStatement(query);
              // Perform the query
              ResultSet rs = (ResultSet) statement.executeQuery(query);

              // Iterate through each row of rs
              while (rs.next())
              {
            	  Movies movie = new Movies();
            	  movie.setTitle(rs.getString("movies.title"));
            	  movie.setId(rs.getString("movies.id"));
            	  movies.add(movie);
              }

              rs.close();
              statement.close();
              dbcon.close();
              return movies; 
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
              //return starsList; 
              return movies; 
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
        	return movies; 
            }
       //  out.close();
		
		//select * from stars, stars_in_movies, movies where stars.id = stars_in_movies.star_id and stars_in_movies.movie_id = movies.id and stars.id = 911;
	}
		
	public Stars getStarById(String id){	
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        //List<Movies> list = new ArrayList<Movies>(); 
    	Stars star = new Stars();


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
              
//              String query = "select * from stars, stars_in_movies, movies"
//              	+ " where stars.id = stars_in_movies.star_id and stars_in_movies.movie_id = movies.id and stars.id=" + id;
              String query = "select * from stars where stars.id=" + id;
              statement = (PreparedStatement) dbcon.prepareStatement(query);

              // Perform the query
              ResultSet rs = (ResultSet) statement.executeQuery(query);

              // Iterate through each row of rs
              while (rs.next())
              {
            	  star.setId(rs.getString("stars.id"));
            	  star.setName(rs.getString("stars.first_name") + rs.getString("stars.last_name"));
                  star.setDob(rs.getString("stars.dob"));
                  star.setPicture(rs.getString("stars.photo_url"));
                  star.setMovies(getMoviesIn(id));
              }

              rs.close();
              statement.close();
              dbcon.close();
              return star; 
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
              //return starsList; 
              return star; 
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
        	return star; 
            }
       //  out.close();
		
		//select * from stars, stars_in_movies, movies where stars.id = stars_in_movies.star_id and stars_in_movies.movie_id = movies.id and stars.id = 911;
	}
}
