/**
 * 
 * This file provides all methods for settings.jsp.
 * 
 * @author Jonathan Schlotz
 *
 */

// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//

$(document).ready(
		
		// Method that triggers the 'OK' button (for password change) when the 'enter'-key is pressed
		function() {			
			$('#password_current, #password_new, #password_new_confirm')
					.keypress(function(e) {
						if (e.keyCode == 13)
							$('#button_confirm_password').click();
					});		
		});

// ====================================================================================//
// ================================== AJAX FUNCTION ===================================//
// ====================================================================================//

/**
 * Sends an ajax request to the backend to update the password.
 *
 */
function updatePassword() {
	 $.ajax({
	 url : '../UpdateServlet',
	 type : 'POST',
	 dataType : 'json',
	 data : $('#password_current, #password_new').serialize(),
	 timeout : 8000,
	 success : function(data) {
	 if (data.pwChangeSuccessful) {
	unsetLoading()
	window.setTimeout(function() {
		cleanPage()
		buildAndShowMessageBar("SCS_PWD_CHANGE", "messagebar_settings")
		$("#page_content_settings").addClass("page_content_move_down")
		hideMessageBarDelayed()
	}, 1000);
	 } else {
				unsetLoading()
				buildAndShowMessageBar("ERR_PWD_CHANGE", "messagebar_settings")
				$("#page_content_settings").addClass("page_content_move_down")
				hideMessageBarDelayed()
			}
		},
		error : function() {
			unsetLoading()
			buildAndShowMessageBar("ERR_CONNECTION", "messagebar_settings")
			$("#page_content_settings").addClass("page_content_move_down")
			hideMessageBarDelayed()
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
 * Executed by a click on 'Password aendern'.
 * 
 */
function changePassword() {
	$("#button_change_password").hide()
	$(
			"#password_current, #password_new, #password_new_confirm, #button_cancel_password, #button_confirm_password")
			.fadeIn()
}

/**
 * Hides the message bar with a delay.
 * 
 */
function hideMessageBarDelayed() {
	window.setTimeout(function() {
		hideMessageBarInstantly()
	}, 2000);
}

/**
 * Hides the message bar instantly.
 * 
 */
function hideMessageBarInstantly() {
	$("#messagebar_settings").addClass("messagebar_hidden")
	$("#page_content_settings").removeClass("page_content_move_down")
}

/**
 * Sets the page to its initial state.
 * 
 */
function cleanPage() {
	$(
			"#password_current, #password_new, #password_new_confirm, #button_cancel_password, #button_confirm_password")
			.fadeOut().promise().done(function() {
				$("#button_change_password").fadeIn()
			})
	hideMessageBarInstantly()
	$("#password_current, #password_new, #password_new_confirm").val("")
}

/**
 * Checks the length of the password.
 * 
 */
function validatePassword() {
	var regEx = /.{6}/;

	if ($("#password_new").val() !== "" && !regEx.test($("#password_new").val())) {
		buildAndShowMessageBar("WRN_INPUT_PASSWORD", "messagebar_settings")
		$("#page_content_settings").addClass("page_content_move_down")
	} else {
		hideMessageBarInstantly()
	}
}

/**
 * Checks the values of both password fields and compares them with each other.
 * 
 */
function confirmPasswordChange() {
	// All fields have to be filled out
	if ($("#password_current").val() !== "" && $("#password_new").val() !== ""
			&& $("#password_new_confirm").val() !== "") {

		// New password has to be different than the current password
		if ($("#password_current").val() !== $("#password_new").val()) {

			// Repeated new password and password have to be the same
			if ($("#password_new").val() === $("#password_new_confirm").val()) {
				setLoading()
				updatePassword()
			} else { // If repeated new password and password are not the
				// same
				buildAndShowMessageBar("ERR_INPUT_PASSWORD_COMP",
						"messagebar_settings")
				$("#page_content_settings").addClass("page_content_move_down")
			}

		} else { // New and current password are the same
			buildAndShowMessageBar("ERR_PWD_CURRENT_EQUALS_NEW",
					"messagebar_settings")
			$("#page_content_settings").addClass("page_content_move_down")
		}

	} else { // If at least one field is empty
		buildAndShowMessageBar("WRN_EMPTY_FIELDS_PWD_CHANGE",
				"messagebar_settings")
		$("#page_content_settings").addClass("page_content_move_down")
	}
}

/**
 * Sets the ui area to a loading state.
 * 
 */
function setLoading() {
	$("#loading_gif_settings").fadeIn()
	$("#button_confirm_password, #button_cancel_password").attr("disabled", "")
	$("#button_confirm_password, #button_cancel_password")
			.removeAttr("onclick")
}

/**
 * Unsets the ui area from the loading state.
 * 
 */
function unsetLoading() {
	$("#loading_gif_settings").fadeOut()
	$("#button_confirm_password, #button_cancel_password").removeAttr(
			"disabled")
	$("#button_confirm_password").attr("onclick", "confirmPasswordChange()")
	$("#button_cancel_password").attr("onclick", "cleanPage('password')")
}