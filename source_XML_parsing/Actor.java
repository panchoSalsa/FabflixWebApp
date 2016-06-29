import java.util.*;


public class Actor{
	private String stagename; 
	private String firstname; 
	private String lastname;
	private String dob; 

	public Actor() {
		this.stagename = "";
		this.firstname = "";
		this.lastname = "";
		this.dob = ""; 
	}

	public Actor (String stagename, String firstname, String lastname, String dob) {
		this.stagename = stagename;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob; 
	}

	public void setstagename (String stagename) {
		if (stagename == null || stagename.isEmpty()) {
		//System.out.println("bug title is null");
			this.stagename = "";
		} else 
			this.stagename = stagename;
	}
	public void setfirstname (String firstname) {
		if (firstname == null || firstname.isEmpty()) {
		//System.out.println("bug title is null");
			this.firstname = "";
		} else 
			this.firstname = firstname;
	}
	public void setlastname (String lastname) {
		if (lastname == null || lastname.isEmpty()) {
		//System.out.println("bug title is null");
			this.lastname = "";
		} else 
			this.lastname = lastname;
	}
	public void setFirstAndLastName(String stagename) {
		String[] splitArray = stagename.split(" ");
		if (splitArray.length == 1)
			setfirstname(splitArray[0]);
		else if (splitArray.length == 2){
			setfirstname(splitArray[0]);
			setlastname(splitArray[1]);
		} else if (splitArray.length == 3){
			setfirstname(splitArray[0] + " " + splitArray[1]);
			setlastname(splitArray[2]);
		} else {
			setfirstname(splitArray[0] + " " + splitArray[1]);
			setlastname(splitArray[2] + " " + splitArray[3]);
		}
	}

	public void setdob (String dob) { // the actorsSAX makes sure to pass in a date
		this.dob = dob;
	}
	public String getstagename () {
		return this.stagename;
	}
	public String getfirstname () {
		return this.firstname;
	}
	public String getlastname () {
		return this.lastname;
	}
	public String getdob () {
		return this.dob;
	}

	public boolean emptyStageName(){
		if (this.stagename.equals(""))
			return true; 
		else 
			return false; 
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Actor Details - ");
		sb.append("stagename:" + this.stagename);
		sb.append(", ");
		sb.append("firstname:" + this.firstname);
		sb.append(", ");
		sb.append("lastname:" + this.lastname);
		sb.append(", ");
		sb.append("dob:" + this.dob);
		sb.append(".");
		
		return sb.toString();
	}

	public String sanitize(String s) {
		if (s == null)
			return "";
		else
			return s; 
	} 
}