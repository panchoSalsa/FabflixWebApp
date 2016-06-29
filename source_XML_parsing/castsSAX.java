import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.*;
import java.sql.*; 
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class castsSAX extends DefaultHandler{

   private static Connection con = ConnectionManager.getConnection();
   // static Map<String,Integer> actors = null;     
   // static Map<Film,Integer> movies = null;     

   private String fid; 
   private String stagename;    
   private String tempVal;
   private Map<String,Integer> movies;
   private Map<String,Integer> actors; 
   private Set<Pair> pairs = new HashSet<Pair>();
   
   
   public castsSAX(Map<String,Integer> movies, Map<String,Integer> actors){
      this.movies = movies; 
      this.actors = actors; 
   }
   
   public void runExample() {
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
         sp.parse("casts124.xml", this);
         
      }catch(SAXException se) {
         se.printStackTrace();
      }catch(ParserConfigurationException pce) {
         pce.printStackTrace();
      }catch (IOException ie) {
         ie.printStackTrace();
      }
   }

   // //Event Handlers
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      //reset
      tempVal = "";
      if(qName.equalsIgnoreCase("m")) {
         stagename = "";
         fid = "";
      }
   }
   

   public void characters(char[] ch, int start, int length) throws SAXException {
      tempVal = new String(ch,start,length);
   }
   
   public void endElement(String uri, String localName, String qName) throws SAXException {
      if (qName.equalsIgnoreCase("m")){
         if(movies.containsKey(fid) && actors.containsKey(stagename)) {
            pairs.add(new Pair(actors.get(stagename),movies.get(fid)));
            if (pairs.size() > 2) { 
               insertIntoDatabase();
               pairs.clear();
            }
         }
      } else if (qName.equalsIgnoreCase("casts")){ // insert remaining pairs 
         insertIntoDatabase();
         pairs.clear();
      } else if(qName.equalsIgnoreCase("f")) {
         fid = tempVal;     
      } else if (qName.equalsIgnoreCase("a")) {
         stagename = tempVal.replaceAll("[^a-zA-Z0-9\\s]", ""); 
      }
   }

   public void insertIntoDatabase() {
      Statement statement = null;
      ResultSet rs = null; 
      try {
         this.con.setAutoCommit(false);
         statement = con.createStatement();
         String batchInsertQuery = "insert into stars_in_movies (star_id, movie_id) values";
         for(Pair pair: pairs){
            batchInsertQuery += " (" + pair.star_id +", "+ pair.movie_id + "),";
         }
         batchInsertQuery = batchInsertQuery.substring(0, batchInsertQuery.length() - 1) + ";";
         statement.executeUpdate(batchInsertQuery);
         statement.close();
         this.con.commit();
            
      } catch (Exception e) {
         // System.out.println("***************************************");
         // System.out.println(e.getMessage());
         // System.out.println("failing in castsSAX");
         // System.out.println(stagename);
         // System.out.println(actors.get(stagename));
         // System.out.println(fid);
         // System.out.println(movies.get(fid));
         // System.out.println("***************************************");
         // System.out.println();
      }  
      finally {
         statement = null; 
         rs = null; 
      }
      
   }
}



