// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {
	$('#password, #password_confirm').keypress(function(e) {
		if (e.keyCode == 13)
			$('#step2_button_extend').click();
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
// ================================== AJAX FUNCTIONS
// ==================================//
// ====================================================================================//
function requestCertificate() {
	setLoading(1)
	$.ajax({
		url : '../RequestCertificateServlet',
		type : 'POST',
		dataType : 'json',
		timeout : 8000,
		success : function(data) {
			if (data.validSession && data.createdRequest) {
				successful(1, "SCS_CERT_REQUEST")
			} else if (data.validSession && data.createdRequest === false) {
				unsuccessful(1, "ERR_CERT_REQUEST")
			} else {
				window.setTimeout(function() {
					location.href = 'login.jsp';
				}, 1000);
			}
		},
		error : function() {
			unsuccessful(1, "ERR_CONNECTION")
		}
	})
}

function activateCertificate() {
	$
			.ajax({
				url : '../ActivateCertificateServlet',
				type : 'POST',
				dataType : 'json',
				data : $('#password').serialize(),
				timeout : 8000,
				success : function(data) {
					if (data.validSession && data.activatedCertificate) {
						successful(2, "SCS_CERT_ACTIIVATION")
					} else if (data.validSession
							&& data.activatedCertificate === false) {
						unsuccessful(2, "ERR_CERT_ACTIVATION")
					} else {
						window.setTimeout(function() {
							location.href = 'login.jsp';
						}, 1000);
					}
				},
				error : function() {
					unsuccessful(2, "ERR_CONNECTION")
				}
			})
}

// ====================================================================================//
// ================================== MAIN FUNCTIONS
// ==================================//
// ====================================================================================//
function logout() {
	document.form_logout.submit()
}

function validatePassword() {
	var regEx = /.{6}/;

	if ($("#password").val() !== "" && !regEx.test($("#password").val())) {
		buildAndShowMessageBar("WRN_INPUT_PASSWORD", "messagebar_extend")
		$("#page_content_extend").addClass("page_content_move_down")
	} else {
		hideMessageBar()
	}
}

function checkPassword() {
	// Both fields have to be filled out
	if ($("#password").val() !== "" && $("#password_confirm").val() !== "") {

		// Repeated new password and password have to be the same
		if ($("#password").val() === $("#password_confirm").val()) {
			return true;

		} else { // If repeated new password and password are not the
			// same
			buildAndShowMessageBar("ERR_INPUT_PASSWORD_COMP",
					"messagebar_extend")
			$("#page_content_extend").addClass("page_content_move_down")
		}

	} else { // If one field is empty
		buildAndShowMessageBar("WRN_EMPTY_CERT_PWD", "messagebar_extend")
		$("#page_content_extend").addClass("page_content_move_down")
	}
	return false;
}

function onActivateClick() {
	if (checkPassword()) {
		setLoading(2)
		activateCertificate()
	}
}

function setLoading(stepNumber) {
	$("#step" + stepNumber + "_panel_body_extend").addClass(
			"panel_next_step_or_loading")
	$("#loading_gif_extend").addClass("loading_gif_request_step" + stepNumber)
	$("#loading_gif_extend").fadeIn()
	$("#step" + stepNumber + "_button_extend").attr("disabled", "");
	$("#step" + stepNumber + "_button_extend").removeAttr("onclick")
}

function successful(stepNumber, message) {
	var stepNumberNextStep = stepNumber + 1
	unsetLoadingSuccessful()
	buildAndShowMessageBar(message, "messagebar_extend")
	$("#page_content_extend").addClass("page_content_move_down")
	if (stepNumber > 1) {
		window.setTimeout(function() {
			hideMessageBar()
			$("#step" + stepNumber + "_content_extend").slideUp()
			$("#step" + stepNumber + "_icon_extend").addClass(
					"messageicon_border_success")
			$("#step" + stepNumber + "_iconfont_extend").addClass(
					"messageicon_success")
			$("#step" + stepNumber + "_header_extend").addClass(
					"panelheader_completed")
			$("#step" + stepNumberNextStep + "_content_extend").slideDown()
			$("#step" + stepNumberNextStep + "_header_extend").addClass(
					"panelheader_set_current_step")
		}, 1500);
	} else {
		window.setTimeout(function() {
			$("#step" + stepNumber + "_content_extend").slideUp()
			$("#step" + stepNumber + "_icon_extend").addClass(
					"messageicon_border_success")
			$("#step" + stepNumber + "_iconfont_extend").addClass(
					"messageicon_success")
			$("#step" + stepNumber + "_header_extend").addClass(
					"panelheader_completed")
			buildAndShowMessageBar("WRN_CERT_REQUEST_WAITING",
					"messagebar_extend")
		}, 1500);
	}
}

function unsetLoadingSuccessful() {
	$("#loading_gif_extend").fadeOut()
}

function unsuccessful(stepNumber, message) {
	unsetLoadingUnsuccessful(stepNumber)
	buildAndShowMessageBar(message, "messagebar_extend")
	$("#page_content_extend").addClass("page_content_move_down")

	window.setTimeout(function() {
		hideMessageBar()
	}, 1500);
}

function unsetLoadingUnsuccessful(stepNumber) {
	$("#step" + stepNumber + "_panel_body_extend").removeClass(
			"panel_next_step_or_loading")
	$("#loading_gif_extend").fadeOut()
	$("#step" + stepNumber + "_button_extend").removeAttr("disabled")
	if (stepNumber === 1) {
		$("#step" + stepNumber + "_button_extend").attr("onclick",
				"requestCertificate()")
	} else if (stepNumber === 2) {
		$("#step" + stepNumber + "_button_extend").attr("onclick",
				"onActivateClick()")
	}
}

function hideMessageBar() {
	$("#messagebar_extend").addClass("messagebar_hidden")
	$("#page_content_extend").removeClass("page_content_move_down")
}