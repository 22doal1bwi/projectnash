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
<script type="text/javascript" src="../js_custom/revoke_certificate.js"></script>
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

<title>simpleCert - Zertifikat widerrufen</title>

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
				boolean hasValidCertificate = uc.hasValidCertificate();

				if (hasValidCertificate) {
		%>

		<div id="page-wrapper">
			<div id="messagebar_revoke"
				class="alert messagebar_intern messagebar_hidden"></div>
			<!-- /.row -->
			<div id="page_content_revoke" class="page_content">
				<div class="row">
					<div class="col-lg-5 col-md-8">
						<div class="panel panel-red functiontile">
							<div id="header_revoke" class="panel-heading">Zertifikat
								widerrufen</div>
							<div id="content_revoke">
								<div id="panel_body_revoke" class="panel-body panel_revoke">
									<div class="form-group">
										<label>Grund des Widerrufs</label>
										<textarea id="textfield_revoke" class="form-control"
											placeholder="Bitte geben Sie hier den Grund Ihres Widerrufs an. Nach dem Klick auf 'Widerrufen' müssen Sie den Widerruf noch bestätigen."
											rows="4"></textarea>
									</div>
								</div>
								<div id="footer_revoke" class="panel-footer">
									<button id="button_revoke" onclick="revokeOnClick()"
										type="button" class="btn simplecert_btn">Widerrufen</button>
								</div>
							</div>
						</div>
						<div id="loading_gif_revoke" class="loading_gif loading_gif_revoke">
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
			} else {
					response.sendRedirect("home.jsp");
				}
			}
		%>
	
</body>
</html>