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
	// Display message when any ajax operation fails
	
    $.ajaxSetup({
        error: function(x, e) {
        	if((x.status+"")[0] == "5" || x.status == 404) {
        		alert("There was an error communicating with the server");
        	}
        }
    });
});

/**
 * KPS.util
 * 
 * Utility methods
 */
(function (cls, $, undefined) {
	/**
	 * Redirect to a url
	 */
	cls.redirect = function(url) {
		window.location = url;
	};

	/**
	 * Submit a html form using ajax
	 * @param form the form to submit
	 * @param url the url to submit to
	 * @param callback the callback to call with the response, optional
	 * 
	 * @note uses POST and only includes input and
	 * 			select values in submission data
	 */
	cls.submitForm = function(form, url, callback) { // TODO: reason if not 200?
		var values = {};
		
		// Turn form into dictionary
		$(form).find("input, select").each(function(index, child) {
			values[child.name] = child.value;
		});
		
		// Submit
		$.post(url, values, function(data) {
			if(callback) {
				callback(data);	
			}
		});
	};
	
	/**
	 * Disable the brosers default autocompleate on all input elements
	 */
	cls.disableInputAutocomplete = function() {
		$("input").attr("autocomplete", "off");
	};
	
	/**
	 * Load a stylesheet
	 */
	cls.loadStyleSheet = function(url) {
		if(document.createStyleSheet) {
			// IE likes this way
            document.createStyleSheet(url);
        }
        else {
        	// Every other browser
            $("head").append($("<link rel='stylesheet' href='"+url+"' type='text/css' />"));
        }
	};
	
	/**
	 * Show a fullscreen modal loading bar
	 */
	cls.showLoadingBar = function(text){
		$loadingBar = $(".loadingOverlay");
		if(Modernizr.touch) {
			$loadingBar.removeClass('active');
		}
		$("#loadingMessage").html(text);
		$loadingBar.parent('#loadingMask').fadeIn(500);
	};
	
	/**
	 * Hide the fullscreen modal loading bar
	 */
	cls.hideLoadingBar = function(){
		$loadingBar = $(".loadingOverlay");
		$loadingBar.parent('#loadingMask').fadeOut(100);
	};
	
}(KPS.util, jQuery));

/**
 * KPS.util.map
 * 
 * Map utilities
 */
(function(cls, $, undefined) {
	/**
	 * Get a static map url for a lat/lng pair using the common style
	 * This renders a cenetered marker on a 250x250 png at zoom level 6
	 */
	cls.staticUrl = function(lat, lng) {
		//http://maps.googleapis.com/maps/api/staticmap?center=-4.434044,136.40625&zoom=4&format=png&sensor=false&size=640x480&maptype=roadmap&style=visibility:off&style=feature:administrative.country|visibility:on&style=feature:administrative.locality|visibility:on&style=feature:water|element:geometry|visibility:on&style=feature:water|invert_lightness:true|hue:0x0066ff
		//http://maps.googleapis.com/maps/api/staticmap?zoom=6&format=png&sensor=false&size=250x250&maptype=roadmap&style=visibility:off&style=feature:administrative.country|visibility:on&style=feature:administrative.locality|visibility:on&style=feature:water|element:geometry|visibility:on&style=feature:water|invert_lightness:true|hue:0x0066ff&markers=color:red|-41,173
		
		return "http://maps.googleapis.com/maps/api/staticmap?zoom=6&format=png&sensor=false&size=250x250&maptype=roadmap&style=feature:administrative|visibility:off&style=feature:landscape|visibility:off&style=feature:poi|visibility:off&style=feature:road|visibility:off&style=feature:transit|visibility:off&style=feature:administrative.country|visibility:on&style=feature:administrative.locality|visibility:on&style=feature:water|element:geometry|visibility:on&style=feature:water|invert_lightness:true|hue:0x0066ff&markers=color:red|"+lat+","+lng;
	};
	
	/**
	 * Get the KPS map style
	 */
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
	
	/**
	 * Get the default KPS map options
	 */
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
	
	/**
	 * Initialize a new KPS styled map
	 */
	cls.newInstance = function(el, zoom, lat, lng) {
		var ret = new google.maps.Map(el, KPS.util.map.options(zoom, lat, lng));
		KPS.util.map.style(ret);
		
		return ret;
	};
	
	/**
	 * Remove all the polylines added using addPolyline from a map
	 */
	cls.removePolylines = function(map) {
		map.__polylines = map.__polylines || [];
		
		for(var n=0;n<map.__polylines.length;n++) {
			map.__polylines[n].setMap(null);
		}
		map.__polylines = [];
	};
	
	/**
	 * Add a polyline to a map
	 */
	cls.addPolyline = function(map, options) {
		map.__polylines = map.__polylines || [];
		
		options.map = map;
		
		options.strokeColor = options.strokeColor===undefined?"#228B22":options.strokeColor;
		options.strokeWeight = options.strokeWeight===undefined?2:options.strokeWeight;
		options.geodesic = options.geodesic===undefined?true:options.geodesic;
		options.strokeOpacity = options.strokeOpacity===undefined?0.5:options.strokeOpacity;
		
		map.__polylines.push(new google.maps.Polyline(options));
	};
	
	/**
	 * Remove all the markers from a map added using addMarker()
	 */
	cls.removeMarkers = function(map) {
		map.__markers = map.__markers || [];
		
		for(var n=0;n<map.__markers.length;n++) {
			map.__markers[n].setMap(null);
		}
		map.__markers = [];
	};
	
	/**
	 * Add a marker to a map
	 */
	cls.addMarker = function(map, options) {
		map.__markers = map.__markers || [];
		
		options.map = map;
		
		map.__markers.push(new google.maps.Marker(options));
	};
}(KPS.util.map, jQuery));

