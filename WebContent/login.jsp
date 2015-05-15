<%@page import="de.projectnash.frontend.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<link rel="icon" type="image/png" sizes="32x32"
	href="img/simplecert/simplecert_favicon_32x32.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="img/simplecert/simplecert_favicon_96x96.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="img/simplecert/simplecert_favicon_16x16.png">
<link rel="icon" href="img/simplecert/simplecert_favicon.ico">

<script src="intern/bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript"
	src="intern/js/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/_messagebar.js"></script>

<title>simpleCert - Login</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/login.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="intern/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="intern/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- Custom styles for the messagebar -->
<link href="css/_messagebar.css" rel="stylesheet" type="text/css">

</head>

<body style="background-color: white">

	<%
		String sessionIdStatus = SessionController.checkForSessionId(
				request, response);

		switch (sessionIdStatus) {
		default:
			response.sendRedirect("intern/index.html");
			break;
		case "0":
		case "-1":
	%>

	<div class="messagebar_placeholder">
		<div id="messagebar_login" class="alert messagebar messagebar_hidden"></div>
	</div>
	<div class="container">
		<div id="login_container">
			<form class="form-signin">
				<!--<h2 class="form-signin-heading">Please sign in</h2>-->
				<img src="img/simplecert/simplecert_logo_text_128x128.png" style="margin-bottom: 20px; margin-top: 5px"/> <br /> <label
					for="inputEmail" class="sr-only">E-Mail-Address</label> <input
					type="email" id="emailAddress" name="emailAddress"
					class="form-control" placeholder="E-Mail-Adresse"
					onchange="cleanInputField('emailAddress')" required autofocus>
				<br /> <label for="inputPassword" class="sr-only">Passwort</label>
				<input type="password" id="password" name="password"
					class="form-control" placeholder="Passwort"
					onchange="cleanInputField('password')" required>
				<button id="loginButton" class="btn btn-lg btn-red btn-block"
					type="button" onclick="checkFormBeforeSubmit()">Einloggen</button>
			</form>
			<br />
			<hr class="horizontal_divider">
			<form class="form-signin">
				<a class="btn btn-lg btn-primary btn-block btn-small"
					href="register.jsp" role="button">Neu anmelden</a>
			</form>
			<br />
		</div>
	</div>
	<!-- End container -->
	<%
		}
	%>
</body>
</html>