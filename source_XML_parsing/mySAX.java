import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.*;
import java.sql.*; 


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class mySAX extends DefaultHandler{

   static List myFilms;
   // public List categories;
   Set<String> categories;

   static Connection con = ConnectionManager.getConnection();
   static Map<String,Integer> movies = null;  // fid -> id 
   static Map<String,Integer> genres = null; 
   static Map<Film, String> titles = null;  //  film -> fid
   static Map<String,Integer> actors = null;    
   // static Map<Film,Integer> movies = null;     

   
   private String tempVal;
   
   // //to maintain context
   private Film tempFilm;
   
   
   public mySAX(){
      myFilms= new ArrayList();
      titles = new HashMap<Film,String>();
      categories = new HashSet<String>();
   }
   
   public void run() {
      parseDocument();
      // printData();
   }

   private void parseDocument() {
      
      //get a factory
      SAXParserFactory spf = SAXParserFactory.newInstance();
      try {
      
         //get a new instance of parser
         SAXParser sp = spf.newSAXParser();
         
         //parse the file and also register this class for call backs
         sp.parse("mains243.xml", this);
         
      }catch(SAXException se) {
         se.printStackTrace();
      }catch(ParserConfigurationException pce) {
         pce.printStackTrace();
      }catch (IOException ie) {
         ie.printStackTrace();
      }
   }

   // /**
   //  * Iterate through the list and print
   //  * the contents
   //  */
   private void printData(){
      
      System.out.println("No of Films '" + myFilms.size() + "'.");
      
      Iterator it = myFilms.iterator();
      while(it.hasNext()) {
         System.out.println(it.next().toString());
      }
   }
   

   // //Event Handlers
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      //reset
      tempVal = "";
      if(qName.equalsIgnoreCase("film")) {
         //create a new instance of employee
         tempFilm = new Film();
         // tempFilm.setType(attributes.getValue("type"));
      }
   }
   

   public void characters(char[] ch, int start, int length) throws SAXException {
      tempVal = new String(ch,start,length);
   }
   
   public void endElement(String uri, String localName, String qName) throws SAXException {
      if(qName.equalsIgnoreCase("film")) {
         //add it to the list
         if (!tempFilm.emptyTitle()){ // if title is not "" then add it to the myFilms list  
            if (categories.size() > 0)
               tempFilm.setgenres(categories);
            myFilms.add(tempFilm);
         }
          categories.clear();
      }else if (qName.equalsIgnoreCase("t")) {
         tempFilm.settitle(tempVal.replaceAll("[^a-zA-Z0-9\\s]", ""));
      }else if (qName.equalsIgnoreCase("year")) {
         if (tempVal.matches("\\d+"))
            tempFilm.setyear(Integer.parseInt(tempVal));
         else 
            tempFilm.setyear(0);
      }else if (qName.equalsIgnoreCase("dirn")) {
         tempFilm.setdirector(tempVal);
      }else if (qName.equalsIgnoreCase("cat")) { // might be a problem that cat ends; 
         if (!tempVal.equals(""))
            categories.add(tempFilm.catToGenre(tempVal)); // problemmmmmmm

      }else if (qName.equalsIgnoreCase("fid")) {
         tempFilm.setfid(tempVal);
      }      
   }

   public static void insertMoviesInDabase() {
      PreparedStatement statement = null; 
      PreparedStatement statement2 = null;
      ResultSet rs = null; 
      try {
         String query = "insert into movies (title,year,director) values (?,?,?)";
         String query2 = "insert into genres_in_movies (genre_id,movie_id) values (?,?)";

         statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
         statement2 = con.prepareStatement(query2);
         int rows = 0; 
         Integer movie_id = 0; 
         Iterator it = myFilms.iterator();
         while(it.hasNext()) {
            Film film = (Film) it.next();
            if (!filmHasBeenAdded(film)) {
               try {
                  statement.setString(1, film.gettitle());
                  statement.setInt(2, film.getyear());
                  statement.setString(3, film.getdirector());
                  rows += statement.executeUpdate();

                  rs = statement.getGeneratedKeys();
                  if (rs.next()) {
                     movie_id = rs.getInt(1);
                     movies.put(film.getfid(), movie_id); 
                  }
                  titles.put(film,film.getfid());
                  
               } catch (Exception e) {
                  // System.out.println(e.getMessage());
               } 
               Set<String> g = film.getgenres();
               addNewGenresToDatabase(g);
               if (g != null) 
                  for (String s : g) {
                     statement2.setInt(1,genres.get(s));
                     statement2.setInt(2,movie_id);
                     statement2.executeUpdate();

                  } 
            }
         }
         statement = null;
         statement2 = null; 
         rs = null; 

      } catch (SQLException se) {
         //Handle errors for JDBC
         se.printStackTrace();
      } finally {
         statement = null; 
      }
   }

   public static boolean filmHasBeenAdded(Film film) {
      if (movies.containsKey(titles.get(film)))
         return true;
      else
         return false; 
   }

   public static Map<String,Integer> storeAllMovieTittlesInAMap() {
   // public static Map<Film,Integer> storeAllMovieTittlesInAMap() {
      Map<String,Integer> map = new HashMap<String,Integer>();
      // Map<Film,Integer> map = new HashMap<Film,Integer>();
      String query = "select * from movies;";
      Statement statement = null;
      ResultSet rs = null; 
      try {
         statement = con.createStatement();
         rs = statement.executeQuery(query);
         while (rs.next()) {
            try {
               map.put(String.valueOf(rs.getInt("id")),rs.getInt("id"));

               Film film = new Film();
               film.settitle(rs.getString("title"));
               film.setyear(rs.getInt("year"));
               film.setdirector(rs.getString("director"));

               titles.put(film,String.valueOf(rs.getInt("id")));

            } catch (Exception e){
               // System.out.println(e.getMessage());
            }
         } 
      } catch (SQLException  e) {
         return null; 
      } finally {
         statement = null;
         rs = null; 
      }
      return map; 
   } 

   public  static void addNewGenresToDatabase(Set<String> genreList) {
      PreparedStatement statement = null; 
      ResultSet rs = null; 
      try {
         String query = "insert into genres(name) values(?)";
         // String batchInsertQuery = "insert into genres(name) values";
         statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
         Iterator it = genreList.iterator();
         while(it.hasNext()) {
            String genre = (String) it.next();
            if (!genreHasBeenAdded(genre)) {
               try {
                  statement.setString(1, genre);
                  statement.executeUpdate();
                  rs = statement.getGeneratedKeys();
                  if (rs.next()) {
                     genres.put(genre, rs.getInt(1)); 
                  }
               } catch (Exception e) {
                  //System.out.println(e.getMessage());
               } 
            }
         }

         statement = null;
         rs = null; 

      } catch (SQLException se) {
         //Handle errors for JDBC
         se.printStackTrace();
      } finally {
         statement = null; 
      }
   }

   public static boolean genreHasBeenAdded(String genre) {
      if (genres.containsKey(genre)) 
         return true;
      else
         return false; 
   }


   public static Map<String,Integer> storeAllGenresInAMap() {
      Map<String,Integer> map = new HashMap<String,Integer>();
      // Map<Film,Integer> map = new HashMap<Film,Integer>();
      String query = "select * from genres;";
      Statement statement = null;
      ResultSet rs = null; 
      try {
         statement = con.createStatement();
         rs = statement.executeQuery(query);
         while (rs.next()) {
            try {
               map.put(rs.getString("name"),rs.getInt("id"));

            } catch (Exception e){
               // System.out.println(e.getMessage());
            }
         } 
      } catch (SQLException  e) {
         return null; 
      } finally {
         statement = null;
         rs = null; 
      }
      return map; 
   } 

   public static void main(String[] args){
      mySAX spe = new mySAX();
      spe.run();
      genres = storeAllGenresInAMap();
      movies = storeAllMovieTittlesInAMap(); 
      insertMoviesInDabase(); 

      actorsSAX actorparser = new actorsSAX();
      
      actors = actorparser.run();

      castsSAX cSAX = new castsSAX(movies,actors);
      cSAX.runExample();


      // for (Map.Entry<String,Integer> entry : genres.entrySet()) {
      // // for (Map.Entry<Film,Integer> entry : movies.entrySet()) {
      //    String key = entry.getKey();
      //    Integer value = entry.getValue();
      //    System.out.println(key + " -> " + value);
      // }
      //System.out.println("movies size: " + titles.size());
      //System.out.println("actors size: " + actors.size());

   }
   
}



