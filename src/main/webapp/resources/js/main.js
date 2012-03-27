function performLogin(siteRoot,role){
	$.post(siteRoot+"/login", {role: role}, function(data) {
		redirect(siteRoot+"/dashboard");
	});
}

function redirect(url) {
	window.location = url;
}

function submitForm(form, url, callback) {
	var values = {};
	$(form).children("input").each(function(index, child) {
		values[child.name] = child.value;
	});
	$.post(url, values, function(data) {
		callback(data);
	});
}
function show(obj){
	alert(JSON.stringify(obj, null, "\t"));
}