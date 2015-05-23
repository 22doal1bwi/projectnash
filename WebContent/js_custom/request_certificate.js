// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {
	$('#password').keypress(function(e) {
		if (e.keyCode == 13)
			$('#step2_button_request').click();
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
// ================================== AJAX FUNCTIONS ==================================//
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
				unsuccessful("ERR_CERT_REQUEST")
			} else {
				window.setTimeout(function() {
					location.href = 'login.jsp';
				}, 1000);
			}
		},
		error : function() {
			unsuccessful("ERR_CONNECTION")
		}
	})
}

function activateCertificate() {
	setLoading(2)
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
function logout() {
	document.form_logout.submit()
}

function onActivateClick() {
	if ($('#password').val() !== "") {
		setLoading(2)
		activateCertificate()
	} else {
		buildAndShowMessageBar("WRN_EMPTY_CERT_PWD", "messagebar_request")
		$("#page_content_request").addClass("page_content_move_down")
		window.setTimeout(function() {
			$("#messagebar_request").addClass("messagebar_hidden")
			$("#page_content_request").removeClass("page_content_move_down")
		}, 3000);
	}
}

function setLoading(stepNumber) {
	$("#step" + stepNumber + "_panel_body_request").addClass(
			"panel_next_step_or_loading")
	$("#loading_gif_request").addClass("loading_gif_request_step" + stepNumber)
	$("#loading_gif_request").fadeIn()
	$("#step" + stepNumber + "_button_request").attr("disabled", "");
	$("#step" + stepNumber + "_button_request").removeAttr("onclick")
}

function unsetLoadingUnsuccessful(stepNumber) {
	$("#step" + stepNumber + "_panel_body_request").removeClass(
			"panel_next_step_or_loading")
	$("#loading_gif_request").fadeOut()
	$("#step" + stepNumber + "_button_request").removeAttr("disabled")
	if (stepNumber === 1) {
		$("#step" + stepNumber + "_button_request").attr("onclick",
				"requestCertificate()")
	} else if (stepNumber === 2) {
		$("#step" + stepNumber + "_button_request").attr("onclick",
				"onActivateClick()")
	}
}

function unsetLoadingSuccessful() {
	$("#loading_gif_request").fadeOut()
}

function successful(stepNumber, message) {
	var stepNumberNextStep = stepNumber + 1
	unsetLoadingSuccessful()
	buildAndShowMessageBar(message, "messagebar_request")
	$("#page_content_request").addClass("page_content_move_down")
	if (stepNumber > 1) {
		window.setTimeout(function() {
			$("#messagebar_request").addClass("messagebar_hidden")
			$("#page_content_request").removeClass("page_content_move_down")
			$("#step" + stepNumber + "_content_request").slideUp()
			$("#step" + stepNumber + "_icon_request").addClass(
					"messageicon_border_success")
			$("#step" + stepNumber + "_iconfont_request").addClass(
					"messageicon_success")
			$("#step" + stepNumber + "_header_request").addClass(
					"panelheader_completed")
			$("#step" + stepNumberNextStep + "_content_request").slideDown()
			$("#step" + stepNumberNextStep + "_header_request").addClass(
					"panelheader_set_current_step")
		}, 1500);
	} else {
		window.setTimeout(function() {
			$("#step" + stepNumber + "_content_request").slideUp()
			$("#step" + stepNumber + "_icon_request").addClass(
					"messageicon_border_success")
			$("#step" + stepNumber + "_iconfont_request").addClass(
					"messageicon_success")
			$("#step" + stepNumber + "_header_request").addClass(
					"panelheader_completed")
			buildAndShowMessageBar("WRN_CERT_REQUEST_WAITING",
					"messagebar_request")
		}, 1500);
	}
}

function unsuccessful(stepNumber, message) {
	unsetLoadingUnsuccessful(stepNumber)
	buildAndShowMessageBar(message, "messagebar_request")
	$("#page_content_request").addClass("page_content_move_down")

	window.setTimeout(function() {
		$("#messagebar_request").addClass("messagebar_hidden")
		$("#page_content_request").removeClass("page_content_move_down")
	}, 1500);
}