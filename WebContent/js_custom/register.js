//====================================================================================//
//================================== INITIALIZATION ==================================//
//====================================================================================//

// Method that triggers the login button when the 'enter'-key is pressed
$(document)
		.ready(
				function() {
					$(
							'#firstName, #lastName, #organizationalUnit, #personalId, #emailAddress, #emailAddress_confirm, #password, #password_confirm')
							.keypress(function(e) {
								if (e.keyCode == 13)
									$('#registerButton').click();
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
// ================================= MESSAGE REGISTRY
// =================================//
// ====================================================================================//

messageRegistry = [];

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

function removeMessageTypeFromRegistry(messageId) {
	for (var i = 0; i < messageRegistry.length; i++) {
		if (messageRegistry[i] === messageId) {
			messageRegistry.splice(i, 1)
		}
	}
}

function getMessagesFromRegistry() {
	if (messageRegistry.length > 0) {
		var messageId = messageRegistry[messageRegistry.length - 1]
		buildAndShowMessageBar(messageId, "messagebar_register")
	} else {
		$("#messagebar_register").addClass("messagebar_hidden")
	}
}

function isMessageRegistryEmpty() {
	for (var i = 0; i < messageRegistry.length; i++) {
		if (messageRegistry[i] !== "WRN_INPUT_PASSWORD") {
			return false
		} else {
			return true
		}
	}
}

// ====================================================================================//
// ================================== AJAX FUNCTIONS
// ==================================//
// ====================================================================================//

// Method which checks whether the entered value for 'personalId' or
// 'emailAddress' already exists
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
				removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase()
						+ "_DB")
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

// Method which submits all data from the input fields
function submitRegisterForm() {
	$
			.ajax({
				url : '../RegisterServlet',
				type : 'POST',
				dataType : 'json',
				data : $(
						'#firstName, #lastName, #organizationalUnit, #personalId, #emailAddress, #password')
						.serialize(),
				success : function(data) {
					if (data.created) {
						addMessageToRegistry("SCS_REGISTRATION")
						window.setTimeout(function() {
							location.href = 'login.jsp';
						}, 3000);

					} else {
						addMessageToRegistry("ERR_REGISTRATION")
						$("#emailAddress").attr("onchange",
								"validateInput('emailAddress', 'ui_and_db')")
						$("#personalId").attr("onchange",
								"validateInput('personalId', 'ui_and_db')")
					}
				},
				error : function() {
					addMessageToRegistry("ERR_CONNECTION")
					$("#emailAddress").attr("onchange",
							"validateInput('emailAddress', 'ui_and_db')")
					$("#personalId").attr("onchange",
							"validateInput('personalId', 'ui_and_db')")
				}
			})
}

// ====================================================================================//
// ================================== MAIN FUNCTIONS
// ==================================//
// ====================================================================================//

// Method which checks input value for all fields
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
		} else if ($("#" + type).val() !== ""
				&& regEx.test($("#" + type).val())) {
			if (type === "emailAddress") {
				compareInputField(type)
			}
			cleanInputField(type)
			removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase()
					+ "_UI")
			getMessagesFromRegistry()
			if (inputDbCheck(type).success(function(notExists) {
				return notExists
			})) {				
				cleanInputField(type)
				removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase()
						+ "_DB")
				getMessagesFromRegistry()
			} else {
				addMessageToRegistry("ERR_INPUT_" + type.toUpperCase() + "_DB")
			}

		} else if ($("#" + type).val() === "") {
			removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase()
					+ "_UI")
			removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase()
					+ "_DB")
			getMessagesFromRegistry()
			cleanInputField(type)
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
			regEx = /^[A-Za-zßÄÖÜäöü\'\-\ ]+$/;
			break

		case "lastName":
			regEx = /^[A-Za-zßÄÖÜäöü\'\-\ ]+$/;
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
		} else {
			removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase())
			getMessagesFromRegistry()
			cleanInputField(type)
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

// Method which compares double email and password input
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
			removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase()
					+ "_COMP")
			if (type === "password") {
				validateInput("password_confirm", "ui_only")
			}
			getMessagesFromRegistry()
			return true;
		}
	} else {
		cleanInputField(type + "_confirm")
		removeMessageTypeFromRegistry("ERR_INPUT_" + type.toUpperCase()
				+ "_COMP")
		getMessagesFromRegistry()
	}
}

// Method which checks all field values before submitting them to the backend
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
		if (compareInputField("emailAddress") && compareInputField("password") && isMessageRegistryEmpty()) {
			$("#emailAddress, #personalId").removeAttr("onchange")
			submitRegisterForm()
		}

	} else {
		addMessageToRegistry("WRN_EMPTY_FIELDS_REGISTRATION")
	}
}

// ====================================================================================//
// ============================= LITTLE HELPER FUNCTIONS
// ==============================//
// ====================================================================================//

// Method which removes any style classes from an inputfield
function cleanInputField(type) {
	if ($("#" + type).hasClass("has-warning")) {
		$("#" + type).removeClass("has-warning")
	}
	if ($("#" + type).hasClass("has-error")) {
		$("#" + type).removeClass("has-error")
	}
}