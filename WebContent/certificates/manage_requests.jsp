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
<!-- Bootstrap Core JavaScript -->
<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script
	src="../bower_components/datatables/media/js/jquery.dataTables.min.js"
	type="text/javascript"></script>
<script
	src="../bower_components/datatables/media/js/jquery.jeditable.js"
	type="text/javascript"></script>
<script src="../bower_components/datatables/media/js/jquery.validate.js"
	type="text/javascript"></script>
<script
	src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="../bower_components/jquery/dist/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="../js_custom/show_certificate.js"></script>
<script type="text/javascript" src="../js_custom/_messagebar.js"></script>
<script type="text/javascript" src="../js_custom/manage_requests.js"></script>

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

<title>simpleCert - Anträge verwalten</title>

<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="../bower_components/bootstrap/dist/css/sb-admin-2.css"
	rel="stylesheet">
<link
	href="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
	rel="stylesheet">
<link
	href="../bower_components/datatables-responsive/css/dataTables.responsive.css"
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

				if (uc.isAdmin()) {
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
			<li><a href="settings.jsp"><i class="fa fa-gear fa-2x"></i></a></li>
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
					%>
					<%
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
					<li><a class="active"><i
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
		<div id="page-wrapper" class="page_with_table">
			<div id="messagebar_show"
				class="alert messagebar_intern messagebar_hidden"></div>
			<div class="row"></div>
			<div id="page_content_settings" class="page_content">
				<div class="row">
					<div class="col-lg-12 col-md-12">
						<h1 class="page-header first_element_upper_margin">Anträge
							verwalten</h1>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="dataTable_wrapper">
							<table class="table table-striped table-hover" id="requests">
								<thead class="table_head">
									<tr>
										<th>Beantragunsdatum</th>
										<th>Vorname</th>
										<th>Nachname</th>
										<th>Abteilung</th>
										<th>Personalnummer</th>
										<th>E-Mail-Adresse</th>
										<th style="min-width: 125px;">Status</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%
		} else {
				response.sendRedirect("home.jsp");
			}
		}
	%>
</body>
</html>