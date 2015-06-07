<%@page import="de.projectnash.frontend.controllers.UserController"%>
<%@page import="de.projectnash.frontend.controllers.SessionController"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, height=device-height, initial-scale=1">
<script src="../bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript"
	src="../bower_components/jquery/dist/jquery.i18n.properties-1.0.9.js"></script>
<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js_custom/frame.js"></script>
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
				boolean hasValidCertificate = false;
				//user status
				boolean hasAcceptedRequest = false;
				boolean isAdmin = false;
				//setting status
				hasAcceptedRequest = uc.hasAcceptedRequest();
				hasValidCertificate = uc.hasValidCertificate();
				isAdmin = uc.isAdmin();
	%>
	<div id="wrapper">
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
		<ul class="nav navbar-top-links navbar-right">

			<%
				if (isAdmin) {
			%><li><div class="admin_flag_name">ADMIN</div></li>
			<%
				}
			%>
			<li><div class="name"><%=uc.getFullName()%></div></li>
			<li><a id="a_settings" onclick="setFrameSrc('settings')" href="settings.jsp" target="app_content"><i
					class="fa fa-gear fa-2x"></i></a></li>
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
		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li><img class="displayed"
						src="../img/simplecert/simplecert_logo_text_128x128.png"
						style="margin-top: 10px; margin-bottom: 15px" /></li>
					<li><a id="a_home" class="active"
						onclick="location.reload(true)" href="home.jsp"
						target="app_content"><i class="fa fa-home fa-fw navbaricon"></i>Home</a></li>
					<%
						if (!hasValidCertificate) {
					%>
					<li><a id="a_req_show_certificate" onclick="setFrameSrc('request_certificate')" href="request_certificate.jsp" target="app_content"><i
							class="fa fa-file-text fa-fw navbaricon"></i>Zertifikat
							beantragen</a></li>
					<%
						} else {
					%>
					<li><a id="a_req_show_certificate" onclick="setFrameSrc('show_certificate')" href="show_certificate.jsp" target="app_content"><i
							class="fa fa-file-text fa-fw navbaricon"></i>Zertifikat anzeigen</a></li>
					<%
						}
					%>
					<%
						if (hasValidCertificate) {
					%>
					<li><a id="a_extend_certificate" onclick="setFrameSrc('extend_certificate')" href="extend_certificate.jsp" target="app_content"><i
							class="fa fa-history fa-fw navbaricon"></i>Zertifikat verlängern</a></li>
					<li><a id="a_revoke_certificate" onclick="setFrameSrc('revoke_certificate')" href="revoke_certificate.jsp" target="app_content"><i
							class="fa fa-ban fa-fw navbaricon"></i>Zertifikat widerrufen</a></li>					
						<%
							} else {
						%>
					
					<li class="disabled"><a id="a_extend" class="navitem_disabled"><i
							class="fa fa-history fa-fw navbaricon"></i>Zertifikat verlängern</a></li>
					<li class="disabled"><a id="a_revoke" class="navitem_disabled"><i
							class="fa fa-ban fa-fw navbaricon"></i>Zertifikat widerrufen</a></li>
					<%
						}
							if (isAdmin) {
					%>
					<li style="height:25px;"></li>
					<li><a id="a_manage_requests" onclick="setFrameSrc('manage_requests')" href="manage_requests.jsp" target="app_content"><i
							class="fa fa-files-o fa-fw navbaricon"></i>Anträge verwalten
							<div class="admin_flag_nav"></div></a></li>
					<li><a id="a_manage_users" onclick="setFrameSrc('manage_users')" href="manage_users.jsp" target="app_content"><i
							class="fa fa-users fa-fw navbaricon"></i>Benutzer verwalten
							<div class="admin_flag_nav"></div></a></li>
					<%
						}
					%>					
				</ul>
			</div>
		</div>
		</nav>
	</div>
	<iframe id="app_content" onload="document.title=this.contentWindow.document.title; setNavItemActive()"
		name="app_content" style="height: 100%; border: none;" src="home.jsp"
		width="100%">
		<p>Your browser does not support iframes.</p>
	</iframe>

	<%
		} // switch (sessionId)
	%>
</body>
</html>