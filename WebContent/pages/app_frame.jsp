<%@page import="de.projectnash.frontend.controllers.UserController"%>
<%@page import="de.projectnash.frontend.controllers.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1">

<script type="text/javascript" src="../js_custom/frame.js"></script>
<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="../img/simplecert/simplecert_favicon_32x32.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="../img/simplecert/simplecert_favicon_96x96.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="../img/simplecert/simplecert_favicon_16x16.png">
<link rel="icon" href="../img/simplecert/simplecert_favicon.ico">

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
			<li><div class="name"><%=uc.getFullName()%></div></li>
			<!-- 			<li><img class="displayed" src="assets/img/find_user.png" -->
			<!-- 				style="width: 20px;" /></li> -->
			<li><a href="settings.jsp" target="app_content"><i class="fa fa-gear fa-2x"></i></a></li>
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
		<!-- /.navbar-top-links -->

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li><img class="displayed"
						src="../img/simplecert/simplecert_logo_text_128x128.png"
						style="margin-top: 10px; margin-bottom: 15px" /></li>
					<li><a href="home.jsp" target="app_content"><i
							class="fa fa-home fa-fw navbaricon"></i>Home</a></li>
					<%
						if (!hasCertificate) {
					%>
					<li><a href="request_certificate.jsp" target="app_content"><i
							class="fa fa-file-text fa-fw navbaricon"></i>Zertifikat
							beantragen</a></li>
					<%
						} else {
					%>
					<li><a href="show_certificate.jsp" target="app_content"><i
							class="fa fa-file-text fa-fw navbaricon"></i>Zertifikat anzeigen</a></li>
					<%
						}
					%>
					<%
						if (hasCertificate) {
					%>
					<li><a href="extend_certificate.jsp" target="app_content"><i
							class="fa fa-history fa-fw navbaricon"></i>Zertifikat verlängern</a></li>
					<li><a href="revoke_certificate.jsp" target="app_content"><i
							class="fa fa-ban fa-fw navbaricon"></i>Zertifikat widerrufen</a></li>
					<li>
						<%
							} else {
						%>
					
					<li class="disabled"><a class="navitem_disabled"><i
							class="fa fa-history fa-fw navbaricon"></i>Zertifikat verlängern</a></li>
					<li class="disabled"><a class="navitem_disabled"><i
							class="fa fa-ban fa-fw navbaricon"></i>Zertifikat widerrufen</a></li>
					
						<%
							}
						%>
				</ul>
				
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		<!-- /.navbar-static-side --> </nav>
		
	</div>

<iframe onload="document.title=this.contentWindow.document.title" name="app_content" style="height: 100%; border: none; seamless" src="home.jsp" width="100%">
  <p>Your browser does not support iframes.</p>
</iframe>
	
<%
	} // switch (sessionId)
%>
</body>
</html>