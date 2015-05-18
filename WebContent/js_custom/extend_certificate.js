// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {
	$("#step2_content_extend").hide()

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

//function extendCertificate() {
//	setLoading()
//	alert("To be implemented")
//	$.ajax({
//		url : '../CertificateServlet',
//		type : 'POST',
//		dataType : 'json',
//		timeout : 8000,
//		success : function(data) {
//			if (data.validSession && data.createdCertificate) {
//				requestSuccessful()
//			} else if (data.validSession && data.createdCertificate === false) {
//				requestUnsuccessful("ERR_CERT_REQUEST", "messagebar_extend")
//			} else {
//				window.setTimeout(function() {
//					location.href = '../home.jsp';
//				}, 1000);
//			}
//		},
//		error : function() {
//			requestUnsuccessful("ERR_CONNECTION", "messagebar_extend")
//		}
//	})
//}

function extendCertificate() {

	$.ajax({
		url : '../AdminRequestServlet',
		type : 'POST',
		dataType : 'json',
		timeout : 8000,
		success : function(data) {
		
		}
	})
}

function setLoading() {
	$("#step1_panel_body_extend").addClass("panel_next_step_or_loading")
	$("#loading_gif_extend").fadeIn()
	$("#button_extend").addClass("disabled")
	$("#button_extend").removeAttr("onclick")
}

function unsetLoadingUnsuccessful() {
	$("#step1_panel_body_extend").removeClass("panel_next_step_or_loading")
	$("#loading_gif_extend").fadeOut()
	$("#button_extend").removeClass("disabled")
	$("#button_extend").attr("onclick", "requestCertificate()")
}

function unsetLoadingSuccessful() {
	$("#loading_gif_extend").fadeOut()
}

function requestSuccessful() {
	unsetLoadingSuccessful()
	buildAndShowMessageBar("SCS_CERT_REQUEST", "messagebar_extend")
	$("#page_content_extend").addClass("page_content_move_down")
	window.setTimeout(function() {
		$("#messagebar_extend").addClass("messagebar_hidden")
		$("#page_content_extend").removeClass("page_content_move_down")
		$("#step1_content_extend").slideUp()
		$("#step1_icon_extend").addClass("messageicon_border_success")
		$("#step1_iconfont_extend").addClass("messageicon_success")
		$("#step1_header_extend").addClass("panelheader_completed")
		$("#step2_content_extend").slideDown()
		$("#step2_header_extend").addClass("panelheader_set_current_step")
	}, 3000);

}

function requestUnsuccessful(message, messageBarId) {
	unsetLoadingUnsuccessful()
	buildAndShowMessageBar(message, messageBarId)
	$("#page_content_extend").addClass("page_content_move_down")

	window.setTimeout(function() {
		$("#messagebar_extend").addClass("messagebar_hidden")
		$("#page_content_extend").removeClass("page_content_move_down")
	}, 3000);
}