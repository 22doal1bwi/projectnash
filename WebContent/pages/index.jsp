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
<html lang="en">
<!-- jQuery -->
<script src="../bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript"
	src="../bower_components/jquery/dist/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="../js_custom/_messagebar.js"></script>
<script type="text/javascript" src="../js_custom/index.js"></script>
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
<meta name="description" content="">
<meta name="author" content="">

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

</head>

<body>

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
				boolean hasCertificate = false;
				boolean hasValidCertificate = false;
				boolean hasRequest = false;
				boolean allowedToDownloadCert = false;
				String remainingTimeOfCert = null;
				int remainingDays = 0;

				hasRequest = uc.hasRequest();
				allowedToDownloadCert = uc.allowedToDownloadCertificate();
				hasCertificate = uc.hasCertificate();
				hasValidCertificate = uc.hasValidCertificate();
				try {
					remainingTimeOfCert = uc
							.getRemainingTimeOfCertificate();
					remainingDays = uc.getRemainingTimeOfCertificate(TimeUnit.DAYS);
				} catch (Exception e) {
					remainingTimeOfCert = "[unbekannte Zeit]";
					e.printStackTrace();
				}
	%>

	<div id="wrapper">

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
			<!--  <a class="navbar-brand" href="index.html">SimpleCert</a>-->


		</div>
		<!-- /.navbar-header -->

		<ul class="nav navbar-top-links navbar-right">

			<!-- /.dropdown -->
			<li><a href="index.jsp"><%=uc.getFullName()%></a></li>
			<!-- 			<li><img class="displayed" src="assets/img/find_user.png" -->
			<!-- 				style="width: 20px;" /></li> -->
			<li><a href="index.jsp"><i class="fa fa-gear fa-2x"></i></a></li>
			<li>
				<form name="form_logout" action="../LogoutServlet" method="post">
					<a role="button" class="fa fa-sign-out fa-2x"
						style="text-decoration: none;" onclick="logout()"></a>
					<script type="text/javascript">
						function logout() {
							document.form_logout.submit()
						}
					</script>
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
					<li><a href="index.jsp" class="active"><i
							class="fa fa-home fa-fw navbaricon"></i>Home</a></li>
					<li><a href="beantragen.jsp"><i
							class="fa fa-file-text fa-fw navbaricon"></i> <%
 	if (!hasCertificate) {
 			out.print("Zertifikat beantragen");
 		} else {
 			out.print("Zertifikat anzeigen");
 		}
 %></a></li>
					<%
				if (hasCertificate) {					
			%>
					<li><a href="index.jsp"><i
							class="fa fa-history fa-fw navbaricon"></i>Zertifikat verlängern</a></li>
					<li><a href="index.jsp"><i
							class="fa fa-ban fa-fw navbaricon"></i>Zertifikat widerrufen</a></li>
					<li>
						<%
				} else {					
			%>				
					<li class="disabled"><a class="navitem_disabled"><i class="fa fa-history fa-fw navbaricon"></i>Zertifikat verlängern</a></li>						
					<li class="disabled"><a class="navitem_disabled"><i class="fa fa-ban fa-fw navbaricon"></i>Zertifikat widerrufen</a></li>
					<li>
						<%
				}					
			%>	
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<div id="page-wrapper">
			<div id="messagebar_home" class="alert messagebar_intern"></div>
			<%
				if (hasCertificate && hasValidCertificate) {
					if (remainingDays > 90) {
			%>
			<script type="text/javascript">
				$(document).ready(function() {
					buildAndShowMessageBar("SCS_CERT_VALID", "messagebar_home")
				});
			</script>
			<%
					} else {
			%>
			<script type="text/javascript">
				$(document).ready(
						function() {
							buildAndShowMessageBar("WRN_CERT_EXPIRING",
									"messagebar_home")
						});
			</script>
			<%
			} // close of if (hasCertificate && hasValidCertificate)
			} else if (hasCertificate && !hasValidCertificate) {
			%>
			<script type="text/javascript">
				$(document).ready(
						function() {
							buildAndShowMessageBar("ERR_CERT_NOT_VALID",
									"messagebar_home")
						});
			</script>
			<%
				} else if (!hasCertificate) {
			%>
			<script type="text/javascript">
				$(document).ready(
						function() {
							buildAndShowMessageBar("ERR_CERT_NOT_EXISTING",
									"messagebar_home")
						});
			</script>
			<%
				} // close of last 'else'		
			%>
			<div class="row">
				<div class="col-lg-12"></div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">

				<div class="col-lg-4 col-md-6">
					<a href="beantragen.jsp">
						<div class="panel panel-green functiontile">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-file-text-o fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">

										<div class="text-top te" style="font-size: 24px">
											<%
												if (!hasCertificate) {
														out.print("Zertifikat beantragen");
													} else {
														out.print("Zertifikat anzeigen");
													}
											%>

										</div>
									</div>
								</div>
							</div>
						</div>
					</a>
				</div>
				<%
					if (hasCertificate) {
				%>
				<div class="col-lg-4 col-md-6">
					<div class="panel panel-yellow functiontile">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-history fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">

									<div class="text-top te" style="font-size: 24px">Zertifikat
										verlängern</div>
									<div class="text-top te" style="font-size: 16px"><%=remainingTimeOfCert%>
										verbleibend
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-md-6">
					<div class="panel panel-red functiontile">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-ban fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">

									<div class="text-top te" style="font-size: 24px">Zertifikat
										widerrufen</div>
									<div class="text-top te" style="font-size: 16px">Missbrauch
										melden</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /.row -->
				<%
					} // close of if(hasCertificate)
				%>
			</div>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->
		<%
			} // switch (sessionId)
		%>
	
</body>

</html>
