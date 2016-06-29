package models;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
// import com.mysql.jdbc.Connection;
// import com.mysql.jdbc.PreparedStatement;
// import com.mysql.jdbc.ResultSet;
// import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.NamingException;

import mysqljdbc.LogIn;

public class Movies
{
	public String title;
	public String director;
	public String banner_url;
	public String year; 
	public String id; 
	public String trailer; 
	//public List<String> starNames;
	public List<Stars> stars;
	public List<String> genres; 
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTrailer()
	{
		return trailer;
	}
	public void setTrailer(String trailer)
	{
		this.trailer = trailer;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getDirector()
	{
		return director;
	}
	public void setDirector(String director)
	{
		this.director = director;
	}
	public String getBanner_url()
	{
		return banner_url;
	}
	public String getYear()
	{
		return year;
	}
	public void setYear(String year)
	{
		this.year = year;
	}
	public void setBanner_url(String banner_url)
	{
		this.banner_url = banner_url;
	}
//	public List<String> getStarName()
//	{
//		return starNames;
//	}
//	public void setStarname(List<String> starNames)
//	{
//		this.starNames = new ArrayList<String>(starNames);
//	}
	public List<Stars> getStars()
	{
		return stars;
	}
	public void setStars(List<Stars> stars)
	{
		this.stars = new ArrayList<Stars>(stars);
	}
	public List<String> getGenres()
	{
		return genres;
	}
	public void setGenres(List<String> genres)
	{
		this.genres = new ArrayList<String>(genres);
	}
	
	public List<String> getMovieGenres(String id){	
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
       // response.setContentType("text/html");    // Response mime type
        List<String> genresList = new ArrayList<String>();

        // Output stream to STDOUT
      //  PrintWriter out = response.getWriter();
//
//        out.println("<HTML><HEAD><TITLE>MovieDB</TITLE></HEAD>");
//        out.println("<BODY><H1>MovieDB</H1>");

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
//        
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              //Class.forName("com.mysql.jdbc.Driver").newInstance();

              //Connection dbcon = (Connection) DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              Connection dbcon =  ds.getConnection();
              // Declare our statement
              Statement statement = (Statement) dbcon.createStatement();
              
//              String query = "select * from movies where title = '"
//              	+ title + "' or director = '" + director + "'";
              String query = "select * from movies, genres_in_movies, genres "
              	+ "where movies.id = genres_in_movies.movie_id and "
              	+ "genres_in_movies.genre_id = genres.id and movies.id = '"
              	+ id + "'";
              // need to know if all paramaters all null so that i can get
              // rid of the where clause in sql statement
             // System.out.println("query for genres " + query);
              // Perform the query
              ResultSet rs = (ResultSet) statement.executeQuery(query);

//              out.println("<TABLE border>");
              // Iterate through each row of rs
              while (rs.next())
              {
            	  String genre = rs.getString("name");
            	  
            	  genresList.add(genre);
            	  
              }
              
//              out.println("</TABLE></BODY></HTML>");
              
              rs.close();
              statement.close();
              dbcon.close();
//              for (String genre : genresList)
//            	  System.out.println(genre);
              
              return genresList; 
             
//              request.setAttribute("Movies", movieList);
//              RequestDispatcher rd = request.getRequestDispatcher("/result");
//              rd.forward(request, response);
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
              return genresList; 
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
        	return genresList; 
            }
       //  out.close();
	}
	public List<Stars> getStars(String id){	
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
       // response.setContentType("text/html");    // Response mime type
        List<Stars> starsList = new ArrayList<Stars>();

        // Output stream to STDOUT
      //  PrintWriter out = response.getWriter();
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
          if(ds == null) System.out.println("ds is null");
          } catch  (NamingException e) {
          System.out.println(e.getMessage());
        }         
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              //Class.forName("com.mysql.jdbc.Driver").newInstance();

              //Connection dbcon = (Connection) DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              Connection dbcon =  ds.getConnection();
              // Declare our statement
              Statement statement = (Statement) dbcon.createStatement();
              
//              String query = "select * from movies where title = '"
//              	+ title + "' or director = '" + director + "'";
              String query = "select * from movies, stars_in_movies, stars "
              	+ "where movies.id = stars_in_movies.movie_id and "
              	+ "stars_in_movies.star_id = stars.id and movies.id = '"
              	+ id + "'";
              // need to know if all paramaters all null so that i can get
              // rid of the where clause in sql statement
//              System.out.println("query for genres " + query);
              // Perform the query
              ResultSet rs = (ResultSet) statement.executeQuery(query);

//              out.println("<TABLE border>");
              // Iterate through each row of rs
              while (rs.next())
              {
            	  Stars star = new Stars();
            	  //String star = rs.getString("first_name") + " " + rs.getString("last_name");
            	  star.setName(rs.getString("first_name") + " " + rs.getString("last_name"));
            	  star.setId(rs.getString("stars.id"));
            	  starsList.add(star);
              }

              
//              out.println("</TABLE></BODY></HTML>");
              
