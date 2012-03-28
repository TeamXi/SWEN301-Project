function submitNewCarrierForm(id) {
	submitCarrierForm(document.getElementById(id), "carrier?new", function () {
		$('#carrierAdded').modal('show');
	});
}

function submitCarrierForm(element, url, callback) {
	if(!validateCarrierForm(element)) return;
	
	submitForm($(element), url, function(data){
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
			callback();
			
			$.get("carrier?listfragment", function (data) {
				$("#carrierListContainer").html(data);
			});
		}
	});
}

function submitUpdateCarrierForm(formId, carrierId) {
	var element = document.getElementById(formId);
	
	if(!validateCarrierForm(element)) return;
	
	submitCarrierForm(element, "carrier?update&carrierId="+carrierId, function () {
		$('#updateCarrierModal').modal('hide');
	});
}

function submitUpdateModal() {
	document.getElementById('updateCarrierForm').submit();
}

function updateCarrier(carrierID) {
	$('#updateCarrierModal').modal('show');
	$.get("carrier?updateform&carrierId="+carrierID, function (data) {
		$("#updateFormContainer").html(data);
	});
}

function validateCarrierForm(form) {
	var ok = true;
	$('.validate-error-message', form).each(function(index, child) {$(child).remove();});

	if(!form.elements['name'].value) {
		ok = false;
		validationError(form.elements['name'], "Please enter a name");
	}
	
	return ok;
}

$(document).ready(function() {
	$('#carrierAdded').modal({show:false, keyboard:true, backdrop: true});
	
	$('#updateCarrierModal').modal({show:false, keyboard:true, backdrop: true});
});