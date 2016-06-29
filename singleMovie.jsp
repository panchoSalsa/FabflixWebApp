<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="models.Movies" import="models.Stars" 
    import="shoppingcart.Cart" import="java.util.HashMap" import="shoppingcart.MovieItem" import="java.text.DecimalFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%String context = (String) session.getAttribute("contextPath");
	String servletDispatcher = (String) session.getAttribute("origin");
		Movies movie = (Movies) request.getAttribute("Movie");
		out.println("<title> " + movie.getTitle() + " </title>");%>
		
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>

	<script> 
	
/* 	 $(function() { */
		function submitForm(myform) {
			   $.ajax(
				        {
				        	method: "get",
				            url:"Addtocart",
				            dataType: "text",
				            data: $(myform).serialize(),
				            success: function (response)
				            {
				                $('#table').load(document.URL +  ' #table');
				            },
				            error: function()
				            {
				            	console.log("ajax request to shopping cart failed");
				            }
				            
				        });
			   return false;  
			} 
	/*  }); */
	</script>		
</head>

<div class="header" style="width: 100%; height: 100px;">
<div align="right" style="margin-right: 100px; margin-top: 20px;">
<a href="logout" style="text-decoration: none;"><b><font color="white">Sign out of Fabflix</font></b></a>
</div>
<div align="left" style="margin-top: -40px;">
	<a href="main">
	<img src="${pageContext.request.contextPath}/Fabflix_logo.png" alt="logo" style="margin: 23px 0px 0px 41px; height: 90px; width: 188px;">
	</a></div><div style="margin-left: -px; margin-top: -80px;">
	<%
		Object obj = session.getAttribute("cart");
		
			out.println("<div align=\"center\" style='margin-top: -50px; margin-left: -20px;'><a href='checkout' style='text-decoration: none'>"
			+ "<font color='white'><b>Checkout </b>");
	%>
	</font><img src="${pageContext.request.contextPath}/checkout.png" style="height: 50px; width: 50px;"></a></div></div>div>
</div>

<body style="background-color: black;">
<div id='table' style="width: 150px; float: right; margin-right: 20px;">
<%	Cart shoppingCart = (Cart) session.getAttribute("cart");
			String contextPath = (String) session.getAttribute("contextPath"); 	
			out.println("<center>");
			out.println("<h2><font color='chucknorris'>Cart</h2>");
			HashMap<String,MovieItem> items = shoppingCart.getCartItems();
 			out.println("<table border='1px'>"); 

			if (!items.isEmpty()) {
	 			for(MovieItem item: items.values()){
					 out.println("<tr><td><font color='white'>" + item.title +"</font></td><td><font color='white'>x" + item.quantity 
							 + "</font></td>" + "<td><font color='white'>$" + item.price + "</font></td></tr>"); 
				} 
			}
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			out.println("<tr><td><font color='white'>" + "Total is $" + df.format(Math.round(shoppingCart.total * 100.0) / 100.0) + "</font></td></tr>");
 			out.println("</table>"); %>
 </div>
<center>
<%	out.println("<div style='margin-left: 175px;'><h1><font color=\"chucknorris\">" + movie.getTitle() + "</font></h1>");
	out.println("<img src=\"" + movie.getBanner_url() + "\" alt=\"Image unavailable\" style=\"color: white;\"><br></div>");
	out.println("<font color=\"chucknorris\">Movie ID: </font><font color=\"white\">" + movie.getId() + "</font><br>");
	out.println("<font color=\"chucknorris\">Director: </font><font color=\"white\">" + movie.getDirector() + "</font><br>");
	out.println("<font color=\"chucknorris\">Year: </font><font color=\"white\">" + movie.getYear() + "</font><br>");
	
	out.println("<font color=\"chucknorris\">Genres: </font><br>");
	
	for(String  genres: movie.getGenres())
	{
		out.println("<a href=\"movieByGenre?genre=" + genres + 
				"\" style=\"text-decoration: none;\"><font color=\"white\">" + genres + ",</font></a>");
	}
	
	out.println("<br><font color=\"chucknorris\">Starring: </font><br>");
	
	for(Stars  star: movie.getStars())
	{
		out.println("<a href=\"singleMovieStar?id=" + star.getId() + 
				"\" style=\"text-decoration: none;\"><font color=\"white\">" + star.getName() + ",</font></a>");
	}
	out.println("<br><br><font color=\"chucknorris\">Click the play button to watch the preview</font><br>");
	out.println("<a href='" + movie.getTrailer() 
	+ "'>");
%>
	<img src="${pageContext.request.contextPath}/play.png" style="height: 100px; width: 100px;"></a><br>
	
<form onsubmit='return submitForm(this);'>
<font color="chucknorris">Price: </font><br>
<font color="white">$5.99</font><br><br>

<font color="chucknorris">How many copies would you like to purchase?</font><br>
<%
out.println("<input type='hidden' name='name' value='" + movie.getTitle() + "'>");
out.println("<input type='hidden' name='price' value='5.99'>");
out.println("<input type='hidden' name='id' value='" + movie.getId() + "'>"); 
%>
<input type='number' name='quantity' min='0'>
<br>
<br>
<input type='image' src="${pageContext.request.contextPath}/addtocart.png">
</form>
</center>
</body>
</html>