// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document)
		.ready(
				function() {
					$("#requests")
							.DataTable(
									{
										"language" : {
										    "sEmptyTable":      "Keine Daten in der Tabelle vorhanden",
										    "sInfo":            "_START_ bis _END_ von _TOTAL_ Einträgen",
										    "sInfoEmpty":       "0 bis 0 von 0 Einträgen",
										    "sInfoFiltered":    "(gefiltert von _MAX_ Einträgen)",
										    "sInfoPostFix":     "",
										    "sInfoThousands":   ".",
										    "sLengthMenu":      "_MENU_ Einträge anzeigen",
										    "sLoadingRecords":  "Wird geladen...",
										    "sProcessing":      "Bitte warten...",
										    "sSearch":          "Suchen",
										    "sZeroRecords":     "Keine Einträge vorhanden.",
										    "oPaginate": {
										        "sFirst":       "Erste",
										        "sPrevious":    "Zurück",
										        "sNext":        "Nächste",
										        "sLast":        "Letzte"
										    },
										    "oAria": {
										        "sSortAscending":  ": aktivieren, um Spalte aufsteigend zu sortieren",
										        "sSortDescending": ": aktivieren, um Spalte absteigend zu sortieren"
										    }
										},
										"sPaginationType" : "full_numbers",
										"ajax" : {
											"type" : "POST",
											"url" : "../AdminRequestServlet"
										},
										"order" : [ [ 0, "desc" ] ],
										responsive : true,
										"columns" : [ {
											"data" : "requestCreationDate"
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
										} ],
										"createdRow" : function(row, data,
												index) {
											row.cells[6].classList
													.add("status_column")
											switch (data.status) {
											case "WAITING":
												row.cells[6].innerHTML = '<i class="fa fa-question-circle table_icon_waiting">'
												break
											case "ACCEPTED":
												row.cells[6].innerHTML = '<i class="fa fa-check-circle table_icon_accepted">'
												break
											case "DENIED":
												row.cells[6].innerHTML = '<i class="fa fa-times-circle table_icon_denied">'
												break
											}
										},
										"fnDrawCallback" : function() {
											var oTable = $('#requests')
													.dataTable();
											$('#requests tbody td:nth-child(7)')
													.editable(
															"../AdminUpdateServlet",
															{
																indicator : '<img class="loading_gif_table" src="../img/general/loading.gif">',
																data : "{'WAITING':'wartet','ACCEPTED':'genehmigt','DENIED':'abgelehnt'}",
																tooltip : "Wählen Sie den Status...",
																loadtext : "lädt...",
																type : "select",
																onblur : 'ignore',
																submit : '<i class="fa fa-check table_edit_icon_confirm">',
																cancel : '<i class="fa fa-times table_edit_icon_deny">',
																submitdata : function() {
																	var updatedUser = {}, aPos = oTable
																			.fnGetPosition(this);
																	return {
																		emailAddress : oTable
																				.fnGetData(aPos[0]).emailAddress
																	}
																},
																callback : function(
																		data) {
																	this.classList
																			.add("status_column")
																	switch (data) {
																	case "WAITING":
																		this.innerHTML = '<i class="fa fa-question-circle table_icon_waiting">'
																		break
																	case "ACCEPTED":
																		this.innerHTML = '<i class="fa fa-check-circle table_icon_accepted">'
																		break
																	case "DENIED":
																		this.innerHTML = '<i class="fa fa-times-circle table_icon_denied">'
																		break
																	}
																}
															})
										},
										"fnInitComplete" : function() {
											$("#requests").removeAttr("style")
										}
									});
				});