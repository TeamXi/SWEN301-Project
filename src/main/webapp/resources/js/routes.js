function submitNewRouteForm(id) {
	if(!validateRouteForm(document.getElementById(id))) return;
	
	submitForm($("#newRouteForm"), "route?new", function(data){

			var returnObj = eval(data);
			var status = returnObj.status;
			
			if(!status){
				for(var error in returnObj.validation){
					validationError(
							$("input[name='"+returnObj.validation[error].name+"']"),
							returnObj.validation[error].message
					);
				}
			}else{
				$('#routeAdded').modal('show');
			}

	});
}



function validateRouteForm(form) {
	var ok = true;
	$('.validate-error-message', form).each(function(index, child) {$(child).remove();});
	ok &= validateLocationField(form.elements['source']);
	ok &= validateLocationField(form.elements['destination']);
	if(form.elements['transportType'].value == "placeholder") {
		ok = false;
		validationError(form.elements['transportType'], "Please select a transport type");
	}

	return ok;
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

$(document).ready(function() {
	$(".portEntry").each(function(index, child) {
					   $(child).typeahead({source: locationNames});
				   });
	$('#routeAdded').modal({show:false, keyboard:true, backdrop: true});
	
});
