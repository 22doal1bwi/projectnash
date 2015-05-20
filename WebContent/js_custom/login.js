// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {
	// Method that triggers the login button when the 'enter'-key is pressed
	$('#password, #emailAddress').keypress(function(e) {
		if (e.keyCode == 13)
			$('#loginButton').click();
	});

	$('#emailAddressForNewPassword').keypress(function(e) {
		if (e.keyCode == 13)
			$('#resetButton').click();
	});

	jQuery.i18n.properties({
		name : 'messages',
		path : '../i18n/',
		language : 'de',
		mode : 'map',
		encoding : 'UTF-8'
	});
});

// ====================================================================================//
// ================================== AJAX FUNCTION
// ===================================//
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
					location.href = 'app_frame.jsp';
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
				window.setTimeout(function() {
					buildAndShowMessageBar("SCS_PASSWORD_RESET",
							"messagebar_login")
				}, 500);
			} else {
				$("#resetModal").modal("hide")
				window.setTimeout(function() {
					buildAndShowMessageBar("ERR_PASSWORD_RESET",
							"messagebar_login")
				}, 500);
			}
			hideMessageBar()
			$("#emailAddressForNewPassword").val("")
		},
		error : function() {
			$("#resetModal").modal("hide")
			window.setTimeout(function() {
			buildAndShowMessageBar("ERR_CONNECTION", "messagebar_login")
			}, 500);
			hideMessageBar()
			$("#emailAddressForNewPassword").val("")
		}
	})
}

// ====================================================================================//
// ================================== MAIN FUNCTION
// ===================================//
// ====================================================================================//
// Method which checks all field values before submitting them to the backend
function checkFormBeforeSubmit() {
	if ($("#emailAddress").val() !== "" && $("#password").val() !== "") {
		$("#messagebar_login").addClass("messagebar_hidden")
		submitLoginForm()
	} else {
		buildAndShowMessageBar("WRN_EMPTY_FIELDS_LOGIN", "messagebar_login")
		hideMessageBar()
	}
}

function hideMessageBar() {
	window.setTimeout(function() {
		$("#messagebar_login").addClass("messagebar_hidden")
	}, 3000);
}