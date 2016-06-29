<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List" import="models.Movies" import="models.Stars"
    import="shoppingcart.Cart" import="java.util.HashMap" import="shoppingcart.MovieItem" import="java.text.DecimalFormat"%>
    	<%  String origin = (String) session.getAttribute("origin");
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Results</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>

<script> 

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
	
	// on document ready 
	$(function()
		{
		$(document).on('mouseover','.row', function() 
			{
			id = $(this).find('a').attr('id');
			url = "MovieDescription";
 			url += "?id=" + id 
		   	$.ajax(
				{
			    method: "post",
			    url:url,
			    success: function (response)
			    	{
 			        console.log("response is " + response);
			        $("#testresult").html(response).offset({ top: response.pageY, left: response.pageX});
			        }
				});
			});
		$(document).on('mouseleave','.row', function()
			{
			$("#testresult").empty();
			})
		});
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
	</font><img src="${pageContext.request.contextPath}/checkout.png" style="height: 50px; width: 50px;"></a></div></div>
</div>

<body style="background-color: black;">
<div style="float: right; margin: 20vh 20vw 0 0;">
<div id="testresult" style="background-color:white;
 position: fixed; max-width: 200px;">
</div></div>

<div id="table" style="width: 150px; float: right; margin-right: 20px;">
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
	<h1><font color="chucknorris">Search Results</font></h1>
	<h1><font color="chucknorris">Sort By Year: </font><a href="sortedByYear?column=year&action=1" style="text-decoration:none">
	<font color="white">Old-New</font></a>  
	<a href="sortedByYear?column=year&action=0" style="text-decoration:none"><font color="white">New-Old</font></a></h1>
	<br>
	<h1><font color="chucknorris">Sort By Title: </font><a href="sortedByYear?column=title&action=1" style="text-decoration:none">
	<font color="white">A-Z</font></a>  
	<a href="sortedByYear?column=title&action=0" style="text-decoration:none"><font color="white">Z-A</font></a></h1>
	<br>
	
	<%
	 out.println("<h1><a href=" + origin + "?offset=10&direction=prev>Prev</a></h1>");
	 out.println("<h1><a href=" + origin + "?offset=10&direction=next>Next</a></h1>");

	List<Movies> movieList = (List<Movies>)session.getAttribute("Movies");
	out.println("<table id='movieTable' border='true'>");
	for(Movies movie : movieList)
	{
		
		out.println("<tr class='row'>");
		out.println("<td>");
 		out.println("<a id='" +movie.getId() + "'" + "href ='SingleMovie?id=" + movie.getId() + "' style='text-decoration: none'><font color='white'>" + movie.getTitle() + "</font></a>"); 

 		out.println("</td>");
		out.println("<td><font color='white'>");
		out.println(movie.getDirector());
		out.println("</font></td>");
		out.println("<td><font color='white'>");
		out.println(movie.getYear());
		out.println("</font></td>");
		out.println("<td><font color='white'>");
		for(String  genres: movie.getGenres()){
			out.println(genres);
			out.println(" ");
		}
		out.println("</font></td>");
		out.println("<td>");
		for(Stars  star: movie.getStars()){
			out.println("<a href ='singleMovieStar?id=" + star.getId() + "' style='text-decoration: none'><font color='white'>" + star.getName() + "</font></a>");
		}
		out.println("</td>");
		out.println("<td>");
		out.println("<img src='" + movie.getBanner_url() + "' alt='Image unavailable' style='color: white; width: 97px; height: 140px;'>");
		out.println("</td>");
		out.println("</tr>");
		out.println("<form class='movieForm' method='post' onsubmit='return submitForm(this);'>");
		out.println("<tr><td><font color='white'>" + movie.getId() + "</font></td><td><font color='white'>$5.99</white></td><td>");
		out.println("<input type='hidden' name='name' value='" + movie.getTitle() + "'>");
		out.println("<input type='hidden' name='price' value='5.99'>");
		out.println("<input type='hidden' name='id' value='" + movie.getId() + "'>");
		out.println("<input type='number' name='quantity' min='0'></td><td>");
		out.println("<input type='submit' value='Add to cart'></td>");
		out.println("</tr>");
		out.println("</form>");
	}
	out.println("</table>");
	%>
</body>
</html>