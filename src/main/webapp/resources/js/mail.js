function submitMailDeliveryForm(form) {
	var values = {};
	$(form).children("input").each(function(index, child) {
		values[child.name] = child.value;
	});
	$.post("mail?new", values, function(data) {
		alert(data);
	});
}

function isNumber(n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
}

function validateMailDeliveryForm(form) {
	var ok = true;
	$('.validate-error-message', form).each(function(index, child) {$(child).remove();});
	ok &= validateLocationField(form.elements['source']);
	ok &= validateLocationField(form.elements['destination']);
	if(form.elements['priority'].value == "placeholder") {
		ok = false;
		validationError(form.elements['priority'], "Please select a priority");
	}
	ok &= validateNumberField(form.elements['weight'], "Weight");
	ok &= validateNumberField(form.elements['volume'], "Volume");
	
	return ok;
}

function validateNumberField(element, name) {
	var isValidNumber = isNumber(element.value);
	if(!isValidNumber || element.value <= 0) {
		if(!isValidNumber) {
			validationError(element, name+" must be a number");
		}
		else {
			validationError(element, name+" must be larger than zero");
		}
		return false;
	}
	return true;
}

function validationError(element, message) {
	$(element).before('<div class="alert alert-error fade in validate-error-message">'+
			'<a class="close" data-dismiss="alert">&times;</a>'+
			message+
		'</div>');
	$(element).addClass('validate-error')
			  .focus(function() {
		$(element).removeClass('validate-error');
	});
}
function validateLocationField(element) {
	if(!isValidLocation(element.value)) {
		validationError(element, '"'+element.value+'" is not a valid location');
		return false;
	}
	return true;
	
}

function isValidLocation(name) {
	for(var n=0;n<locationList.length;n++) {
		if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
			return true;
		}
	}
	return false;
}

function isLocationInternational(name) {
	for(var n=0;n<locationList.length;n++) {
		if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
			return locationList[n].international;
		}
	}
	return undefined;
}

function updateMailPriorityDropdown() {
	var form = document.getElementById('newMailForm');
	var isInternational = isLocationInternational(form.elements['source'].value) || isLocationInternational(form.elements['destination'].value);
	var priorityElm = form.elements['priority'];
	$("option.international-priority", priorityElm).each(function(index, child) {
		child.disabled = !isInternational;
	});
	$("option.domestic-priority", priorityElm).each(function(index, child) {
		child.disabled = isInternational;
	});
	if($("option:selected", priorityElm).is(":disabled")) {
		priorityElm.value = "placeholder";
	}
}

$(document).ready(function() {
	$(".portEntry").each(function(index, child) {
					   $(child).typeahead({source: locationNames})
					   		   .blur(function () {
						   setTimeout(updateMailPriorityDropdown, 200);
					   });
				   });
	$("#newMailForm select[name='priority'] option[value!='placeholder']").each(function(index, child) {
		if(child.value.match('^International')) {
			$(child).addClass('international-priority');
		}
		else {
			$(child).addClass('domestic-priority');
		}
	});
});
