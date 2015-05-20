<%@page import="de.projectnash.frontend.controllers.SessionController"%>
<%@page import="de.projectnash.application.SessionLogic"%>
<%@page
	import="de.projectnash.frontend.controllers.CertificateController"%>
<%@page import="de.projectnash.frontend.controllers.UserController"%>
<%@page import="de.projectnash.entities.User"%>
<%@page import="de.projectnash.application.UserLogic"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<!-- jQuery -->
<script src="../bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript"
	src="../bower_components/jquery/dist/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="../js_custom/settings.js"></script>
<script type="text/javascript" src="../js_custom/_messagebar.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<link rel="icon" type="image/png" sizes="32x32"
	href="../img/simplecert/simplecert_favicon_32x32.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="../img/simplecert/simplecert_favicon_96x96.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="../img/simplecert/simplecert_favicon_16x16.png">
<link rel="icon" href="../img/simplecert/simplecert_favicon.ico">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>simpleCert - Account</title>

<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="../bower_components/bootstrap/dist/css/sb-admin-2.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- Intern Pages CSS -->
<link href="../css_custom/intern.css" rel="stylesheet" type="text/css">

<!-- Custom styles for the messagebar -->
<link href="../css_custom/_messagebar.css" rel="stylesheet"
	type="text/css">

<!-- Roboto font -->
<link href="../css_custom/_roboto.css" rel="stylesheet" type="text/css">

</head>

<body>
		<%
			//allow access only if session exists if not, redirect to login
			String sessionId = SessionController.checkForSessionId(request,
					response);

			switch (sessionId) {

			case "-1":
			case "0":
				response.sendRedirect("login.jsp");
				break;
			default:
				UserController uc = new UserController(sessionId);
				boolean hasValidCertificate = false;

				String firstName = uc.getFirstName();
				String lastName = uc.getLastName();
				String department = uc.getDepartment();
				String emailAddress = uc.getEmailAddress();
				hasValidCertificate = uc.hasValidCertificate();				
		%>

		<div id="page-wrapper">
			<div id="messagebar_settings"
				class="alert messagebar_intern messagebar_hidden"></div>
			<div class="row"></div>
			<!-- /.row -->
			<div id="page_content_settings" class="page_content">
				<div class="row">
					<div class="col-lg-4 col-md-8">
						<h1 class="page-header">Account</h1>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-4 col-md-8">
						<div class="form-group">
							<label>Vorname</label>
							<p class="form-control-static"><%=firstName%></p>
							<label>Nachname</label>
							<p class="form-control-static"><%=lastName%></p>
							<label>Abteilung</label>
							<p class="form-control-static"><%=department%></p>
							<label>E-Mail-Addresse</label>
							<p class="form-control-static"><%=emailAddress%></p>
							<label>Passwort</label><br> <input type="password"
								class="form-control password_field_hidden"
								id="password_current" name="password_current" placeholder="Aktuelles Passwort"
								onpaste="return false;"> <input type="password"
								class="form-control password_field_hidden"
								id="password_new" name="password_new" placeholder="Neues Passwort"
								onpaste="return false;"> <input type="password"
								class="form-control password_field_hidden"
								id="password_new_confirm"
								placeholder="Neues Passwort wiederholen" onpaste="return false;"><br>
							<button id="button_cancel_password" onclick="cleanPage()"
								type="button" class="btn simplecert_inv_btn"
								style="display: none; margin-right: 2px;">Abbrechen</button>
							<button id="button_confirm_password" onclick="confirmPasswordChange()"
								type="button" class="btn simplecert_btn" style="display: none;">OK</button>
							<button id="button_change_password" onclick="changePassword()"
								type="button" class="btn simplecert_btn password_change_btn">Passwort
								ändern</button>
						</div>
						<div id="loading_gif_settings" class="loading_gif_settings" style="display: none;">
							<img src="../img/general/loading.gif">
						</div>
					</div>
				</div>
				<!-- /.row -->

			</div>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->
		<%
			}
		%>
	
</body>
</html>