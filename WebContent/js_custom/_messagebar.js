function buildAndShowMessageBar(message, messageBarId) {
	var messagebarContent, styleMessagebar, iconBorderColor, iconColor, iconType, kindOfMessage = message
			.split("_")[0]

	switch (kindOfMessage) {

	// Error
	case "ERR":
		iconBorderColor = "messageicon_border_error"
		iconType = "fa-times"
		iconColor = "messageicon_error"
		styleMessagebar = "alert-danger"
		break

		// Warning
	case "WRN":
		iconBorderColor = "messageicon_border_warning"
		iconType = "fa-exclamation"
		iconColor = "messageicon_warning"
		styleMessagebar = "alert-warning"
		break

		// Success
	case "SCS":
		iconBorderColor = "messageicon_border_success"
		iconType = "fa-check"
		iconColor = "messageicon_success"
		styleMessagebar = "alert-success"
		break
	}
	messagebarContent = '<table><tr><td><div class="btn btn-default btn-circle messagebar_icon '
			+ iconBorderColor + '"> <i class="fa ' + iconType + ' ' + iconColor
			+ '"></i></div></td><td>' + jQuery.i18n.prop(message) + '</td></tr></table>'
			
	// Remove any style classes
	$("#" + messageBarId).html(messagebarContent)
	if ($("#" + messageBarId).hasClass("alert-success")) {
		$("#" + messageBarId).removeClass("alert-success")
	}
	if ($("#" + messageBarId).hasClass("alert-warning")) {
		$("#" + messageBarId).removeClass("alert-warning")
	}
	if ($("#" + messageBarId).hasClass("alert-danger")) {
		$("#" + messageBarId).removeClass("alert-danger")
	}
	$("#" + messageBarId).addClass(styleMessagebar)
	$("#" + messageBarId).removeClass("messagebar_hidden")
}