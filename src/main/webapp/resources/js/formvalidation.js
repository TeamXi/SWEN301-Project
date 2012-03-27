function isNumber(n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
}

function validateNumberField(element, name) {
	var isValidNumber = isNumber(element.value);
	if(!isValidNumber || element.value <= 0) {
		if(!isValidNumber) {
			validationError(element, name+" must be a number");
		}
		else {
			validationError(element, name+" must be larger than zero");
		}
		return false;
	}
	return true;
}

function validationError(element, message) {
	$(element).before('<div class="alert alert-error fade in validate-error-message">'+
			'<a class="close" data-dismiss="alert">&times;</a>'+
			message+
		'</div>');
	$(element).addClass('validate-error')
			  .focus(function() {
		$(element).removeClass('validate-error');
	});
}