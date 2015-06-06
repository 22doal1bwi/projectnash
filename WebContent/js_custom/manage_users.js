// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {
	createTable()
});
//====================================================================================//
//================================= MAIN FUNCTIONS ===================================//
//====================================================================================//
function createTable() {
	$("#users")
			.DataTable(
					{
						"language" : {
							"sEmptyTable" : "Keine Daten in der Tabelle vorhanden",
							"sInfo" : "_START_ bis _END_ von _TOTAL_ Einträgen",
							"sInfoEmpty" : "0 bis 0 von 0 Einträgen",
							"sInfoFiltered" : "(gefiltert von _MAX_ Einträgen)",
							"sInfoPostFix" : "",
							"sInfoThousands" : ".",
							"sLengthMenu" : "_MENU_ Einträge anzeigen",
							"sLoadingRecords" : "Wird geladen...",
							"sProcessing" : "Bitte warten...",
							"sSearch" : "Suchen",
							"sZeroRecords" : "Keine Einträge vorhanden.",
							"oPaginate" : {
								"sFirst" : "Erste",
								"sPrevious" : "Zurück",
								"sNext" : "Nächste",
								"sLast" : "Letzte"
							},
							"oAria" : {
								"sSortAscending" : ": aktivieren, um Spalte aufsteigend zu sortieren",
								"sSortDescending" : ": aktivieren, um Spalte absteigend zu sortieren"
							}
						},
						"sPaginationType" : "full_numbers",
						"ajax" : {
							"type" : "POST",
							"url" : "../AdminUsersServlet"
						},
						"order" : [ [ 0, "desc" ] ],
						responsive : true,
						"columns" : [ {
							"data" : "certificateDate"
						}, {
							"data" : "firstName"
						}, {
							"data" : "lastName"
						}, {
							"data" : "department"
						}, {
							"data" : "personalId"
						}, {
							"data" : "emailAddress"
						}, {
							"data" : "status"
						}, {
							"data" : "hasSession"
						} ],
						"createdRow" : function(row, data, index) {
							row.cells[6].classList.add("status_column")
							switch (data.status) {
							case "ACTIVE":
								row.cells[6].innerHTML = '<i class="fa fa-check-circle table_icon_accepted" title="Status: aktiv - Zum Ändern klicken">'
								row.cells[6].classList
										.add("editable_certificate_status")
								break
							case "REVOKED":
								row.cells[6].innerHTML = '<i class="fa fa-times-circle table_icon_revoked" title="Status: ungültig - Ändern des Status nicht möglich">'
								break
							case "OUTDATED":
								row.cells[6].innerHTML = '<i class="fa fa-history table_icon_outdated" title="Status: abgelaufen - Ändern des Status nicht möglich">'
								break
							case "NONE":
								row.cells[6].innerHTML = '<div title="Benutzer besitzt kein Zertifikat">-</div>'
								break
							}
							row.cells[7].classList.add("status_column")
							if (!data.hasSession) {
							row.cells[7].innerHTML = '<i class="fa fa-trash-o table_icon_trash" id='
									+ data.emailAddress
									+ ' onclick="confirmDelete(this)" title="Benutzer löschen">'
							} else {
								row.cells[7].innerHTML = '<i class="fa fa-laptop table_icon_session" title="Benutzer ist momentan eingeloggt...">'
							}
						},
						"fnInitComplete" : function() {
							var oTable = $('#users').dataTable();
							$("#users").removeAttr("style")
							$(".editable_certificate_status")
									.editable(
											"../AdminUpdateUsersServlet",
											{
												indicator : '<img class="loading_gif_table" src="../img/general/loading.gif" title="lädt...">',
												data : "{'ACTIVE':'aktiv','REVOKED':'ungültig'}",
												tooltip : "Wählen Sie den Status...",
												loadtext : "lädt...",
												type : "select",
												placeholder : "",
												onblur : 'ignore',
												submit : '<i class="fa fa-check table_edit_icon_confirm" title="Auswahl bestätigen">',
												cancel : '<i class="fa fa-times table_edit_icon_deny" title="Abbrechen">',
												submitdata : function() {
													if (this.firstChild.firstChild.value !== "ACTIVE") {
														var aPos = oTable
																.fnGetPosition(this);
														return {
															emailAddress : oTable
																	.fnGetData(aPos[0]).emailAddress,
															action : "revokeCertificate"
														}
													} 
												},
												callback : function(data) {
													oTable.fnDestroy();
													createTable()													
												}
											})
						}
					});
}

function confirmDelete(element) {
	var elementId = element.id
	element.parentElement.innerHTML = '<div><i id="'
			+ elementId
			+ '_confirm" class="fa fa-check table_edit_icon_confirm" style="display: inline" title="Bestätigen"></i><i id="'
			+ elementId
			+ '_deny"class="fa fa-times table_edit_icon_deny" style="display: inline" title="Abbrechen"></i>'
	document.getElementById(elementId + "_confirm").onclick = function() {
		deleteUser(this, elementId)
	}
	document.getElementById(elementId + "_deny").onclick = function() {
		resetRowColumn(this, elementId)
	}
}

function resetRowColumn(element, id) {
	element.parentElement.innerHTML = '<i class="fa fa-trash-o table_icon_trash" id='
			+ id + ' onclick="confirmDelete(this)" title="Benutzer löschen">'
}

function setLoading(element, id) {
	element.parentElement.innerHTML = '<img id="'
			+ id
			+ '_loading" class="loading_gif_table" src="../img/general/loading.gif" title="lädt...">'
	return document.getElementById(id + "_loading")
}

function deleteUser(element, id) {
	var loadingElement = setLoading(element, id)
	$.ajax({
		url : '../AdminUpdateUsersServlet',
		type : 'POST',
		dataType : 'json',
		data : "emailAddress=" + id + "&action=deleteUser",
		timeout : 8000,
		success : function(data) {
			if (data.deletedUser) {
				$('#users').dataTable().fnDestroy();
				createTable()
			} else {
				resetRowColumn(loadingElement, id)
			}
		},
		error : function() {
			resetRowColumn(loadingElement, id)
		}
	})
}