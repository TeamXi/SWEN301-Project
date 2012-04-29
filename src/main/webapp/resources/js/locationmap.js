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
	
	function applyRoutesOverlay(routes,locations){
		var locationByNameMap = {};
		for(var locIdx in locations){
			var location = locations[locIdx];
			locationByNameMap[location.name] = new google.maps.LatLng(location.latitude, location.longitude);
		}
		for(var routeIdx in routes){
			var route = routes[routeIdx];
			var startPos = locationByNameMap[route.startPoint];
			var endPos = locationByNameMap[route.endPoint];
			if(startPos && endPos){
				var polyline = new google.maps.Polyline({path:[startPos,endPos], strokeColor:"#228B22", strokeWeight:2,map:globalMap,geodesic:true,strokeOpacity: 0.5});
			}
		
		}
	}
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
			$.ajax({url: KPS.siteRoot+"/event/route?routeListJSON",dataType: 'json',data: {},
				  success: function(data){
					  applyRoutesOverlay(data,allLocations);
				  }
				});
		});
	});
}(KPS.event.location, jQuery));