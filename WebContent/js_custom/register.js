/**
 * 
 * This file provides all methods for register.jsp.
 * 
 * @author Jonathan Schlotz
 *
 */

//====================================================================================//
//================================== INITIALIZATION ==================================//
//====================================================================================//

$(document).ready(function() {
	determineContainerStyle()

	// Method that triggers the 'Absenden' button when the 'enter'-key is pressed
	$('#firstName, #lastName, #organizationalUnit, #personalId, #emailAddress, #emailAddress_confirm, #password, #password_confirm').keypress(function(e) {
		if (e.keyCode == 13)
			$('#registerButton').click();
	});
});

$(window).resize(function() {
	determineContainerStyle()
})

// ====================================================================================//
// ================================= MESSAGE REGISTRY =================================//
// ====================================================================================//

// Define global array to save the messages
messageRegistry = [];

/**
 * Adds a message to the message registry.
 * 
 * @param messageId The message id.
 *
 */
function addMessageToRegistry(messageId) {
	var found = false
	for (var i = 0; i < messageRegistry.length; i++) {
		if (messageRegistry[i] === messageId) {
			found = true
			break
		}
	}
	if (!found) {
		messageRegistry.push(messageId)
		buildAndShowMessageBar(messageId, "messagebar_register")
	}
}

/**
 * Removes a message from the message registry.
 * 
 * @param messageId The message id.
 *
 */
function removeMessageTypeFromRegistry(messageId) {
	for (var i = 0; i < messageRegistry.length; i++) {
		if (messageRegistry[i] === messageId) {
			messageRegistry.splice(i, 1)
		}
	}
}

/**
 * Gets a remaining message from the message registry if there is still one message left.
 *
 */
function getMessagesFromRegistry() {
	if (messageRegistry.length > 0) {
		var messageId = messageRegistry[messageRegistry.length - 1]
		buildAndShowMessageBar(messageId, "messagebar_register")
	} else {
		$("#messagebar_register").addClass("messagebar_hidden")
	}
}

/**
 * Checks if the message registry is empty.
 *
 * @return The boolean that describes whether the message bar is empty or not.
 *
 */
function isMessageRegistryEmpty() {
	if ((messageRegistry.length === 1 && messageRegistry[0] === "WRN_INPUT_PASSWORD") || messageRegistry.length === 0) {
		return true
	} else {
		return false
	}
}

// ====================================================================================//
// ================================== AJAX FUNCTIONS ==================================//
// ====================================================================================//

/**
 * Sends an ajax request to the backend to check if the entered value for the type of field already exists.
 * 
 * @param type The field id.
 * @return The boolean that describes whether the value of the field exists already in the database or not.
 *
 */
function inputDbCheck(type) {
	var notExists

	return $.ajax({
		url : '../CheckRegisterServlet',
		type : 'POST',
		dataType : 'json',
		data : $('#' + type).serialize(),
		success : function(data) {
			if (!data.alreadyExists) {
				cleanInputField(type)
				removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase() + "_DB")
				getMessagesFromRegistry()
				notExists = true
				return notExists
			} else {
				cleanInputField(type)
				$('#' + type).addClass("has-error")
				addMessageToRegistry("ERR_INPUT_" + type.toUpperCase() + "_DB")
				notExists = false
				return notExists
			}
		},
		error : function() {
			addMessageToRegistry("ERR_CONNECTION")
		}
	})
}

/**
 * Submits all the form data to the backend.
 *
 */
