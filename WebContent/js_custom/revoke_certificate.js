// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {
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
			} else if (data.validSession && data.revokedCertificate === false) {
				revokeUnsuccessful("ERR_CERT_REVOKE", "messagebar_revoke")
			} else {
				window.setTimeout(function() {
					location.reload(true)
				}, 1000);
			}
		},
		error : function() {
			revokeUnsuccessful("ERR_CONNECTION", "messagebar_revoke")
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
								+ 'type="button" class="btn simplecert_btn" style="display: inline">Bestätigen</button><img id="loading_gif_revoke" src="../img/general/loading.gif" class="loading_gif">')
	} else {
		buildAndShowMessageBar("WRN_EMPTY_FIELD_REASON", "messagebar_revoke")
		$("#page_content_revoke").addClass("page_content_move_down")
	}
}

function cancelRevoke() {
	$("#footer_revoke").html(
			'<button id="button_revoke" onclick="onRevokeClick()" type="button"'
					+ 'class="btn simplecert_btn">Widerrufen</button>')
	$("#textfield_revoke").removeAttr("disabled")
}

function setLoading() {
	$("#loading_gif_revoke").fadeIn()
	$("#button_cancel_revoke, #button_confirm_revoke").addClass("disabled")
	$("#button_cancel_revoke, #button_confirm_revoke").removeAttr("onclick")
}

function unsetLoadingUnsuccessful() {
	$("#loading_gif_revoke").fadeOut()
	$("#textfield_revoke").removeAttr("disabled")
	$("#button_cancel_revoke, #button_confirm_revoke").removeClass("disabled")
	$("#button_cancel_revoke").attr("onclick", "cancelRevoke()")
	$("#button_confirm_revoke").attr("onclick", "revokeCertificate()")
}

function unsetLoadingSuccessful() {
	$("#loading_gif_revoke").fadeOut()
}

function revokeSuccessful() {
	unsetLoadingSuccessful()
	buildAndShowMessageBar("SCS_CERT_REVOKE", "messagebar_revoke")
	$("#page_content_revoke").addClass("page_content_move_down")
	window.setTimeout(function() {
		$("#messagebar_revoke").addClass("messagebar_hidden")
		$("#page_content_revoke").removeClass("page_content_move_down")
	}, 3000);

}

function revokeUnsuccessful(message, messageBarId) {
	unsetLoadingUnsuccessful()
	buildAndShowMessageBar(message, messageBarId)
	$("#page_content_revoke").addClass("page_content_move_down")

	window.setTimeout(function() {
		$("#messagebar_revoke").addClass("messagebar_hidden")
		$("#page_content_revoke").removeClass("page_content_move_down")
	}, 3000);
}