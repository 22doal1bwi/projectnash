/**
 * 
 * This file provides all methods for revoke.jsp.
 * 
 * @author Jonathan Schlotz
 *
 */

// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//

$(document).ready(function() {
	
	// Method that shows the remaining chars for the revoke text area
	$("#textfield_revoke").on("keyup",function(){
		var remainingChars = 150 - $("#textfield_revoke").val().length
		$("#remainingChars").fadeIn()
		$("#remainingChars").html(remainingChars + " Zeichen übrig")
	  });
});

// ====================================================================================//
// ================================== AJAX FUNCTION ===================================//
// ====================================================================================//

/**
 * Sends an ajax request to the backend to revoke a certificate..
 *
 */
function revokeCertificate() {
	setLoading()
	$.ajax({
		url : '../RevokeCertificateServlet',
		type : 'POST',
		dataType : 'json',
		data : "revokeReason=" + revokeReason,
		timeout : 8000,
		success : function(data) {
			if (data.validSession && data.revokedCertificate) {
				revokeSuccessful()
				window.setTimeout(function() {
					location.href = 'home.jsp';
				}, 3000);
			} else if (data.validSession && data.revokedCertificate === false) {
				revokeUnsuccessful("ERR_CERT_REVOKE", "messagebar_revoke")
			} else {
				window.setTimeout(function() {
					location.href = 'login.jsp';
				}, 1000);
			}
		},
		error : function() {
			revokeUnsuccessful("ERR_CONNECTION", "messagebar_revoke")
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
 * Executed by a click on 'Widerrufen'.
 *
 */
function onRevokeClick() {
	revokeReason = ""
	if ($("#textfield_revoke").val() !== "") {
		revokeReason = $("#textfield_revoke").val()
		$("#textfield_revoke").attr("disabled", "")
		$("#messagebar_revoke").addClass("messagebar_hidden")
		$("#page_content_revoke").removeClass("page_content_move_down")
		$("#footer_revoke")
				.html(
						'<button id="button_cancel_revoke" onclick="cancelRevoke()" type="button"'
								+ 'class="btn simplecert_inv_btn" style="display: inline">Abbrechen</button><button id="button_confirm_revoke" onclick="revokeCertificate()"'
								+ 'type="button" class="btn simplecert_btn" style="display: inline">Bestätigen</button><img id="loading_gif_revoke" src="../img/general/loading.gif" class="loading_gif_revoke">')
	} else {
		buildAndShowMessageBar("WRN_EMPTY_FIELD_REASON", "messagebar_revoke")
		$("#page_content_revoke").addClass("page_content_move_down")
	}
}

/**
 * Executed by a click on 'Abbrechen'.
 *
 */
function cancelRevoke() {
	$("#footer_revoke").html(
			'<button id="button_revoke" onclick="onRevokeClick()" type="button"'
					+ 'class="btn simplecert_btn">Widerrufen</button>')
	$("#textfield_revoke").removeAttr("disabled")
}

/**
 * Sets the ui area to a loading state.
 *
 */
function setLoading() {
	$("#loading_gif_revoke").fadeIn()
	$("#button_cancel_revoke, #button_confirm_revoke").addClass("disabled")
	$("#button_cancel_revoke, #button_confirm_revoke").removeAttr("onclick")
}

/**
 * Unsets the ui area from the loading state when the ajax request was was successful.
 *
 */
function revokeSuccessful() {
	$("#loading_gif_revoke").fadeOut()
	buildAndShowMessageBar("SCS_CERT_REVOKE", "messagebar_revoke")
	$("#page_content_revoke").addClass("page_content_move_down")
	window.setTimeout(function() {
		$("#messagebar_revoke").addClass("messagebar_hidden")
		$("#page_content_revoke").removeClass("page_content_move_down")
	}, 3000);

}

/**
 * Unsets the ui area from the loading state when the ajax request was was unsuccessful.
 *
 * @param message The message to be displayed.
 * @param messageBarId The id of the message bar.
 * 
 */
function revokeUnsuccessful(message, messageBarId) {
	$("#loading_gif_revoke").fadeOut()
	$("#textfield_revoke").removeAttr("disabled")
	$("#button_cancel_revoke, #button_confirm_revoke").removeClass("disabled")
	$("#button_cancel_revoke").attr("onclick", "cancelRevoke()")
	$("#button_confirm_revoke").attr("onclick", "revokeCertificate()")
	
	buildAndShowMessageBar(message, messageBarId)
	$("#page_content_revoke").addClass("page_content_move_down")

	window.setTimeout(function() {
		$("#messagebar_revoke").addClass("messagebar_hidden")
		$("#page_content_revoke").removeClass("page_content_move_down")
	}, 3000);
}