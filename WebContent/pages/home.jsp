<%@page import="java.util.concurrent.TimeUnit"%>
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
<!-- jQuery -->
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

				//certificate status
				boolean hasCertificate = false;
				boolean hasValidCertificate = false;
				String remainingTimeOfCert = null;
				int remainingDays = 0;

				//request status
				boolean hasAcceptedRequest = false;

				//setting status
				hasCertificate = uc.hasCertificate();
				hasValidCertificate = uc.hasValidCertificate();
				remainingTimeOfCert = uc.getRemainingTimeOfCertificate();
				remainingDays = uc.getRemainingTimeOfCertificate(TimeUnit.DAYS);
				hasAcceptedRequest = uc.hasAcceptedRequest();
	%>
	<div id="page-wrapper">
		<div id="messagebar_home"
			class="alert messagebar_intern messagebar_hidden"></div>
		<%
			if (hasValidCertificate) {
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
			$(document).ready(function() {
				buildAndShowMessageBar("WRN_CERT_EXPIRING", "messagebar_home")
			});
		</script>
		<%
				} // close of if (hasCertificate && hasValidCertificate)
			} else if (hasCertificate && !hasValidCertificate) {
		%>
		<script type="text/javascript">
			$(document).ready(function() {
				buildAndShowMessageBar("ERR_CERT_NOT_VALID", "messagebar_home")
			});
		</script>
		<%
			} else if (!hasCertificate) {
		%>
		<script type="text/javascript">
			$(document).ready(
					function() {
						window.buildAndShowMessageBar("ERR_CERT_NOT_EXISTING",
								"messagebar_home")
					});
		</script>
		<%
			} // close of last 'else if'
		%>
		<div class="row">
			<div class="col-lg-12"></div>
		</div>
		<div class="row">

			<div class="col-lg-4 col-md-6">
				
				<%
					if (!hasValidCertificate) {
				%>
				
				<a href="request_certificate.jsp">
				
				<%
 					} else {
 				%>
 				
 				<a href="show_certificate.jsp">
 				
 				<%
 					}
 				%>
						<div class="panel panel-green functiontile">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-file-text-o fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">

										<div class="text-top te" style="font-size: 24px">
											<%
												if (!hasValidCertificate) {
													if (hasAcceptedRequest) {
														out.print("Zertifikat aktivieren");
													} else {
														out.print("Zertifikat beantragen");
													}
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
				if (hasValidCertificate) {
			%>
			<div class="col-lg-4 col-md-6">
				<a href="extend_certificate.jsp">
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
				</a>
			</div>
			<div class="col-lg-4 col-md-6">
				<div class="panel panel-red functiontile">
					<a href="revoke_certificate.jsp">
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
					</a>
				</div>
			</div>
			<%
				} // close of if(hasCertificate)
			%>
		</div>
	</div>
	<%
		} // switch (sessionId)
	%>

</body>

</html>
