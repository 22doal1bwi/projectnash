<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="de.projectnash.application.UserLogic"%>
<%@page import="de.projectnash.frontend.controllers.UserController"%>
<%@page import="de.projectnash.frontend.interfaces.IUserController"%>
<%@page import="de.projectnash.frontend.SessionController"%>
<%@page import="de.projectnash.application.SessionLogic"%>
<%@page import="de.projectnash.entities.User"%>
<%@page import="de.projectnash.entities.Session"%>
<%@page import="de.projectnash.databackend.SessionPersistenceService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<!-- jQuery -->
<script src="../bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript"
	src="../bower_components/jquery/dist/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="../js_custom/extend_certificate.js"></script>
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

<title>simpleCert - Zertifikat anzeigen</title>

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
	<div id="wrapper">

		<%
			//allow access only if session exists if not, redirect to login
			String sessionId = SessionController.checkForSessionId(request,
					response);

			switch (sessionId) {

				case "-1" :
				case "0" :
					response.sendRedirect("login.jsp");
					break;
				default :
					UserController uc = new UserController(sessionId);
					boolean hasValidCertificate = uc.hasValidCertificate();

					int remainingDays = 0;

					try {
						remainingDays = uc
								.getRemainingTimeOfCertificate(TimeUnit.DAYS);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (hasValidCertificate) {
		%>

		<!-- Navigation -->
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
		<!-- /.navbar-header -->

		<ul class="nav navbar-top-links navbar-right">

			<!-- /.dropdown -->
			<li><div class="name"><%=uc.getFullName()%></div></li>
			<!-- 			<li><img class="displayed" src="assets/img/find_user.png" -->
			<!-- 				style="width: 20px;" /></li> -->
			<li><a href="settings.jsp"><i class="fa fa-gear fa-2x"></i></a></li>
			<li>
				<form name="form_logout" action="../LogoutServlet" method="post">
					<a role="button" class="fa fa-sign-out fa-2x logout"
						style="text-decoration: none;" onclick="logout()"></a>
				</form>
			</li>


		</ul>
		<!-- /.navbar-top-links -->

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li><img class="displayed"
						src="../img/simplecert/simplecert_logo_text_128x128.png"
						style="margin-top: 10px; margin-bottom: 15px" /></li>
					<li><a href="home.jsp"><i
							class="fa fa-home fa-fw navbaricon"></i>Home</a></li>
					<li><a href="show_certificate.jsp"><i
							class="fa fa-file-text fa-fw navbaricon"></i>Zertifikat anzeigen</a></li>
					<li><a class="active navitem_disabled"><i
							class="fa fa-history fa-fw navbaricon"></i>Zertifikat verlängern</a></li>
					<li><a href="revoke_certificate.jsp"><i
							class="fa fa-ban fa-fw navbaricon"></i>Zertifikat widerrufen</a></li>
					<li>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<div id="page-wrapper">
			<div id="messagebar_extend"
				class="alert messagebar_intern messagebar_hidden"></div>
			<%
				if (remainingDays > 800) {
			%>
			<script type="text/javascript">
				$(document).ready(
						function() {
							buildAndShowMessageBar(
									"WRN_CERT_EXTENSION_IMPOSSIBLE",
									"messagebar_extend")
						});
			</script>
			<%
				}
			%>
			<!-- /.row -->

			<%
				if (remainingDays <= 800) {
			%>
			<div id="page_content_extend" class="page_content">
				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-default functiontile">
							<div id="step1_header_extend" class="panel-heading panelheader">
								<button id="step1_icon_extend" type="button"
									class="btn btn-default btn-circle panelicon">
									<i id="step1_iconfont_extend" class="fa fa-check"></i>
								</button>
								Schritt 1: Zertifikatsverlängerung beantragen
							</div>
							<div id="step1_content_extend">
								<div id="step1_panel_body_extend" class="panel-body">
									<p>Beantragen Sie eine Verlängerung für Ihr Zertifikat. Im
										nächsten Schritt können Sie anschließend - nach erfolgreicher
										Genehmigung Ihres Antrags - das länger gültige Zertifikat
										herunterladen.</p>
								</div>
								<div class="panel-footer">
									<button id="button_extend" onclick="extendCertificate()"
										type="button" class="btn simplecert_btn">Beantragen</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-default">
							<div id="step2_header_extend"
								class="panel-heading panelheader panel_next_step_or_loading">
								<button type="submit"
									class="btn btn-default btn-circle panelicon">
									<i class="fa fa-check"></i>
								</button>
								Schritt 2: Zertifikat herunterladen
							</div>
							<div id="step2_content_extend">
								<div class="panel-body">
									<p>Laden Sie nun ihr Zertifikat herunter und speichern Sie
										es, um es anschließend in ihren Browser importieren zu können.</p>
								</div>
								<div class="panel-footer">
									<form action="../CrtDownload" method="get">
										<button type="submit" class="btn simplecert_btn">Herunterladen</button>
									</form>
								</div>
							</div>

						</div>
						<div id="loading_gif_extend" class="loading_gif_request">
							<img src="../img/general/loading.gif">
						</div>
					</div>
				</div>
			</div>
			<!-- /.row -->
			<%
				} else {
			%>
			<div id="page_content_extend">
				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-default functiontile">
							<div id="step1_header_extend"
								class="panel-heading panelheader panel_next_step_or_loading">
								<button id="step1_icon_extend" type="button"
									class="btn btn-default btn-circle panelicon">
									<i id="step1_iconfont_extend" class="fa fa-check"></i>
								</button>
								Schritt 1: Zertifikatsverlängerung beantragen
							</div>
						</div>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-default">
							<div id="step2_header_extend"
								class="panel-heading panelheader panel_next_step_or_loading">
								<button type="submit"
									class="btn btn-default btn-circle panelicon">
									<i class="fa fa-check"></i>
								</button>
								Schritt 2: Zertifikat herunterladen
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.row -->
			<%
				}
			%>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->
		<%
			} else {
					response.sendRedirect("home.jsp");
				}
			}
		%>
	
</body>
</html>