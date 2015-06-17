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
	if ($('#emailAddressForNewPassword').val() !== "") {
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
}

// ====================================================================================//
// ================================== MAIN FUNCTIONS
// ==================================//
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
		$("#modalDialog").addClass("modal_dialog_fitting")
	} else {
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

function hideMessageBar() {
	window.setTimeout(function() {
		$("#messagebar_login").addClass("messagebar_hidden")
	}, 3000);
}

function clearField() {
	$("#emailAddressForNewPassword").val("")
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
	$("#loading_gif_login_" + type).fadeOut()
	if (type === "password") {
		$("#cancelButton, #resetButton").removeAttr("disabled");
		$("#resetButton").attr("onclick", "requestNewPassword()")
		$("#cancelButton").attr("onclick", "clearField()")
		clearField()
	} else if (type === "redirect") {
		$("#loginButton").removeAttr("disabled");
		$("#loginButton").attr("onclick", "checkFormBeforeSubmit()")
	}
	
}