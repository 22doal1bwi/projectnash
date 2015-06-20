/**
 * 
 * This file provides all methods for home.jsp.
 * 
 * @author Jonathan Schlotz
 *
 */

// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//

// document.ready would be to early for mozilla firefox
$(window).load(function() {
	setSameHeight()	
})

$(window).resize(function () {
	setSameHeight()
})

//====================================================================================//
//============================== TILES RESIZE FUNCTION ===============================//
//====================================================================================//

/**
 * Determines the highest tile of all tiles and sets this height to the other tiles.
 * 
 */
function setSameHeight() {
	var height
	$("#panel1, #panel2, #panel3, #panel4, #panel5").removeAttr("style")
	height = Math.max($("#panel1_h").height(), $("#panel2_h").height(), $(
			"#panel3_h").height(), $("#panel4_h").height(), $("#panel5_h")
			.height())
	height = height + "px"
	$("#panel1, #panel2, #panel3, #panel4, #panel5").attr("style",
			"height:" + height)
}