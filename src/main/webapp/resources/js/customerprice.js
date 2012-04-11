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
	
	var newForm = undefined;
	var updateForm = undefined;
	
	var addModalConfiguration = {
			title: "Add customer price",
			okButton: {
				title: "Add",
				action: function() {
					newForm.submit();
				}
			}
	};
	var updateModalConfiguration = {
			title: "Update customer price",
			okButton: {
				title: "Update",
				action: function() {
					updateForm.submit();
				}
			}
	};
	
	cls.addCustomerPrice = function() {
		KPS.modal.load("customerprice?addform"+newFormURL,function(){
			KPS.util.disableInputAutocomplete();
			
			newForm = document.getElementById(newFormId);
			KPS.modal.configure(addModalConfiguration);
			KPS.data.locations.load(function () { // TODO: needed?
				KPS.data.locations.setupPortEntryTypeahead();
			});
			KPS.modal.show();
		});
	};
	
	$(document).ready(function() {
		if(window.location.hash == "#new") {
			cls.addCustomerPrice();
		}
	});
}(KPS.event.customerprice,  jQuery));