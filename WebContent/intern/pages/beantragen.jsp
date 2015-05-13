<%@page import="de.projectnash.frontend.SessionController"%>
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
<link rel="icon" type="image/png" sizes="32x32"
	href="../../img/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="../../img/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="../../img/favicon-16x16.png">
<link rel="icon" href="../../img/favicon.ico">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>simpleCert - Zertifikat beantragen</title>

<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- Timeline CSS -->
<link href="../dist/css/timeline.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="../bower_components/morrisjs/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- Intern Pages CSS -->
<link href="../../css/intern.css" rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#step2_content").hide()
		$("#loading_gif").hide()
	});
</script>

<body>
	<div id="wrapper">

		<%
			//allow access only if session exists if not, redirect to login
			String sessionId = SessionController.checkForSessionId(request,
					response);

			switch (sessionId) {

				case "-1" :
				case "0" :
					response.sendRedirect("../../login.jsp");
					break;
				default :
					UserController uc = new UserController(sessionId);
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
			<li><a href="index.jsp"><%=uc.getFirstName()%> <%=uc.getLastName()%>
					(<%=uc.getPersonalId()%>)</a></li>
			<!-- 			<li><img class="displayed" src="assets/img/find_user.png" -->
			<!-- 				style="width: 20px;" /></li> -->
			<li><a href="index.jsp"><i class="fa fa-gear fa-2x"></i></a></li>
			<li>
				<form name="form_logout" action="../../LogoutServlet" method="post">
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
						src="assets/img/cleanCert_logo_key_red.png" style="width: 160px;" />
					</li>
					<li><a href="index.jsp"><i
							class="fa fa-home fa-fw navbaricon"></i>Home</a></li>
					<li><a href="beantragen.jsp"><i
							class="fa fa-file-text-o fa-fw navbaricon"></i>Zertifikat
							beantragen</a></li>
					<li><a href="index.jsp"><i
							class="fa fa-history fa-fw navbaricon"></i>Zertifikat verlängern</a></li>
					<li><a href="index.jsp"><i
							class="fa fa-ban fa-fw navbaricon"></i>Zertifikat widerrufen</a></li>
					<li>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>

		<div id="page-wrapper">
			<div id="messagebar" class="alert messagebar messagebar_hidden">
			</div>
			<div class="row"></div>
			<!-- /.row -->
			<div id="page_content" class="page_content">
				<div class="row">
					<div id="step1_whole_panel" class="col-lg-6 col-md-6">
						<div class="panel panel-default functiontile">
							<div id="step1_header" class="panel-heading panelheader">
								<button id="step1_icon" type="button"
									class="btn btn-default btn-circle panelicon">
									<i id="step1_iconfont" class="fa fa-check"></i>
								</button>
								Schritt 1: Zertifikat beantragen
							</div>
							<div id="step1_content">
								<div id="panel_body_step1" class="panel-body">
									<p>Beantragen Sie ihr persönliches Sicherheits-Zertifikat,
										damit Sie es im nächsten Schritt - nach erfolgreicher
										Genehmigung - herunterladen können.</p>
								</div>
								<div class="panel-footer">
									<button id="request_button" onclick="requestCertificate()" type="button"
										class="btn btn-danger" id="requestCertificateButton">Beantragen</button>

									<script type="text/javascript">
										function requestCertificate() {	
											setLoading()
											$
													.ajax({
														url : '../../CertificateServlet',
														type : 'POST',
														dataType : 'json',
														timeout : 8000,
														success : function(data) {
															if (data.validSession
																	&& data.createdCertificate) {
																requestSuccessful()
															} else if (data.validSession
																	&& data.createdCertificate === false) {
																requestNotSuccessful()
															} else {
																window
																		.setTimeout(
																				"redirect()",
																				1000);
															}
														},
														error : function() {
															showMessageBar(
																	"connection",
																	"error")
														}
													})
										}
										
										function setLoading () {
											$("#panel_body_step1").addClass("panel_next_step_or_loading")
											$("#loading_gif").fadeIn()
											$("#request_button").addClass("disabled")
											$("#request_button").removeAttr("onclick")
										}
										
										function unsetLoading () {	
											$("#panel_body_step1").removeClass("panel_next_step_or_loading")
											$("#loading_gif").fadeOut()	
											$("#request_button").removeClass("disabled")
											$("#request_button").attr("onclick", "requestCertificate()")
										}

										function requestSuccessful() {
											unsetLoading()
											showMessageBar("", "success")
											$("#messagebar").removeClass(
													"messagebar_hidden")
											$("#page_content").addClass(
													"page_content_move_down")
											window
													.setTimeout(
															function() {
																$("#messagebar")
																		.addClass(
																				"messagebar_hidden")
																$(
																		"#page_content")
																		.removeClass(
																				"page_content_move_down")
																$(
																		"#step1_content")
																		.slideUp()
																$("#step1_icon")
																		.addClass(
																				"messageicon_border_success")
																$(
																		"#step1_iconfont")
																		.addClass(
																				"messageicon_success")
																$(
																		"#step1_header")
																		.addClass(
																				"panelheader_completed")
																$(
																		"#step2_content")
																		.slideDown()
																$(
																		"#step2_header")
																		.addClass(
																				"panelheader_set_current_step")
															}, 3000);

										}

										function requestNotSuccessful() {
											unsetLoading()
											showMessageBar("request", "error")
											$("#messagebar").removeClass(
													"messagebar_hidden")
											$("#page_content").addClass(
													"page_content_move_down")

											window
													.setTimeout(
															function() {
																$("#messagebar")
																		.addClass(
																				"messagebar_hidden")
																$(
																		"#page_content")
																		.removeClass(
																				"page_content_move_down")
															}, 3000);
										}

										function showMessageBar(type,
												kindOfMessage) {
											var message, messagebar = document
													.getElementById('messagebar'), messagebarContent, styleMessagebar, iconBorderColor, iconColor, iconType

											switch (kindOfMessage) {

											case "success":
												iconBorderColor = "#3c763d"
												iconColor = "#5cb85c"
												iconType = "fa-check"
												styleMessagebar = "alert-success"
												message = "Sie haben Ihr Zertifikat erfolgreich beantragt."
												break

											case "error":
												iconBorderColor = "#843534"
												iconColor = "#d9534f"
												iconType = "fa-times"
												styleMessagebar = "alert-danger"

												switch (type) {

												case "request":
													message = "Ihr Zertifikat konnte nicht beantragt werden."
													break

												case "connection":
													message = "Der Server antwortet nicht."
													break
												}
												break
											}

											messagebarContent = '<div class="btn btn-default btn-circle messageicon" style="cursor: context-menu; border-color:' + iconBorderColor + ';"> <i id="messagebar_icon" style="color:' + iconColor + '; line-height: 17px;" class="fa ' + iconType + '"></i> </div>'
													+ message
											messagebar.innerHTML = messagebarContent;
											cleanMessageBar()
											messagebar.classList
													.add(styleMessagebar)
										}

										// Method which removes any style classes from the massagebar
										function cleanMessageBar() {
											messagebar = document
													.getElementById('messagebar')
											if ($("#messagebar").hasClass(
													"alert-danger")) {
												messagebar.classList
														.remove("alert-danger")
											}
											if ($("#messagebar").hasClass(
													"alert-success")) {
												messagebar.classList
														.add("alert-success")
											}
										}

										// Method which is called for redirection after logout
										function redirect() {
											location.href = 'index.jsp';
										}
									</script>
								</div>
							</div>
						</div>						
					</div>

					<!-- /.row -->

				</div>
				<div class="row">
					<div class="col-lg-6 col-md-6">
						<div class="panel panel-default">
							<div id="step2_header"
								class="panel-heading panelheader panel_next_step_or_loading">
								<button type="submit"
									class="btn btn-default btn-circle panelicon">
									<i class="fa fa-check"></i>
								</button>
								Schritt 2: Zertifikat herunterladen
							</div>
							<div id="step2_content">
								<div class="panel-body">
									<p>Laden Sie nun ihr Zertifikat herunter und speichern Sie
										es, um es anschließend in ihren Browser importieren zu können.</p>
								</div>
								<div class="panel-footer">
									<form action="../../CrtDownload" method="get">
										<button type="submit" class="btn btn-danger">Herunterladen</button>
									</form>
								</div>
							</div>

						</div>
						<div id="loading_gif" class="loading_gif">
							<img
								src="http://navajotimes.com/wordpress/wp-content/themes/novomag-theme/images/loading.gif">
						</div>
					</div>					
				</div>
				<!-- /.row -->

			</div>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->



		<!-- Bootstrap Core JavaScript -->
		<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

		<!-- Metis Menu Plugin JavaScript -->
		<script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

		<!-- Morris Charts JavaScript -->
		<script src="../bower_components/raphael/raphael-min.js"></script>
		<script src="../bower_components/morrisjs/morris.min.js"></script>
		<script src="../js/morris-data.js"></script>

		<!-- Custom Theme JavaScript -->
		<script src="../dist/js/sb-admin-2.js"></script>
		<%
			}
		%>
	
</body>
</html>