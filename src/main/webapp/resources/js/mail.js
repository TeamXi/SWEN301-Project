function submitMailDeliveryForm(form) {
	var values = {};
	$(form).children("input").each(function (index, child) {
		values[child.name] = child.value;
	});
	$.post("mail?new", values, function (data) {
		alert(data);
	});
}

$(document).ready(function() {$(".portEntry").typeahead({source: locationList});});
