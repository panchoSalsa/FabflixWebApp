import java.util.*;
public class Film {
	private String title; 
	private String director; 
	private int year; 
	private Set<String> genres;
	private String genre; 
	private String fid; 

	public Film() {
		this.title = "";
		this.director = "";
		this.year = 0;
		this.genres = new HashSet<String>();
		this.fid = ""; // id's from mysql will also be fids 

	}

	public Film (String title, String director, int year) {
		this.title = title;
		this.director = director;
		this.year = year;
	}

	public Film (String title, String director, int year, String genre) {
		this.title = title;
		this.director = director;
		this.year = year;
		this.genre = genre;	
	}

	public boolean emptyTitle(){
		if (this.title.equals(""))
			return true; 
		else
			return false; 
	}

	public String transform() {
		String t = title.replaceAll("(?=[]\\[+&|!(){}^\"~*?:\\\\-])", "\\\\");
		return t;
	}
	public void settitle (String title) {
		if (title == null || title.isEmpty()) {
			//System.out.println("bug title is null");
			this.title = "";
		}
		else {
			// title = title.replaceAll("(?=[]\\[+&|!(){}^\"~*?:\\\\-])", "\\\\");
			// char[] titleChar = title.toCharArray();
			// for (int i = 0; i < titleChar.length; ++i) {
			// 	if (titleChar[i].isDigit() || titleChar[i].isLetter()) {

			// 	} else {

			// 	}
			// }
			// myName = String.valueOf(myNameChars);
			this.title = title;
		}
	}
	public void setdirector (String director) {
		if (director == null || director.isEmpty() || director.equals("null")) {
			// System.out.println("bug director is null");
			this.director = "";
		}
		else
			this.director = director;
	}
	public void setgenres (Set<String> genres) {
		this.genres.addAll(genres);
	}

	public void setyear (int year) {
		this.year = year;
	}
	public void setfid (String fid) {
			this.fid = fid;
	}
	public void setgenre (String genre) {
		if (genre == null || genre.isEmpty()) {
			//System.out.println("bug genre is null");
			this.genre = "";
		}
		else
			this.genre = genre;
	}
	public String gettitle() {
		return this.title;
	}
	public String getdirector() {
		return this.director;
	}
	public int getyear () {
		return this.year;
	}
	public String getfid() {
		return this.fid; 
	}
	public Set<String> getgenres() {
		return this.genres;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Film Details - ");
		sb.append("title:" + this.title);
		sb.append(", ");
		sb.append("year:" + this.year);
		sb.append(", ");
		sb.append("director:" + this.director);
		sb.append(", ");
		sb.append("fid:" + this.fid);
		sb.append(", ");
		sb.append("genre:" + this.genre);
		sb.append(".");
		
		return sb.toString();
	}

	public String sanitize(String s) {
		if (s == null)
			return "";
		else
			return s; 
	} 

	//**
	
	@Override
	public boolean equals(Object obj) {
	 	if (obj == null){ 
	 		System.out.print("obj == null is false leads to false" );
	 		return false;
	 	}
    	if (obj == this) {
    		return true;}
    	if (!(obj instanceof Film)) {
    		System.out.print("obj == this leads instance false leads to false" );
    		return false;
    	}

		Film film =  (Film) obj;
		if (this.title.equals(film.gettitle()) && 
		 	this.year == film.getyear() && this.director.equals(film.getdirector())){ 
			// System.out.println("equality");
			// System.out.println(film.toString());
			return true; 
		}
		else {			
			// System.out.println("else");
			return false; 
		}

	}

	@Override
	public int hashCode() {
	    int hash = 3;
   	 	hash = 53 * hash * (this.title.length() + this.director.length() + this.year); 
		return hash; 
	}

	//**



	public String catToGenre(String cat) {
		String genreConverted = "";
		switch (cat) {
			case "Susp" : {
				genreConverted = "Thriller"; 
				break; 
			}
			case "CnR" : {
				genreConverted = "Cops and Robbers";
				break; 
			}
			case "Dram" : {
				genreConverted = "Drama";
				break; 
			}
			case "West" : {
				genreConverted = "Westerns";
				break; 
			}
			case "Myst" : {
				genreConverted = "Mystery";
				break; 
			}
			case "S.F." : {
				genreConverted = "SciFi";
				break; 
			}
			case "Advt" : {
				genreConverted = "advanture";
				break; 
			}
			case "Horr" : {
				genreConverted = "Horror";
				break; 
			}
			case "Romt" : {
				genreConverted = "Romance";
				break; 
			}
			case "Comd" : {
				genreConverted = "Comedy";
				break; 
			}
			case "Musc" : {
				genreConverted = "Musical/Performing Arts";
				break; 
			}
			case "Docu" : {
				genreConverted = "Documentary";
				break; 
			}
			case "Porn" : {
				genreConverted = "Pornography, including Soft";
				break; 
			}
			case "Noir" : {
				genreConverted = "Black";
				break; 
			}
			case "BioP" : {
				genreConverted = "Biographical Picture";
				break; 
			}
			case "TV" : {
				genreConverted = "Tv Show";
				break; 
			}
			case "TVs" : {
				genreConverted = "TV Series";
				break; 
			}
			case "TVm" : {
				genreConverted = "TV Miniseries";
				break; 
			}
		}
		return genreConverted; 
	}

}