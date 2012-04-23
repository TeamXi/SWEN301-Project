KPS.event = KPS.event || {};
KPS.event.maildelivery = KPS.event.maildelivery || {};

(function (cls, $, undefined) {
	// TODO: el, $el?
	
	var modalConfiguration = {
			title: "New mail delivery",
			okButton: {
				title: "Submit",
				action: function() {
					document.getElementById('newMailForm').submit();
				}
			}
	};
	
	cls.submitForm = function(id) {
		var form = document.getElementById(id);
		if(!validateForm(form)) return;
		
		KPS.util.submitForm(form, KPS.siteRoot+"/event/mail?new", function(data){
			var returnObj = eval(data);
			var status = returnObj.status;
			
			if(!status){
				KPS.validation.validationErrors(form, returnObj.validation);
			}else{
				$("#addMailSuccessMessage").fadeIn(500,function(){
					setTimeout(function () {
						$("#addMailSuccessMessage").fadeOut(500);
					}, 1000);
				});
			}
			
		});
	};
	
	function validateForm(form) {
		var ok = true;
		KPS.validation.resetValidation(form);
		ok &= validateLocationField(form.elements['source']);
		ok &= validateLocationField(form.elements['destination']);
		if(form.elements['priority'].value == "placeholder") {
			ok = false;
			KPS.validation.validationError(form.elements['priority'], "Please select a priority");
		}
		ok &= KPS.validation.validateNumberField(form.elements['weight'], "Weight");
		ok &= KPS.validation.validateNumberField(form.elements['volume'], "Volume");
		
		return ok;
	}
	
	function validateLocationField(element) {
		if(!KPS.data.locations.exists(element.value)) {
			KPS.validation.validationError(element, '"'+element.value+'" is not a valid location');
			return false;
		}
		return true;
	}
	
	function updatePriorityDropdown() {
		var form = document.getElementById('newMailForm');
		var isInternational = KPS.data.locations.isInternational(form.elements['source'].value) || KPS.data.locations.isInternational(form.elements['destination'].value);
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
		KPS.data.locations.load(function () {
			KPS.data.locations.setupPortEntryTypeahead(function (child) {
				$(child).blur(function () { // TODO: only if child of form
					setTimeout(updatePriorityDropdown, 200);
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
		
		$(".new-mail-delivery-link").each(function (index, child) {
			$(child).click(function () {
				KPS.modal.load(KPS.siteRoot+"/event/mail", function(){
					KPS.util.disableInputAutocomplete();
					
					KPS.modal.configure(modalConfiguration);
					KPS.modal.show();
				});
			});
		});
	});
}(KPS.event.maildelivery, jQuery));
