function performLogin(siteRoot,role){
	$.post(siteRoot+"/login", {role: role}, function(data) {
		redirect(siteRoot+"/dashboard");
	});
}

function redirect(url) {
	window.location = url;
}