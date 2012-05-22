KPS.validation = KPS.validation || {};

/**
 * Form validation helper methods/style
 */
(function (cls, $, undefined) {
	cls.isNumber = function(n) {
		return !isNaN(parseFloat(n)) && isFinite(n);
	};
	
	/**
	 * Validate a number field.
	 * Constraints: >0 and a real number
	 */
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

	/**
	 * Display a validation error on a form element
	 * @param element the element to display the error for
	 * @param message the message to show
	 * @param the error level for the error, optional, defaults to 'error'
	 */
	cls.validationError = function(element, message, level) {
		level = level || 'error';
		
		// Find the help box
		var helptext = $(element).nextAll(".help-block")[0];
		
		// If it doesn't exist insert a new one after all add-ons or inline-helps
		if(!helptext) {
			helptext = $("<p class='help-block'></p>");
			var after2 = $(element).nextAll(".add-on");
			if(after2.length == 0) {
				after2 = element;
			}
			var after = $(after2).nextAll(".help-inline");
			if(after.length == 0) {
				after = after2;
			}
			$(after).after(helptext);
		}
		
		// Set the html to message
		$(helptext).html(message).find(".inject-form-element").each(function (index, child) {
			child.formElement = element;
		});;
		
		// Set the style on the control-group
		$(element).closest(".control-group").addClass(level);
	};

	/**
	 * Reset all the validation on a form
	 */
	cls.resetValidation = function(form) {
		$('.help-block', form).html('');
		$('.control-group', form).removeClass('error').removeClass('warning').removeClass('success');
	};
	
	/**
	 * Apply a bulk load of errors.
	 * @param form the form to apply the validation errors to
	 * @param errors the error descriptions, must be in [{name:'', message:''}...] form
	 */
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