/**
 * KPS.util.user
 * 
 * User login/logout utilities
 */
(function(cls, $, undefined) {
	/**
	 * Perform a login
	 */
	cls.login = function(role){
		KPS.util.showLoadingBar("Signing in...");
		$.post(KPS.siteRoot+"/login?in", {role: role}, function(data) {
			KPS.util.hideLoadingBar();
			KPS.util.redirect(KPS.siteRoot+"/dashboard");
		});
	};

	/**
	 * Perform a logout
	 */
	cls.logout = function(){
		KPS.util.showLoadingBar("Signing out...");
		$.post(KPS.siteRoot+"/login?out", function(data) {
			KPS.util.hideLoadingBar();
			KPS.util.redirect(KPS.siteRoot || "/");
		});
	};
}(KPS.util.user,jQuery));

/**
 * KPS.util.criticalroutes
 * 
 * Critical route utilities
 * 
 * TODO: new js file or other js file?
 */
(function(cls, $, undefined) {
	var route_popover = undefined;
	var route_popover_map_div = undefined;
	var map = undefined;
	
	/**
	 * Setup the critical route map popover
	 */
	cls.setupCriticalRouteHover = function() {
		// Load the locations list
		KPS.data.locations.load(function() {
			$(".critical-route-hover").unbind('mouseover mouseout').hover(function(e) {
				// When a element with the .critical-route-hover style is hovered over...
				
				// Calculate the absolute position of the popover
				var pos = $(e.currentTarget).position();
				var xdiff = $(e.currentTarget).width()/2-route_popover.width()/2;
				var ydiff = $(e.currentTarget).height();
				
				// Get the to/from locations
				var from = KPS.data.locations.get($(e.currentTarget).attr("data-from"));
				var to = KPS.data.locations.get($(e.currentTarget).attr("data-to"));
				
				// Position the popover
				route_popover.css({left: pos.left+xdiff, top: pos.top+ydiff, display: 'block'});
				
				// Setup the map
				if(map) {
					KPS.util.map.removePolylines(map);
					KPS.util.map.removeMarkers(map);
				}
				else {
					map = KPS.util.map.newInstance(route_popover_map_div[0], 0, 0, 0);
				}
				
				// Add the markers/polyline to the map
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
					
					// Zoom/center on the relevant part of the map
					var bounds = new google.maps.LatLngBounds();
					bounds.extend(startPos);
					bounds.extend(endPos);
					map.fitBounds(bounds);
				}
			}, function(e) {
				// Unhover, hide the popover
				route_popover.css({display: 'none'});
			});
		});
	};
	
	$(document).ready(function() {
		// Create the popover element
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

/**
 * KPS.carrousel **OBJECT**
 * 
 * A carrousel object, not a static class
 */
(function (pack, $, undefined) {
	/**
	 * Constructor
	 */
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
	
	/**
	 * Set whether the carrousel clips the content (default)
	 */
	pack.carrousel.prototype.setClips = function(clips) {
		if(clips) {
			this.$el.addClass('clip');
		}
		else {
			this.$el.removeClass('clip');
		}
	};
	
	/**
	 * Layout the contents of the carrousel
	 */
	pack.carrousel.prototype.layout = function() {
		this.$body.find('> div').css({
			width: this.width+'px',
			marginRight: this.spacing+'px'
		});
	};
	
	/**
	 * Resize the carrousel
	 * @param num the page to resize for, optionl defaults to the current page
	 */
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
	
	/**
	 * Show a given container
	 * @param num the number view to show
	 * @param animated whether to animate, optional - defaults to true
	 */
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
	
	/**
	 * Show the next item in the carrousel
	 */
	pack.carrousel.prototype.next = function() {
		this.show(this.currentPage+1);
	};
	
	/**
	 * Show the previous item in the carrousel
	 */
	pack.carrousel.prototype.previous = function() {
		this.show(this.currentPage-1);
	};
	
	/**
	 * Load the content of the carrousel
	 * @param url the url to load from
	 * @param callback the callback to call after load - optional
	 * 			The callback function is passed in the loaded elements as the first argument
	 */
	pack.carrousel.prototype.load = function(url, callback) {
		var self = this;
		self.$body.load(url, {}, function (responseText, textStatus, XMLHttpRequest) {
			if(XMLHttpRequest.status == 200) {
				self.layout();
				if(callback) {
					callback(self.$body.children());
				}
			}
		});
	};
	
	/**
	 * Add an element to the carrousel
	 */
	pack.carrousel.prototype.add = function(element) {
		// TODO: checks
		this.body.appendChild(element);
		this.layout();
	};
}(KPS, jQuery));

/**
 * KPS.modal
 * 
 * The global singleton modal dialouge
 */
(function (cls, $, undefined) {
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

	/**
	 * Set the title
	 */
	function setTitle(title) {
		$title.html(title);
	};

	/**
	 * Set the ok button title
	 */
	function setOkButtonTitle(name) {
		$okButton.html(name);
	};

	/**
	 * Set the ok button action
	 */
	function setOkButtonAction(action) {
		$okButton.unbind('click');
		$okButton.click(action);
	};

	/**
	 * Set the cancel button title
	 */
	function setCancelButtonTitle(name) {
		$cancelButton.html(name);
	};

	/**
	 * Set the cancel button action
	 */
	function setCancelButtonAction(action) {
		$cancelButton.unbind('click');
		$cancelButton.click(action);
	};

	/**
	 * Show the modal
	 */
	cls.show = function() {
		cls.carrousel.show(0, false);
		cls.$el.modal('show');
	};

	/**
	 * Hide the modal
	 */
	cls.hide = function() {
		cls.$el.modal('hide');
	};

	/**
	 * Configure the carrousel
	 * @returns the old configuration
	 */
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

	/**
	 * Load the contents of the modal (inside the carrousel)
	 * @param url the url to load from
	 * @param callback the callback passes to carrousel.load, optional
	 * @see KPS.carrousel
	 */
	cls.load = function(url, callback) {
		KPS.util.showLoadingBar("Loading. Please be patient...");
		cls.carrousel.load(url, function(children) {
			KPS.util.hideLoadingBar();
			if(callback) {
				callback(children);
			}
		});
	};
	
	$(document).ready(function() {
		document.body.appendChild(cls.el);
		$(".nav-collapse").click(function(){$(".btn-navbar").click();});
		cls.$el.modal({show:false, keyboard:true, backdrop: 'static'});
	});
} (KPS.modal, jQuery));

/**
 * KPS.data.locations
 * 
 * Location data utilities
 */
(function (cls, $, undefined)  {
	var locationList = [];
	var locationNames = [];
	var locationDataDirty = true;
	var location_popover = undefined;
	var location_popover_img = undefined;
	
	/**
	 * Mark that the location data needs update
	 */
	cls.setNeedsUpdate = function() {
		locationDataDirty = true;
	};

	/**
	 * Load the location data.
	 * Immeadiatly calls callback if the data is cached
	 * @param callback the callback to call on success
	 * 			The loaded location list is passed in as the first argument
	 */
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
				callback(locationList);
			}
		}
	};

	/**
	 * Setup all the location name autocomplete fields
	 */
	cls.setupPortEntryTypeahead = function(iterator) {
		$(".portEntry").each(function(index, child) {
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

	/**
	 * Check to see if a location exists
	 */
	cls.exists = function(name) {
		for(var n=0;n<locationList.length;n++) {
			if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
				return true;
			}
		}
		return false;
	};
	
	/**
	 * Get a location by name
	 * @return the location or null if not found
	 */
	cls.get = function(name) {
		for(var n=0;n<locationList.length;n++) {
			if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
				return locationList[n];
			}
		}
		return null;
	};

	/**
	 * Check if a location is international or not
	 * @return true/false or undefined if the location is not found
	 */
	cls.isInternational = function(name) {
		for(var n=0;n<locationList.length;n++) {
			if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
				return locationList[n].international;
			}
		}
		return undefined;
	};
	
	/**
	 * Get the entire location list
	 */
	cls.locationList = function() {
		return locationList;
	};
	
	/**
	 * Setup the location name map popover hover
	 */
	cls.setupLocationNameHover = function() {
		cls.load(function() {
			$(".location-name-hover").unbind('mouseover mouseout').hover(function(e) {
				// When a location name is hovered over
				
				// Calulate the position of the popover
				var pos = $(e.currentTarget).position();
				var width = $(e.currentTarget).width();
				var diff = $(e.currentTarget).height()/2-location_popover.height()/2-4;
				
				// Get the location
				var location = KPS.data.locations.get(e.currentTarget.innerHTML);
				
				// Position the popover and load the image
				location_popover_img.attr('src', KPS.util.map.staticUrl(location.latitude, location.longitude));
				location_popover.css({left: pos.left+width, top: pos.top+diff, display: 'block'});
			}, function(e) {
				// Unhover, hide the popover
				location_popover.css({display: 'none'});
			});
		});
	};
	
	$(document).ready(function() {
		// Create the popover
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

/**
 * KPS.data.carriers
 * 
 * Carrier data utilities
 */
(function (cls, $, undefined)  {
	var carrierList = [];
	
	/**
	 * Load the carrier list
	 * @param callback - optional callback, carrier list passed in as first argument
	 * @note data is cached
	 */
	cls.load = function(callback) {
		if(carrierList.length > 0) {
			if(callback) {
				callback(carrierList);
			}
		}
		else {
			$.get(KPS.siteRoot+"/event/carrier?list&format=json", function(data) {
				carrierList = eval(data);
				
				if(callback) {
					callback(carrierList);
				}
			});
		}
	};

	/**
	 * Setup the autocomplete fields on all .carrierEntry inputs
	 */
	cls.setupCarrierEntryTypeahead = function(iterator) {
		$(".carrierEntry").each(function(index, child) {
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

	/**
	 * Check to see if a carrier exists
	 * @return true/false
	 */
	cls.exists = function(name) {
		for(var n=0;n<carrierList.length;n++) {
			if(carrierList[n].toLowerCase() == name.toLowerCase()) {
				return true;
			}
		}
		return false;
	};
}(KPS.data.carriers, jQuery));

/**
 * KPS.data.format
 * 
 * Formatting utilities
 */
(function(cls, $, undefined) {
	/**
	 * Format a nice descriptive date
	 */
	cls.date = function(date) {
		return date.format("h:MMtt ddd dS mmm 'yy");
	};
	
	/**
	 * Format a short dd/mm/yy date
	 */
	cls.shortDate = function(date) {
		return date.format("dd/mm/yy");
	};
}(KPS.data.format, jQuery));