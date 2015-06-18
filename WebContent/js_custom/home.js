// ====================================================================================//
// ================================= INITIALIZATION ===================================//
// ====================================================================================//
$(document).ready(function() {	
	jQuery.i18n.properties({
		name : 'messages',
		path : '../i18n/',
		language : 'de',
		mode : 'map',
		encoding : 'UTF-8'
	});
});

// only needed for mozilla firefox (tile height bug)
$(window).load(function() {
	setSameHeight()	
})

//====================================================================================//
//================================ RESIZE FUNCTION ===================================//
//====================================================================================//
$(window).resize(function () {
	setSameHeight()
})

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