<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="de" class="document">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<script src="../bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript"
	src="../bower_components/jquery/dist/jquery.i18n.properties-1.0.9.js"></script>
<script type="text/javascript" src="../js_custom/register.js"></script>
<script type="text/javascript" src="../js_custom/_messagebar.js"></script>
<link rel="icon" type="image/png" sizes="32x32"
	href="../img/simplecert/simplecert_favicon_32x32.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="../img/simplecert/simplecert_favicon_96x96.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="../img/simplecert/simplecert_favicon_16x16.png">
<link rel="icon" href="../img/simplecert/simplecert_favicon.ico">

</head>
<title>simpleCert - Registrierung</title>
<!-- Bootstrap core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../css_custom/register.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../bower_components/bootstrap/dist/css/sb-admin-2.css"
	rel="stylesheet">

<!-- Custom Fonts -->
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- Custom styles for the messagebar -->
<link href="../css_custom/_messagebar.css" rel="stylesheet"
	type="text/css">

<!-- Roboto font -->
<link href="../css_custom/_roboto.css" rel="stylesheet" type="text/css">

<body>
<body class="body_register">
	<div class="messagebar_placeholder">
		<div id="messagebar_register"
			class="alert messagebar messagebar_hidden"></div>
	</div>
	<div id="register_container">
		<div class="register_area">
			<img src="../img/simplecert/simplecert_logo_text_128x128.png"
				style="margin-top: 20px;" /><br />
			<h3 style="color: #595959">Eröffnen Sie Ihren
				simpleCert-Account.</h3>
			<br />
			<form id="form_register" name="form_register">
				<div class="form-group col-lg-6">
					<input type="text" name="firstName" class="form-control"
						id="firstName" onchange="validateInput('firstName', 'ui_only')"
						placeholder="Vorname" required>
				</div>
				<div class="form-group col-lg-6">
					<input type="text" name="lastName" class="form-control"
						id="lastName" onchange="validateInput('lastName', 'ui_only')"
						placeholder="Nachname" required>
				</div>
				<div class="form-group col-lg-6">
					<select class="form-control" id="organizationalUnit"
						name="organizationalUnit"
						onchange="validateInput('organizationalUnit', 'ui_only')" required>
						<option value="" selected disabled>Abteilung</option>
						<option>IT</option>
						<option>Verwaltung</option>
						<option>Vertrieb</option>
					</select>
				</div>
				<div class="form-group col-lg-6">
					<input name="personalId" id="personalId" class="form-control"
						onchange="validateInput('personalId', 'ui_and_db')"
						placeholder="Personalnummer" required>
				</div>
				<div class="clearfix"></div>
				<div class="form-group col-lg-6">
					<input type="email" name="emailAddress" class="form-control"
						id="emailAddress"
						onchange="validateInput('emailAddress', 'ui_and_db')"
						placeholder="E-Mail-Adresse" required>
				</div>
				<div class="form-group col-lg-6">
					<input type="email" class="form-control" id="emailAddress_confirm"
						placeholder="E-Mail-Adresse wiederholen"
						onchange="compareInputField('emailAddress')" required>
					<!-- 					onpaste="return false;" -->
				</div>

				<div class="form-group col-lg-6">
					<input type="password" name="password" class="form-control"
						id="password" onchange="validateInput('password', 'ui_only')"
						placeholder="Passwort" required>
				</div>
				<div class="form-group col-lg-6">
					<input type="password" class="form-control" id="password_confirm"
						placeholder="Passwort wiederholen"
						onchange="compareInputField('password')" required>
					<!-- 					onpaste="return false;" -->
				</div>
				<button id="registerButton" type="button"
					class="btn btn-lg btn_submit simplecert_btn btn-block"
					onclick="checkFormBeforeSubmit()">Absenden</button>
			</form>
			<br />
		</div>
	</div>
</body>
</html>