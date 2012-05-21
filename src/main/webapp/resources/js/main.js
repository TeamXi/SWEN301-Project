KPS.util = KPS.util || {};
KPS.util.map = KPS.util.map || {};
KPS.util.user = KPS.util.user || {};
KPS.util.criticalroutes = KPS.util.criticalroutes || {};
KPS.modal = KPS.modal || {};
KPS.data = KPS.data || {};
KPS.data.locations = KPS.data.locations || {};
KPS.data.carriers = KPS.data.carriers || {};
KPS.data.format = KPS.data.format || {};

$(document).ready(function() {
    $.ajaxSetup({
        error: function(x, e) {
        	if((x.status+"")[0] == "5" || x.status == 404) {
        		alert("There was an error communicating with the server");
        	}
        }
    });
});

var waitForFinalEvent = (function () {
	  var timers = {};
	  return function (callback, ms, uniqueId) {
	    if (!uniqueId) {
	      uniqueId = "Don't call this twice without a uniqueId";
	    }
	    if (timers[uniqueId]) {
	      clearTimeout (timers[uniqueId]);
	    }
	    timers[uniqueId] = setTimeout(callback, ms);
	  };
	})();

// KPS.util
(function (cls, $, undefined) {
	cls.redirect = function(url) {
		window.location = url;
	};

	cls.submitForm = function(form, url, callback) { // TODO: reason if not 200?
		var values = {};
		$(form).find("input, select").each(function(index, child) {
			values[child.name] = child.value;
		});
		$.post(url, values, function(data) {
			callback(data);
		});
	};
	
	cls.disableInputAutocomplete = function() {
		$("input").attr("autocomplete", "off");
	};
	
	cls.loadStyleSheet = function(url) {
		if(document.createStyleSheet) {
            document.createStyleSheet(url);
        }
        else {
            $("head").append($("<link rel='stylesheet' href='"+url+"' type='text/css' />"));
        }
	};
} (KPS.util, jQuery));

