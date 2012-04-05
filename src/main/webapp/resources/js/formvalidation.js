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

	cls.validationError = function(element, message) {
		$(element).before('<div class="alert alert-error fade in validate-error-message"><a class="close" data-dismiss="alert">&times;</a>'+message+'</div>')
					.prev()
					.find(".inject-form-element")
					.each(function (index, child) {
						child.formElement = element;
					});
		
		$(element).addClass('validate-error')
			.focus(function() {
				$(element).removeClass('validate-error');
			});
	};

	cls.resetValidation = function(form) {
		$('.validate-error-message', form).each(function (index, child) {$(child).remove();});
		$('.validate-error', form).each(function (index, child) {$(child).removeClass('validate-error');});
	};
}(KPS.validation, jQuery));