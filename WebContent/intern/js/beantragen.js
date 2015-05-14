// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {
	$("#step2_content_request").hide()
	$("#loading_gif_request").hide()

	jQuery.i18n.properties({
		name : 'messages',
		path : '../../resources/i18n/',
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

function requestCertificate() {
	setLoading()
	$.ajax({
		url : '../../CertificateServlet',
		type : 'POST',
		dataType : 'json',
		timeout : 8000,
		success : function(data) {
			if (data.validSession && data.createdCertificate) {
				requestSuccessful()
			} else if (data.validSession && data.createdCertificate === false) {
				requestUnsuccessful()
			} else {
				window.setTimeout(function() {
					location.href = 'index.jsp';
				}, 1000);
			}
		},
		error : function() {
			buildAndShowMessageBar("ERR_CONNECTION", "messagebar_request")
		}
	})
}

function setLoading() {
	$("#step1_panel_body_request").addClass("panel_next_step_or_loading")
	$("#loading_gif_request").fadeIn()
	$("#button_request").addClass("disabled")
	$("#button_request").removeAttr("onclick")
}

function unsetLoadingUnsuccessful() {
	$("#step1_panel_body_request").removeClass("panel_next_step_or_loading")
	$("#loading_gif_request").fadeOut()
	$("#button_request").removeClass("disabled")
	$("#button_request").attr("onclick", "requestCertificate()")
}

function unsetLoadingSuccessful() {
	$("#loading_gif_request").fadeOut()
}

function requestSuccessful() {
	unsetLoadingSuccessful()
	buildAndShowMessageBar("SCS_CERT_REQUEST", "messagebar_request")
	$("#page_content_request").addClass("page_content_move_down")
	window.setTimeout(function() {
		$("#messagebar_request").addClass("messagebar_hidden")
		$("#page_content_request").removeClass("page_content_move_down")
		$("#step1_content_request").slideUp()
		$("#step1_icon_request").addClass("messageicon_border_success")
		$("#step1_iconfont_request").addClass("messageicon_success")
		$("#step1_header_request").addClass("panelheader_completed")
		$("#step2_content_request").slideDown()
		$("#step2_header_request").addClass("panelheader_set_current_step")
	}, 3000);

}

function requestUnsuccessful() {
	unsetLoadingUnsuccessful()
	buildAndShowMessageBar("ERR_CERT_REQUEST", "messagebar_request")
	$("#page_content_request").addClass("page_content_move_down")

	window.setTimeout(function() {
		$("#messagebar_request").addClass("messagebar_hidden")
		$("#page_content_request").removeClass("page_content_move_down")
	}, 3000);
}