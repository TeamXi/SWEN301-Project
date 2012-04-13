KPS.validation = KPS.validation || {};

(function (cls, $, undefined) {
	cls.isNumber = function(n) {
		return !isNaN(parseFloat(n)) && isFinite(n);
	};
	
	cls.validateNumberField = function(element, name) {
		var isValidNumber = cls.isNumber(element.value);
		if(!isValidNumber || element.value <= 0) {
			if(!isValidNumber) {
				cls.validationError(element, name+" must be a number");
			}
			else {
				cls.validationError(element, name+" must be larger than zero");
			}
			return false;
		}
		return true;
	};

	cls.validationError = function(element, message, level) {
		level = level || 'error';
		
		var helptext = $(element).next(".help-block")[0];
		if(!helptext) {
			helptext = $("<p class='help-block'></p>");
			var after = $(element).next(".add-on");
			if(after.length == 0) {
				after = element;
			}
			$(after).after(helptext);
		}
		
		$(helptext).html(message).find(".inject-form-element").each(function (index, child) {
			child.formElement = element;
		});;
		
		$(element).closest(".control-group").addClass(level);
	};

	cls.resetValidation = function(form) {
		$('.help-block', form).html('');
		$('.control-group', form).removeClass('error').removeClass('warning').removeClass('success');
	};
	
	cls.validationErrors = function(form, errors) {
		for(var n=0;n<errors.length;n++){
			var error = errors[n];
			
			KPS.validation.validationError(
					$(form).find("input[name='"+error.name+"']"),
					error.message
			);
		}
	};
}(KPS.validation, jQuery));