              rs.close();
              statement.close();
              dbcon.close();
//              for (String genre : genresList)
//            	  System.out.println(genre);
              
              return starsList; 
             
//              request.setAttribute("Movies", movieList);
//              RequestDispatcher rd = request.getRequestDispatcher("/result");
//              rd.forward(request, response);
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
              return starsList; 
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
        	return starsList; 
            }
       //  out.close();
	}
public List<Movies> getMoviesNoPool(String query, int currentOffset, String[] splitSearch){	
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
        String loginUrl = login.db;
        List<Movies> list = new ArrayList<Movies>();  

        long startTime = System.nanoTime();
       /* Context initCtx = null; 
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
      }  */  
        try
           {
              //Class.forName("org.gjt.mm.mysql.Driver");
              Class.forName("com.mysql.jdbc.Driver").newInstance();

              Connection dbcon = (Connection) DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              //Connection dbcon =  ds.getConnection();
              PreparedStatement ps = null;
           
              String limit = String.valueOf(currentOffset);
              query = query.concat("limit " + limit);
             // System.out.println("query bug: " +  query);
             
              
              ps= (PreparedStatement) dbcon.prepareStatement(query);

              //System.out.println("query before setting params");
              int args = 1;
              for(int i = 0; i < splitSearch.length; i++) {            	  
            	  ps.setString(args,"%" + splitSearch[i] + "%");
            	  ++args; 

            	  if (isInteger(splitSearch[i]))
            		  ps.setInt(args,Integer.parseInt(splitSearch[i]));
            	  else
            		  ps.setInt(args,0);

            	  ++args; 
            	  ps.setString(args, splitSearch[i] + "%");
            	  ++args; 
            	  ps.setString(args,"%" + splitSearch[i]);
            	  ++args; 
            	  ps.setString(args,splitSearch[i] + "%");
            	  ++args; 
            	  ps.setString(args,splitSearch[i] + "%");
            	  ++args;
              }
             // System.out.println("preparedStatement to string: " + ps.toString());
              ResultSet rs = (ResultSet) ps.executeQuery();

              // Iterate through each row of rs
              while (rs.next())
              {
            	  //System.out.println("inside next");
            	  Movies movie = new Movies();
            	  movie.setTrailer(rs.getString("trailer_url"));
            	  movie.setId(rs.getString("movies.id"));
                  movie.setDirector(rs.getString("director"));
                  movie.setTitle(rs.getString("title"));
                  movie.setYear(rs.getString("year"));
                  movie.setBanner_url(rs.getString("banner_url"));
                  movie.setGenres(movie.getMovieGenres(rs.getString("movies.id")));
                  movie.setStars(movie.getStars(rs.getString("movies.id")));
                  list.add(movie);
              }
              //System.out.println("outside while next");
              rs.close();
              ps.close();
              dbcon.close();
              long endTime = System.nanoTime();
              long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
              System.out.print("TJ time is " + elapsedTime + " nano seconds, ");
              return list; 
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
              //return starsList; 
              System.out.println("sql exception raised");
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
        	
        	System.out.println("java exception raised");
        	 System.out.println ("this is the message : " + ex.getMessage ());
        	return list; 
            }
       //  out.close();
	}

	
	public List<Movies> getMovies(String query, int currentOffset){	
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        List<Movies> list = new ArrayList<Movies>(); 
	 long startTime = System.nanoTime();
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
              Connection dbcon =  ds.getConnection();
              // Declare our statement
            
              
              //Statement statement = (Statement) dbcon.createStatement();
              
              Statement statement;  
//              System.out.println("DEBUG");
 
//              String query = "select * from movies where title like '%" + keyword + "%'"
//              	+ " or director like '%" + keyword + "%'  or year like '%" + keyword + "%' limit " + l; 
//              System.out.println("query " + query);
              String limit = String.valueOf(currentOffset);
              query = query.concat("limit " + limit);
  //            System.out.println("query bug: " +  query);
              statement = (PreparedStatement) dbcon.prepareStatement(query);
             
              // Perform the query
              ResultSet rs = (ResultSet) statement.executeQuery(query);

              // Iterate through each row of rs
              while (rs.next())
              {
            	  Movies movie = new Movies();
            	  movie.setTrailer(rs.getString("trailer_url"));
            	  movie.setId(rs.getString("movies.id"));
                  movie.setDirector(rs.getString("director"));
                  movie.setTitle(rs.getString("title"));
                  movie.setYear(rs.getString("year"));
                  movie.setBanner_url(rs.getString("banner_url"));
                  movie.setGenres(movie.getMovieGenres(rs.getString("movies.id")));
                  movie.setStars(movie.getStars(rs.getString("movies.id")));
                  list.add(movie);
              }

              rs.close();
              statement.close();
              dbcon.close();
              long endTime = System.nanoTime();
              long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds
              System.out.print("TJ time is " + elapsedTime + " nano seconds, ");
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
	
	
	public boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public List<Movies> getMoviesWithPreparedStatement(String query, int currentOffset, String[] splitSearch){	
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
        String loginUrl = login.db;
        List<Movies> list = new ArrayList<Movies>();  

        long startTime = System.nanoTime();
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
              Connection dbcon =  ds.getConnection();
              PreparedStatement ps = null;
           
              String limit = String.valueOf(currentOffset);
              query = query.concat("limit " + limit);
             // System.out.println("query bug: " +  query);
             
              
              ps= (PreparedStatement) dbcon.prepareStatement(query);

              //System.out.println("query before setting params");
              int args = 1;
              for(int i = 0; i < splitSearch.length; i++) {            	  
            	  ps.setString(args,"%" + splitSearch[i] + "%");
            	  ++args; 

            	  if (isInteger(splitSearch[i]))
            		  ps.setInt(args,Integer.parseInt(splitSearch[i]));
            	  else
            		  ps.setInt(args,0);

            	  ++args; 
            	  ps.setString(args, splitSearch[i] + "%");
            	  ++args; 
            	  ps.setString(args,"%" + splitSearch[i]);
            	  ++args; 
            	  ps.setString(args,splitSearch[i] + "%");
            	  ++args; 
            	  ps.setString(args,splitSearch[i] + "%");
            	  ++args;
              }
             // System.out.println("preparedStatement to string: " + ps.toString());
              ResultSet rs = (ResultSet) ps.executeQuery();

              // Iterate through each row of rs
              while (rs.next())
              {
            	  //System.out.println("inside next");
            	  Movies movie = new Movies();
            	  movie.setTrailer(rs.getString("trailer_url"));
            	  movie.setId(rs.getString("movies.id"));
                  movie.setDirector(rs.getString("director"));
                  movie.setTitle(rs.getString("title"));
                  movie.setYear(rs.getString("year"));
                  movie.setBanner_url(rs.getString("banner_url"));
                  movie.setGenres(movie.getMovieGenres(rs.getString("movies.id")));
                  movie.setStars(movie.getStars(rs.getString("movies.id")));
                  list.add(movie);
              }
              //System.out.println("outside while next");
              rs.close();
              ps.close();
              dbcon.close();
              long endTime = System.nanoTime();
              long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds 
              System.out.print("TJ time is " + elapsedTime + " nano seconds, ");
              return list; 
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
              //return starsList; 
              System.out.println("sql exception raised");
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
        	
        	System.out.println("java exception raised");
        	 System.out.println ("this is the message : " + ex.getMessage ());
        	return list; 
            }
       //  out.close();
	}
	
	public Movies getMovieById(String id){	
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        //List<Movies> list = new ArrayList<Movies>(); 
        Movies movie = new Movies();
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
              Connection dbcon =  ds.getConnection();
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
            	  movie.setTrailer(rs.getString("trailer_url"));
            	  movie.setId(rs.getString("movies.id"));
                  movie.setDirector(rs.getString("director"));
                  movie.setTitle(rs.getString("title"));
                  movie.setYear(rs.getString("year"));
                  movie.setBanner_url(rs.getString("banner_url"));
                  movie.setGenres(movie.getMovieGenres(rs.getString("movies.id")));
                  movie.setStars(movie.getStars(rs.getString("movies.id")));
  
              }

              rs.close();
              statement.close();
              dbcon.close();
              return movie; 
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
              //return starsList; 
              return movie; 
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
        	return movie; 
            }
       //  out.close();
	}
	
	
	public Movies getMovieByTitle(String title){	
		LogIn login = new LogIn();
		String loginUser = login.user;
        String loginPasswd = login.password;
//        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        String loginUrl = login.db;
        //List<Movies> list = new ArrayList<Movies>(); 
        Movies movie = new Movies();
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
              Connection dbcon =  ds.getConnection();
              // Declare our statement
              
              
              //Statement statement = (Statement) dbcon.createStatement();
              
              Statement statement; 
              
              String query = "select title, id, director, banner_url, trailer_url, year from movies where title = '"+ title  + "' limit 1"; 
              statement = (PreparedStatement) dbcon.prepareStatement(query);

              // Perform the query
              ResultSet rs = (ResultSet) statement.executeQuery(query);

              // such a title doesnt exist 
              if (!rs.isBeforeFirst() ) {    
            	  rs.close();
                  statement.close();
                  dbcon.close();
                  return null;   
              }

              while (rs.next())
              {
            	  movie.setTrailer(rs.getString("trailer_url"));
            	  movie.setId(rs.getString("movies.id"));
                  movie.setDirector(rs.getString("director"));
                  movie.setTitle(rs.getString("title"));
                  movie.setYear(rs.getString("year"));
                  movie.setBanner_url(rs.getString("banner_url"));
                  movie.setGenres(movie.getMovieGenres(rs.getString("movies.id")));
                  movie.setStars(movie.getStars(rs.getString("movies.id")));
  
              }

              rs.close();
              statement.close();
              dbcon.close();
              return movie; 
            }
        catch (SQLException ex) {
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
              //return starsList; 
              return movie; 
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
        		return movie; 
            }
       //  out.close();
	}
	
	
}
