<%@page import="de.projectnash.frontend.controllers.SessionController"%>
<%@page import="de.projectnash.frontend.controllers.UserController"%>
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
		// Allow access only if session exists - if not, redirect to login
		String sessionId = SessionController.checkForSessionId(request,
				response);

		switch (sessionId) {

			case "-1" :
			case "0" :
				response.sendRedirect("login.jsp");
				break;
			default :
				UserController uc = new UserController(sessionId);
	%>
	<div id="wrapper">
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<ul class="nav navbar-top-links navbar-right">

			<%
				if (uc.isAdmin()) {
			%><li><div class="admin_flag_name">ADMIN</div></li>
			<%
				}
			%>
			<li><div class="name"><%=uc.getFullName()%></div></li>
			<li><a class="active" href="settings.jsp"><i
					class="fa fa-gear fa-2x"></i></a></li>
			<li>
				<form name="form_logout" action="../LogoutServlet" method="post">
					<a role="button" class="fa fa-sign-out fa-2x logout"
						style="text-decoration: none;" onclick="logout()"></a>
					<script type="text/javascript">
						function logout() {
							document.form_logout.submit()
						}
					</script>
				</form>
			</li>

		</ul>
		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li><img class="displayed"
						src="../img/simplecert/simplecert_logo_text_128x128.png"
						style="margin-top: 10px; margin-bottom: 15px" /></li>
					<li><a href="home.jsp"><i
							class="fa fa-home fa-fw navbaricon"></i>Home</a></li>
					<%
						if (!uc.hasValidCertificate()) {
					%>
					<li><a href="request_certificate.jsp"><i
							class="fa fa-file-text fa-fw navbaricon"></i>Zertifikat
							beantragen</a></li>
					<%
						} else {
					%>
					<li><a href="show_certificate.jsp"><i
							class="fa fa-file-text fa-fw navbaricon"></i>Zertifikat anzeigen</a></li>
					<%
						}
							if (uc.hasValidCertificate()) {
					%>
					<li><a href="extend_certificate.jsp"><i
							class="fa fa-history fa-fw navbaricon"></i>Zertifikat verlängern</a></li>
					<li><a href="revoke_certificate.jsp"><i
							class="fa fa-ban fa-fw navbaricon"></i>Zertifikat widerrufen</a></li>
					<%
						} else {
					%>

					<li class="disabled"><a class="navitem_disabled"><i
							class="fa fa-history fa-fw navbaricon"></i>Zertifikat verlängern</a></li>
					<li class="disabled"><a class="navitem_disabled"><i
							class="fa fa-ban fa-fw navbaricon"></i>Zertifikat widerrufen</a></li>
					<%
						}
							if (uc.isAdmin()) {
					%>
					<li style="height: 25px;"></li>
					<li><a href="manage_requests.jsp"><i
							class="fa fa-files-o fa-fw navbaricon"></i>Anträge verwalten
							<div class="admin_flag_nav"></div></a></li>
					<li><a href="manage_users.jsp"><i
							class="fa fa-users fa-fw navbaricon"></i>Benutzer verwalten
							<div class="admin_flag_nav"></div></a></li>
					<%
						}
					%>
				</ul>
			</div>
		</div>
		</nav>
		<div id="page-wrapper">
			<div id="messagebar_settings"
				class="alert messagebar_intern messagebar_hidden"></div>
			<div class="row"></div>
			<div id="page_content_settings" class="page_content">
				<div class="row">
					<div class="col-lg-4 col-md-8">
						<h1 class="page-header first_element_upper_margin">Account</h1>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-4 col-md-8">
						<div class="form-group">
							<label>Vorname</label>
							<p class="form-control-static"><%=uc.getFirstName()%></p>
							<label>Nachname</label>
							<p class="form-control-static"><%=uc.getLastName()%></p>
							<label>Abteilung</label>
							<p class="form-control-static"><%=uc.getDepartment()%></p>
							<label>Personalnummer</label>
							<p class="form-control-static"><%=uc.getPersonalId()%></p>
							<label>E-Mail-Addresse</label>
							<p class="form-control-static"><%=uc.getEmailAddress()%></p>
							<label>Passwort</label><br> <input type="password"
								class="form-control password_field_hidden" id="password_current"
								name="password_current" placeholder="Aktuelles Passwort">
							<input type="password" class="form-control password_field_hidden"
								id="password_new" name="password_new"
								onchange="validatePassword()" placeholder="Neues Passwort"
								onpaste="return false;"> <input type="password"
								class="form-control password_field_hidden"
								id="password_new_confirm"
								placeholder="Neues Passwort wiederholen" onpaste="return false;"><br>
							<button id="button_cancel_password" onclick="cleanPage()"
								type="button" class="btn simplecert_inv_btn"
								style="display: none; margin-right: 2px;">Abbrechen</button>
							<button id="button_confirm_password"
								onclick="confirmPasswordChange()" type="button"
								class="btn simplecert_btn" style="display: none;">OK</button>
							<button id="button_change_password" onclick="changePassword()"
								type="button" class="btn simplecert_btn password_change_btn">Passwort
								ändern</button>
							<img id="loading_gif_settings" class="loading_gif"
								style="display: none" src="../img/general/loading.gif">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%
		}
	%>

</body>
</html>