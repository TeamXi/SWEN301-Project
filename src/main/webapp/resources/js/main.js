KPS.util = KPS.util || {};
KPS.util.map = KPS.util.map || {};
KPS.modal = KPS.modal || {};
KPS.data = KPS.data || {};
KPS.data.locations = KPS.data.locations || {};
KPS.data.carriers = KPS.data.carriers || {};
KPS.data.format = KPS.data.format || {};

$(document).ajaxError(function(){
	alert("There was an error communicating with the server");
});

function performLogin(siteRoot,role){
	$.post(siteRoot+"/login", {role: role}, function(data) {
		KPS.util.redirect(siteRoot+"/dashboard");
	});
}
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
} (KPS.util, jQuery));

(function(cls, $, undefined) {
	$(document).ready(function() {
		var popover = $('<div class="popover fade right in" style="display: none;">'+
				'<div class="arrow"></div>'+
				'<div class="popover-inner">'+
					'<img class="location-name-hover-map-image"/>'+
				'</div>'+
			'</div>');
		var img = popover.find(".location-name-hover-map-image");
		$(document.body).append(popover);
		
		$(".location-name-hover").hover(function(e) {
			var pos = $(e.currentTarget).position();
			var width = $(e.currentTarget).width();
			var diff = $(e.currentTarget).height()/2-popover.height()/2-4;
			var location = KPS.data.locations.get(e.currentTarget.innerHTML);
			img.attr('src', KPS.util.map.staticUrl(location.latitude, location.longitude));
			popover.css({left: pos.left+width, top: pos.top+diff, display: 'block'});
		}, function(e) {
			if(popover.has(e.relatedTarget).length > 0) {
				e.stopPropagation();
				e.preventDefault();
			}
			else {
				popover.css({display: 'none'});
			}
		});
	});
	
	cls.staticUrl = function(lat, lng) {
		//http://maps.googleapis.com/maps/api/staticmap?center=-4.434044,136.40625&zoom=4&format=png&sensor=false&size=640x480&maptype=roadmap&style=visibility:off&style=feature:administrative.country|visibility:on&style=feature:administrative.locality|visibility:on&style=feature:water|element:geometry|visibility:on&style=feature:water|invert_lightness:true|hue:0x0066ff
		//http://maps.googleapis.com/maps/api/staticmap?zoom=6&format=png&sensor=false&size=250x250&maptype=roadmap&style=visibility:off&style=feature:administrative.country|visibility:on&style=feature:administrative.locality|visibility:on&style=feature:water|element:geometry|visibility:on&style=feature:water|invert_lightness:true|hue:0x0066ff&markers=color:red|-41,173
		
		return "http://maps.googleapis.com/maps/api/staticmap?zoom=6&format=png&sensor=false&size=250x250&maptype=roadmap&style=visibility:off&style=feature:administrative.country|visibility:on&style=feature:administrative.locality|visibility:on&style=feature:water|element:geometry|visibility:on&style=feature:water|invert_lightness:true|hue:0x0066ff&markers=color:red|"+lat+","+lng;
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
}(KPS.util.map, jQuery));

function show(obj){
	alert(JSON.stringify(obj, null, "\t"));
}

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
	
	pack.carrousel.prototype.show = function(num, animated) {
		this.$body.children().css({height: 'auto'});
		// Make sure modal is visible before getting height, parent might be invisible so .height() doesn't work all the time.
		var child = $(this.$body.children()[num]);
		// Add it to the body
		$(document.body).append(child);
		var height = child.height();
		// Insert it back at the same location
		var next = this.$body.children()[num];
		if(next) {
			child.insertBefore(next);
		} else {
			this.$body.append(child);
		}
		// Set height of all carrousel items so that the scrollbar displays correctly.
		this.$body.children().animate({height: height+'px'});
		
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
				callback(responseText, textStatus, XMLHttpRequest);
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
		return date.format("h:Mtt ddd dS mmm 'yy");
	};
}(KPS.data.format, jQuery));