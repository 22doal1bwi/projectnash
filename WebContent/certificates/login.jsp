<%@page import="de.projectnash.frontend.controllers.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="de" class="document">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<link rel="icon" type="image/png" sizes="32x32"
	href="../img/simplecert/simplecert_favicon_32x32.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="../img/simplecert/simplecert_favicon_96x96.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="../img/simplecert/simplecert_favicon_16x16.png">
<link rel="icon" href="../img/simplecert/simplecert_favicon.ico">

<script src="../bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript"
	src="../bower_components/jquery/dist/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="../js_custom/login.js"></script>
<script type="text/javascript" src="../js_custom/_messagebar.js"></script>
<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<title>simpleCert - Login</title>

<!-- Bootstrap core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../css_custom/login.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../bower_components/bootstrap/dist/css/sb-admin-2.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- Custom styles for the messagebar -->
<link href="../css_custom/_messagebar.css" rel="stylesheet"
	type="text/css">

<!-- Roboto font -->
<link href="../css_custom/_roboto.css" rel="stylesheet" type="text/css">

</head>

<body class="body_login">
	<%
		String sessionIdStatus = SessionController.checkForSessionId(request, response);

		switch (sessionIdStatus) {
		default:
			response.sendRedirect("home.jsp");
			break;
		case "0":
		case "-1":
	%>
	<div class="messagebar_placeholder">
		<div id="messagebar_login" class="alert messagebar messagebar_hidden"></div>
	</div>
	<div id="login_container">
		<div class="login_area">
			<form class="form-signin">
				<img src="../img/simplecert/simplecert_logo_text_128x128.png"
					style="margin-bottom: 20px; margin-top: 20px;" /><br />
				<input type="email" id="emailAddress" name="emailAddress"
					class="form-control" placeholder="E-Mail-Adresse" required
					autofocus> <br />
				<input type="password" id="password" name="password"
					class="form-control" placeholder="Passwort" required>
				<button id="loginButton" class="btn btn-lg simplecert_btn btn-block"
					type="button" onclick="checkFormBeforeSubmit()">Einloggen</button>
			</form>
			<br /> <a class="link" href="" data-toggle="modal"
				data-target="#resetModal">Passwort vergessen?</a>
			<hr class="horizontal_divider">
			<form class="form-signin">
				<a class="btn btn-lg simplecert_inv_btn btn-block btn-small"
					href="register.jsp" role="button">Neu anmelden</a><img
					id="loading_gif_login_redirect" src="../img/general/loading_big.gif"
					class="loading_gif_redirect">
			</form>
			<br />
		</div>
	</div>
	<div class="modal fade" id="resetModal" tabindex="-1" role="dialog"
		aria-labelledby="resetModalLabel" aria-hidden="true"
		style="display: none;">
		<div id="modalDialog" class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">Passwort vergessen?</h4>
				</div>
				<div class="modal-body">
					<p class="modal-text">Geben Sie Ihre E-Mail-Adresse ein und wir
						helfen Ihnen, Ihr Passwort zurückzusetzen.</p>
					<input type="email" id="emailAddressForNewPassword"
						name="emailAddressForNewPassword"
						class="form-control email_address_login_modal"
						placeholder="E-Mail-Adresse"
						onchange="validateInput('emailAddressForNewPassword')" required>
					<input type="email" id="emailAddressForNewPasswordConfirm"
						name="emailAddressForNewPasswordConfirm" class="form-control"
						placeholder="E-Mail-Adresse wiederholen"
						onchange="validateInput('emailAddressForNewPasswordConfirm')"
						required>
				</div>
				<div class="modal-footer">
					<img id="loading_gif_login_password"
						src="../img/general/loading.gif" class="loading_gif_password">
					<button id="cancelButton" onclick="clearFields()" type="button"
						class="btn simplecert_inv_btn" data-dismiss="modal">Abbrechen</button>
					<button id="resetButton" type="button"
						onclick="requestNewPassword()" class="btn simplecert_btn">Absenden</button>
				</div>
			</div>
		</div>
	</div>
	<%
		}
	%>
</body>
</html>