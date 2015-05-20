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
// ================================== MAIN FUNCTIONS ==================================//
// ====================================================================================//
function logout() {
	document.form_logout.submit()
}

function extendCertificate() {
	setLoading(1)
//	$.ajax({
//		url : '../RequestCertificateServlet',
//		type : 'POST',
//		dataType : 'json',
//		timeout : 8000,
//		success : function(data) {
//			if (data.validSession && data.createdRequest) {
				successful(1, "SCS_CERT_REQUEST")
//			} else if (data.validSession && data.createdRequest === false) {
//				unsuccessful("ERR_CERT_REQUEST")
//			} else {
//				window.setTimeout(function() {
//					location.href = '../pages/login.jsp';
//				}, 1000);
//			}
//		},
//		error : function() {
//			unsuccessful("ERR_CONNECTION")
//		}
//	})
}

function activateCertificate() {
	setLoading(2)
	$.ajax({
		url : '../ActivateCertificateServlet',
		type : 'POST',
		dataType : 'json',
		data : $('#password').serialize(),
		timeout : 8000,
		success : function(data) {
			if (data.validSession && data.activatedCertificate) {
				successful(2, "SCS_CERT_ACTIIVATION")
			} else if (data.validSession && data.activatedCertificate === false) {
				unsuccessful("ERR_CERT_ACTIVATION")
			} else {
				window.setTimeout(function() {
					location.href = '../pages/login.jsp';
				}, 1000);
			}
		},
		error : function() {
			unsuccessful("ERR_CONNECTION")
		}
	})
}

function setLoading(stepNumber) {
	$("#step" + stepNumber + "_panel_body_extend").addClass("panel_next_step_or_loading")
	$("#loading_gif_extend").addClass("loading_gif_request_step" + stepNumber)
	$("#loading_gif_extend").fadeIn()
	$("#" + stepNumber + "_button_extend").addClass("disabled")
	$("#" + stepNumber + "_button_extend").removeAttr("onclick")
}

function unsetLoadingUnsuccessful(stepNumber) {
	$("#step" + stepNumber + "_panel_body_extend").removeClass("panel_next_step_or_loading")
	$("#loading_gif_extend").fadeOut()
	$("#" + stepNumber + "_button_extend").removeClass("disabled")
	$("#" + stepNumber + "_button_extend").attr("onclick", "requestCertificate()")
}

function unsetLoadingSuccessful(type) {
	$("#loading_gif_extend").fadeOut()
}

function successful(stepNumber, message) {
	var stepNumberNextStep = stepNumber + 1
	unsetLoadingSuccessful()
	buildAndShowMessageBar(message, "messagebar_extend")
	$("#page_content_extend").addClass("page_content_move_down")
	if(stepNumber > 1) {	
	window.setTimeout(function() {
		$("#messagebar_extend").addClass("messagebar_hidden")
		$("#page_content_extend").removeClass("page_content_move_down")
		$("#step" + stepNumber + "_content_extend").slideUp()
		$("#step" + stepNumber + "_icon_extend").addClass("messageicon_border_success")
		$("#step" + stepNumber + "_iconfont_extend").addClass("messageicon_success")
		$("#step" + stepNumber + "_header_extend").addClass("panelheader_completed")			
		$("#step" + stepNumberNextStep + "_content_extend").slideDown()
		$("#step" + stepNumberNextStep + "_header_extend").addClass("panelheader_set_current_step")		
	}, 3000);
	} else {
		window.setTimeout(function() {
			$("#step" + stepNumber + "_content_extend").slideUp()
			$("#step" + stepNumber + "_icon_extend").addClass("messageicon_border_success")
			$("#step" + stepNumber + "_iconfont_extend").addClass("messageicon_success")
			$("#step" + stepNumber + "_header_extend").addClass("panelheader_completed")	
			buildAndShowMessageBar("WRN_CERT_REQUEST_WAITING", "messagebar_extend")
		}, 3000);		
	}
}

function unsuccessful(message) {
	unsetLoadingUnsuccessful()
	buildAndShowMessageBar(message, "messagebar_extend")
	$("#page_content_extend").addClass("page_content_move_down")

	window.setTimeout(function() {
		$("#messagebar_extend").addClass("messagebar_hidden")
		$("#page_content_extend").removeClass("page_content_move_down")
	}, 3000);
}