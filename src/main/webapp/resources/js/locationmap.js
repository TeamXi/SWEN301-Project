KPS.event = KPS.event || {};
KPS.event.location = KPS.event.location || {};

/**
 * KPS.event.location
 * 
 * The location map stuff
 */
(function(cls, $, undefined) {
	var globalMap = undefined;
	var infoWindow = undefined;
	
	/**
	 * Initialize the map
	 */
	function createWorldMap() {
		globalMap = KPS.util.map.newInstance(document.getElementById('allLocationsMap'), 4, -34.397, 150.644);
		
		// Custom info window
		infoWindow = new InfoBubble(globalMap);
	};
	
	/**
	 * Show the routes to/from a location
	 * 
	 * @param name the name of the location
	 */
	function showRoutesFor(name) {
		$.get("location?connected", {name: name}, function(data) {
			var list = eval(data);
			
			var from = KPS.data.locations.get(name);
			
			for(var n=0;n<list.length;n++) {
				var to = KPS.data.locations.get(list[n]);
				
				var startPos = new google.maps.LatLng(from.latitude, from.longitude);
				var endPos = new google.maps.LatLng(to.latitude, to.longitude);
				if(startPos && endPos) {
					KPS.util.map.addPolyline(globalMap, {
						path: [startPos,endPos]
					});
				}
			}
		});
	}
	
	/**
	 * Hide/remove all the route lines
	 */
	function hideRoutes() {
		KPS.util.map.removePolylines(globalMap);
	}
	
	/**
	 * Add all the markers to the map
	 */
	function loadLocations(allLocations) {
		for(locIdx in allLocations){
			(function(location) {
				var marker = new google.maps.Marker({
					map: globalMap, 
					position: new google.maps.LatLng(location.latitude, location.longitude)
				});
				
				// Setup hover actions, like customer popover and route lines
				google.maps.event.addListener(marker, "mouseover", function(event) {
					infoWindow.setContent(location.name);
					infoWindow.setPosition(event.latLng);
					infoWindow.open(globalMap);
					
					showRoutesFor(location.name);
				});
				google.maps.event.addListener(marker, "mouseout", function(event) {
					infoWindow.close();
					
					hideRoutes();
				});
			}(allLocations[locIdx]));
		}
	};
	
	$(document).ready(function () {
		KPS.data.locations.load(function (allLocations) {
			createWorldMap();
			loadLocations(allLocations);
		});
	});
}(KPS.event.location, jQuery));