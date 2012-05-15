KPS.event = KPS.event || {};
KPS.event.route = KPS.event.route || {};
KPS.event.location = KPS.event.location || {};

(function(cls, $, undefined) {
	var newDivId = 'newRouteFormContainer';
	var newFormId = 'newRouteForm';
	var newFormSubmitCallback = 'KPS.event.route.submitNewForm';
	var newFormURL = "&divId="+newDivId+"&formId="+newFormId+"&submitCallback="+newFormSubmitCallback+"()";
	
	var updateDivId = 'updateRouteFormContainer';
	var updateFormId = 'updateRouteForm';
	var updateFormSubmitCallback = 'KPS.event.route.submitUpdateForm';
	var updateFormPartialURL = "&divId="+updateDivId+"&formId="+updateFormId;
	
	var newLocationDivId = 'newLocationMapWrapper';
	var newLocationDiv = undefined;
	
	var newForm = undefined;
	var updateForm = undefined;
	
	var addModalConfiguration = {
			title: "Add route",
			okButton: {
				title: "Create route",
				action: function() {
					newForm.submit();
				}
			}
	};
	var updateModalConfiguration = {
			title: "Update route",
			okButton: {
				title: "Update",
				action: function() {
					updateForm.submit();
				}
			}
	};
	
	function submitForm(form, url, callback) {
		if(!cls.validateForm(form)) return;

		KPS.util.submitForm(form, url, function(data){
			var returnObj = eval(data);
			var status = returnObj.status;

			if(!status){ // TODO: check reason and alert if server failure
				KPS.validation.validationErrors(form, returnObj.validation);
			}else{
				if(callback) {
					callback();
				}
				updateRouteList();
			}
		});
	};
	
	cls.submitNewForm = function() {
		submitForm(newForm, "route?new", function() {
			KPS.modal.hide();
		});
	};
	
	cls.submitUpdateForm = function(id) {
		submitForm(updateForm, "route?update&routeId="+id, function() {
			KPS.modal.hide();
		});
	};
	
	function updateRouteList(){
		$("#routeListContainer").load("route?listfragment", function() {
			KPS.data.locations.setupLocationNameHover();
		});
	}
	
	cls.validateForm = function(form) {
		var ok = true;
		KPS.validation.resetValidation(form);
		ok &= validateLocationField(form.elements['source']);
		ok &= validateLocationField(form.elements['destination']);
		if(!KPS.data.carriers.exists(form.elements['carrier'].value)) {
			ok = false;
			KPS.validation.validationError(form.elements['carrier'], "Please enter a valid carrier");
		}
		if(form.elements['transportType'].value == "placeholder") {
			ok = false;
			KPS.validation.validationError(form.elements['transportType'], "Please select a transport type");
		}
		ok &= KPS.validation.validateNumberField(form.elements['weightCost'], "Cost per gram");
		ok &= KPS.validation.validateNumberField(form.elements['volumeCost'], "Cost per cm&sup3;");
		ok &= KPS.validation.validateNumberField(form.elements['frequency'], "Departure frequency");
		ok &= KPS.validation.validateNumberField(form.elements['duration'], "Transit duration");

		return ok;
	};
	
	cls.showNewLocationScreen = function(placeName, element) {
		KPS.event.location.show(placeName, element);
	};
	
	function validateLocationField(element) {
		if(!KPS.data.locations.exists(element.value)) { // TODO: generate DOM?
			KPS.validation.validationError(element, '"'+element.value+'" is not a valid location.'+(element.value?' Would you like to <a class="inject-form-element" onclick="KPS.event.route.showNewLocationScreen(\''+element.value+'\', this.formElement);">create it</a>?':''));
			return false;
		}
		return true;
	}
	
	cls.addRoute = function(){ // TODO: only load once.
		KPS.modal.load("route?addform"+newFormURL,function(){
			KPS.util.disableInputAutocomplete();
			
			newForm = document.getElementById(newFormId);
			KPS.modal.carrousel.add(newLocationDiv);
			KPS.modal.configure(addModalConfiguration);
			KPS.data.locations.load(function () { // TODO: needed?
				KPS.data.locations.setupPortEntryTypeahead();
			});
			KPS.data.carriers.setupCarrierEntryTypeahead();
			KPS.modal.show();
		});
	};
	
	cls.updateRoute = function(id) {
		KPS.modal.load("route?updateform&routeId="+id+updateFormPartialURL+"&submitCallback="+updateFormSubmitCallback+"("+id+")", function () {
			KPS.util.disableInputAutocomplete();
			
			updateForm = document.getElementById(updateFormId);
			KPS.modal.configure(updateModalConfiguration);
			KPS.data.locations.load(function () {
				KPS.data.locations.setupPortEntryTypeahead();
			});
			KPS.data.carriers.setupCarrierEntryTypeahead();
			KPS.modal.show();
		});
	};

	cls.deleteRoute = function(message, id) {
		if(confirm("Are you sure you wish to delete the route " + message + "?")) {
			$.post("route?delete&routeId="+id, function (data) {
				var response = eval(data);
				
				if(response.status) {
					updateRouteList();
				} else {
					alert("There was an error deleting this route.");
				}
			});
		}
	};
	
	$(document).ready(function() {
		if(window.location.hash == "#new") {
			cls.addRoute();
		}
		
		$("#menu-newRouteDropdown").click(function () {
			cls.addRoute();
		});
		
		KPS.data.carriers.load();
		newLocationDiv = document.getElementById(newLocationDivId);
	});
}(KPS.event.route, jQuery));