(function(cls, $, undefined) {
	cls.staticUrl = function(lat, lng) {
		//http://maps.googleapis.com/maps/api/staticmap?center=-4.434044,136.40625&zoom=4&format=png&sensor=false&size=640x480&maptype=roadmap&style=visibility:off&style=feature:administrative.country|visibility:on&style=feature:administrative.locality|visibility:on&style=feature:water|element:geometry|visibility:on&style=feature:water|invert_lightness:true|hue:0x0066ff
		//http://maps.googleapis.com/maps/api/staticmap?zoom=6&format=png&sensor=false&size=250x250&maptype=roadmap&style=visibility:off&style=feature:administrative.country|visibility:on&style=feature:administrative.locality|visibility:on&style=feature:water|element:geometry|visibility:on&style=feature:water|invert_lightness:true|hue:0x0066ff&markers=color:red|-41,173
		
		return "http://maps.googleapis.com/maps/api/staticmap?zoom=6&format=png&sensor=false&size=250x250&maptype=roadmap&style=feature:administrative|visibility:off&style=feature:landscape|visibility:off&style=feature:poi|visibility:off&style=feature:road|visibility:off&style=feature:transit|visibility:off&style=feature:administrative.country|visibility:on&style=feature:administrative.locality|visibility:on&style=feature:water|element:geometry|visibility:on&style=feature:water|invert_lightness:true|hue:0x0066ff&markers=color:red|"+lat+","+lng;
	};
	
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
		
	cls.style = function(map) {
		var mapStyles = [ {
			featureType : "administrative",
			stylers : [ {
				visibility : "off"
			} ]
		}, {
			featureType : "landscape",
			stylers : [ {
				visibility : "off"
			} ]
		}, {
			featureType : "poi",
			stylers : [ {
				visibility : "off"
			} ]
		}, {
			featureType : "road",
			stylers : [ {
				visibility : "off"
			} ]
		}, {
			featureType : "transit",
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
	
	cls.options = function(_zoom, _lat, _lng) {
		return {
			zoom : _zoom,
			center : new google.maps.LatLng(_lat, _lng),
			disableDefaultUI: true,
			mapTypeControlOptions: {
				mapTypeIds: [google.maps.MapTypeId.ROADMAP, 'kpsmartstyle']
			}
		};
	};
	
	cls.newInstance = function(el, zoom, lat, lng) {
		var ret = new google.maps.Map(el, KPS.util.map.options(zoom, lat, lng));
		KPS.util.map.style(ret);
		
		return ret;
	};
	
	cls.removePolylines = function(map) {
		map.__polylines = map.__polylines || [];
		
		for(var n=0;n<map.__polylines.length;n++) {
			map.__polylines[n].setMap(null);
		}
		map.__polylines = [];
	};
	
	cls.addPolyline = function(map, options) {
		map.__polylines = map.__polylines || [];
		
		options.map = map;
		
		options.strokeColor = options.strokeColor===undefined?"#228B22":options.strokeColor;
		options.strokeWeight = options.strokeWeight===undefined?2:options.strokeWeight;
		options.geodesic = options.geodesic===undefined?true:options.geodesic;
		options.strokeOpacity = options.strokeOpacity===undefined?0.5:options.strokeOpacity;
		
		map.__polylines.push(new google.maps.Polyline(options));
	};
	
	cls.removeMarkers = function(map) {
		map.__markers = map.__markers || [];
		
		for(var n=0;n<map.__markers.length;n++) {
			map.__markers[n].setMap(null);
		}
		map.__markers = [];
	};
	
	cls.addMarker = function(map, options) {
		map.__markers = map.__markers || [];
		
		options.map = map;
		
		map.__markers.push(new google.maps.Marker(options));
	};
}(KPS.util.map, jQuery));

// KPS.util.user
(function(cls, $, undefined) {
	cls.login = function(role){
		$.post(KPS.siteRoot+"/login?in", {role: role}, function(data) {
			KPS.util.redirect(KPS.siteRoot+"/dashboard");
		});
	};

	cls.logout = function(){
		$.post(KPS.siteRoot+"/login?out", function(data) {
			KPS.util.redirect(KPS.siteRoot);
		});
	};
}(KPS.util.user,jQuery));

(function(cls, $, undefined) {
	var route_popover = undefined;
	var route_popover_map_div = undefined;
	var map = undefined;
	
	cls.setupCriticalRouteHover = function() {
		KPS.data.locations.load(function() {
			$(".critical-route-hover").unbind('mouseover mouseout').hover(function(e) {
				var pos = $(e.currentTarget).position();
				var xdiff = $(e.currentTarget).width()/2-route_popover.width()/2;
				var ydiff = $(e.currentTarget).height();
				
				var from = KPS.data.locations.get($(e.currentTarget).attr("data-from"));
				var to = KPS.data.locations.get($(e.currentTarget).attr("data-to"));
								
				route_popover.css({left: pos.left+xdiff, top: pos.top+ydiff, display: 'block'});
				
				if(map) {
					KPS.util.map.removePolylines(map);
					KPS.util.map.removeMarkers(map);
				}
				else {
					map = KPS.util.map.newInstance(route_popover_map_div[0], 0, 0, 0);
				}
				
				var startPos = new google.maps.LatLng(from.latitude, from.longitude);
				var endPos = new google.maps.LatLng(to.latitude, to.longitude);
				if(startPos && endPos) {
					KPS.util.map.addPolyline(map, {
						path: [startPos,endPos]
					});
					
					KPS.util.map.addMarker(map, {
						position: startPos
					});
					KPS.util.map.addMarker(map, {
						position: endPos
					});
					
					var bounds = new google.maps.LatLngBounds();
					bounds.extend(startPos);
					bounds.extend(endPos);
					map.fitBounds(bounds);
				}
			}, function(e) {
				route_popover.css({display: 'none'});
			});
		});
	};
	
	$(document).ready(function() {
		route_popover = $('<div class="popover fade bottom in" style="display: none;">'+
				'<div class="arrow"></div>'+
				'<div class="popover-inner">'+
					'<div id="critical-routes-hover-map"></div>'+
				'</div>'+
			'</div>');
		route_popover_map_div = route_popover.find("#critical-routes-hover-map");
		
		$(document.body).append(route_popover);
	});
}(KPS.util.criticalroutes, jQuery));

// KPS.carrousel object
(function (pack, $, undefined) {
	pack.carrousel = function() {
		this.el = document.createElement('div');
		this.$el = $(this.el);
		this.$el.addClass('kps-carrousel');
		this.body = document.createElement('div');
		this.el.appendChild(this.body);
		this.$body = $(this.body);
		this.$body.addClass('kps-carrousel-body');
		
		this.setClips(true);
		this.width = 200;
		this.spacing = 20;
		this.currentPage = -1;
	};
	
	pack.carrousel.prototype.setClips = function(clips) {
		if(clips) {
			this.$el.addClass('clip');
		}
		else {
			this.$el.removeClass('clip');
		}
	};
	
	pack.carrousel.prototype.layout = function() {
		this.$body.find('> div').css({
			width: this.width+'px',
			marginRight: this.spacing+'px'
		});
	};
		
	pack.carrousel.prototype.resize = function(num) {
		if(num === undefined) {
			num = this.currentPage;
		}
		
		// Make sure modal is visible before getting height, parent might be invisible so .height() doesn't work all the time.
		var child = $(this.$body.children()[num]);
		// Add it to the body
		$(document.body).append(child);
		child.css({height: 'auto'});
		var height = child.height();
		// Set height of all carrousel items so that the scrollbar displays correctly.
		this.$body.children().animate({height: height+'px'});
		// Insert child back at the same location
		var next = this.$body.children()[num];
		if(next) {
			child.insertBefore(next);
		} else {
			this.$body.append(child);
		}
	};
	
	pack.carrousel.prototype.show = function(num, animated) {
		this.resize(num);
		
		var margin = (num*-(this.width+this.spacing))+"px";
		if(animated === undefined || animated === true) {
			this.$body.animate({marginLeft:margin},400);
		}
		else { // Animated is false
			this.$body.css("margin-left", margin);
		}
		this.currentPage = num;
	};
	
	pack.carrousel.prototype.next = function() {
		this.show(this.currentPage+1);
	};
	
	pack.carrousel.prototype.previous = function() {
		this.show(this.currentPage-1);
	};
	
	pack.carrousel.prototype.load = function(url, callback) {
		var self = this;
		self.$body.load(url, {}, function (responseText, textStatus, XMLHttpRequest) {
			if(XMLHttpRequest.status == 200) {
				self.layout();
				callback(self.$body.children());
			}
		});
	};
	
	pack.carrousel.prototype.add = function(element) {
		// TODO: checks
		this.body.appendChild(element);
		this.layout();
	};
}(KPS, jQuery));

// KPS.modal
(function (cls, $, undefined) { // TODO: common alert area in modals?
//<div class="modal fade">
//	<div class="modal-header">
//		<a class="close" data-dismiss="modal"></a>
//		<h3></h3>
//	</div>
//	<div class="modal-body">
//	</div>
//	<div class="modal-footer">
//		<a href="#" class="btn btn-warning"></a>
//		<a href="#" class="btn btn-success"></a>
//	</div>
//</div>
	
	// El setup

	cls.el = document.createElement('div');
	cls.$el = $(cls.el);
	cls.$el.addClass('modal fade');
	
	var header = document.createElement('div');
	$(header).addClass('modal-header');
	var cross = document.createElement('a');
	$(cross).addClass('close').html('&times;').attr('data-dismiss', 'modal');
	header.appendChild(cross);
	var title = document.createElement('h3');
	var $title = $(title);
	header.appendChild(title);
	
	cls.el.appendChild(header);
	
	var body = document.createElement('div');
	var $body = $(body);
	$body.addClass('modal-body');
	
	cls.el.appendChild(body);
	
	var footer = document.createElement('div');
	$(footer).addClass('modal-footer');
	var cancelButton = document.createElement('a');
	var $cancelButton = $(cancelButton);
	$cancelButton.addClass('btn btn-warning');
	footer.appendChild(cancelButton);
	var okButton = document.createElement('a');
	var $okButton = $(okButton);
	$okButton.addClass('btn btn-success');
	footer.appendChild(okButton);
	
	cls.el.appendChild(footer);
	
	// Carrousel setup
	
	cls.carrousel = new KPS.carrousel();
	cls.carrousel.width = 530;
	cls.carrousel.spacing = 30;
	cls.carrousel.setClips(false);
	cls.carrousel.layout();
	body.appendChild(cls.carrousel.el);
	
	// Configurations

	var defaultConfiguration = {
			title: "Modal",
			okButton: {
				title: "Ok",
				action: function () {
					cls.hide();
				}
			},
			cancelButton: {
				title: "Cancel",
				action: function () {
					cls.hide();
				}
			}
	};
	var currentConfiguration = defaultConfiguration;

	function setTitle(title) {
		$title.html(title);
	};

	function setOkButtonTitle(name) {
		$okButton.html(name);
	};

	function setOkButtonAction(action) {
		$okButton.unbind('click');
		$okButton.click(action);
	};

	function setCancelButtonTitle(name) {
		$cancelButton.html(name);
	};

	function setCancelButtonAction(action) {
		$cancelButton.unbind('click');
		$cancelButton.click(action);
	};

	cls.show = function() {
		cls.carrousel.show(0, false);
		cls.$el.modal('show');
	};

	cls.hide = function() {
		cls.$el.modal('hide');
	};

	cls.configure = function(configuration) {
		setTitle(configuration.title || defaultConfiguration.title);
		setOkButtonTitle((configuration.okButton || {}).title || defaultConfiguration.okButton.title);
		setOkButtonAction((configuration.okButton || {}).action || defaultConfiguration.okButton.action);
		setCancelButtonTitle((configuration.cancelButton || {}).title || defaultConfiguration.cancelButton.title);
		setCancelButtonAction((configuration.cancelButton || {}).action || defaultConfiguration.cancelButton.action);

		var tmp = currentConfiguration;
		currentConfiguration = configuration;
		return tmp;
	};

	cls.load = function(url, callback) {
		cls.carrousel.load(url, callback);
	};
	
	$(document).ready(function() {
		document.body.appendChild(cls.el);
		$(".nav-collapse").click(function(){$(".btn-navbar").click();});
		cls.$el.modal({show:false, keyboard:true, backdrop: 'static'});
	});
} (KPS.modal, jQuery));

// KPS.data.locations
(function (cls, $, undefined)  {
	var locationList = [];
	var locationNames = [];
	var locationDataDirty = true;
	var location_popover = undefined;
	var location_popover_img = undefined;
	
	cls.setNeedsUpdate = function() {
		locationDataDirty = true;
	};

	cls.load = function(callback) {
		if(locationDataDirty) {
			$.get(KPS.siteRoot+"/event/location?list&format=json", function(data) {
				response = eval(data);
				names = [];

				for(var n=0;n<response.length;n++) {
					var location = response[n];
					names.push(location.name);
				}

				locationList = response;
				locationNames = names;

				locationDataDirty = false;

				if(callback) {
					callback(locationList);
				}
			});
		}
		else {
			if(callback) {
				callback();
			}
		}
	};

	cls.setupPortEntryTypeahead = function(iterator) {
		$(".portEntry").each(function(index, child) { // TODO: ????
			if(child.typeahead) { // Update
				child.typeahead.source = locationNames;
			}
			else { // New
				$(child).typeahead({source: locationNames});
				if(iterator) {
					iterator(child);
				}
			}
		});
	};

	cls.exists = function(name) {
		for(var n=0;n<locationList.length;n++) {
			if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
				return true;
			}
		}
		return false;
	};
	
	cls.get = function(name) {
		for(var n=0;n<locationList.length;n++) {
			if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
				return locationList[n];
			}
		}
		return null;
	};

	cls.isInternational = function(name) {
		for(var n=0;n<locationList.length;n++) {
			if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
				return locationList[n].international;
			}
		}
		return undefined;
	};
	
	cls.locationList = function() {
		return locationList;
	};
	
	cls.setupLocationNameHover = function() {
		cls.load(function() {
			$(".location-name-hover").unbind('mouseover mouseout').hover(function(e) {
				var pos = $(e.currentTarget).position();
				var width = $(e.currentTarget).width();
				var diff = $(e.currentTarget).height()/2-location_popover.height()/2-4;
				var location = KPS.data.locations.get(e.currentTarget.innerHTML);
				location_popover_img.attr('src', KPS.util.map.staticUrl(location.latitude, location.longitude));
				location_popover.css({left: pos.left+width, top: pos.top+diff, display: 'block'});
			}, function(e) {
//				if(location_popover.has(e.relatedTarget).length > 0) {
//					e.stopPropagation();
//					e.preventDefault();
//				}
//				else {
					location_popover.css({display: 'none'});
//				}
			});
		});
	};
	
	$(document).ready(function() {
		location_popover = $('<div class="popover fade right in" style="display: none;">'+
				'<div class="arrow"></div>'+
				'<div class="popover-inner">'+
					'<img class="location-name-hover-map-image"/>'+
				'</div>'+
			'</div>');
		location_popover_img = location_popover.find(".location-name-hover-map-image");
		$(document.body).append(location_popover);
		
		cls.setupLocationNameHover();
	});
}(KPS.data.locations, jQuery));

//KPS.data.carriers
(function (cls, $, undefined)  {
	var carrierList = [];
	
	cls.load = function(callback) {
		$.get(KPS.siteRoot+"/event/carrier?list&format=json", function(data) {
			carrierList = eval(data);
			
			if(callback) {
				callback();
			}
		});
	};

	cls.setupCarrierEntryTypeahead = function(iterator) {
		$(".carrierEntry").each(function(index, child) { // TODO: ????
			if(child.typeahead) { // Update
				child.typeahead.source = carrierList;
			}
			else { // New
				$(child).typeahead({source: carrierList});
				if(iterator) {
					iterator(child);
				}
			}
		});
	};

	cls.exists = function(name) {
		for(var n=0;n<carrierList.length;n++) {
			if(carrierList[n].toLowerCase() == name.toLowerCase()) {
				return true;
			}
		}
		return false;
	};
}(KPS.data.carriers, jQuery));

(function(cls, $, undefined) {
	cls.date = function(date) {
		return date.format("h:MMtt ddd dS mmm 'yy");
	};
}(KPS.data.format, jQuery));