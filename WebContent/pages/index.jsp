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

				if (uc.hasValidCertificate()) {
					response.sendRedirect("beantragen_end.jsp");
				} else {
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
			<li><a href="index.jsp"><%=uc.getFirstName()%> <%=uc.getLastName()%>
					(<%=uc.getPersonalId()%>)</a></li>
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
			<div class="alert messagebar_intern"></div>
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

										<div class="text-top te" style="font-size: 24px">Zertifikat
											beantragen</div>
									</div>
								</div>
							</div>
						</div>
					</a>
				</div>


				<!-- /.row -->

			</div>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->
		<%
			}
			} //close of else block for session check
		%>
	
</body>

</html>