(function(cls, $, undefined) {
	var searchResults = [];
	var map = undefined;
	var backStackInfo = {};
	
	var modalConfiguration = {
			title: "Add location",
			okButton: {
				title: "Create location",
				action: function() {
					create();
				}
			},
			cancelButton: {
				title: "Back",
				action: function() {
					dismiss();
//					$("#emptyModalSuccessMessage").html("Location added!"); // TODO: look at all of these
				}
			}
	};
	
	cls.show = function(name, element){
		backStackInfo = {element: element};
		
		if(!map) {
			setupMap();
		}
		
		backStackInfo.oldModal = KPS.modal.configure(modalConfiguration);
		KPS.modal.carrousel.next();
		document.getElementById('newLocationMapLocationName').value = name;
		cls.search();
	};

	function dismiss() {
		KPS.modal.carrousel.previous();
		KPS.modal.configure(backStackInfo.oldModal);
	};
	
	function create() { // TODO: success info
		var location = searchResults[document.getElementById('newLocationMapLocationResults').value];
		$.post("location?new", {
			name: location.formatted_address,
			longitude: location.geometry.location.lng(),
			latitude: location.geometry.location.lat(),
			isInternational: document.getElementById('newLocationMapLocationIsInternational').checked == true ? 'true' : 'false'
		}, function (data) {
			KPS.data.locations.setNeedsUpdate();
			KPS.data.locations.load(function () { // TODO: insert rather than reload
				KPS.data.locations.setupPortEntryTypeahead();
				backStackInfo.element.value = location.formatted_address;
				KPS.event.route.validateForm(document.getElementById('newRouteForm')); // TODO:...
				dismiss();
			});
		});
	}
	
	cls.search = function() {
		var geocoder = new google.maps.Geocoder();
		geocoder.geocode( { 'address': document.getElementById('newLocationMapLocationName').value}, function(results, status) {
			searchResults = results;
			
			if (status == google.maps.GeocoderStatus.OK) {
				var select = document.getElementById('newLocationMapLocationResults');
				var selectChangeFunc = function() {
					var location = results[select.value];
					map.setCenter(location.geometry.location);
					KPS.util.map.clearMarkers(map);
					KPS.util.map.addMarker(map, new google.maps.Marker({
						map: map, 
						position: location.geometry.location
					}));
				};
				$(select).empty().unbind().change(selectChangeFunc);
				for(var n=0;n<results.length;n++) {
					var location = results[n];
					var option = document.createElement("option");
					option.value = n;
					option.innerHTML = location.formatted_address;
					select.appendChild(option);
				}
				selectChangeFunc();
			} else {
				alert("Geocode was not successful for the following reason: " + status);
			}
		});
	}
	
	function setupMap() {
		var myOptions = {
			zoom : 4,
			center : new google.maps.LatLng(-34.397, 150.644),
			disableDefaultUI: true,
			mapTypeControlOptions : {
				mapTypeIds : [ google.maps.MapTypeId.ROADMAP, 'kpsmartstyle' ]
			}
		};
		
		map = new google.maps.Map(document.getElementById('newLocationMap'), myOptions);

		KPS.util.map.style(map);
	};
}(KPS.event.location, jQuery));