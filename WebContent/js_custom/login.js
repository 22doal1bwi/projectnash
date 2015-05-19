// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {
	// Method that triggers the login button when the 'enter'-key is pressed
	$('#password, #emailAddress').keypress(function(e) {
		if (e.keyCode == 13)
			$('#loginButton').click();
	});

	jQuery.i18n.properties({
		name : 'messages',
		path : '../i18n/',
		language : 'de',
		mode : 'map',
		encoding: 'UTF-8'
	});
});

// ====================================================================================//
// ================================== AJAX FUNCTION ===================================//
// ====================================================================================//
// Method which submits the 'emailAddress' and 'password' from the input fields
function submitLoginForm() {
	return $.ajax({
		url : '../LoginServlet',
		type : 'POST',
		dataType : 'json',
		data : $('#emailAddress, #password').serialize(),
		success : function(data) {
			if (data.loginFailed) {
				buildAndShowMessageBar("ERR_CREDENTIALS", "messagebar_login")
			} else {
				window.setTimeout(function() {
					location.href = 'home.jsp';
				}, 1000);
			}
		},
		error : function() {
			buildAndShowMessageBar("ERR_CONNECTION", "messagebar_login")
		}
	})
}

function requestNewPassword() {
	$.ajax({
		url : '../ResetPasswordServlet',
		type : 'POST',
		dataType : 'json',
		data : $('#emailAddressForNewPassword').serialize(),
		success : function(data) {
			if (data.resetSuccessful) {
				buildAndShowMessageBar("SCS_PASSWORD_RESET", "messagebar_login")
			} else {
				buildAndShowMessageBar("ERR_PASSWORD_RESET", "messagebar_login")
			}
		},
		error : function() {
			buildAndShowMessageBar("ERR_CONNECTION", "messagebar_login")
		}
	})
}

// ====================================================================================//
// ================================== MAIN FUNCTION ===================================//
// ====================================================================================//
// Method which checks all field values before submitting them to the backend
function checkFormBeforeSubmit() {
	if ($("#emailAddress").val() !== "" && $("#password").val() !== "") {
		$("#messagebar_login").addClass("messagebar_hidden")
		submitLoginForm()
	} else {
		buildAndShowMessageBar("WRN_EMPTY_FIELDS_LOGIN", "messagebar_login")
	}
}