function submitRegisterForm() {
	$.ajax({
		url : '../RegisterServlet',
		type : 'POST',
		dataType : 'json',
		data : $('#firstName, #lastName, #organizationalUnit, #personalId, #emailAddress, #password').serialize(),
		success : function(data) {
			if (data.created) {
				addMessageToRegistry("SCS_REGISTRATION")
				window.setTimeout(function() {
					location.href = 'login.jsp';
				}, 3000);
			} else if (!data.created && data.personalIdAlreadyExists) {
				unsetLoading()
				addMessageToRegistry("ERR_INPUT_PERSONALID_DB")
				$("#personalId").addClass("has-error")
				$("#emailAddress").attr("onchange", "validateInput('emailAddress', 'ui_and_db')")
				$("#personalId").attr("onchange", "validateInput('personalId', 'ui_and_db')")
			} else if (!data.created && data.emailAddressAlreadyExists) {
				unsetLoading()
				addMessageToRegistry("ERR_INPUT_EMAILADDRESS_DB")
				$("#emailAddress").addClass("has-error")
				$("#emailAddress").attr("onchange", "validateInput('emailAddress', 'ui_and_db')")
				$("#personalId").attr("onchange", "validateInput('personalId', 'ui_and_db')")
			} else {
				unsetLoading()
				addMessageToRegistry("ERR_REGISTRATION")
				removeMessageTypeFromRegistry("ERR_REGISTRATION")
				window.setTimeout(function() {
					getMessagesFromRegistry()
				}, 3000);
				$("#emailAddress").attr("onchange", "validateInput('emailAddress', 'ui_and_db')")
				$("#personalId").attr("onchange", "validateInput('personalId', 'ui_and_db')")
			}
		},
		error : function() {
			unsetLoading()
			addMessageToRegistry("ERR_CONNECTION")
			removeMessageTypeFromRegistry("ERR_CONNECTION")
			window.setTimeout(function() {
				getMessagesFromRegistry()
			}, 3000);
			$("#emailAddress").attr("onchange", "validateInput('emailAddress', 'ui_and_db')")
			$("#personalId").attr("onchange", "validateInput('personalId', 'ui_and_db')")
		}
	})
}

// ====================================================================================//
// ================================== MAIN FUNCTIONS ==================================//
// ====================================================================================//

/**
 * Determines the register container style for responsiveness.
 *
 */
function determineContainerStyle() {
	if ($(window).height() < "555") {
		if ($("#register_container").hasClass("container_free")) {
			$("#register_container").removeClass("container_free")
		}
		$("#register_container").addClass("container_fitting")
	} else if ($(window).height() >= "555" && $(window).height() < "835") {
		if ($(window).width() < "1200") {
			if ($("#register_container").hasClass("container_free")) {
				$("#register_container").removeClass("container_free")
			}
			$("#register_container").addClass("container_fitting")
		} else if ($(window).width() >= "1200") {
			if ($("#register_container").hasClass("container_fitting")) {
				$("#register_container").removeClass("container_fitting")
			}
			$("#register_container").addClass("container_free")
		}
	} else if ($(window).height() >= "835") {
		if ($("#register_container").hasClass("container_fitting")) {
			$("#register_container").removeClass("container_fitting")
		}
		$("#register_container").addClass("container_free")
	}
}

/**
 * Checks the validity of the input values.
 * 
 * @param type The field id.
 * @param kindOfCheck The kind of check: 'ui_only' (syntax check) or 'ui_and_db' (syntax check - then database check, if value already exists).
 * @return The boolean that describes whether the value of the field is valid or not.
 *
 */
function validateInput(type, kindOfCheck) {
	var regEx

	switch (kindOfCheck) {

	case "ui_and_db":

		switch (type) {

		case "personalId":
			regEx = /^\d{1,6}$/;
			break

		case "emailAddress":
			regEx = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			break
		}
		if ($("#" + type).val() !== "" && !regEx.test($("#" + type).val())) {
			cleanInputField(type)
			addMessageToRegistry("ERR_INPUT_" + type.toUpperCase() + "_UI")
			$("#" + type).addClass("has-error")
			return false
		} else if ($("#" + type).val() !== "" && regEx.test($("#" + type).val())) {
			if (type === "emailAddress") {
				compareInputField(type)
			}
			cleanInputField(type)
			removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase() + "_UI")
			getMessagesFromRegistry()
			if (inputDbCheck(type).success(function(notExists) {
				return notExists
			})) {
				cleanInputField(type)
				removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase() + "_DB")
				getMessagesFromRegistry()
				return true
			} else {
				addMessageToRegistry("ERR_INPUT_" + type.toUpperCase() + "_DB")
			}

		} else if ($("#" + type).val() === "") {
			removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase() + "_UI")
			removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase() + "_DB")
			getMessagesFromRegistry()
			cleanInputField(type)
			return false
		}
		break

	case "ui_only":

		switch (type) {

		case "organizationalUnit":
			if ($("#" + type).val() !== "") {
				cleanInputField(type)
			}
			break

		case "firstName":
			regEx = /^[A-Za-zßÄÖÜäöüé\'\-\ ]+$/;
			break

		case "lastName":
			regEx = /^[A-Za-zßÄÖÜäöüé\'\-\ ]+$/;
			break

		case "password":
		case "password_confirm":
			regEx = /.{6}/;
			break
		}
	}

	if (type === "firstName" || type === "lastName") {
		if ($("#" + type).val() !== "" && !regEx.test($("#" + type).val())) {
			addMessageToRegistry("ERR_INPUT_" + type.toUpperCase())
			cleanInputField(type)
			$("#" + type).addClass("has-error")
			return false
		} else {
			removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase())
			getMessagesFromRegistry()
			cleanInputField(type)
			return true
		}
	} else if (type === "password" || type === "password_confirm") {
		if ($("#" + type).val() !== "" && !regEx.test($("#" + type).val())) {
			addMessageToRegistry("WRN_INPUT_PASSWORD")
			cleanInputField(type)
			$("#" + type).addClass("has-warning")
		} else {
			removeMessageTypeFromRegistry("WRN_INPUT_PASSWORD")
			getMessagesFromRegistry()
			cleanInputField(type)
		}
	}

}

