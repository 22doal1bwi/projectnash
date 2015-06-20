/**
 * 
 * This file provides the methods to build the message bar.
 * 
 * @author Jonathan Schlotz
 *
 */

// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//

$(document).ready(function() {
	
	// Needed to get the messages for the message bar
	jQuery.i18n.properties({
		name : 'messages',
		path : '../i18n/',
		language : 'de',
		mode : 'map',
		encoding : 'UTF-8'
	});
});

//====================================================================================//
//================================= MAIN FUNCTION ====================================//
//====================================================================================//

/**
 * Builds the message bar based on the input parameters and displays it instantly.
 * 
 * @param message The message to be displayed.
 * @param messageBarId The id of the message bar which belongs to the respective page.
 * 
 */
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