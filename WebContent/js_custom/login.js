// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {
	determineContainerStyle()

	$(window).resize(function() {
		determineContainerStyle()
	})

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
				unsetLoading("redirect")
				buildAndShowMessageBar("ERR_CREDENTIALS", "messagebar_login")
				hideMessageBar()
			} else {
				location.href = 'home.jsp';
			}
		},
		error : function() {
			unsetLoading("redirect")
			buildAndShowMessageBar("ERR_CONNECTION", "messagebar_login")
			hideMessageBar()
		}
	})
}

function requestNewPassword() {
	// if both fields are NOT empty
	if ($("#emailAddressForNewPassword").val() !== "" && $("#emailAddressForNewPasswordConfirm").val() !== "") {
		cleanInputFieldWarnings("emailAddressForNewPassword")
		cleanInputFieldWarnings("emailAddressForNewPasswordConfirm")
		// if the values of both fields are valid
		if (validateInput("emailAddressForNewPassword") && validateInput("emailAddressForNewPasswordConfirm")) {
			setLoading("password")
			$.ajax({
				url : '../ResetPasswordServlet',
				type : 'POST',
				dataType : 'json',
				data : $('#emailAddressForNewPassword').serialize(),
				success : function(data) {
					$("#resetModal").modal("hide")
					unsetLoading("password")
					window.setTimeout(function() {
						buildAndShowMessageBar("SCS_PASSWORD_REQUEST", "messagebar_login")
					}, 500);
					hideMessageBar()
				},
				error : function() {
					$("#resetModal").modal("hide")
					unsetLoading("password")
					window.setTimeout(function() {
						buildAndShowMessageBar("ERR_CONNECTION", "messagebar_login")
					}, 500);
					hideMessageBar()
				}
			})
		}
	} else {
		if ($("#emailAddressForNewPassword").val() === "") {
			$("#emailAddressForNewPassword").addClass("has-warning")
		} else if ($("#emailAddressForNewPasswordConfirm").val() === "") {
			$("#emailAddressForNewPasswordConfirm").addClass("has-warning")
		}
	}
}

// ====================================================================================//
// ================================== MAIN FUNCTIONS ==================================//
// ====================================================================================//

// Function to determine the container style based on the window height
function determineContainerStyle() {
	if ($(window).height() < "546") {
		if ($("#login_container").hasClass("container_free")) {
			$("#login_container").removeClass("container_free")
		}
		$("#login_container").addClass("container_fitting")
		if ($("#resetModal").hasClass("modal_free")) {
			$("#resetModal").removeClass("modal_free")
		}
		$("#resetModal").addClass("modal_fitting")
		if ($("#modalDialog").hasClass("modal_dialog_free")) {
			$("#modalDialog").removeClass("modal_dialog_free")
		}		
		$("#modalDialog").addClass("modal_dialog_fitting")
	} else if ($(window).height() >= "546") {
		if ($("#login_container").hasClass("container_fitting")) {
			$("#login_container").removeClass("container_fitting")
		}
		$("#login_container").addClass("container_free")
		if ($("#resetModal").hasClass("modal_fitting")) {
			$("#resetModal").removeClass("modal_fitting")
		}
		$("#resetModal").addClass("modal_free")
		if ($("#modalDialog").hasClass("modal_dialog_fitting")) {
			$("#modalDialog").removeClass("modal_dialog_fitting")
		}
		$("#modalDialog").addClass("modal_dialog_free")
	}
}

// Method which checks all field values before submitting them to the backend
function checkFormBeforeSubmit() {
	if ($("#emailAddress").val() !== "" && $("#password").val() !== "") {
		$("#messagebar_login").addClass("messagebar_hidden")
		setLoading("redirect")
		submitLoginForm()
	} else {
		buildAndShowMessageBar("WRN_EMPTY_FIELDS_LOGIN", "messagebar_login")
		hideMessageBar()
	}
}

