var newLocationMap;

function submitNewRouteForm() {
	var form = document.getElementById('newRouteForm');
	if(!validateRouteForm(form)) return;

	submitForm(form, "route?new", function(data){
		var returnObj = eval(data);
		var status = returnObj.status;

		if(!status){
			for(var error in returnObj.validation){
				validationError(
						$("input[name='"+returnObj.validation[error].name+"']"),
						returnObj.validation[error].message
				);
			}
		}else{
			$("#emptyModalSuccessMessage").fadeIn(500,function(){
				setTimeout(function () {
					$("#emptyModalSuccessMessage").fadeOut(500);
				}, 1000);
			});
			updateRouteList();
		}
	});
}

function updateRouteList(){
	$.get("route?listfragment", function (data) {
		$("#routeListContainer").html(data);
	});
}

function validateRouteForm(form) {
	var ok = true;
	removeValidationMessages(form);
	ok &= validateRouteLocationField(form.elements['source']);
	ok &= validateRouteLocationField(form.elements['destination']);
	if(form.elements['transportType'].value == "placeholder") {
		ok = false;
		validationError(form.elements['transportType'], "Please select a transport type");
	}

	return ok;
}

function validateRouteLocationField(element) {
	if(!isValidLocation(element.value)) {
		validationError(element, '"'+element.value+'" is not a valid location.'+(element.value?' Would you like to <a class="inject-form-element" onclick="showNewLocationMap(\''+element.value+'\', this.formElement);">create it</a>?':''));
		return false;
	}
	return true;
	
}

var newLocationScreenBackStackInfo;
function showNewLocationMap(name, element){
	newLocationScreenBackStackInfo = {element: element};
	
	$("#newRouteCarousel").animate({marginLeft:"-580px"},400);
	document.getElementById('newLocationMapLocationName').value = name;
	configureAddLocationModal();
	searchNewLocation();
}

function dismissNewLocation() {
	$("#newRouteCarousel").animate({marginLeft:"0"},400);
	configureAddRouteModal(); ; // TODO: update route
}

function createNewLocation() {
	var location = newLocationSearchResults[document.getElementById('newLocationMapLocationResults').value];
	$.post("location?new", {
		name: location.formatted_address,
		longitude: location.geometry.location.Ya,
		latitude: location.geometry.location.Xa,
		isInternational: document.getElementById('newLocationMapLocationIsInternational').checked == true
	}, function (data) {
		setNeedsLocationDataUpdate();
		initilizeLocationData(function () {
			newLocationScreenBackStackInfo.element.value = location.formatted_address;
			validateRouteForm(document.getElementById('newRouteForm'));
			dismissNewLocation();
		});
	});
}

var newLocationSearchResults; // TODO: hide?
function searchNewLocation() {
	var geocoder = new google.maps.Geocoder();
	geocoder.geocode( { 'address': document.getElementById('newLocationMapLocationName').value}, function(results, status) {
		newLocationSearchResults = results;
		
		if (status == google.maps.GeocoderStatus.OK) {
			var select = document.getElementById('newLocationMapLocationResults');
			var selectChangeFunc = function() {
				var location = results[select.value];
				newLocationMap.setCenter(location.geometry.location);
				clearMarkers(newLocationMap);
				addMarker(newLocationMap, new google.maps.Marker({
					map: newLocationMap, 
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

function addMarker(map, marker) {
	if(!map.markers) {
		map.markers = [];
	}
	map.markers.push(marker);
}

function clearMarkers(map) {
	if(map.markers) {
		for(var i=0; i < map.markers.length; i++){
			map.markers[i].setMap(null);
		}
		map.markers = [];
	}
}

function configureAddRouteModal() {
	setEmptyModalTitle("Add route");
	setEmptyModalOkButton("Create route", createNewRoute);
	setEmptyModalCancelButton(true);
	$("#emptyModalSuccessMessage").html("Route added!");
	updateTypeaheadLocations();
}

function configureAddLocationModal() {
	setEmptyModalTitle("Add location");
	setEmptyModalOkButton("Create location", createNewLocation);
	setEmptyModalCancelButton(function () {
		dismissNewLocation();
	});
	$("#emptyModalSuccessMessage").html("Location added!"); // TODO: look at all of these
}

function addRoute(){
	$("#emptyLayoutModalBody").load("/kpsmart/event/route?add",{},function(){
		var mailModal = $('#emptyLayoutModal');
		configureAddRouteModal();
		mailModal.modal('show');
		initializeNewLocationMap();
	});
}

function updateTypeaheadLocations() {
	initilizeLocationData(function () {
		initilizePortEntryTypeahead();
	});
}

function createNewRoute(){
	submitNewRouteForm('newRouteForm');
}

function updateRoute(id) {
	$('#updateCarrierModal').modal('show');
	$.get("carrier?updateform&carrierId="+id, function (data) {
		$("#updateFormContainer").html(data);
	});
}

function deleteRoute(message, id) {
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
}

$(document).ready(function() {
	
	
	if(window.location.hash == "#new") {
		addRoute();
	}
	
	$("#menu-newRouteDropdown").click(function () {
		addRoute();
	});
	
});


function initializeNewLocationMap() {
	var myOptions = {
		zoom : 4,
		center : new google.maps.LatLng(-34.397, 150.644),
		disableDefaultUI: true,
		mapTypeControlOptions : {
			mapTypeIds : [ google.maps.MapTypeId.ROADMAP, 'kpsmartstyle' ]
		}
	};
	
	newLocationMap = new google.maps.Map(document
			.getElementById('newLocationMap'), myOptions);

	var mapStyles = [ {
		stylers : [ {
			visibility : "off"
		} ]
	}, {
		featureType : "administrative.country",
		stylers : [ {
			visibility : "on"
		} ]
	}, {
		featureType : "administrative.locality",
		stylers : [ {
			visibility : "on"
		} ]
	}, {
		featureType : "water",
		elementType : "geometry",
		stylers : [ {
			visibility : "on"
		} ]
	}, {
		featureType : "water",
		stylers : [ {
			invert_lightness : true
		}, {
			hue : "#0066ff"
		} ]
	} ];
	
	var styledMapOptions = {
		name : "KPSmart Style"
	};

	var mapType = new google.maps.StyledMapType(mapStyles, styledMapOptions);

	newLocationMap.mapTypes.set('kpsmartstyle', mapType);
	newLocationMap.setMapTypeId('kpsmartstyle');
}
