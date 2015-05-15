<%@page import="de.projectnash.frontend.controllers.UserController"%>
<%@page import="de.projectnash.frontend.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<!-- jQuery -->
<script src="../bower_components/jquery/dist/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>


<!-- Custom Theme JavaScript -->
<script src="../bower_components/bootstrap/dist/js/sb-admin-2.js"></script>

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>SimpleCert</title>

<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="../bower_components/bootstrap/dist/css/sb-admin-2.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

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
				<li><a href="index.jsp"><i class="fa fa-fw"></i><%=uc.getFirstName()%>
						<%=uc.getLastName()%> (<%=uc.getPersonalId()%>)</a></li>
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
			<!-- /.dropdown-tasks -->

			<!-- /.navbar-top-links -->

			<div class="navbar-default sidebar" role="navigation">
				<div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
						<li><img class="displayed"
							src="../img/simplecert/simplecert_logo_text_128x128.png"
							style="margin-top: 10px; margin-bottom: 15px" /></li>
						<li><a href="index.jsp"><i class="fa fa-home fa-fw"></i>
								Home</a></li>
						<li><a href="#"><i class="fa fa-file-text-o fa-fw"></i>
							Zertifikat anzeigen</a></li>
						<li><a href="index.jsp"><i class="fa fa-history fa-fw"></i>
								Zertifikat verlängern</a></li>
						<li><a href="index.jsp"><i class="fa fa-ban fa-fw"></i>
								Zertifikat widerrufen</a></li>
						<li>
				</div>
				<!-- /.sidebar-collapse -->
			</div>
			<!-- /.navbar-static-side -->
		</nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<div class="col-lg-12 alert alert-success">
						<!-- <button type="button" class="btn btn-warning btn-circle btn-lg"><i class="fa fa-times"></i>
                            </button> -->
						<button type="button" class="btn btn-default btn-circle btn-lg">
							<i class="fa fa-check"></i>
						</button>
						Sie besitzen ein gültiges Zertifikat!
					</div>


				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">

				<div class="col-lg-4 col-md-6">
					<div class="panel panel-green">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-file-text-o fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">

									<div class="text-top te" style="font-size: 24px">Zertifikat
										anzeigen</div>
								</div>
							</div>
						</div>
						<a href="beantragen_1.html">
							<div class="panel-footer">
								<span class="pull-left">Details ansehen</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-4 col-md-6">
					<div class="panel panel-yellow">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-history fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">

									<div class="text-top te" style="font-size: 24px">Zertifikat
										verlängern</div>
									<div class="text-top te" style="font-size: 12px">in 3
										Monaten möglich</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left">Details ansehen</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-4 col-md-6">
					<div class="panel panel-red">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-ban fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">

									<div class="text-top te" style="font-size: 24px">Zertifikat
										widerrufen</div>
									<div class="text-top te" style="font-size: 12px">Missbrauch
										melden</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left">Details ansehen</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>

				<!-- /.row -->

			</div>
			<!-- /#page-wrapper -->

		</div>
		<!-- /#wrapper -->
		<%
			}
		%>
	
</body>

</html>
