<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
      <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
      <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      <!-- Javascript -->
      <script>
      
        
/*          $(function() {
    	 	 
             $( "#automplete-1" ).autocomplete({
                source:function(request,response) {
                	   $.ajax(
                		        {
                		            url:"AjaxTest",
                		            dataType: "json",
                		            data:
                		            {
                		            	 term : request.term
                		            },
                		            minLength: 7,
                		            success: function (data)
                		            {
                		                console.log("data: " + data);
                		                response(data);
                		            },
                		            error: function()
                		            {
                		            	console.log("failed");
                		            }
                		            
                		        });
                },
                autoFocus:true
             });
          }); 
          */

      </script>


<title>Search</title>
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
<center>
	<form method="get" action="SearchingServlet">
<!-- 		<h1><font color="chucknorris">keyword: </font><input name="keyword" type="text" style="width: 378px; height: 38px;"></h1> 
 -->		
		<div class="ui-widget">
	   		<label for="automplete-1">Tags: </label>
	   		<input id="automplete-1" name="keyword" type="text" autocomplete="off" style="width: 378px; height: 38px;">
		</div>
		
		<br>
		<input type="image" src="${pageContext.request.contextPath}/search.png" >
	</form>
</center>

<br>
<br>







</body>
</html>