<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="de">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<link rel="icon" href="img/favicon.ico">
</head>
<title>simpleCert - Login</title>
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/signin.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<style>
#login_container {
	margin: 80px auto;
	width: 420px;
	height: auto;
	background-color: #F9F9F9;
	text-align: center;
}

.btn-red {
	color: #fff;
	background-color: #C03A2A;
	border-color: #C03A2A;
	outline: none;
}

.btn-red:hover, .btn-red:focus {
	color: #fff;
	background-color: #A83A2A;
	border-color: #A83A2A;
	outline: none;
	transition: background-color .4s ease;
}

.btn-primary {
	color: #2C2E2F;
	background-color: #C8C8C8;
	border-color: #C8C8C8;
	outline: none;
}

.btn-primary:hover, .btn-primary:focus {
	color: #2C2E2F;
	background-color: #B8B8B8;
	border-color: #B8B8B8;
	outline: none;
	transition: background-color .4s ease;
}

#btn-small {
	width: 420px;
}

</style>

</head>

<body>

	<div class="container">
		<div id="login_container">
			<form class="form-signin" action="LoginServlet" method="post">
				<!--<h2 class="form-signin-heading">Please sign in</h2>-->
					<img src="img/logo_text.png"
						style="width: 140px;" />
				<br /> <label for="inputEmail" class="sr-only">E-Mail-Address</label>
				<input type="email" id="inputEmail" name="emailAddress"
					class="form-control" placeholder="E-Mail-Adresse" required
					autofocus> <br /> <label for="inputPassword"
					class="sr-only">Passwort</label> <input type="password"
					id="inputPassword" name="password" class="form-control"
					placeholder="Passwort" required>
				<div class="checkbox">
					<!--<label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>-->
				</div>
				<button class="btn btn-lg btn-red btn-block" type="submit">Einloggen</button>
				<!-- input class="btn btn-lg btn-red btn-block" type="submit" role="button">Einloggen</input>-->
			</form>
			<br />
			<hr style="height: 1px; background: #C6C6C6; width: 70%;">
			<form class="form-signin">
				<!--<button class="btn btn-lg btn-primary btn-block btn-small" href="register.html">Neu anmelden</button>-->
				<a class="btn btn-lg btn-primary btn-block btn-small"
					href="register.jsp" role="button">Jetzt registieren</a>
			</form>
			<br />
		</div>
	</div>
	<!-- /container -->


	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
