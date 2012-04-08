KPS.util = KPS.util || {};
KPS.modal = KPS.modal || {};
KPS.data = KPS.data || {};
KPS.data.locations = KPS.data.locations || {};

function performLogin(siteRoot,role){
	$.post(siteRoot+"/login", {role: role}, function(data) {
		KPS.util.redirect(siteRoot+"/dashboard");
	});
}

// KPS.util
(function (cls, $, undefined) {
	cls.redirect = function(url) {
		window.location = url;
	};

	cls.submitForm = function(form, url, callback) {
		var values = {};
		$(form).children("input, select").each(function(index, child) {
			values[child.name] = child.value;
		});
		$.post(url, values, function(data) {
			callback(data);
		});
	};
} (KPS.util, jQuery));

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
		this.currentPage = 0;
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
	
	pack.carrousel.prototype.show = function(num) {
		if(this.currentPage != num) {
			this.$body.animate({marginLeft:(num*-(this.width+this.spacing))+"px"},400);
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
			self.layout();
			callback(responseText, textStatus, XMLHttpRequest);
		});
	};
}(KPS, jQuery));

// KPS.modal
(function (cls, $, undefined) { // TODO: common alert area in modals?
	// TODO: create element in js?
//	var $el; // TODO: use

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
	
	$(document).ready(function() { // TODO: not needed
		cls.carrousel = new KPS.carrousel();
		cls.carrousel.width = 550;
		cls.carrousel.spacing = 30;
		cls.carrousel.setClips(false);
		cls.carrousel.layout();
		$("#emptyLayoutModalBody").append(cls.carrousel.el);
	});

	function setTitle(title) {
		$("#emptyLayoutModalTitle").html(title);
	};

	function setOkButtonTitle(name) {
		$("#emptyLayoutModalSubmit").html(name);
	};

	function setOkButtonAction(action) {
		$("#emptyLayoutModalSubmit").unbind('click');
		$("#emptyLayoutModalSubmit").click(action);
	};

	function setCancelButtonTitle(name) {
		$("#emptyLayoutModalCancel").html(name);
	};

	function setCancelButtonAction(action) {
		$("#emptyLayoutModalCancel").unbind('click');
		$("#emptyLayoutModalCancel").click(action);
	};

	cls.show = function() {
		$('#emptyLayoutModal').modal('show');
	};

	cls.hide = function() {
		$('#emptyLayoutModal').modal('hide');
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
		$('#emptyLayoutModal').modal({show:false, keyboard:true, backdrop: 'static'});
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

				callback();
			});
		}
		else {
			callback();
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

	cls.isInternational = function(name) {
		for(var n=0;n<locationList.length;n++) {
			if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
				return locationList[n].international;
			}
		}
		return undefined;
	};
}(KPS.data.locations, jQuery));