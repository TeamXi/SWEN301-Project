function submitNewCarrierForm(id) {
	if(!validateCarrierForm(document.getElementById(id))) return;
	
	submitForm($("#newCarrierForm"), "carrier?new", function(data){

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
					$('#carrierAdded').modal('show');
			}

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
	
});