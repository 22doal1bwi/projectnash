<%@page import="de.projectnash.entities.User"%>
<%@page import="de.projectnash.entities.Session"%>
<%@page import="de.projectnash.databackend.SessionPersistenceService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Success Page</title>
</head>
<body>
	<%
		//allow access only if session exists
		User user = null;
		String sessionID = null;
		if (session.getAttribute("emailAddress") == null) {
			response.sendRedirect("login.html");
		} else {
			String userName = null;
			
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("JSESSIONID")){
						sessionID = cookie.getValue();
					}
				}
				Session appSession = SessionPersistenceService.loadSession(sessionID);
				user = appSession.getUser();
			}
		}
	%>
	<h3>Hi <%=user.getFirstName() %>, Login successful. Your Session ID=<%=sessionID%></h3>
	<br>
	User= <%=user.toString() %>
	<br>
	<a href="CheckoutPage.jsp">Checkout Page</a>
	<form action="LogoutServlet" method="post">
		<input type="submit" value="Logout">
	</form>
</body>
</html>