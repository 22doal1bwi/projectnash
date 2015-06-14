function setNavItemActive() {
	$("a").removeClass('active')
	var src = document.getElementById("app_content").src.split("/")
	src = src[src.length - 1]
	src = src.split(".")[0]
	$('a').removeClass('active')
	if (src === "request_certificate" || src === "show_certificate") {
		$("#a_req_show_certificate").addClass('active')
	} else {
		$("#a_" + src).addClass('active')
	}
}

function setFrameSrc(src) {	
	if(document.getElementById("app_content") !== null) {
		document.getElementById("app_content").src = src + '.jsp'
	}
	if(window.frameElement !== null) {
		window.frameElement.src = src + '.jsp'
	}
}