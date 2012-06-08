KPS.event = KPS.event || {};
KPS.event.customerprice = KPS.event.customerprice || {};

// KPS.event.customerprice
(function (cls, $, undefined) {
	var newDivId = 'newCustomerPriceFormContainer';
	var newFormId = 'newCustomerPriceForm';
	var newFormSubmitCallback = 'KPS.event.customerprice.submitNewForm';
	var newFormURL = "&divId="+newDivId+"&formId="+newFormId+"&submitCallback="+newFormSubmitCallback+"()";
	
	var updateDivId = 'updateCustomerPriceFormContainer';
	var updateFormId = 'updateCustomerPriceForm';
	var updateFormSubmitCallback = 'KPS.event.customerprice.submitUpdateForm';
	var updateFormPartialURL = "&divId="+updateDivId+"&formId="+updateFormId;
	var updateDomesticFormSubmitCallback = 'KPS.event.customerprice.submitDomesticUpdateForm';
	
	var newForm = undefined;
	var updateForm = undefined;
	
	/**
	 * The add customer price modal configuration
	 */
	var addModalConfiguration = {
			title: "Add a customer price",
			okButton: {
				title: "Add",
				action: function() {
					newForm.submit();
				}
			}
	};
	/**
	 * The update customer price modal configuration
	 */
	var updateModalConfiguration = {
			title: "Update a customer price",
			okButton: {
				title: "Update",
				action: function() {
					updateForm.submit();
				}
			}
	};
	
	/**
	 * Submit a form and apply the validation
	 */
	function submitForm(form, url, callback) {
		if(!validateForm(form)) return;

		KPS.util.submitForm(form, url, function(data){
			var returnObj = eval(data);
			var status = returnObj.status;

			if(!status){ // TODO: check reason and alert if server failure
				KPS.validation.validationErrors(form, returnObj.validation);
			}else{
				if(callback) {
					callback();
				}
				updatePriceList();
			}
		});
	};
	
	/**
	 * Perform the client-side validation
	 */
	function validateForm(form) {
		var ok = true;
		KPS.validation.resetValidation(form);
		
		if(form.elements['location']) {
			ok &= validateLocationField(form.elements['location']);
		}
		
		if(form.elements['priority'].value == "placeholder") {
			ok = false;
			KPS.validation.validationError(form.elements['priority'], "Please select a priority");
		}
		
		ok &= KPS.validation.validateNumberField(form.elements['weightPrice'], "Price per gram");
		ok &= KPS.validation.validateNumberField(form.elements['volumePrice'], "Price per cm&sup3;");
		
		return ok;
	};
	
	/**
	 * Validate a location name field
	 */
	function validateLocationField(element) {
		if(!KPS.data.locations.exists(element.value)) {
			KPS.validation.validationError(element, '"'+element.value+'" is not a valid location.');
			return false;
		}
		if(!KPS.data.locations.isInternational(element.value)) {
			KPS.validation.validationError(element, "This location must be international");
			return false;
		}
		return true;
	}
	
	/**
	 * Update the customer price list
	 */
	function updatePriceList(){
		$("#customerPriceListContainer").load("customerprice?listfragment", function() {
			KPS.data.locations.setupLocationNameHover();
		});
	}
	
	/**
	 * Flip the to/from button on the new form
	 */
	cls.flipNewToFrom = function() {
		var form = newForm;
		
		// Get the old to/from direction
		var was = form.elements['directionfield'].value;
		
		// Switch the values and update the ui
		if(was == 'From') {
			was = 'To';
			$(form.elements['location']).insertBefore(document.getElementById('tofromswitchbutton'));
			$("#tonewzealanddirectiontext").html("New Zealand to&nbsp;");
		}
		else {
			was = 'From';
			$("#tonewzealanddirectiontext").insertBefore(document.getElementById('tofromswitchbutton'));
			$("#tonewzealanddirectiontext").html("&nbsp;to New Zealand");
		}
		
		// Update the hidden field
		form.elements['directionfield'].value = was;
	};
	
	/**
	 * Submit the new form
	 */
	cls.submitNewForm = function() {
		submitForm(newForm, "customerprice?new", function() {
			KPS.modal.hide();
		});
	};
	
	/**
	 * Submit the update form
	 */
	cls.submitUpdateForm = function(id) {
		submitForm(updateForm, "customerprice?update&priceId="+id, function() {
			KPS.modal.hide();
		});
	};
	
	/**
	 * Submit a domestic customer price update form (different url to others)
	 */
	cls.submitDomesticUpdateForm = function(priority) {
		submitForm(updateForm, "customerprice?updatedomestic&domesticPriority="+priority, function() {
			KPS.modal.hide();
		});
	};
	
	/**
	 * Display the add customer price UI
	 */
	cls.addCustomerPrice = function() {
		KPS.modal.load("customerprice?addform"+newFormURL,function(){
			KPS.util.disableInputAutocomplete();
			
			// Configure the modal
			newForm = document.getElementById(newFormId);
			KPS.modal.configure(addModalConfiguration);
			KPS.data.locations.load(function () {
				// Setup the location name autocompletion
				KPS.data.locations.setupPortEntryTypeahead();
			});
			KPS.modal.show();
		});
	};
	
	/**
	 * Display the uptate customer price UI
	 */
	cls.updateCustomerPrice = function(id) {
		KPS.modal.load("customerprice?updateform&priceId="+id+updateFormPartialURL+"&submitCallback="+updateFormSubmitCallback+"("+id+")", function () {
			KPS.util.disableInputAutocomplete();
			
			// Configure the modal
			updateForm = document.getElementById(updateFormId);
			KPS.modal.configure(updateModalConfiguration);
			KPS.data.locations.load(function () {
				// Setup the location name autocompletion
				KPS.data.locations.setupPortEntryTypeahead();
			});
			KPS.modal.show();
		});
	};
	
	/**
	 * Show the update domestic customer price UI
	 */
	cls.updateDomesticCustomerPrice = function(priority) {
		KPS.modal.load("customerprice?updatedomesticform&domesticPriority="+priority+updateFormPartialURL+"&submitCallback="+updateDomesticFormSubmitCallback+"('"+priority+"')", function () {
			KPS.util.disableInputAutocomplete();
			
			updateForm = document.getElementById(updateFormId);
			KPS.modal.configure(updateModalConfiguration);
			KPS.modal.show();
		});
	};
	
	/**
	 * Delete a customer price
	 */
	cls.deleteCustomerPrice = function(message, id) {
		if(confirm("Are you sure you wish to delete the customer price " + message + "?")) {
			$.post("customerprice?delete&priceId="+id, function (data) {
				var response = eval(data);
				
				if(response.status) {
					// Update the list
					updatePriceList();
				} else {
					alert("There was an error deleting this route.");
				}
			});
		}
	};
	
	$(document).ready(function() {
		if(window.location.hash == "#new") {
			cls.addCustomerPrice();
		}
		
		$("#menu-newCustomerPriceDropdown").click(function () {
			cls.addCustomerPrice();
		});
	});
}(KPS.event.customerprice,  jQuery));