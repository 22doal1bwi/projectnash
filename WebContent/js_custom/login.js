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
				hideMessageBar()
			} else {
				window.setTimeout(function() {
					location.href = 'home.jsp';
				}, 1000);
			}
		},
		error : function() {
			buildAndShowMessageBar("ERR_CONNECTION", "messagebar_login")
			hideMessageBar()
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
				$("#resetModal").modal("hide")
				buildAndShowMessageBar("SCS_PASSWORD_RESET", "messagebar_login")	
				hideMessageBar()
			} else {
				$("#resetModal").modal("hide")
				buildAndShowMessageBar("ERR_PASSWORD_RESET", "messagebar_login")	
				hideMessageBar()
			}
		},
		error : function() {
			buildAndShowMessageBar("ERR_CONNECTION", "messagebar_login")
			hideMessageBar()
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

function hideMessageBar() {
	window.setTimeout(function() {
		$("#messagebar_login").addClass("messagebar_hidden")
	}, 3000);	
}