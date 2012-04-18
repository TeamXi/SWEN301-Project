KPS.event = KPS.event || {};
KPS.event.location = KPS.event.location || {};

(function(cls, $, undefined) {
	var globalMap = undefined;
	var infoWindow = undefined;
	
	function createWorldMap() {
		var myOptions = {
			zoom : 4,
			center : new google.maps.LatLng(-34.397, 150.644),
			disableDefaultUI: true,
			mapTypeControlOptions : {
				mapTypeIds : [ google.maps.MapTypeId.ROADMAP, 'kpsmartstyle' ]
			}
		};
		
		globalMap = new google.maps.Map(document.getElementById('allLocationsMap'), myOptions);

		KPS.util.map.style(globalMap);
		
		infoWindow = new google.maps.InfoWindow();
	};
	
	function loadLocations(allLocations) {
		for(locIdx in allLocations){
			var location = allLocations[locIdx];
			var marker = new google.maps.Marker({
				map: globalMap, 
				position: new google.maps.LatLng(location.latitude, location.longitude),
				title:location.name
			});
			google.maps.event.addListener(marker, "mouseover", function(event) {
				infoWindow.setContent(this.title);
				infoWindow.setPosition(event.latLng);
				infoWindow.open(globalMap);
			});
			google.maps.event.addListener(marker, "mouseout", function(event) {
				infoWindow.close();
			});
		}

	};
	
	$(document).ready(function () {
		KPS.data.locations.load(function (allLocations) {
			createWorldMap();
			loadLocations(allLocations);
		});
	});
}(KPS.event.location, jQuery));