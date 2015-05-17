// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {
	$("#loading_gif_revoke").hide()

	jQuery.i18n.properties({
		name : 'messages',
		path : '../i18n/',
		language : 'de',
		mode : 'map',
		encoding : 'UTF-8'
	});
});

// ====================================================================================//
// ================================== MAIN FUNCTIONS ==================================//
// ====================================================================================//
function logout() {
	document.form_logout.submit()
}

function revokeCertificate() {
	if ($("#textfield_revoke").val() !== "") {
		$("#textfield_revoke").attr("disabled", "")
		$("#messagebar_revoke").addClass("messagebar_hidden")
		$("#page_content_revoke").removeClass("page_content_move_down")
		$("#footer_revoke")
				.html(
						'<button id="button_revoke" onclick="cancelRevoke()" type="button"'
								+ 'class="btn simplecert_inv_btn">Abbrechen</button><button id="button_revoke" onclick="confirmRevoke()"'
								+ 'type="button" class="btn simplecert_btn">Bestätigen</button>')
	} else {
		buildAndShowMessageBar("WRN_EMPTY_FIELD_REASON", "messagebar_revoke")
		$("#page_content_revoke").addClass("page_content_move_down")
	}
}

function cancelRevoke() {
	$("#footer_revoke").html(
			'<button id="button_revoke" onclick="revokeCertificate()" type="button"'
					+ 'class="btn simplecert_btn">Widerrufen</button>')
	$("#textfield_revoke").removeAttr("disabled")
}

function confirmRevoke() {
	alert("To be implemented")
}