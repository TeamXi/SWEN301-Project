KPS.event = KPS.event || {};
KPS.event.carrier = KPS.event.carrier || {};

(function (cls, $, undefined) {
	var newFormId = 'newCarrierForm';
	var newFormSubmitCallback = 'KPS.event.carrier.submitNewForm';
	var newFormURL = "&formId="+newFormId+"&submitCallback="+newFormSubmitCallback+"()";
	
	var updateFormId = 'updateCarrierForm';
	var updateFormSubmitCallback = 'KPS.event.carrier.submitUpdateForm';
	var updateFormPartialURL = "&formId="+updateFormId;
	
	var newForm = undefined;
	var updateForm = undefined;
	
	/**
	 * The modal configuration for the add carrier modal
	 */
	var addModalConfiguration = {
			title: "Add carrier",
			okButton: {
				title: "Create",
				action: function() {
					newForm.submit();
				}
			}
	};
	/**
	 * The modal configuration for the update carrier modal
	 */
	var updateModalConfiguration = {
			title: "Update carrier",
			okButton: {
				title: "Update",
				action: function() {
					updateForm.submit();
				}
			}
	};

	/**
	 * Validate validate the form
	 */
	function validateForm(form) {
		var ok = true;
		KPS.validation.resetValidation(form);
		if(!form.elements['name'].value) {
			ok = false;
			KPS.validation.validationError(form.elements['name'], "Please enter a name");
		}

		return ok;
	}

	/**
	 * Submit a form and apply the form validation
	 */
	function submitForm(form, url, callback) {
		if(!validateForm(form)) return;

		KPS.util.submitForm($(form), url, function(data){
			var returnObj = eval(data);
			var status = returnObj.status;

			if(!status){
				KPS.validation.validationErrors(form, returnObj.validation);
			} else{
				if(callback) {
					callback(data);
				}
				updateCarrierList();
			}
		});
	}

	/**
	 * Submit the new form
	 */
	cls.submitNewForm = function() {
		submitForm(newForm, "carrier?new", function (data) {
			var response = eval(data);

			if(!response.status) {
				alert("There was an error adding this carrier.");
			}
			else {
				KPS.modal.hide();
			}
		});
	};

	/**
	 * Submit the update form
	 */
	cls.submitUpdateForm = function(carrierId) {
		submitForm(updateForm, "carrier?update&carrierId="+carrierId, function (data) {
			var response = eval(data);

			if(!response.status) {
				alert("There was an error updating this carrier.");
			}
			else {
				KPS.modal.hide();
			}
		});
	};

	/**
	 * Delete a carrier
	 */
	cls.deleteCarrier = function(name, id) {
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
	};

	/**
	 * Show the add carrier ui
	 */
	cls.addCarrier = function() {
		KPS.modal.load("carrier?newform"+newFormURL, function (){
			newForm = document.getElementById(newFormId);
			
			KPS.util.disableInputAutocomplete();
			
			KPS.modal.configure(addModalConfiguration);
			KPS.modal.show();
		});
	};

	/**
	 * Show the update carrier ui
	 */
	cls.updateCarrier = function(carrierID) {
		KPS.modal.load("carrier?updateform&carrierId="+carrierID+updateFormPartialURL+"&submitCallback="+updateFormSubmitCallback+"("+carrierID+")", function () {
			updateForm = document.getElementById(updateFormId);
			
			KPS.util.disableInputAutocomplete();
			
			KPS.modal.configure(updateModalConfiguration);
			KPS.modal.show();
		});
	};

	/**
	 * Update the list of carriers
	 */
	function updateCarrierList() {
		$.get("carrier?listfragment", function (data) {
			$("#carrierListContainer").html(data);
		});
	}

	$(document).ready(function() {
		if(window.location.hash == "#new") {
			cls.addCarrier();
		}

		$("#menu-newCarrierDropdown").click(function () {
			cls.addCarrier();
		});
	});
}(KPS.event.carrier, jQuery));