KPS.event = KPS.event || {};
KPS.event.maildelivery = KPS.event.maildelivery || {};

(function (cls, $, undefined) {
	// TODO: el, $el?
	cls.submitForm = function(id) {
		var form = document.getElementById(id);
		if(!validateForm(form)) return;
		
		KPS.util.submitForm(form, "/kpsmart/event/mail?new", function(data){
			var returnObj = eval(data);
			var status = returnObj.status;
			
			if(!status){
				for(var error in returnObj.validation){ // TODO: common, part of submitForm?
					KPS.validation.validationError(
							$("input[name='"+returnObj.validation[error].name+"']"),
							returnObj.validation[error].message
					);
				}
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
	
	function configureModal() {
		KPS.modal.setTitle("New mail delivery");
		KPS.modal.setOkButtonTitle("Submit");
		KPS.modal.setOkButtonAction(function() {
			cls.submitForm('newMailForm');
		});
		KPS.modal.setCancelButtonAction(true);
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
				KPS.modal.load("/kpsmart/event/mail", function(){
					configureModal();
					KPS.modal.show();
				});
			});
		});
	});
}(KPS.event.maildelivery, jQuery));
