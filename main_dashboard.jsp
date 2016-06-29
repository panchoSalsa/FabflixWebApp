<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<H1 ALIGN="CENTER">Add a New Star</H1>

<FORM ACTION="process"
      METHOD="POST">  
   <CENTER>
  First Name: <input name="first" type="text">
  Last Name: <INPUT name="last" TYPE="text" ><BR>

  	<input type="hidden" name="function" value="star">
  	<br>
    <INPUT TYPE="SUBMIT" VALUE="Submit Order">
  </CENTER> 
</FORM>

<br>
<br>
<H1 ALIGN="CENTER">Request MetaData</H1>

<FORM ACTION="process"
      METHOD="POST">
  <CENTER>
  	<input type="hidden" name="function" value="metadata">
    <INPUT TYPE="SUBMIT" VALUE="Submit Order">
  </CENTER> 
</FORM>

<CENTER>
<%
	Object obj = session.getAttribute("metadata");
  	if (obj != null) {
  		out.println((String) obj);
  	}
  	
%></CENTER>

<br>

<H1 ALIGN="CENTER">Update Movie Information</H1>
<FORM ACTION="process"
      METHOD="POST">
  <CENTER>
  	Title: <input name="title" type="text"> <br>
  	Year: <input name="year" type="text"> <br>
  	Director: <input name="director" type="text"> <br>
  	Genre: <input name="genre" type="text"> <br>
  	Stars First Name: <input name="first_name" type="text"> <br>
  	Stars Last Name: <input name="last_name" type="text"> <br>
  	<input type="hidden" name="function" value="procedure">
  	<br>
    <INPUT TYPE="SUBMIT" VALUE="Submit Order">
  </CENTER> 
</FORM>

<br>

<%
	Object objadd_movie = session.getAttribute("add_movie");
  	if (objadd_movie != null) {
  		out.println((String) objadd_movie);
  	}
  	session.setAttribute("add_movie", null);
  	
%>




</body>
</html>