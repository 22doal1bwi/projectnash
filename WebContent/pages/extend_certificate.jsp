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
	<div id="page-wrapper">
		<div id="messagebar_extend"
			class="alert messagebar_intern messagebar_hidden"></div>
		<%
			if (remainingDays > 800) {
		%>
		<script type="text/javascript">
			$(document).ready(
					function() {
						buildAndShowMessageBar("WRN_CERT_EXTENSION_IMPOSSIBLE",
								"messagebar_extend")
					});
		</script>
		<%
			}
		%>
		<div id="page_content_extend" class="page_content">
			<%
				if (remainingDays <= 800) {
			%>
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
			<%
				} else {
			%>
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
			<%
				}
			%>
			<%
				if (uc.hasRequest() && uc.allowedToDownloadCertificate()) {
			%>
			<div class="row">
				<div class="col-lg-5 col-md-8">
					<div class="panel panel-default">
						<div id="step2_header_extend" class="panel-heading panelheader">
							<button id="step2_icon_extend" type="button"
								class="btn btn-default btn-circle panelicon">
								<i id="step2_iconfont_extend" class="fa fa-check"></i>
							</button>
							Schritt 2: Zertifikat aktivieren
						</div>
						<div id="step2_content_extend">
							<div id="step2_panel_body_extend" class="panel-body">
								<p>Geben Sie ein Passwort ein, mit welchem das Zertifikat
									gesichert werden soll und aktivieren Sie anschließend Ihr
									Zertifikat.</p>
								<input type="password" id="password" name="password"
									class="form-control passwort_field_request_extend" placeholder="Passwort" required>
							</div>
							<div class="panel-footer">
								<button id="step2_button_extend" onclick="activateCertificate()"
									type="button" class="btn simplecert_btn">Aktivieren</button>
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
							<button id="step2_icon_extend" type="button"
								class="btn btn-default btn-circle panelicon">
								<i id="step2_iconfont_extend" class="fa fa-check"></i>
							</button>
							Schritt 2: Zertifikat aktivieren
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
							<button type="submit"
								class="btn btn-default btn-circle panelicon">
								<i class="fa fa-check"></i>
							</button>
							Schritt 3: Zertifikat herunterladen
						</div>
						<div id="step3_content_extend"
							class="panel_content_next_step_or_loading">
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
					<div id="loading_gif_extend" class="loading_gif">
						<img src="../img/general/loading.gif">
					</div>
				</div>
			</div>
		</div>
	</div>
	<%
		} else {
				response.sendRedirect("app_frame.jsp");
			}
		}
	%>
</body>
</html>