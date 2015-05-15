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

// ====================================================================================//
// ================================== MAIN FUNCTIONS ==================================//
// ====================================================================================//
// Method which checks all field values before submitting them to the backend
function checkFormBeforeSubmit() {
	var emailAddress = document.getElementById('emailAddress'), password = document
			.getElementById('password'), messagebar = document
			.getElementById('messagebar')

	if (emailAddress.value === "") {
		emailAddress.classList.add("has-warning")
	}
	if (password.value === "") {
		password.classList.add("has-warning")
	}

	if (emailAddress.value !== "" && password.value !== "") {
		$("#messagebar_login").addClass("messagebar_hidden")
		submitLoginForm()
	} else {
		buildAndShowMessageBar("WRN_EMPTY_FIELDS_LOGIN", "messagebar_login")
	}
}

// ====================================================================================//
// ============================= LITTLE HELPER FUNCTIONS ==============================//
// ====================================================================================//

// Method which removes any style classes from an inputfield
function cleanInputField(type) {
	var inputField = document.getElementById(type)
	if ($("#" + type).hasClass("has-warning")) {
		$("#" + type).removeClass("has-warning")
		if ($("#emailAddress").hasClass("has-warning") === false
				&& ($("#password").hasClass("has-warning") === false)) {
			$("#messagebar_login").addClass("messagebar_hidden")
		}
	}
	if ($("#" + type).hasClass("has-error")) {
		$("#" + type).removeClass("has-error")
	}
}