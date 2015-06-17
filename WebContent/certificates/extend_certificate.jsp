<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="de.projectnash.application.util.Constants"%>
<%@page import="de.projectnash.application.UserLogic"%>
<%@page import="de.projectnash.frontend.controllers.UserController"%>
<%@page import="de.projectnash.frontend.interfaces.IUserController"%>
<%@page import="de.projectnash.frontend.controllers.SessionController"%>
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

<title>simpleCert - Zertifikat verlängern</title>

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

				if (uc.hasValidCertificate()) {
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
			<div id="messagebar_extend"
				class="alert messagebar_intern messagebar_hidden"></div>
			<!-------------------------------<BEGIN> INITIALIZE MESSAGEBAR---------------------------------->
			<%
				if (uc.getRemainingTimeOfCertificate(TimeUnit.DAYS) > Constants.TIMEFRAME_FOR_EXTENSION) {
			%>
			<script type="text/javascript">
				$(document).ready(function() {
					window.setTimeout(function() {
						$("#page_content_extend").addClass("page_content_move_down")
						buildAndShowMessageBar("WRN_CERT_EXTENSION_IMPOSSIBLE", "messagebar_extend")
					}, 250);
				});
			</script>
			<%
				} else if (uc.hasWaitingRequest()) {
			%>
			<script type="text/javascript">
				$(document).ready(function() {
					window.setTimeout(function() {
						$("#page_content_extend").addClass("page_content_move_down")
						buildAndShowMessageBar("WRN_CERT_REQUEST_WAITING", "messagebar_extend")
					}, 250);
				});
			</script>
			<%
				} else if (uc.hasAcceptedRequest()) {
			%>
			<script type="text/javascript">
				$(document).ready(function() {
					window.setTimeout(function() {
						$("#page_content_extend").addClass("page_content_move_down")
						buildAndShowMessageBar("SCS_CERT_REQUEST_ACCEPTED", "messagebar_extend")
					}, 250);
				});
			</script>
			<%
				} else if (uc.hasDeniedRequest()) {
			%>
			<script type="text/javascript">
				$(document).ready(function() {
					window.setTimeout(function() {
						$("#page_content_extend").addClass("page_content_move_down")
						buildAndShowMessageBar("ERR_CERT_REQUEST_DENIED", "messagebar_extend")
					}, 250);
				});
			</script>
			<%
				}
			%>
			<!-------------------------------<END> INITIALIZE MESSAGEBAR---------------------------------->
			<div id="page_content_extend" class="page_content">
				<%
					if (!uc.hasRequest()
									&& uc.getRemainingTimeOfCertificate(TimeUnit.DAYS) <= Constants.TIMEFRAME_FOR_EXTENSION) {
				%>
				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-default">
							<div id="step1_header_extend" class="panel-heading panelheader">
								<table>
									<tr>
										<td><div id="step1_icon_extend"
												class="btn btn-default btn-circle panelicon">
												<i id="step1_iconfont_extend"
													class="fa fa-check messageicon_default"></i>
											</div></td>
										<td>Schritt 1: Zertifikatsverlängerung beantragen</td>
									</tr>
								</table>
							</div>
							<div id="step1_content_extend">
								<div id="step1_panel_body_extend" class="panel-body">
									<p>Beantragen Sie eine Verlängerung für Ihr Zertifikat. Im
										nächsten Schritt können Sie - nach erfolgreicher Genehmigung
										Ihres Antrags - das länger gültige Zertifikat herunterladen.</p>
								</div>
								<div class="panel-footer">
									<button id="step1_button_extend" onclick="requestCertificate()"
										type="button" class="btn simplecert_btn"
										style="display: inline">Beantragen</button>
									<img id="loading_gif_extend" class="loading_gif"
										src="../img/general/loading.gif">
								</div>
							</div>
						</div>
					</div>
				</div>
				<%
					} else if (!uc.hasRequest()
									&& uc.getRemainingTimeOfCertificate(TimeUnit.DAYS) > Constants.TIMEFRAME_FOR_EXTENSION) {
				%>
				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-default">
							<div id="step1_header_extend"
								class="panel-heading panelheader panel_next_step_or_loading">
								<table>
									<tr>
										<td><div id="step1_icon_extend"
												class="btn btn-default btn-circle panelicon">
												<i id="step1_iconfont_extend"
													class="fa fa-check messageicon_default"></i>
											</div></td>
										<td>Schritt 1: Zertifikatsverlängerung beantragen</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<%
					}
				%>
				<%
					if (uc.hasAcceptedRequest()) {
				%>
				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-default">
							<div id="step1_header_extend"
								class="panel-heading panelheader panelheader_completed">
								<table>
									<tr>
										<td><div id="step1_icon_extend"
												class="btn btn-default btn-circle panelicon messageicon_border_success">
												<i id="step1_iconfont_extend"
													class="fa fa-check messageicon_success"></i>
											</div></td>
										<td>Schritt 1: Zertifikatsverlängerung beantragen</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-default">
							<div id="step2_header_extend" class="panel-heading panelheader">
								<table>
									<tr>
										<td><div id="step2_icon_extend"
												class="btn btn-default btn-circle panelicon">
												<i id="step2_iconfont_extend"
													class="fa fa-check messageicon_default"></i>
											</div></td>
										<td>Schritt 2: Zertifikat aktivieren</td>
									</tr>
								</table>
							</div>
							<div id="step2_content_extend">
								<div id="step2_panel_body_extend" class="panel-body">
									<p>Geben Sie ein Passwort ein, mit welchem das Zertifikat
										gesichert werden soll und aktivieren Sie anschließend Ihr
										Zertifikat.</p>
									<p>
										<input type="password" id="password" name="password"
											onchange="validatePassword()"
											class="form-control passwort_field_request_extend"
											placeholder="Passwort" required>
									</p>
									<p>
										<input type="password" id="password_confirm"
											class="form-control passwort_field_request_extend"
											placeholder="Passwort wiederholen" required>
									</p>
								</div>
								<div class="panel-footer">
									<button id="step2_button_extend" onclick="onActivateClick()"
										type="button" class="btn simplecert_btn"
										style="display: inline">Aktivieren</button>
									<img id="loading_gif_extend" class="loading_gif"
										src="../img/general/loading.gif">
								</div>
							</div>
						</div>
					</div>
				</div>
				<%
					} else {
				%>
				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-default">
							<div id="step2_header_extend"
								class="panel-heading panelheader panel_next_step_or_loading">
								<table>
									<tr>
										<td><div id="step2_icon_extend"
												class="btn btn-default btn-circle panelicon">
												<i id="step2_iconfont_extend"
													class="fa fa-check messageicon_default"></i>
											</div></td>
										<td>Schritt 2: Zertifikat aktivieren</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<%
					}
				%>
				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-default">
							<div id="step3_header_extend"
								class="panel-heading panelheader panel_next_step_or_loading">
								<table>
									<tr>
										<td><div id="step3_icon_extend"
												class="btn btn-default btn-circle panelicon">
												<i id="step3_iconfont_extend"
													class="fa fa-check messageicon_default"></i>
											</div></td>
										<td>Schritt 3: Zertifikat herunterladen</td>
									</tr>
								</table>
							</div>
							<div id="step3_content_extend"
								class="panel_content_next_step_or_loading">
								<div class="panel-body">
									<p>Laden Sie nun ihr Zertifikat herunter und speichern Sie
										es, um es anschließend in Ihren Browser importieren zu können.</p>
								</div>
								<div class="panel-footer">
									<form action="../CrtDownload" method="get">
										<button type="submit" class="btn simplecert_btn">Herunterladen</button>
									</form>
								</div>
							</div>
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