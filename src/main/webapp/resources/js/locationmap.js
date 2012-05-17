KPS.event = KPS.event || {};
KPS.event.location = KPS.event.location || {};

(function(cls, $, undefined) {
	var globalMap = undefined;
	var infoWindow = undefined;
	
	function createWorldMap() {
		globalMap = KPS.util.map.newInstance(document.getElementById('allLocationsMap'), 4, -34.397, 150.644);
		
		infoWindow = new InfoBubble(globalMap);
	};
	
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
	
	function hideRoutes() {
		KPS.util.map.removePolylines(globalMap);
	}
	
	function loadLocations(allLocations) {
		for(locIdx in allLocations){
			(function(location) {
				var marker = new google.maps.Marker({
					map: globalMap, 
					position: new google.maps.LatLng(location.latitude, location.longitude)
				});
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