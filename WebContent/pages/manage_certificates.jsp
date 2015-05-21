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
	<div id="wrapper">
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

					if (hasValidCertificate) {

						CertificateController cc = new CertificateController(
								sessionId);
		%>	
		<div id="page-wrapper">
			<div id="messagebar_show"
				class="alert messagebar_intern messagebar_hidden"></div>
			<div class="row"></div>
			<div id="page_content_settings" class="page_content">
				<div class="row">
					<div class="col-lg-4 col-md-8">
						<h1 class="page-header">Anträge verwalten [GEEKMODE]</h1>
					</div>
				</div>
				<div class="row">
					<script type="text/javascript">
						$(document)
								.ready(
									function() {
											$("#Requests")
													.dataTable(
															{														
																"sPaginationType" : "full_numbers",
																"ajax" : {
																	"type" : "POST",
																	"url" : "../AdminRequestServlet"
																},
																"columns" : [
																		{
																			"data" : "firstName"
																		},
																		{
																			"data" : "lastName"
																		},
																		{
																			"data" : "department"
																		},
																		{
																			"data" : "personalId"
																		},
																		{
																			"data" : "emailAddress"
																		},
																		{
																			"data" : "requestCreationDate"
																		},
																		{
																			"data" : "status"
																		} ],
																"fnDrawCallback" : function() {
																	/* Init DataTable */
																	var oTable = $(
																			'#Requests')
																			.dataTable();
																	$(
																			'#Requests tbody td:nth-child(7)')
																			.editable(
																					"../AdminUpdateServlet",
																					{
																						indicator : '<img src="../img/general/loading.gif">',
																						data : "{'WAITING':'Warten','ACCEPTED':'Genehmigen','DENIED':'Ablehnen'}",
																						tooltip : "Wählen Sie den Status...",
																						loadtext : "lädt...",
																						type : "select",
																						onblur : 'submit',
																						submitdata : function() {
																							var updatedUser = {}, aPos = oTable
																									.fnGetPosition(this);
																							return {
																								emailAddress : oTable
																										.fnGetData(aPos[0]).emailAddress
																							}
																						}
																					})
																}
															});
										});
					</script>
					<div class="dataTable_wrapper">
						<table class="table table-striped table-bordered table-hover"
							id="Requests">
							<thead>
								<tr>
									<th>Vorname</th>
									<th>Nachname</th>
									<th>Abteilung</th>
									<th>Personalnummer</th>
									<th>E-Mail-Adresse</th>
									<th>Beantragunsdatum</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<%
			} else {
					response.sendRedirect("home.jsp"); // TODO
				}
			}
		%>
	
</body>
</html>