/**
 * Compares the value of two fields witch each other.
 * 
 * @param type The field id.
 * @return The boolean that describes whether the value of the fields are identical.
 *
 */
function compareInputField(type) {
	// Compare field values and add style classes according to the result
	if ($("#" + type).val() !== "" && $("#" + type + "_confirm").val() !== "") {
		if ($("#" + type).val() !== $("#" + type + "_confirm").val()) {
			cleanInputField(type + "_confirm")
			$("#" + type + "_confirm").addClass("has-error")
			addMessageToRegistry("ERR_INPUT_" + type.toUpperCase() + "_COMP")
			return false;
		} else {
			cleanInputField(type + "_confirm")
			removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase() + "_COMP")
			if (type === "password") {
				validateInput("password_confirm", "ui_only")
			}
			getMessagesFromRegistry()
			return true;
		}
	} else {
		cleanInputField(type + "_confirm")
		removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase() + "_COMP")
		getMessagesFromRegistry()
	}
}

/**
 * Checks all form data before submitting it to the backend.
 *
 */
function checkFormBeforeSubmit() {
	var inputFields = [], invalidField = false, emptyField = false
	// Get all field values
	inputFields[0] = document.getElementById('firstName')
	inputFields[1] = document.getElementById('lastName')
	inputFields[2] = document.getElementById('organizationalUnit')
	inputFields[3] = document.getElementById('personalId')
	inputFields[4] = document.getElementById('emailAddress')
	inputFields[5] = document.getElementById('emailAddress_confirm')
	inputFields[6] = document.getElementById('password')
	inputFields[7] = document.getElementById('password_confirm')

	// All fields have to be filled
	for (var i = 0; i < inputFields.length; i++) {
		if (inputFields[i].value === "") {
			emptyField = true;
			inputFields[i].classList.add("has-warning")
		}
	}
	if (!emptyField) {
		removeMessageTypeFromRegistry("WRN_EMPTY_FIELDS_REGISTRATION")
		if (compareInputField("emailAddress") && compareInputField("password") && isMessageRegistryEmpty() && validateInput("firstName", "ui_only")
				&& validateInput("lastName", "ui_only")) {
			$("#emailAddress, #personalId").removeAttr("onchange")
			setLoading()
			submitRegisterForm()
		} else {
			$("#emailAddress").attr("onchange", "validateInput('emailAddress', 'ui_and_db')")
			$("#personalId").attr("onchange", "validateInput('personalId', 'ui_and_db')")
		}

	} else {
		addMessageToRegistry("WRN_EMPTY_FIELDS_REGISTRATION")
	}
}

/**
 * Sets the ui to a loading state.
 *
 */
function setLoading() {
	$("#loading_gif_register").fadeIn()
	$("#registerButton").attr("disabled", "");
	$("#registerButton").removeAttr("onclick")
}

/**
 * Unsets the ui from the loading state.
 *
 */
function unsetLoading() {
	$("#loading_gif_register").fadeOut(200)
	$("#registerButton").removeAttr("disabled");
	$("#registerButton").attr("onclick", "checkFormBeforeSubmit()")
}

/**
 * Resets a field to its initial state.
 * 
 * @param type The field id.
 *
 */
function cleanInputField(type) {
	if ($("#" + type).hasClass("has-warning")) {
		$("#" + type).removeClass("has-warning")
	}
	if ($("#" + type).hasClass("has-error")) {
		$("#" + type).removeClass("has-error")
	}
}