// Method which checks the input value of a field
function validateInput(type) {
	var regEx = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

	// if field IS NOT empty
	if ($("#" + type).val() !== "") {
		cleanInputFieldWarnings(type)
		// if entered value IS NOT a valid email address
		if (!regEx.test($("#" + type).val())) {
			$("#" + type).addClass("has-error")
			// if entered value IS a valid email address
		} else {
			cleanInputFieldErrors(type)			

			if (type === "emailAddressForNewPassword") {
				// if the other field IS NOT empty and value of this field
				// EQUALS NOT the value of the current field
				if ($("#emailAddressForNewPasswordConfirm").val() !== "" && $("#" + type).val() !== $("#emailAddressForNewPasswordConfirm").val()) {
					$("#" + type).addClass("has-error")
					// if the other field IS NOT empty and value of this field
					// EQUALS the value of the current field
				} else if ($("#emailAddressForNewPasswordConfirm").val() !== "" && $("#" + type).val() === $("#emailAddressForNewPasswordConfirm").val()) {
					cleanInputFieldErrors(type)
					cleanInputFieldErrors("emailAddressForNewPasswordConfirm")
					return true
				}
				// if the other field IS NOT empty and value of this field
				// EQUALS NOT the value of the current field
			} else if (type === "emailAddressForNewPasswordConfirm") {
				if ($("#emailAddressForNewPassword").val() !== "" && $("#" + type).val() !== $("#emailAddressForNewPassword").val()) {
					$("#" + type).addClass("has-error")
					// if the other field IS NOT empty and value of this field
					// EQUALS the value of the current field
				} else if ($("#emailAddressForNewPassword").val() !== "" && $("#" + type).val() === $("#emailAddressForNewPassword").val()) {
					cleanInputFieldErrors(type)
					cleanInputFieldErrors("emailAddressForNewPassword")
					return true
				}
			}
		}
		// if field IS empty
	} else {
		cleanInputFieldErrors(type)
	}
	return false
}

function hideMessageBar() {
	window.setTimeout(function() {
		$("#messagebar_login").addClass("messagebar_hidden")
	}, 3000);
}

function clearFields() {
	$("#emailAddressForNewPassword").val("")
	$("#emailAddressForNewPasswordConfirm").val("")
	cleanInputFieldErrors("emailAddressForNewPassword")
	cleanInputFieldErrors("emailAddressForNewPasswordConfirm")
	cleanInputFieldWarnings("emailAddressForNewPassword")
	cleanInputFieldWarnings("emailAddressForNewPasswordConfirm")
}
function setLoading(type) {
	$("#loading_gif_login_" + type).fadeIn()
	if (type === "password") {
		$("#cancelButton, #resetButton").attr("disabled", "");
		$("#cancelButton, #resetButton").removeAttr("onclick")
	} else if (type === "redirect") {
		$("#loginButton").attr("disabled", "");
		$("#loginButton").removeAttr("onclick")
	}
}

function unsetLoading(type) {
	$("#loading_gif_login_" + type).fadeOut(200)
	if (type === "password") {
		$("#cancelButton, #resetButton").removeAttr("disabled");
		$("#resetButton").attr("onclick", "requestNewPassword()")
		$("#cancelButton").attr("onclick", "clearField()")
		clearFields()
		cleanInputFieldErrors("emailAddressForNewPassword")
		cleanInputFieldErrors("emailAddressForNewPasswordConfirm")
	} else if (type === "redirect") {
		$("#loginButton").removeAttr("disabled");
		$("#loginButton").attr("onclick", "checkFormBeforeSubmit()")
	}
}

// Method which removes the error style classes from an inputfield
function cleanInputFieldErrors(type) {
	if ($("#" + type).hasClass("has-error")) {
		$("#" + type).removeClass("has-error")
	}
}

//Method which removes the warning style classes from an inputfield
function cleanInputFieldWarnings(type) {
	if ($("#" + type).hasClass("has-warning")) {
		$("#" + type).removeClass("has-warning")
	}
}