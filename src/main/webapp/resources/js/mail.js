KPS.event = KPS.event || {};
KPS.event.maildelivery = KPS.event.maildelivery || {};

/**
 * KPS.event.maildelivery
 * 
 * The mail screen js
 */
(function (cls, $, undefined) {
	// TODO: el, $el?
	
	/**
	 * The initial modal configuration
	 */
	var modalConfiguration = {
			title: "New mail delivery",
			okButton: {
				title: "Submit",
				action: function() {
					// Submit the form
					document.getElementById('newMailForm').submit();
				}
			}
	};
	
	/**
	 * The quote screen modal configuration
	 */
	var doneModalConfiguration = {
			title: "Delivery summary",
			okButton: {
				title: "Confirm",
				action: function() {
					// Confirm the submission
					submitNewMailForm('newMailForm');
				}
			},
			cancelButton: {
				title: "Back",
				action: function() {
					// Move back to first form
					KPS.modal.configure(modalConfiguration);
					KPS.modal.carrousel.show(0);
				}
			}
	};
	
	/**
	 * Submit the new action, ie confirm the mail delivery
	 */
	function submitNewMailForm(id) {
		var form = document.getElementById(id);
		
		if(!validateForm(form)) return;
		
		KPS.util.submitForm(form, KPS.siteRoot+"/event/mail?new", function(data){
			var returnObj = eval(data);
			var status = returnObj.status;
			
			if(!status){
				// Errors, go back and show them
				KPS.validation.validationErrors(form, returnObj.validation);
				KPS.modal.configure(modalConfiguration);
				KPS.modal.carrousel.show(0);
			}else{
				KPS.modal.hide();
			}
		});
	};
	
	/**
	 * Submit the form for a quote
	 */
	cls.quoteForm = function(id) {
		var form = document.getElementById(id);
		
		if(!validateForm(form)) return;
		
		KPS.util.submitForm(form, KPS.siteRoot+"/event/mail?quote", function(data){
			var returnObj = eval(data);
			var status = returnObj.status;
			
			if(!status){
				// Errors, show them
				KPS.validation.validationErrors(form, returnObj.validation);
			}else{
				// ALl good, slide to next screen
				
				KPS.modal.configure(doneModalConfiguration);
				KPS.modal.carrousel.show(1);
				
				// Set up the map & route summary
				var map = setUpMap();
				KPS.util.map.removePolylines(map);	
				KPS.util.map.removeMarkers(map);
				applyRoutesOverlay(map, returnObj.data.summary.route);
				
				// Fill in the quote info
				document.getElementById('mail-success-info-revenue').innerHTML = returnObj.data.summary.revenue;
				document.getElementById('mail-success-info-expenditure').innerHTML = returnObj.data.summary.expenditure;
				document.getElementById('mail-success-info-delivery-duration').innerHTML = returnObj.data.summary.duration;
			}
		});
	};
		
	var summaryMap = undefined;
	
	/**
	 * Add the markers and route lines to the summary map
	 */
	function applyRoutesOverlay(map, routes){
		var bounds = new google.maps.LatLngBounds();
		var startPos;
		var endPos = undefined;

		for(var routeIdx in routes){
			var route = routes[routeIdx];
			startPos = new google.maps.LatLng(route.from.lat, route.from.lng);
			endPos = new google.maps.LatLng(route.to.lat, route.to.lng);
			KPS.util.map.addMarker(map,{position:startPos});
			if(startPos && endPos) {
				bounds.extend(startPos);
				bounds.extend(endPos);
				KPS.util.map.addPolyline(map, {
					path: [startPos,endPos]
				});
			}
		}
		if(endPos){
			KPS.util.map.addMarker(map,{position:endPos});
		}
		map.fitBounds(bounds);
	}
	
	/**
	 * Initialize the map
	 */
	function setUpMap() {
		if(!summaryMap){
			summaryMap = KPS.util.map.newInstance(document.getElementById('mail-success-info-route-map'), 1, 0, 0);
		}
		return summaryMap;
	}
	
	/**
	 * Validate the form for submission
	 */
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
	
	/**
	 * Validate a location field
	 */
	function validateLocationField(element) {
		if(!KPS.data.locations.exists(element.value)) {
			KPS.validation.validationError(element, '"'+element.value+'" is not a valid location');
			return false;
		}
		return true;
	}
	
	/**
	 * Update the avalible priorities in the dropdown
	 */
	function updatePriorityDropdown() {
		// Disable all the priorities
		$("#newMailForm select[name='priority'] option[value!='placeholder']").each(function(index, child) {
			child.disabled= true;
		});
		
		var form = document.getElementById('newMailForm');
		var source = form.elements['source'].value;
		var destination = form.elements['destination'].value;
		var priorityElm = form.elements['priority'];
		
		// Reset the form validation
		KPS.validation.resetValidation(form);
		
		// Function that show the falied errors
		function dontDeliverMessage() {
			KPS.validation.validationError(form.elements['destination'], "KPS does not deliver mail from "+source+" to "+destination);
		}
		
		if(KPS.data.locations.exists(source) && KPS.data.locations.exists(destination)) {
			// If both locations valid
			
			if(KPS.data.locations.isInternational(source) && KPS.data.locations.isInternational(destination)) {
				// Fail if both international
				
				dontDeliverMessage();
			}
			else {
				// Looks valid client-side, query server
				
				// Show the spinner
				$("#select-spinner").show();
				$.get(KPS.siteRoot+"/event/mail?availablepriorities",{source:source, destination:destination},function(data){
					var priorities = eval(data);
					if (priorities.length == 0){
						// No priorities valid, show error
						dontDeliverMessage();
					}
					else{
						// Some priorities valid, enable those in dropdown
						
						$("option[value!='placeholder']", priorityElm).each(function(index, child) {
							child.disabled = priorities.indexOf(child.value)<0;
						});
					}
					
					// Hide the spinner
					$("#select-spinner").hide();
				});
			}
		}
		else if(source && destination){
			// If both locations are defined, show the standard location validation errors
			validateLocationField(form.elements['source']);
			validateLocationField(form.elements['destination']);
		}
		
		// If selected option is disabled select the placeholder
		if($("option:selected", priorityElm).is(":disabled")) {
			priorityElm.value = "placeholder";
		}
	}
	
	$(document).ready(function() {
		$("#newMailForm select[name='priority'] option[value!='placeholder']").each(function(index, child) {
			child.disabled= true;
		});
		
		$(".new-mail-delivery-link").each(function (index, child) {
			$(child).click(function () {
				KPS.modal.load(KPS.siteRoot+"/event/mail", function(){
					summaryMap = null;
					
					KPS.data.locations.load(function () {
						KPS.data.locations.setupPortEntryTypeahead(function (child) {
							$(child).blur(function () { // TODO: only if child of form
								setTimeout(updatePriorityDropdown, 200);
							});
						});
					});
					
					KPS.util.disableInputAutocomplete();
					
					KPS.modal.configure(modalConfiguration);
					KPS.modal.show();
				});
			});
		});
	});
}(KPS.event.maildelivery, jQuery));
