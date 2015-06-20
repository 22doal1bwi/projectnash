/**
 * 
 * This file provides all methods for extend_certificate.jsp.
 * 
 * @author Jonathan Schlotz
 *
 */

// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//

$(document).ready(function() {

	// Method that triggers the 'Aktivieren' button when the 'enter'-key is pressed
	$('#password, #password_confirm').keypress(function(e) {
		if (e.keyCode == 13)
			$('#step2_button_extend').click();
	});
	
});

// ====================================================================================//
// ================================== AJAX FUNCTIONS ==================================//
// ====================================================================================//

/**
 * Sends an ajax request to the backend to request a certificate.
 * 
 */
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

/**
 * Sends an ajax request to the backend to activate a certificate
 * 
 */
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
// ================================== MAIN FUNCTIONS ==================================//
// ====================================================================================//

/**
 * Logs out the user.
 * 
 */
function logout() {
	document.form_logout.submit()
}

/**
 * Checks the length of the password.
 * 
 */
function validatePassword() {
	var regEx = /.{6}/;

	if ($("#password").val() !== "" && !regEx.test($("#password").val())) {
		buildAndShowMessageBar("WRN_INPUT_PASSWORD", "messagebar_extend")
		$("#page_content_extend").addClass("page_content_move_down")
	} else {
		hideMessageBar()
	}
}

/**
 * Compare both password field values witch each other.
 * 
 */
function comparePasswords() {
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

/**
 * Executed by a click on the 'Aktivieren' button.
 * 
 */
function onActivateClick() {
	if (comparePasswords()) {
		setLoading(2)
		activateCertificate()
	}
}

/**
 * Sets the ui area to a loading state.
 * 
 * @param stepNumber The step which is currently shown as active.
 * 
 */
function setLoading(stepNumber) {
	$("#step" + stepNumber + "_panel_body_extend").addClass(
			"panel_next_step_or_loading")
	$("#loading_gif_extend").fadeIn()
	$("#step" + stepNumber + "_button_extend").attr("disabled", "");
	$("#step" + stepNumber + "_button_extend").removeAttr("onclick")
}

/**
 *  Manipulates the ui after successful ajax request.
 * 
 * @param stepNumber The step which is currently shown as active.
 * @param message The message to be displayed in the message bar.
 * 
 */
function successful(stepNumber, message) {
	var stepNumberNextStep = stepNumber + 1
	$("#loading_gif_extend").fadeOut()
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

/**
 * Manipulates the ui after unsuccessful ajax request.
 * 
 * @param stepNumber The step which is currently shown as active.
 * @param message The message which will be displayed in the message bar.
 * 
 */
function unsuccessful(stepNumber, message) {
	$("#step" + stepNumber + "_panel_body_extend").removeClass("panel_next_step_or_loading")
	$("#loading_gif_extend").fadeOut()
	$("#step" + stepNumber + "_button_extend").removeAttr("disabled")
	
	if (stepNumber === 1) {
		$("#step" + stepNumber + "_button_extend").attr("onclick","requestCertificate()")
	} else if (stepNumber === 2) {
		$("#step" + stepNumber + "_button_extend").attr("onclick","onActivateClick()")
	}
	
	buildAndShowMessageBar(message, "messagebar_extend")
	$("#page_content_extend").addClass("page_content_move_down")

	window.setTimeout(function() {
		hideMessageBar()
	}, 1500);
}

/**
 * Hides the message bar.
 * 
 */
function hideMessageBar() {
	$("#messagebar_extend").addClass("messagebar_hidden")
	$("#page_content_extend").removeClass("page_content_move_down")
}