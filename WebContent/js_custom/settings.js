// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(
		function() {
			$('#password_current, #password_new, #password_new_confirm')
					.keypress(function(e) {
						if (e.keyCode == 13)
							$('#button_confirm_password').click();
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
// ================================== AJAX FUNCTION
// ===================================//
// ====================================================================================//
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
					buildAndShowMessageBar("SCS_PWD_CHANGE",
							"messagebar_settings")
					$("#page_content_settings").addClass(
							"page_content_move_down")
					hideMessageBar()
				}, 1000);
			} else {
				unsetLoading()
				buildAndShowMessageBar("ERR_PWD_CHANGE", "messagebar_settings")
				$("#page_content_settings").addClass("page_content_move_down")
				hideMessageBar()
			}
		},
		error : function() {
			unsetLoading()
			buildAndShowMessageBar("ERR_CONNECTION", "messagebar_settings")
			$("#page_content_settings").addClass("page_content_move_down")
			hideMessageBar()
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

function changePassword() {
	$("#button_change_password").hide()
	$(
			"#password_current, #password_new, #password_new_confirm, #button_cancel_password, #button_confirm_password")
			.fadeIn()
}
function hideMessageBar() {
	window.setTimeout(function() {
		$("#messagebar_settings").addClass("messagebar_hidden")
		$("#page_content_settings").removeClass("page_content_move_down")
	}, 3000);
}

function cleanPage() {
	$(
			"#password_current, #password_new, #password_new_confirm, #button_cancel_password, #button_confirm_password")
			.fadeOut().promise().done(function() {
				$("#button_change_password").fadeIn()
			})
	$("#messagebar_settings").addClass("messagebar_hidden")
	$("#page_content_settings").removeClass("page_content_move_down")
	$("#password_current, #password_new, #password_new_confirm").val("")
}

function confirmPasswordChange() {
	if ($("#password_current").val() !== "" && $("#password_new").val() !== ""
			&& $("#password_new_confirm").val() !== "") {
		$("#messagebar_settings").addClass("messagebar_hidden")
		$("#page_content_settings").removeClass("page_content_move_down")
		if ($("#password_new").val() === $("#password_new_confirm").val()) {
			$("#messagebar_settings").addClass("messagebar_hidden")
			$("#page_content_settings").removeClass("page_content_move_down")
			setLoading()
			updatePassword()
		} else {
			buildAndShowMessageBar("ERR_INPUT_PASSWORD_COMP",
					"messagebar_settings")
			$("#page_content_settings").addClass("page_content_move_down")
			hideMessageBar()
		}

	} else {
		buildAndShowMessageBar("WRN_EMPTY_FIELDS_PWD_CHANGE",
				"messagebar_settings")
		$("#page_content_settings").addClass("page_content_move_down")
		hideMessageBar()
	}
}

function setLoading() {
	$("#loading_gif_settings").fadeIn()
	$("#button_confirm_password").addClass("disabled")
	$("#button_confirm_password").removeAttr("onclick")
	$("#button_cancel_password").addClass("disabled")
	$("#button_cancel_password").removeAttr("onclick")
}

function unsetLoading() {
	$("#loading_gif_settings").fadeOut()
	$("#button_confirm_password").removeClass("disabled")
	$("#button_confirm_password").attr("onclick", "confirmPasswordChange()")
	$("#button_cancel_password").removeClass("disabled")
	$("#button_cancel_password").attr("onclick", "cleanPage('password')")
}