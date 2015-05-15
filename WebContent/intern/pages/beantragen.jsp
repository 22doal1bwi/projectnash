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
<script type="text/javascript"
	src="../js/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="../js/beantragen.js"></script>
<script type="text/javascript" src="../../js/_messagebar.js"></script>

<link rel="icon" type="image/png" sizes="32x32"
	href="../../img/simplecert/simplecert_favicon_32x32.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="../../img/simplecert/simplecert_favicon_96x96.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="../../img/simplecert/simplecert_favicon_16x16.png">
<link rel="icon" href="../../img/simplecert/simplecert_favicon.ico">

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

<!-- Custom styles for the messagebar -->
<link href="../../css/_messagebar.css" rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
	<div id="wrapper">

		<%
			//allow access only if session exists if not, redirect to login
			String sessionId = SessionController.checkForSessionId(request,
					response);

			switch (sessionId) {

			case "-1":
			case "0":
				response.sendRedirect("../../login.jsp");
				break;
			default:
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
				</form>
			</li>


		</ul>
		<!-- /.navbar-top-links -->

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li><img class="displayed"
						src="assets/img/simplecert_logo_text_128x128.png" style=" margin-top: 10px; margin-bottom: 15px" />
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
			<div id="messagebar_request"
				class="alert messagebar_intern messagebar_hidden"></div>
			<div class="row"></div>
			<!-- /.row -->
			<div id="page_content_request" class="page_content">
				<div class="row">
					<div class="col-lg-6 col-md-6">
						<div class="panel panel-default functiontile">
							<div id="step1_header_request" class="panel-heading panelheader">
								<button id="step1_icon_request" type="button"
									class="btn btn-default btn-circle panelicon">
									<i id="step1_iconfont_request" class="fa fa-check"></i>
								</button>Schritt 1: Zertifikat beantragen
							</div>
							<div id="step1_content_request">
								<div id="step1_panel_body_request" class="panel-body">
									<p>Beantragen Sie ihr persönliches Sicherheits-Zertifikat,
										damit Sie es im nächsten Schritt - nach erfolgreicher
										Genehmigung - herunterladen können.</p>
								</div>
								<div class="panel-footer">
									<button id="button_request" onclick="requestCertificate()"
										type="button" class="btn btn-danger">Beantragen</button>
								</div>
							</div>
						</div>
					</div>

					<!-- /.row -->

				</div>
				<div class="row">
					<div class="col-lg-6 col-md-6">
						<div class="panel panel-default">
							<div id="step2_header_request"
								class="panel-heading panelheader panel_next_step_or_loading">
								<button type="submit"
									class="btn btn-default btn-circle panelicon">
									<i class="fa fa-check"></i>
								</button>Schritt 2: Zertifikat herunterladen
							</div>
							<div id="step2_content_request">
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
						<div id="loading_gif_request" class="loading_gif">
							<img src="assets/img/loading.gif">
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