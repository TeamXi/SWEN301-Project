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
		} else{
			callback(data);
			
			updateCarrierList();
		}
	});
}


function submitNewCarrierForm(id) {
	submitCarrierForm(document.getElementById(id), "carrier?new", function (data) {
		var response = eval(data);
		
		if(!response.status) {
			alert("There was an error adding this carrier.");
		}
		else {
			$("#addCarrierSuccessMessage").fadeIn(500,function(){
				setTimeout(function () {
					$("#addCarrierSuccessMessage").fadeOut(500);
				}, 1000);
			});
		}
	});
}

function submitAddModal() {
	document.getElementById('newCarrierForm').submit();
}


function submitUpdateCarrierForm(formId, carrierId) {	// TODO: merge with submitNewCarrierForm?
	submitCarrierForm(document.getElementById(formId), "carrier?update&carrierId="+carrierId, function (data) {
		var response = eval(data);
		
		if(!response.status) {
			alert("There was an error updating this carrier.");
		}
		
		$('#updateCarrierModal').modal('hide');
	});
}

function submitUpdateModal() {
	document.getElementById('updateCarrierForm').submit();
}


function updateCarrierList() {
	$.get("carrier?listfragment", function (data) {
		$("#carrierListContainer").html(data);
	});
}

function updateCarrier(carrierID) {
	$('#updateCarrierModal').modal('show');
	$.get("carrier?updateform&carrierId="+carrierID, function (data) {
		$("#updateFormContainer").html(data);
	});
}

function addCarrier() {
	$('#addCarrierModal').modal('show');
}

function deleteCarrier(name, id) {
	if(confirm("Are you sure you wish to delete " + name + "? If you do this all the routes run by this carrier will be discontinued.")) {
		$.post("carrier?delete&carrierId="+id, function (data) {
			var response = eval(data);
			
			if(response.status) {
				updateCarrierList();
			} else {
				alert("There was an error deleting this carrier.");
			}
		});
	}
}

function validateCarrierForm(form) {
	var ok = true;
	removeValidationMessages(form);
	if(!form.elements['name'].value) {
		ok = false;
		validationError(form.elements['name'], "Please enter a name");
	}
	
	return ok;
}

$(document).ready(function() {
	$('#addCarrierModal').modal({show:false, keyboard:true, backdrop: true});
	
	$('#updateCarrierModal').modal({show:false, keyboard:true, backdrop: true});
	
	if(window.location.hash == "#new") {
		addCarrier();
	}
	
	$("#menu-newCarrierDropdown").click(function () {
		addCarrier();
	});
});