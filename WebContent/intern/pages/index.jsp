<%@page import="de.projectnash.entities.User"%>
<%@page import="de.projectnash.entities.Session"%>
<%@page import="de.projectnash.databackend.SessionPersistenceService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SimpleCert</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Timeline CSS -->
    <link href="../dist/css/timeline.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="../bower_components/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

	<%
		//allow access only if session exists if not, redirect to login
		User user = null;
		String sessionID = null;
		if (session.getAttribute("emailAddress") == null) {
			response.sendRedirect("../../login.html");
		} else {
			String userName = null;
			
			Cookie[] cookies = request.getCookies();
			if (cookies == null) {
				
			} else {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("JSESSIONID")){
						sessionID = cookie.getValue();
					}
				}
				Session appSession = SessionPersistenceService.loadSession(sessionID);
				user = appSession.getUser();
			
				
				//close else blocks at end of body
	%>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
               <!--  <a class="navbar-brand" href="index.html">SimpleCert</a>-->
                    
					
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                 
                <!-- /.dropdown -->
				 <li>
                     <a href="index.html"><i class="fa fa-fw"></i> <%=user.getFirstName()%> <%=user.getLastName()%></a>
                 </li>
				 <li>
                    <img class="displayed" src="assets/img/find_user.png" style="width:20px;"/>
					
					</li>
                    <li>
                        <a href="index.html"><i class="fa fa-gear fa-2x"></i></a>
                    </li>
					<li>
					<form action="../../LogoutServlet" method="post">
                        <button class="fa fa-sign-out fa-2x" type="submit"></button>
                    </form>
                    </li>
                
                
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
					<li>
                    <img class="displayed" src="assets/img/cleanCert_logo_key_red.png" style="width:160px;"/>
					
					</li>
                        <li>
                            <a href="index.html"><i class="fa fa-home fa-fw"></i> Home</a>
                        </li>
              			 <li>
                            <a href="beantragen_1.html"><i class="fa fa-file-text-o fa-fw"></i> Zertifikat beantragen</a>
                           
                        </li>
                        <li>
                            <a href="index.html"><i class="fa fa-history fa-fw"></i> Zertifikat verlängern</a>
                        </li>
                        <li>
                            <a href="index.html"><i class="fa fa-ban fa-fw"></i> Zertifikat widerrufen</a>
                        </li>
                        <li>
                           
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>

        <div id="page-wrapper">
		<div class="alert alert-danger">
				<button type="button" class="btn btn-default btn-circle btn-lg"><i class="fa fa-times"></i>
                            </button>
                 Sie besitzen kein gültiges Zertifikat!
            </div>
            <div class="row">
                <div class="col-lg-12">
				
                   
					
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
                                    
                                    <div class="text-top te" style="font-size:24px">Zertifikat beantragen</div>
                                </div>
                            </div>
                        </div>
                        <a href="beantragen_1.html">
                            <div class="panel-footer">
                                <span class="pull-left">Details ansehen</span>
                                <span class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
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

    <!-- jQuery -->
    <script src="../bower_components/jquery/dist/jquery.min.js"></script>

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
	<% }} //close of else block for session check %> 
</body>

</html>