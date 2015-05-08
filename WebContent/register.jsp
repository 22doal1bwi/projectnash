<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="de">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
	type="text/javascript"></script>
<link rel="icon" href="img/favicon.ico">
</head>
<title>simpleCert - Registrierung</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/register.css" rel="stylesheet">

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
.container {
	margin: 40px auto;
	max-width: 600px;
	height: auto;
	background-color: #F9F9F9;
	text-align: center;
}

.btn-red {
	color: #fff;
	background-color: #C03A2A;
	border-color: #A83A2A;
	margin: 20px auto 10px auto;
	outline: none;
	width: 50%;
	margin-bottom: 25px;
}

.btn-red:hover, .btn-red:focus {
	color: #fff;
	background-color: #A83A2A;
	border-color: #A83A2A;
	outline: none;
	transition: background-color .4s ease;
}

.btn-primary {
	color: #000;
	background-color: #C8C8C8;
	border-color: #C8C8C8;
}

.btn-small {
	width: 320px;
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
	border-radius: 0px;
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

.messagebar_down {
	bottom: 0;
}
</style>
<body>
<body id="body" style="background-color: white">
	<div class="messagebar_placeholder">
		<div id="messagebar" style="cursor: context-menu;"
			class="alert messagebar"></div>
	</div>
	<div class="container">
		<img src="img/logo_text.png" style="width: 140px; margin-top: 20px;" /><br />
		<h3 style="color: #595959">Eröffnen Sie Ihren simpleCert-Account.</h3>
		<br />
		<form id="form_register" name="form_register">
			<div class="form-group col-lg-6">
				<input type="text" name="firstName" class="form-control"
					id="firstName" onchange="validateInput('firstName')"
					placeholder="Vorname" required>
			</div>
			<div class="form-group col-lg-6">
				<input type="text" name="lastName" class="form-control"
					id="lastName" onchange="validateInput('lastName')"
					placeholder="Nachname" required>
			</div>
			<div class="form-group col-lg-6">
				<!--<div class="form-group"> -->
				<select class="form-control" id="organizationalUnit"
					name="organizationalUnit"
					onchange="checkInputField('organizationalUnit')" required>
					<option value="" selected disabled>Abteilung</option>
					<option>IT</option>
					<option>Verwaltung</option>
					<option>Vertrieb</option>
				</select>
			</div>
			<div class="form-group col-lg-6">
				<input type="number" name="personalId" id="personalId"
					class="form-control"
					onchange="validateInput('personalId'); inputDbCheck('personalId') "
					placeholder="Personalnummer" required>
			</div>
			<div class="clearfix"></div>
			<div class="form-group col-lg-6">
				<input type="email" name="emailAddress" class="form-control"
					id="emailAddress"
					onchange="checkInputField('emailAddress'); inputDbCheck('emailAddress') "
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
					id="password" onchange="validateInput('password')"
					placeholder="Passwort" required>
			</div>
			<div class="form-group col-lg-6">
				<input type="password" class="form-control" id="password_confirm"
					placeholder="Passwort wiederholen"
					onchange="compareInputField('password')" required>
				<!-- 					onpaste="return false;" -->
			</div>

			<script type="text/javascript">
				function inputDbCheck(type) {
					$.ajax({
						url : 'CheckRegisterServlet',
						type : 'POST',
						dataType : 'json',
						data : $('#' + type).serialize(),
						success : function(data) {
							if (data.alreadyExists === false) {
								hideMessageBar()
							} else {
								showMessageBar(type, "db_check", "error")
							}
						}
					})
				}

				function submitRegisterForm() {
					$
							.ajax({
								url : 'RegisterServlet',
								type : 'POST',
								dataType : 'json',
								data : $(
										'#firstName, #lastName, #organizationalUnit, #personalId, #emailAddress, #password')
										.serialize(),
								success : function(data) {
									if (data.created) {
										showMessageBar("createdUserSuccessful",
												"", "success")
												window.setTimeout("redirect()", 3000);
										
									} else {
										showMessageBar("createdUserFailed", "",
												"error")
									}
								}
							})
				}
				
				function redirect() 
				{ 
				  location.href='login.jsp'; 
				} 

				

				// Method checks input value for 'organizationalUnit' and 'emailAddress' (html5-validation)
				function checkInputField(type) {
					var inputField = document.getElementById(type)
					if (inputField.value !== "" && !inputField.checkValidity()) {
						inputField.classList.remove("has-warning")
						inputField.classList.add("has-error")
						showMessageBar(type, "ui_check", "error")
					} else if (inputField.value !== ""
							&& inputField.checkValidity()) {
						hideMessageBar()
						inputField.classList.remove("has-warning")
						inputField.classList.remove("has-error")

					} else if (inputField.value === "") {
						hideMessageBar()
						inputField.classList.remove("has-error")
					}
				}
				// Method checks input value for all other fields (js-regex)
				function validateInput(type) {
					// 					var inputField = document.getElementById(type), regEx
					// 					switch (type) {
					// 					case "firstName":
					// 						regEx = /^[A-Za-zßÄÖÜ\'\-\ ]+\n/
					// 						break
					// 					case "lastName":
					// 						regEx = /^[A-Za-zßÄÖÜ\'\-\ ]+\n/
					// 						break
					// 					case "personalId":
					//höchstens 6-stellig und danach ans backend
					// 						regEx = /^\d{6}$/
					// 						break
					// 					case "password":
					// 						regEx = /.{6}/
					// 						break
					// 					}
					// 					if (inputField.value !== ""
					// 							&& !regEx.test(inputField.value)) {
					// 						showMessageBar(type, "ui_check")
					// 						inputField.classList.remove("has-warning")
					// 						inputField.classList.add("has-error")
					// 					} else if (inputField.value !== ""
					// 							&& regEx.test(inputField.value)) {
					// 						hideMessageBar()
					// 						inputField.classList.remove("has-warning")
					// 						inputField.classList.remove("has-error")
					// 					} else if (inputField.value === "") {
					// 						hideMessageBar()
					// 						inputField.classList.remove("has-error")
					// 					}
				}

				// Method which checks email and password confirmation and gives feedback to the user
				function compareInputField(type) {
					var inputValue, inputValueConfirm, inputConfirmField
					inputValue = document.getElementById(type).value
					inputValueConfirm = document.getElementById(type
							+ '_confirm').value
					inputConfirmField = document.getElementById(type
							+ '_confirm')

					// Compare field values and add style classes according to the result
					if (inputValue !== "" && inputValueConfirm !== "") {
						if (inputValue !== inputValueConfirm) {
							inputConfirmField.classList.remove("has-warning")
							inputConfirmField.classList.add("has-error")
							showMessageBar(type, "ui_compare", "error")
							return false;
						} else {
							inputConfirmField.classList.remove("has-warning")
							inputConfirmField.classList.remove("has-error")
							hideMessageBar()
							return true;
						}
					} else {
						inputConfirmField.classList.remove("has-warning")
						inputConfirmField.classList.remove("has-error")
						hideMessageBar()
					}
				}

				// Method which checks all field values before submitting them to the backend and gives feedback to the user				
				function checkFormBeforeSubmit() {
					var inputFields = [], messagebar, invalidField = false, emptyField = false;
					// Get all field values
					inputFields[0] = document.getElementById('firstName')
					inputFields[1] = document.getElementById('lastName')
					inputFields[2] = document
							.getElementById('organizationalUnit')
					inputFields[3] = document.getElementById('personalId')
					inputFields[4] = document.getElementById('emailAddress')
					inputFields[5] = document
							.getElementById('emailAddress_confirm')
					inputFields[6] = document.getElementById('password')
					inputFields[7] = document
							.getElementById('password_confirm')
					messagebar = document.getElementById('messagebar')

					// All fields have to be filled
					for (var i = 0; i < inputFields.length; i++) {
						if (inputFields[i].value === "") {
							emptyField = true;
							inputFields[i].classList.add("has-warning")
						}
					}
					if (!emptyField) {
						hideMessageBar()
						if (compareInputField("emailAddress")
								&& compareInputField("password")) {
							//					document.form_register.submit()
							submitRegisterForm()
						}

					} else {
						showMessageBar("emptyField", "", "warning")
					}
				}

				// Method which builds and shows the appropriate messagebar
				function showMessageBar(type, kindOfCheck, kindOfMessage) {
					var message, messagebar = document
							.getElementById('messagebar'), messagebarContent = '<div class="btn btn-default btn-circle messageicon" style="cursor: context-menu; border-color:'

					switch (kindOfMessage) {

					case "error":

						switch (type) {
						case "firstName":
							message = "Der eingegebene "
									+ document.getElementById(type).placeholder
									+ " enthält ungültige Zeichen."
							break
						case "lastName":
							message = "Der eingegebene "
									+ document.getElementById(type).placeholder
									+ " enthält ungültige Zeichen."
							break
						case "personalId":
							if (kindOfCheck === "ui_check") {
								message = "Die eingegebene "
										+ document.getElementById(type).placeholder
										+ " enthält ungültige Zeichen oder besitzt die falsche Länge"
							} else if (kindOfCheck === "db_check") {
								message = "Dieser Benutzer existiert bereits. Bitte überprüfen Sie Ihre Personalnummer."
							}
							break

						case "emailAddress":
							switch (kindOfCheck) {
							case "ui_check":
								message = "Die eingegebene "
										+ document.getElementById(type).placeholder
										+ " ist ungültig. (Beispiel: max.mustermann@simplecert.de)"
								break
							case "ui_compare":
								message = "Die eingegebenen E-Mail-Adressen sind nicht identisch."
								break
							case "db_check":
								message = "Dieser Benutzer existiert bereits. Bitte überprüfen Sie Ihre E-Mail-Adresse."
								break
							}
							break

						case "password":
							if (kindOfCheck === "ui_check") {
								message = "Ihr Passwort muss mindestens eine Länge von 8 Zeichen haben."
							} else if (kindOfCheck === "ui_compare") {
								message = "Die eingegebenen Passwörter sind nicht identisch."
							}

							break
						case "createdUserFailed":
							message = "Die Registrierung konnte nicht erfolgreich abgeschlossen werden."
							break
						}
						messagebarContent = messagebarContent
								+ '#843534;"> <i id="messagebar_icon" style="color: #d9534f;" class="fa fa-times"></i> </div>'
								+ message
						if (messagebar.style.bottom !== 0) {
							messagebar.innerHTML = messagebarContent;
							messagebar.classList.remove("alert-warning")
							messagebar.classList.add("alert-danger")
						}
						break

					case "warning":

						message = "Bitte füllen Sie alle Felder aus."
						messagebarContent = messagebarContent
								+ ' #66512c;"> <i id="messagebar_icon" style="color: #f0ad4e;" class="fa fa-exclamation"></i> </div>'
								+ message
						if (messagebar.style.bottom !== 0) {
							messagebar.innerHTML = messagebarContent;
							messagebar.classList.remove("alert-danger")
							messagebar.classList.add("alert-warning")
						}
						break

					case "success":
						message = "Sie haben sich erfolgreich registriert. Sie werden in Kürze weitergeleitet..."
						messagebarContent = messagebarContent
								+ ' #3c763d;"> <i id="messagebar_icon" style="color: #f0ad4e;" class="fa fa-check"></i> </div>'
								+ message
						if (messagebar.style.bottom !== 0) {
							messagebar.innerHTML = messagebarContent;
							messagebar.classList.remove("alert-danger")
							messagebar.classList.add("alert-success")
						}

						
						break
					}
					// SlideDown
					messagebar.style.bottom = 0

				}
				// Method which destroys and hides the messagebar
				function hideMessageBar() {
					// SlideUp
					messagebar.style.bottom = "55px"
					messagebar.innerHTML = "";
				}
			</script>
			<button type="button" class="btn btn-lg btn-red btn-block"
				onclick="checkFormBeforeSubmit()">Absenden</button>
		</form>
		<br />
	</div>
	<!-- End container -->
</body>
</html>