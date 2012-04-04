function submitMailDeliveryForm(id) {
	var form = document.getElementById(id);
	if(!validateMailDeliveryForm(form)) return;
	
	submitForm(form, "/kpsmart/event/mail?new", function(data){
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
			$("#emptyModalSuccessMessage").fadeIn(500,function(){
				setTimeout(function () {
					$("#emptyModalSuccessMessage").fadeOut(500);
				}, 1000);
			});
		}
		
	});
}

function createNewMail(){
	submitMailDeliveryForm('newMailForm');
}

function validateMailDeliveryForm(form) {
	var ok = true;
	removeValidationMessages(form);
	ok &= validateMailLocationField(form.elements['source']);
	ok &= validateMailLocationField(form.elements['destination']);
	if(form.elements['priority'].value == "placeholder") {
		ok = false;
		validationError(form.elements['priority'], "Please select a priority");
	}
	ok &= validateNumberField(form.elements['weight'], "Weight");
	ok &= validateNumberField(form.elements['volume'], "Volume");
	
	return ok;
}


function validateMailLocationField(element) {
	if(!isValidLocation(element.value)) {
		validationError(element, '"'+element.value+'" is not a valid location');
		return false;
	}
	return true;
	
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
	initilizeLocationData(function () {
		initilizePortEntryTypeahead(function (child) {
			$(child).blur(function () {
				setTimeout(updateMailPriorityDropdown, 200);
			});
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
	
	$('#mailDelivered').modal({show:false, keyboard:true, backdrop: true});
});
