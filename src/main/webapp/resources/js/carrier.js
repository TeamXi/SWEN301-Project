KPS.event = KPS.event || {};
KPS.event.carrier = KPS.event.carrier || {};

(function (cls, $, undefined) {
	var addModalConfiguration = {
			title: "Add carrier",
			okButton: {
				title: "Create",
				action: function() {
					document.getElementById('newCarrierForm').submit();
				}
			}
	};
	var updateModalConfiguration = {
			title: "Update carrier",
			okButton: {
				title: "Update",
				action: function() {
					document.getElementById('updateCarrierForm').submit();
				}
			}
	};

	function validateForm(form) {
		var ok = true;
		KPS.validation.resetValidation(form);
		if(!form.elements['name'].value) {
			ok = false;
			KPS.validation.validationError(form.elements['name'], "Please enter a name");
		}

		return ok;
	}

	function submitForm(form, url, callback) {
		if(!validateForm(form)) return;

		KPS.util.submitForm($(form), url, function(data){
			var returnObj = eval(data);
			var status = returnObj.status;

			if(!status){
				KPS.validation.validationErrors(form, returnObj.validation);
			} else{
				callback(data);

				updateCarrierList();
			}
		});
	}

	cls.submitNewForm = function(id) {
		submitForm(document.getElementById(id), "carrier?new", function (data) {
			var response = eval(data);

			if(!response.status) {
				alert("There was an error adding this carrier.");
			}
			else {
				$("#carrierSuccessMessage").fadeIn(500,function(){
					setTimeout(function () {
						$("#carrierSuccessMessage").fadeOut(500);
					}, 1000);
				});
			}
		});
	};

	cls.submitUpdateForm = function(formId, carrierId) {
		submitForm(document.getElementById(formId), "carrier?update&carrierId="+carrierId, function (data) {
			var response = eval(data);

			if(!response.status) {
				alert("There was an error updating this carrier.");
			}

			KPS.modal.hide();
		});
	};

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

	cls.addCarrier = function() {
		KPS.modal.load("carrier?newform", function (){
			KPS.modal.configure(addModalConfiguration);
			KPS.modal.show();
		});
	};

	cls.updateCarrier = function(carrierID) {
		KPS.modal.load("carrier?updateform&carrierId="+carrierID, function () {
			KPS.modal.configure(updateModalConfiguration);
			KPS.modal.show();
		});
	};

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