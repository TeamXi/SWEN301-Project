KPS.event = KPS.event || {};
KPS.event.route = KPS.event.route || {};
KPS.event.location = KPS.event.location || {};
KPS.util = KPS.util || {};
KPS.util.map = KPS.util.map || {};

(function(cls, $, undefined) {
	// TODO: el, $el?
	cls.submitNewForm = function() {
		var form = document.getElementById('newRouteForm');
		if(!cls.validateForm(form)) return;

		KPS.util.submitForm(form, "route?new", function(data){
			var returnObj = eval(data);
			var status = returnObj.status;

			if(!status){
				KPS.validation.validationErrors(form, returnObj.validation);
			}else{
				$("#emptyModalSuccessMessage").fadeIn(500,function(){ // TODO: implement
					setTimeout(function () {
						$("#emptyModalSuccessMessage").fadeOut(500);
					}, 1000);
				});
				updateRouteList();
			}
		});
	};
	
	function updateRouteList(){
		$.get("route?listfragment", function (data) {
			$("#routeListContainer").html(data);
		});
	}
	
	cls.validateForm = function(form) {
		var ok = true;
		KPS.validation.resetValidation(form);
		ok &= validateLocationField(form.elements['source']);
		ok &= validateLocationField(form.elements['destination']);
		if(form.elements['transportType'].value == "placeholder") {
			ok = false;
			KPS.validation.validationError(form.elements['transportType'], "Please select a transport type");
		}

		return ok;
	};
	
	cls.showNewLocationScreen = function(placeName, element) {
		KPS.event.location.show(placeName, element, function() {
			configureAddModal();
		});
	};
	
	function validateLocationField(element) {
		if(!KPS.data.locations.exists(element.value)) {
			KPS.validation.validationError(element, '"'+element.value+'" is not a valid location.'+(element.value?' Would you like to <a class="inject-form-element" onclick="KPS.event.route.showNewLocationScreen(\''+element.value+'\', this.formElement);">create it</a>?':''));
			return false;
		}
		return true;
	}
	
	function configureAddModal() { // TODO: use config()
		KPS.modal.setTitle("Add route");
		KPS.modal.setOkButtonTitle("Create route");
		KPS.modal.setOkButtonAction(function() {
			cls.submitNewForm();
		});
		KPS.modal.setCancelButtonAction(true);
//		$("#emptyModalSuccessMessage").html("Route added!"); // TODO: else
		KPS.data.locations.load(function () {
			KPS.data.locations.setupPortEntryTypeahead();
		});
	}
	
	function configureUpdateModal() { // TODO: use config()
		KPS.modal.setTitle("Update route");
		KPS.modal.setOkButtonTitle("Update route"); // TODO: all spelling of buttons
		KPS.modal.setOkButtonAction(function(){}); // TODO: implement
		KPS.modal.setCancelButtonAction(true);
//		$("#emptyModalSuccessMessage").html("Route added!"); // TODO: else
		KPS.data.locations.load(function () {
			KPS.data.locations.setupPortEntryTypeahead();
		});
	}
	
	cls.addRoute = function(){
		KPS.modal.load("/kpsmart/event/route?add",function(){
			configureAddModal();
			KPS.modal.show();
		});
	};
	
	cls.updateRoute = function(id) {
		KPS.modal.load("route?updateform&routeId="+id, function (data) {
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
	});
}(KPS.event.route, jQuery));

(function(cls, $, undefined) {
	var searchResults = [];
	var map = undefined;
	var backStackInfo = {};
	
	cls.show = function(name, element, callback){
		backStackInfo = {element: element, callback: callback};
		
		if(!map) {
			setupMap();
		}
		
		configureModal();
		$("#newRouteCarousel").animate({marginLeft:"-580px"},400); // TODO: move animation stuff to modal something?
		document.getElementById('newLocationMapLocationName').value = name;
		search();
	};

	function dismiss() {
		$("#newRouteCarousel").animate({marginLeft:"0"},400);
		backStackInfo.callback();
	};
	
	function create() { // TODO: success info
		var location = searchResults[document.getElementById('newLocationMapLocationResults').value];
		$.post("location?new", {
			name: location.formatted_address,
			longitude: location.geometry.location.Ya,
			latitude: location.geometry.location.Xa,
			isInternational: document.getElementById('newLocationMapLocationIsInternational').checked == true
		}, function (data) {
			KPS.data.locations.setNeedsUpdate();
			KPS.data.locations.load(function () { // TODO: insert rather than reload
				backStackInfo.element.value = location.formatted_address;
				KPS.event.route.validateForm(document.getElementById('newRouteForm')); // TODO:...
				dismiss();
			});
		});
	}
	
	function search() {
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
	
	function configureModal() {
		KPS.modal.setTitle("Add location");
		KPS.modal.setOkButtonTitle("Create location");
		KPS.modal.setOkButtonAction(create);
		KPS.modal.setCancelButtonAction(function () {
			dismissNewLocation();
		});
//		$("#emptyModalSuccessMessage").html("Location added!"); // TODO: look at all of these
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

		map.mapTypes.set('kpsmartstyle', mapType);
		map.setMapTypeId('kpsmartstyle');
	};
}(KPS.event.location, jQuery));

(function(cls, $, undefined) {
	cls.addMarker = function(map, marker) {
		if(!map.markers) {
			map.markers = [];
		}
		map.markers.push(marker);
	};
	
	cls.clearMarkers = function(map) {
		if(map.markers) {
			for(var i=0; i < map.markers.length; i++){
				map.markers[i].setMap(null);
			}
			map.markers = [];
		}
	};
}(KPS.util.map, jQuery));