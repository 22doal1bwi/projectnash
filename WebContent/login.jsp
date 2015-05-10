<%@page import="de.projectnash.frontend.SessionController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<link rel="icon" type="image/png" sizes="32x32" href="img/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="img/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="img/favicon-16x16.png">
<link rel="icon" href="img/favicon.ico">

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
	type="text/javascript"></script>
<title>simpleCert - Login</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/signin.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="intern/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link
	href="intern/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

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

.messagebar_placeholder {
	position: relative;
	top: -40px;
	height: 55px;
}

.messagebar {
	position: relative;
	height: 55px;
	bottom: 55px;
	padding: 0;
	margin: 0;
	border: 0;
	transition: bottom .4s ease;
	line-height: 55px;
	border-radius: 0px;
}

.messageicon {
	outline: none;
	margin-left: 15px;
	margin-right: 10px;
}

</style>

</head>

<body style="background-color: white">

	<%
		String sessionIdStatus = SessionController.checkForSessionId(
				request, response);

		switch (sessionIdStatus) {
			default :
				response.sendRedirect("intern/index.html");
				break;
			case "0" :
			case "-1" :
	%>

	<div class="messagebar_placeholder">
		<div id="messagebar" style="cursor: context-menu;"
			class="alert messagebar"></div>
	</div>
	<div class="container">
		<div id="login_container">
			<form class="form-signin">
				<!--<h2 class="form-signin-heading">Please sign in</h2>-->
				<img src="img/logo_text.png" style="width: 140px;" /> <br /> <label
					for="inputEmail" class="sr-only">E-Mail-Address</label> <input
					type="email" id="emailAddress" name="emailAddress"
					class="form-control" placeholder="E-Mail-Adresse"
					onchange="cleanInputField('emailAddress')" required autofocus>
				<br /> <label for="inputPassword" class="sr-only">Passwort</label>
				<input type="password" id="password" name="password"
					class="form-control" placeholder="Passwort"
					onchange="cleanInputField('password')" required>
				<button class="btn btn-lg btn-red btn-block" type="button"
					onclick="checkFormBeforeSubmit()">Einloggen</button>
				<script type="text/javascript">
					//====================================================================================//
					//================================== AJAX FUNCTION ===================================//
					//====================================================================================//
					// Method which submits the 'emailAddress' and 'password' from the input fields
					function submitLoginForm() {
						return $.ajax({
							url : 'LoginServlet',
							type : 'POST',
							dataType : 'json',
							data : $('#emailAddress, #password').serialize(),
							success : function(data) {
								if (data.loginFailed) {
									showMessageBar("error")									
								} else {									
									window.setTimeout("redirect()", 1000);
								}
							}
						})
					}
					//====================================================================================//

					//====================================================================================//
					//================================== MAIN FUNCTIONS ==================================//
					//====================================================================================//
					// Method which checks all field values before submitting them to the backend			
					function checkFormBeforeSubmit() {
						var emailAddress = document
								.getElementById('emailAddress'), password = document
								.getElementById('password'), messagebar = document
								.getElementById('messagebar')

						if (emailAddress.value === "") {
							emailAddress.classList.add("has-warning")
						}
						if (password.value === "") {
							password.classList.add("has-warning")
						}

						if (emailAddress.value !== "" && password.value !== "") {
							hideMessageBar()
							submitLoginForm()
						} else {
							showMessageBar("warning")
						}
					}

					// Method which builds and shows the messagebar	for wrong 'emailAddress' or 'password'				
					function showMessageBar(kindOfMessage) {
						var message, messagebar = document
								.getElementById('messagebar'), messagebarContent, styleMessagebar, iconBorderColor, iconColor, iconType, styleMessagebar

						switch (kindOfMessage) {

						case "error":
							iconBorderColor = "#843534"
							iconColor = "#d9534f"
							iconType = "fa-times"
							styleMessagebar = "alert-danger"
							message = "Ihre E-Mail-Adresse oder Ihr Passwort ist nicht korrekt."
							break

						case "warning":
							iconBorderColor = "#66512c"
							iconColor = "#f0ad4e"
							iconType = "fa-exclamation"
							styleMessagebar = "alert-warning"
							message = "Bitte füllen Sie beide Felder aus."
							break
						}
						messagebarContent = '<div class="btn btn-default btn-circle messageicon" style="cursor: context-menu; border-color:' + iconBorderColor + ';"> <i id="messagebar_icon" style="color:' + iconColor + '; line-height: 17px;" class="fa ' + iconType + '"></i> </div>'
								+ message
						if (messagebar.style.bottom !== 0) {
							messagebar.innerHTML = messagebarContent;
							cleanMessageBar()
							messagebar.classList.add(styleMessagebar)
						}
						// SlideDown
						messagebar.style.bottom = "-40px"
					}
					//====================================================================================//

					//====================================================================================//
					//============================= LITTLE HELPER FUNCTIONS ==============================//
					//====================================================================================//
					// Method which is called for redirection after successful registration
					function redirect() {
						location.href = 'intern/index.html';
					}

					// Method which removes any style classes from an inputfield
					function cleanInputField(type) {
						var inputField = document.getElementById(type)
						if ($("#" + type).hasClass("has-warning")) {
							inputField.classList.remove("has-warning")
							if ($("#emailAddress").hasClass("has-warning") === false
									&& ($("#password").hasClass("has-warning") === false)) {
								hideMessageBar()
							}
						}
						if ($("#" + type).hasClass("has-error")) {
							inputField.classList.remove("has-error")
						}
					}

					// Method which removes any style classes from the massagebar
					function cleanMessageBar() {
						messagebar = document.getElementById('messagebar')
						if ($("#messagebar").hasClass("alert-warning")) {
							messagebar.classList.remove("alert-warning")
						}
						if ($("#messagebar").hasClass("alert-danger")) {
							messagebar.classList.remove("alert-danger")
						}
					}
					// Method which destroys and hides the messagebar
					function hideMessageBar() {
						messagebar = document.getElementById('messagebar')
						// SlideUp
						messagebar.style.bottom = "55px"
						messagebar.innerHTML = "";
					}
					//====================================================================================//
				</script>
			</form>
			<br />
			<hr style="height: 1px; background: #C6C6C6; width: 70%;">
			<form class="form-signin">
				<a class="btn btn-lg btn-primary btn-block btn-small"
					href="register.jsp" role="button">Neu anmelden</a>
			</form>
			<br />
		</div>
	</div>
	<!-- End container -->
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
	<%
		}
	%>
</body>
</html>