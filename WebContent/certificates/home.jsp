<%@page import="de.projectnash.application.RequestLogic"%>
<%@page import="de.projectnash.frontend.controllers.RequestController"%>
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
<html lang="en">
<script src="../bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript"
	src="../bower_components/jquery/dist/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="../js_custom/_messagebar.js"></script>
<script type="text/javascript" src="../js_custom/home.js"></script>
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

<title>simpleCert - Home</title>

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

<body class="body">
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
				RequestController rc = new RequestController();
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
					<li><a class="active" href="home.jsp"><i
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
			<div id="messagebar_home"
				class="alert messagebar_intern messagebar_hidden"></div>
			<!-------------------------------<BEGIN> INITIALIZE MESSAGEBAR---------------------------------->
			<%
				if (uc.hasValidCertificate()) {
							if (uc.getRemainingTimeOfCertificate(TimeUnit.DAYS) > Constants.TIMEFRAME_FOR_EXTENSION) {
			%>
			<script type="text/javascript">
				$(document).ready(function() {
					window.setTimeout(function() {
						$("#page_content_home").addClass("page_content_move_down")
						buildAndShowMessageBar("SCS_CERT_VALID", "messagebar_home")
					}, 250);
				});
			</script>
			<%
				} else {
			%>
			<script type="text/javascript">
				$(document).ready(function() {
					window.setTimeout(function() {
						$("#page_content_home").addClass("page_content_move_down")
						buildAndShowMessageBar("WRN_CERT_EXPIRING", "messagebar_home")
					}, 250);
				});
			</script>
			<%
				}
						} else if (uc.hasCertificate() && !uc.hasValidCertificate()) {
			%>
			<script type="text/javascript">
				$(document).ready(function() {
					window.setTimeout(function() {
						$("#page_content_home").addClass("page_content_move_down")
						buildAndShowMessageBar("ERR_CERT_NOT_VALID", "messagebar_home")
					}, 250);
				});
			</script>
			<%
				} else if (!uc.hasCertificate()) {
			%>
			<script type="text/javascript">
				$(document).ready(function() {
					window.setTimeout(function() {
						$("#page_content_home").addClass("page_content_move_down")
						window.buildAndShowMessageBar("ERR_CERT_NOT_EXISTING", "messagebar_home")
					}, 250);
				});
			</script>
			<%
				} // close of last 'else if'
			%>
			<!-------------------------------<END> INITIALIZE MESSAGEBAR---------------------------------->
			<div id="page_content_home" class="page_content">
				<div class="row">
					<div class="col-lg-4 col-md-6">
						<!-------------------------------<BEGIN> SET LINK FOR THE FIRST TILE--------------------------------->
						<%
							if (!uc.hasValidCertificate()) {
						%>

						<a href="request_certificate.jsp"> <%
 	} else {
 %> <a href="show_certificate.jsp"> <%
 	}
 %> <!-------------------------------<END> SET LINK FOR THE FIRST TILE--------------------------------->
								<div id="panel1_h" class="panel panel-green">
									<div id="panel1" class="panel-heading">
										<div class="row">
											<div class="col-xs-3">
												<i class="fa fa-file-text-o fa-5x"></i>
											</div>
											<div class="col-xs-9 text-right">

												<div class="huge">
													<!-------------------------------<BEGIN> SET MAIN TEXT FOR THE 'REQUEST'/'SHOW'-TILE------------------------------->
													<%
														if (!uc.hasValidCertificate()) {
																out.print("Zertifikat beantragen");
															} else {
																out.print("Zertifikat anzeigen");
															}
													%>
													<!-------------------------------<END> SET MAIN TEXT FOR THE 'REQUEST'/'SHOW'-TILE------------------------------->
												</div>
												<!-------------------------------<BEGIN> SET ACTION TEXT FOR THE 'REQUEST'-TILE-------------------------------->
												<%
													if (!uc.hasValidCertificate() && uc.hasAcceptedRequest()) {
												%>
												<div class="action_info">
													<i class="fa fa-play action_arrow bounce"></i>Jetzt
													aktivieren
												</div>
												<%
													} else if (!uc.hasValidCertificate() && uc.hasRequest()) {
												%>
												<div class="action_info">Ihr Antrag wird bearbeitet</div>
												<%
													}
												%>
												<!-------------------------------<END> SET ACTION TEXT FOR THE 'REQUEST'-TILE-------------------------------->
											</div>
										</div>
									</div>
								</div>
						</a>
					</div>
					<!-------------------------------<BEGIN> SET ACTION TEXT FOR THE FIRST TILE-------------------------------->
					<!-----Close the row to place the admin-tiles in a new row if user is admin and has no valid certificate----->
					<%
						if (uc.isAdmin() && !uc.hasValidCertificate()) {
					%>
				</div>
				<%
					}
						if (uc.hasValidCertificate()) {
				%>
				<div class="col-lg-4 col-md-6">
					<a href="extend_certificate.jsp">
						<div id="panel2_h" class="panel panel-yellow">
							<div id="panel2" class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-history fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">Zertifikat verlängern</div>
										<!-------------------------------<BEGIN> SET ACTION TEXT FOR THE 'EXTEND'-TILE------------------------------->
										<%
											if (!uc.hasAcceptedRequest()
															&& uc.getRemainingTimeOfCertificate(TimeUnit.DAYS) > Constants.TIMEFRAME_FOR_EXTENSION) {
										%>
										<div class="action_info"><%=uc.getRemainingTimeOfCertificate()%>
											verbleibend
										</div>
										<%
											} else if (!uc.hasAcceptedRequest()
															&& uc.getRemainingTimeOfCertificate(TimeUnit.DAYS) <= Constants.TIMEFRAME_FOR_EXTENSION
															&& !uc.hasRequest()) {
										%>
										<div class="action_info">
											<i class="fa fa-play action_arrow bounce"></i>Nur noch
											<%=uc.getRemainingTimeOfCertificate()%>
											verbleibend
										</div>
										<%
											} else if (uc.hasAcceptedRequest()) {
										%>
										<div class="action_info">
											<i class="fa fa-play action_arrow bounce"></i>Jetzt
											aktivieren
										</div>
										<%
											} else if (!uc.hasAcceptedRequest()
															&& uc.getRemainingTimeOfCertificate(TimeUnit.DAYS) <= Constants.TIMEFRAME_FOR_EXTENSION
															&& uc.hasRequest()) {
										%>
										<div class="action_info">Ihr Antrag wird bearbeitet</div>
										<%
											}
										%>
										<!-------------------------------<END> SET ACTION TEXT FOR THE 'EXTEND'-TILE-------------------------------->
									</div>
								</div>
							</div>
						</div>
					</a>
				</div>
				<div class="col-lg-4 col-md-6">
					<div id="panel3_h" class="panel panel-red">
						<a href="revoke_certificate.jsp">
							<div id="panel3" class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-ban fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">Zertifikat widerrufen</div>
										<div class="action_info">Missbrauch melden</div>
									</div>
								</div>
							</div>
						</a>
					</div>
				</div>
			</div>
			<%
				} // close of if(hasValidCertificate)
			%>
			<%
				if (uc.isAdmin()) {
			%>
			<hr class="horizontal_divider">
			<div class="row">
				<div class="col-lg-4 col-md-6">
					<div id="panel4_h" class="panel panel-primary">
						<a href="manage_requests.jsp">
							<div id="panel4" class="panel-heading panel_primary_heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-files-o fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">Anträge verwalten</div>
										<%
											if (rc.getNumberOfWaitingRequests() > 1) {
										%>
										<div class="action_info">
											<i class="fa fa-play action_arrow bounce"></i><%=rc.getNumberOfWaitingRequests()%>
											Anträge zu bearbeiten
										</div>
										<%
											} else if (rc.getNumberOfWaitingRequests() == 1) {
										%>
										<div class="action_info">
											<i class="fa fa-play action_arrow bounce"></i><%=rc.getNumberOfWaitingRequests()%>
											Antrag zu bearbeiten
										</div>
										<%
											} else {
										%>
										<div class="action_info">Keine neuen Anträge</div>
										<%
											}
										%>
									</div>
								</div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-4 col-md-6">
					<div id="panel5_h" class="panel panel-primary">
						<a href="manage_users.jsp">
							<div id="panel5" class="panel-heading panel_primary_heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-users fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">Benutzer verwalten</div>
										<div class="action_info"></div>
									</div>
								</div>
							</div>
						</a>
					</div>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>
	<%
		} // switch (sessionId)
	%>

</body>

</html>
