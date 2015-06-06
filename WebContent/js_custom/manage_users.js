// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document)
		.ready(
				function() {

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
											"data" : "status"
										} ],
										"createdRow" : function(row, data,
												index) {
											row.cells[6].classList
													.add("status_column")
											switch (data.status) {
											case "ACTIVE":
												row.cells[6].innerHTML = '<i class="fa fa-check-circle table_icon_accepted">'
												row.cells[6].classList
														.add("editable_certificate_status")
												break
											case "REVOKED":
												row.cells[6].innerHTML = '<i class="fa fa-times-circle table_icon_denied">'
												break
											case "OUTDATED":
												row.cells[6].innerHTML = '<i class="fa fa-history table_icon_outdated">'
												break
											case "NONE":
												row.cells[6].innerHTML = '-'
												break
											}
											row.cells[7].classList
													.add("status_column")
											row.cells[7].innerHTML = '<i class="fa fa-trash-o table_icon_trash" id='
													+ data.emailAddress
													+ ' onclick="confirmDelete(this)">'
										},
										"fnInitComplete" : function() {
											var oTable = $('#users')
													.dataTable();
											$("#users").removeAttr("style")
											$(".editable_certificate_status")
													.editable(
															"../AdminUpdateUsersServlet",
															{
																indicator : '<img class="loading_gif_table" src="../img/general/loading.gif">',
																data : "{'ACTIVE':'aktiv','REVOKED':'ungültig'}",
																tooltip : "Wählen Sie den Status...",
																loadtext : "lädt...",
																type : "select",
																placeholder : "",
																onblur : 'ignore',
																submit : '<i class="fa fa-check table_edit_icon_confirm">',
																cancel : '<i class="fa fa-times table_edit_icon_deny">',
																submitdata : function() {
																	var updatedUser = {}, aPos = oTable
																			.fnGetPosition(this);
																	return {
																		emailAddress : oTable
																				.fnGetData(aPos[0]).emailAddress,
																		action : "revokeCertificate"
																	}
																},
																callback : function(
																		data) {
																	this.classList
																			.add("status_column")
																	switch (data.revokedCertificate) {
																	case false:
																		this.innerHTML = '<i class="fa fa-check-circle table_icon_accepted">'
																		break
																	case true:
																		this.innerHTML = '<i class="fa fa-times-circle table_icon_denied">'
																		break
																	}
																}
															})
										}
									});
				});

function confirmDelete(element) {
	var elementId = element.id
	element.parentElement.innerHTML = '<i id="'
			+ elementId
			+ '_confirm" class="fa fa-check table_edit_icon_confirm"><i class="fa fa-times table_edit_icon_deny">'
	document.getElementById(elementId + "_confirm").onclick = function() {
		deleteUser(elementId)
	}
}

function deleteUser(id) {
	// setLoading()
	$.ajax({
		url : '../AdminUpdateUsersServlet',
		type : 'POST',
		dataType : 'json',
		data : "emailAddress=" + id + "&action=deleteUser",
		timeout : 8000,
		success : function(data) {
			if (data.deletedUser) {
				alert("deleted")
			} else {
				alert("not_deleted")
			}
		},
		error : function() {

		}
	})
}